package Model.Stocks;


import com.owlike.genson.Genson;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class StocksAPITest {

    @Test
    public void jsonTest() throws Exception {
        HashMap<String, Indicator> dic = new HashMap<String, Indicator>();
        dic.put("KEY1", new Indicator(0.12));
        dic.put("KEY2", new Indicator(9.2143));
        SMA ans = new SMA("symbol", "indicator", "last refreshed", "interval", "timePeriod", "seriesType", "timeZone", dic);
        Genson genson = SMA.getGenson();
        String json = genson.serialize(ans);
        // just to see output
        Assert.assertNotEquals("", json);

        SMA ans3 = genson.deserialize(json, SMA.class);
        String realJson = "{" +
                "\"Meta Data\": {" +
                "\"1: Symbol\": \"MSFT\"," +
                "\"2: Indicator\": \"Simple Moving Average (SMA)\"," +
                "\"3: Last Refreshed\": \"2017-08-04 16:00:00\"," +
                "\"4: Interval\": \"15min\"," +
                "\"5: Time Period\": 10," +
                "\"6: Series Type\": \"close\"," +
                "\"7: Time Zone\": \"US/Eastern\"" +
                "}," +
                "\"Technical Analysis: SMA\": {" +
                "\"2017-08-04 16:00\": {" +
                "\"SMA\": \"72.5811\"" +
                "}," +
                "\"2017-08-04 15:45\": {" +
                "\"SMA\": \"72.5726\"" +
                "}," +
                "\"2017-08-04 15:30\": {" +
                "\"SMA\": \"72.5696\"" +
                "}}}";


        SMA ans2 = genson.deserialize(realJson, SMA.class);

        //SMA result = StocksAPI.getIntradayStock();
        // just to see
        Assert.assertNotEquals(ans, ans2);


    }

    @Test
    public void jsonConverter() throws Exception {
        String realJson = "{ \"Meta Data\": {" +
            "\"1. Information\": \"Intraday (1min) prices and volumes\","+
                    "\"2. Symbol\": \"GOOGL\","+
                    "\"3. Last Refreshed\": \"2017-08-11 16:00:00\","+
                    "\"4. Interval\": \"1min\","+
                    "\"5. Output Size\": \"Compac\t\","+
                    "\"6. Time Zone\": \"US/Eastern\""+
        "},"+
        "\"Time Series (1min)\": {"+
            "\"2017-08-11 16:00:00\": {"+
                "\"1. open\": \"929.7800\","+
                        "\"2. high\": \"930.4900\","+
                        "\"3. low\": \"929.4300\","+
                        "\"4. close\": \"930.0900\","+
                        "\"5. volume\": \"68359\""+
            "},"+
            "\"2017-08-11 15:59:00\": {"+
                "\"1. open\": \"930.4200\","+
                        "\"2. high\": \"930.4500\","+
               "         \"3. low\": \"929.6100\","+
              "          \"4. close\": \"929.8700\","+
             "           \"5. volume\": \"10004\""+
            "}}}";
        StockCandles candles = StockCandles.getGenson().deserialize(realJson, StockCandles.class);
        Assert.assertNotEquals(1,2);
    }

}
