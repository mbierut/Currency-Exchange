package pl.mbierut.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RateJSON{
    public String number;
    public String effectiveDate;
    public double bid;
    public double ask;


}