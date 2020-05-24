package pl.mbierut.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import pl.mbierut.models.Currency;
import pl.mbierut.JSON.CurrencyJSON;

import java.io.IOException;

public class CurrencyRestClient {
    private final String URL = "https://api.nbp.pl/api/exchangerates/rates/c/";

    public double getSellRate(Currency currency) throws IOException {
        return this.getCurrencyExchangeRate(currency, true);
    }

    public double getBuyRate(Currency currency) throws IOException {
        return this.getCurrencyExchangeRate(currency, false);
    }

    private double getCurrencyExchangeRate(Currency currency, boolean sell) throws IOException {
        if (Currency.PLN.equals(currency)) {
            return 1.0;
        }

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL + currency.toString()).newBuilder();

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseBody responseBody = client.newCall(request).execute().body();
        CurrencyJSON currencyJSON = objectMapper.readValue(responseBody.string(), CurrencyJSON.class);

        if (sell) {
            return currencyJSON.getRates()[0].getBid();
        }
        return currencyJSON.getRates()[0].getAsk();
    }

}
