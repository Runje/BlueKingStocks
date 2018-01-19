package Model.Stocks;

import java.time.LocalDateTime;

public class MetaData {

    public String information;
    public String symbol;
    public LocalDateTime lastRefreshed;

    /**
     * In minutes
     */
    public int interval;

    public MetaData(String information, String symbol, LocalDateTime lastRefreshed, int interval) {
        this.information = information;
        this.symbol = symbol;
        this.lastRefreshed = lastRefreshed;
        this.interval = interval;
    }

    public MetaData() {

    }
}
