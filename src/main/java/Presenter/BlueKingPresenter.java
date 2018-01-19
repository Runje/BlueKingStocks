package Presenter;

import Model.Stocks.Stock;
import Model.Stocks.StockCandles;
import View.BlueKingView;
import View.ViewEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BlueKingPresenter implements ViewEventListener {
    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
    private final BlueKingView view;

    public void setEventListener(BlueKingEventListener eventListener) {
        this.eventListener = eventListener;
    }

    private BlueKingEventListener eventListener;

    public BlueKingPresenter(BlueKingView view) {
        this.view = view;
        view.setEventListener(this);
    }

    public void showStockList(List<Stock> stockList) {
        view.showStocks(stockList);
    }

    @Override
    public void onStockSelection(Stock stock) {
        if (eventListener != null) {
            eventListener.onStockSelection(stock);
        }
    }

    public void showStockCandles(StockCandles stockCandles) {
        view.showStockCandles(stockCandles);
    }
}
