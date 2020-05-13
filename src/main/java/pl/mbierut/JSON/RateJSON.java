package pl.mbierut.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateJSON{
    public String no;
    public String effectiveDate;
    public double bid;
    public double ask;


}