package pl.mbierut.models.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
public class TableDTO {
    public String table;
    public String no;
    public String tradingDate;
    public String effectiveDate;
    public TableRateDTO[] rates;
}
