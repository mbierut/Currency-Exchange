package pl.mbierut.models.enums;

import pl.mbierut.clients.CurrencyRestClient;

import java.io.IOException;

public enum Currency {
    PLN, USD, AUD, CAD, EUR, HUF, CHF, GBP, JPY, CZK, DKK, NOK, SEK, XDR;

    public double getSellRate() {
        CurrencyRestClient client = new CurrencyRestClient();
        try {
            return client.getSellRate(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1.0;
    }

    public double getBuyRate() {
        CurrencyRestClient client = new CurrencyRestClient();
        try {
            return client.getBuyRate(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1.0;
    }
}