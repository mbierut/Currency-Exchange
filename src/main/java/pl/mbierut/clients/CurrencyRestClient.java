package pl.mbierut.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import pl.mbierut.models.dto.TableDTO;
import pl.mbierut.models.dto.TableRateDTO;
import pl.mbierut.models.enums.Currency;
import pl.mbierut.models.dto.CurrencyDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyRestClient {
    private static final String URL_RATES = "https://api.nbp.pl/api/exchangerates/rates/c/";
    private static final String URL_TABLES = "https://api.nbp.pl/api/exchangerates/tables/c";

    public double getSellRate(Currency currency) throws IOException {
        return this.getCurrencyExchangeRate(currency, true);
    }

    public double getBuyRate(Currency currency) throws IOException {
        return this.getCurrencyExchangeRate(currency, false);
    }

    public List<String[]> getExchangeRatesTable() throws IOException{

        List<String[]> listOfCurrencies = new ArrayList<>();
        TableRateDTO[] rates = this.getAllExchangeRates();
        for (TableRateDTO rate : rates) {
            String[] temp = {rate.getCode(), rate.getCurrency(),
                    Double.toString(rate.getBid()), Double.toString(rate.getAsk())};
            listOfCurrencies.add(temp);
        }
        return listOfCurrencies;
    }

    private double getCurrencyExchangeRate(Currency currency, boolean sell) throws IOException {
        if (Currency.PLN.equals(currency)) {
            return 1.0;
        }

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL_RATES + currency.toString()).newBuilder();

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

    private TableRateDTO[] getAllExchangeRates() throws IOException {

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL_TABLES).newBuilder();

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseBody responseBody = client.newCall(request).execute().body();
        TableDTO[] tableDTO = objectMapper.readValue(responseBody.string(), TableDTO[].class);

        return tableDTO[0].getRates();

    }

}
