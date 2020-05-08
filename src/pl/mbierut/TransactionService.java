package pl.mbierut;

public class TransactionService {
    public String fulfillOrder(Order order, Wallet wallet){

        if (order.getRate() <= 0){
            return "Rate non-positive";
        }

        if (!wallet.getCurrencies().containsKey(order.getCurrencySell())
                || order.getAmountToSell() > wallet.getCurrencies().get(order.getCurrencySell())){
            return "Insufficient funds";
        }
        wallet.getCurrencies().merge(order.getCurrencyBuy(), order.getAmountToSell()*order.getRate(), Double::sum);
        double curSubtractedFrom = wallet.getCurrencies().get(order.getCurrencySell());
        wallet.getCurrencies().put(order.getCurrencySell(), curSubtractedFrom - order.getAmountToSell());

        return "Transaction successful";
    }

}