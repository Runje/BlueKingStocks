package Model.Stocks;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class StockCandles {
    public MetaData metaData;

    public List<Candle> candles;

    public static Genson getGenson()
    {
        return new GensonBuilder().withConverter(new StockCandlesConverter(),  StockCandles.class).create();
    }

    public static StockCandles fromJSON(String result) {
        return getGenson().deserialize(result, StockCandles.class);
    }
}
