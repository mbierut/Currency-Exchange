package pl.mbierut.database.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mbierut.exceptions.InsufficientFundsException;
import pl.mbierut.models.Funds;
import pl.mbierut.models.enums.BuyOrSell;
import pl.mbierut.models.enums.Currency;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    private long id;

    @Column(name = "username")
    @Pattern(regexp="^[a-zA-ZąĄćĆółŁńŃóÓśŚźŹŻŹ]{1,}", message="must be valid user name")
    private String username;

    @Column(name = "email")
    @Pattern(regexp="\\S+@\\S*\\.\\S+", message="must be valid email address")
    private String email;

    @Column(name = "password")
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message="must contain at least 8 characters including lower, uppercase, a number and a special character")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WalletEntryEntity> walletEntries;

    public UserEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.walletEntries = new ArrayList<>();
    }

    public void fulfillOrder(OrderEntity order) throws InsufficientFundsException {
        if (order.getBuyOrSell().equals(BuyOrSell.sell)) {
            this.sellCurrency(order);
        } else if (order.getBuyOrSell().equals(BuyOrSell.buy)) {
            this.buyCurrency(order);
        }
    }

    private void sellCurrency(OrderEntity order) throws InsufficientFundsException {
        Funds fundsSold = this.findCurrencyInWallet(order.getFundsToBuyOrSell().getCurrency()).getFunds();
        Funds fundsAddedTo = this.findCurrencyInWallet(order.getOtherCurrencyToSellOrBuy()).getFunds();
        double curSubtractedFrom = fundsSold.getAmount();
        if (curSubtractedFrom - order.getFundsToBuyOrSell().getAmount() < 0.0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        fundsSold.setAmount(curSubtractedFrom - order.getFundsToBuyOrSell().getAmount());
        fundsAddedTo.setAmount(fundsAddedTo.getAmount() + order.getFundsValueInOtherCurrency());
    }

    private void buyCurrency(OrderEntity order) throws InsufficientFundsException {
        Funds fundsAddedTo = this.findCurrencyInWallet(order.getFundsToBuyOrSell().getCurrency()).getFunds();
        Funds fundsSold = this.findCurrencyInWallet(order.getOtherCurrencyToSellOrBuy()).getFunds();
        double curSubtractedFrom = fundsSold.getAmount();
        if (curSubtractedFrom - order.getFundsValueInOtherCurrency() < 0.0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        fundsSold.setAmount(curSubtractedFrom - order.getFundsValueInOtherCurrency());
        fundsAddedTo.setAmount(fundsAddedTo.getAmount() + order.getFundsToBuyOrSell().getAmount());
    }

    public void sendMoney(UserEntity user, Funds funds) throws InsufficientFundsException {
        Funds baseFunds = this.findCurrencyInWallet(funds.getCurrency()).getFunds();
        if (baseFunds.getAmount() - funds.getAmount() < 0.0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        user.addFunds(funds);
        baseFunds.setAmount(baseFunds.getAmount() - funds.getAmount());
    }

    public void addFunds(Funds addedFunds) {
        Funds baseFunds = this.findCurrencyInWallet(addedFunds.getCurrency()).getFunds();
        baseFunds.setAmount(baseFunds.getAmount() + addedFunds.getAmount());
    }

    public void withdrawFunds(Funds funds) throws InsufficientFundsException {
        Funds baseFunds = this.findCurrencyInWallet(funds.getCurrency()).getFunds();
        if (baseFunds.getAmount() - funds.getAmount() < 0.0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        baseFunds.setAmount(baseFunds.getAmount() - funds.getAmount());
    }

    public WalletEntryEntity findCurrencyInWallet(Currency currency) {
        for (WalletEntryEntity entry : this.walletEntries) {
            if (entry.getFunds().getCurrency().equals(currency)) {
                return entry;
            }
        }
        WalletEntryEntity walletEntry = new WalletEntryEntity(new Funds(currency, 0.0));

        walletEntry.setUser(this);
        this.walletEntries.add(walletEntry);
        return walletEntry;
    }


}
