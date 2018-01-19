package View;

import Model.Stocks.Stock;

public interface ViewEventListener {
    void onStockSelection(Stock stock);
}
