package pl.mbierut;

public class Main {

    public static void main(String[] args) {
        TransactionService service = new TransactionService();

        User user1 = new User("One", "one@one.com", "1");
        user1.getWallet().getCurrencies().put(Currency.PLN, 100.0);
        user1.getWallet().getCurrencies().put(Currency.AUD, 10.0);
        user1.getWallet().getCurrencies().put(Currency.CAD, 35.1);
        user1.getWallet().getCurrencies().put(Currency.JPY, 995.0);
        user1.getWallet().getCurrencies().put(Currency.SEK, 3.5);


        Order order1 = new Order(Currency.PLN, Currency.USD, 5.0,15.0);
        System.out.println(service.fulfillOrder(order1, user1.getWallet()));
        System.out.println(user1.getWallet().getCurrencies().get(Currency.PLN));
        System.out.println(user1.getWallet().getCurrencies().get(Currency.USD));
        System.out.println(user1.getWallet());



    }
}
