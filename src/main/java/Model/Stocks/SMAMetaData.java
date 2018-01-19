package Model.Stocks;

/**
 * Created by Thomas on 11.08.2017.
 */

public class SMAMetaData
{
    //[JsonProperty("1: Symbol")]
    public String Symbol;
    //[JsonProperty("2: Indicator")]
    public String Indicator;
    //[JsonProperty("3: Last Refreshed")]
    public String LastRefreshed;
    //[JsonProperty("4: Interval")]
    public String Interval;
    //[JsonProperty("5: Time Period")]
    public String TimePeriod;
    //[JsonProperty("6: Series Type")]
    public String SeriesType;
    //[JsonProperty("7: Time Zone")]
    public String TimeZone;

    public SMAMetaData() {}
    public SMAMetaData(String symbol, String indicator, String lastRefreshed, String interval, String timePeriod, String seriesType, String timeZone)
    {
        Symbol = symbol;
        Indicator = indicator;
        LastRefreshed = lastRefreshed;
        Interval = interval;
        TimePeriod = timePeriod;
        SeriesType = seriesType;
        TimeZone = timeZone;
    }
}