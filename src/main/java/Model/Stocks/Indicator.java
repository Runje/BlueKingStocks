package Model.Stocks;

/**
 * Created by Thomas on 11.08.2017.
 */

public class Indicator {
    public Indicator() {}
    public Indicator(double value)
    {
        Value = value;
    }
    //[JsonProperty("SMA")]
    public double Value;
}
