package Model.Stocks;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

import java.util.Map;

/**
 * Created by Thomas on 11.08.2017.
 */

public class SMA {
        //@JsonProperty(name="Meta Data")
        public SMAMetaData MetaData;
        //[JsonProperty("Technical Analysis: SMA")]
        public Map<String, Indicator> TechnicalAnalysis;

        public SMA() {}
        public SMA(String symbol, String indicator, String lastRefreshed, String interval, String timePeriod, String seriesType, String timeZone, Map<String, Indicator> technicalAnalysis)
        {
            MetaData = new SMAMetaData(symbol, indicator, lastRefreshed, interval, timePeriod, seriesType, timeZone);
            TechnicalAnalysis = technicalAnalysis;
        }

    public static SMA fromJSON(String json) {
        return getGenson().deserialize(json, SMA.class);
    }

    public static Genson getGenson()
        {
            return new GensonBuilder()
                    .rename("MetaData", "Meta Data")
                    .rename("Symbol", "1: Symbol")
                    .rename("Indicator", "2: Indicator")
                    .rename("LastRefreshed", "3: Last Refreshed")
                    .rename("Interval", "4: Interval")
                    .rename("TimePeriod", "5: Time Period")
                    .rename("SeriesType", "6: Series Type")
                    .rename("TimeZone", "7: Time Zone")
                    .rename("TechnicalAnalysis", "Technical Analysis: SMA")
                    .rename("value", "SMA")
                    .useIndentation(true)
                    .create();
        }
    }




