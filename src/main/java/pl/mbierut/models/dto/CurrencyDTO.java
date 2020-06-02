package pl.mbierut.models.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDTO {
    public String table;
    public String currency;
    public String code;
    public RateDTO[] rates;


}
