package pl.mbierut.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableRateDTO {
    public String currency;
    public String code;
    public double bid;
    public double ask;
}
