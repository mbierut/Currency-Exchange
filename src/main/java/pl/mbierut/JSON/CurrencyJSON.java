package pl.mbierut.JSON;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyJSON {
    public String table;
    public String currency;
    public String code;
    public RateJSON[] rates;


}
