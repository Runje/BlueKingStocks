package Model.Stocks;

public class Stock {
    private String symbol;
    private String fullName;
    private String isin;
    private String wkn;

    public Stock(String symbol, String fullName, String isin, String wkn) {
        this.symbol = symbol;
        this.fullName = fullName;
        this.isin = isin;
        this.wkn = wkn;
    }

    public Stock() {
        symbol = "";
        wkn = "";
        isin = "";
        fullName = "";
    }

    public String getSymbol() {
        return symbol;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIsin() {
        return isin;
    }

    public String getWkn() {
        return wkn;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", fullName='" + fullName + '\'' +
                ", isin='" + isin + '\'' +
                ", wkn='" + wkn + '\'' +
                '}';
    }
}
