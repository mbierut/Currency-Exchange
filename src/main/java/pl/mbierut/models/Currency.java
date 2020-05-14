package pl.mbierut.models;

import pl.mbierut.clients.CurrencyRestClient;

import java.io.IOException;

public enum Currency {
    PLN, USD, AUD, CAD, EUR, HUF, CHF, GBP, JPY, CZK, DKK, NOK, SEK, XDR;

    public double getRate(){
        CurrencyRestClient client = new CurrencyRestClient();
        try {
            return client.getCurrencyExchangeRate(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1.0;
    }
}