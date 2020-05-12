package pl.mbierut.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import pl.mbierut.models.Currency;
import pl.mbierut.JSON.CurrencyJSON;

import java.io.IOException;

public class CurrencyRestClient {
    private final String URL = "https://api.nbp.pl/api/exchangerates/rates/c/";


    public double getCurrencyExchangeRate(Currency currency) throws IOException {
        if (Currency.PLN.equals(currency)){
            return 1.0;
        }

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL + currency.toString() + "/today/").newBuilder();

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseBody responseBody = client.newCall(request).execute().body();
        CurrencyJSON currencyJSON = objectMapper.readValue(responseBody.string(), CurrencyJSON.class);

        return currencyJSON.getRates()[0].getBid() * currencyJSON.getRates()[0].getAsk() / 2.0;

    }

}
