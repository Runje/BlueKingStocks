package View;

import Model.Stocks.Stock;
import Model.Stocks.StockCandles;

import java.util.List;

public interface BlueKingView {

    void start();
    void showStocks(List<Stock> stocksList);
    void setEventListener(ViewEventListener eventListener);

    void showStockCandles(StockCandles stockCandles);
}
