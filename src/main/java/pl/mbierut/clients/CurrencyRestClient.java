package pl.mbierut.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import pl.mbierut.models.enums.Currency;
import pl.mbierut.models.dto.CurrencyDTO;

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
        CurrencyDTO currencyDTO = objectMapper.readValue(responseBody.string(), CurrencyDTO.class);

        if (sell) {
            return currencyDTO.getRates()[0].getBid();
        }
        return currencyDTO.getRates()[0].getAsk();
    }

}
