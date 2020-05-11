package pl.mbierut.clients;

import okhttp3.*;
import pl.mbierut.models.Currency;

import java.io.IOException;

public class CurrencyRestClient {
    private final String URL = "https://api.nbp.pl/api/exchangerates/rates/c/";


    double getCurrencyExchangeRate(Currency currency) throws IOException {
        if (Currency.PLN.equals(currency)){
            return 1.0;
        }

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL + currency.toString() + "/today/").newBuilder();

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        ResponseBody responseBody = call.execute().body();

    }

}
