package Model;

import Model.Communication.Interval;
import Model.Communication.OutputSize;
import Model.Stocks.Candle;
import Model.Stocks.StockCandles;
import Model.Communication.StocksAPI;
import View.JfreeCandlestickChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * The Class FxMarketPxFeeder.
 * 
 * @author ashraf
 */
public class FxMarketPxFeeder {
    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
	private JfreeCandlestickChart jfreeCandlestickChart;
	private int simulationTime;
	private ExecutorService executorService;
	
	public FxMarketPxFeeder(JfreeCandlestickChart jfreeCandlestickChart, int simulationTime) {
		super();
		this.executorService = Executors.newCachedThreadPool();
		this.jfreeCandlestickChart = jfreeCandlestickChart;
		this.simulationTime = simulationTime;
	}

	public void run() {
		executorService.execute(() -> read());
	}

	private void read() {
        try {
            StockCandles stockCandles = StocksAPI.getIntradayStock("GOOGL", Interval.MIN1, OutputSize.COMPACT);
            List<Candle> candleList = stockCandles.candles;
            for (Candle candle :
                    candleList) {
                Thread.sleep(simulationTime);
                jfreeCandlestickChart.addCandle(candle);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            executorService.shutdown();
        }



    }


}
