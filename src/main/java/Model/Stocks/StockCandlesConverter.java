package Model.Stocks;

import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.Genson;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class StockCandlesConverter implements Converter<StockCandles> {
    @Override
    public void serialize(StockCandles stockCandles, ObjectWriter objectWriter, Context context) throws Exception {
        // don't care
        context.genson.serialize(stockCandles, objectWriter, context);
    }

    @Override
    public StockCandles deserialize(ObjectReader objectReader, Context context) throws Exception {
        StockCandles result = new StockCandles();

        objectReader.beginObject();

        Candle lastCandle = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime lastDate = null;
        String timeZone= null;
        while(objectReader.hasNext()) {
            objectReader.next();
            String name = objectReader.name();

            if (name == null) {
                continue;
            }
            if (name.contains("Symbol")) {
                result.metaData.symbol = objectReader.valueAsString();
            } else if (name.contains("Information")) {
                result.metaData.information = objectReader.valueAsString();
            } else if (name.contains("Last Refreshed")) {
                String date = objectReader.valueAsString();
                result.metaData.lastRefreshed = LocalDateTime.parse(date, formatter);
            } else if (name.contains("Interval")) {
                String interval = objectReader.valueAsString();
                result.metaData.interval = Integer.parseInt(interval.substring(0,interval.length() - 3));
            } else if (name.contains("Output Size")) {
                // skip value
                objectReader.skipValue();

            } else if (name.contains("Time Zone")) {
                // skip value
                timeZone = objectReader.valueAsString();
                objectReader.endObject();
            } else if (name.contains("Time Series")) {
                result.candles = new ArrayList<>();
                objectReader.beginObject();
            } else if (name.contains("Meta Data")) {
                result.metaData = new MetaData();
                objectReader.beginObject();
            } else if (name.contains("open")) {
                lastCandle.open = Float.parseFloat(objectReader.valueAsString());
            } else if (name.contains("high")) {
                lastCandle.high = Float.parseFloat(objectReader.valueAsString());
            } else if (name.contains("low")) {
                lastCandle.low = Float.parseFloat(objectReader.valueAsString());
            } else if (name.contains("close")) {
                lastCandle.close = Float.parseFloat(objectReader.valueAsString());
            } else if (name.contains("volume")) {
                lastCandle.volume = objectReader.valueAsInt();
                objectReader.endObject();

            } else
            {
                // must be a date

                // add old candle to hashmap
                if (lastCandle != null)
                {
                    result.candles.add(lastCandle);
                }

                lastCandle = new Candle();
                LocalDateTime localDateTime = LocalDateTime.parse(name, formatter);
                lastCandle.dateTime = ZonedDateTime.of(localDateTime, ZoneId.of(timeZone));
                objectReader.beginObject();
            }
        }

        // add last candle to hashmap
        if (lastCandle != null)
        {
            result.candles.add(lastCandle);
        }
        objectReader.endObject();
        objectReader.endObject();
        return result;
    }
}
