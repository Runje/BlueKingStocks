package View;

import Model.Stocks.Stock;
import Model.Stocks.StockCandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BlueKingStocksView extends JFrame implements BlueKingView {
    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
    private HeaderView header;
    private ViewEventListener eventListener;
    private JfreeCandlestickChart candleChart;

    public BlueKingStocksView() {
        super("BlueKingStocks");
    }

    @Override
    public void start() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the chart.
        candleChart = new JfreeCandlestickChart("Blue King Stocks");
        //new FxMarketPxFeeder(candleChart,  2).run();
        getContentPane().add(candleChart, BorderLayout.CENTER);
        header = new HeaderView();
        getContentPane().add(header, BorderLayout.NORTH);

        //Disable the resizing feature
        setResizable(false);
        //Display the window.
        pack();
        setVisible(true);
        header.setOnStockSelection(new OnStockSelection() {
            @Override
            public void onStockSelection(Stock stock) {
                if (eventListener != null) {
                    eventListener.onStockSelection(stock);
                }
            }
        });
    }

    public void setEventListener(ViewEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void showStockCandles(StockCandles stockCandles) {
        candleChart.showCandles(stockCandles);
    }

    @Override
    public void showStocks(List<Stock> stocksList) {
        header.setStocks(stocksList.toArray(new Stock[stocksList.size()]));
    }
}
