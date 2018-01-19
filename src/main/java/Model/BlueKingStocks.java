package Model;

import Model.Communication.Interval;
import Model.Communication.OutputSize;
import Model.Communication.StocksAPI;
import Model.Database.Database;
import Model.Database.StockDatabase;
import Model.Stocks.Stock;
import Model.Stocks.StockCandles;
import Presenter.BlueKingEventListener;
import Presenter.BlueKingPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlueKingStocks implements BlueKingEventListener {
    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
    private static final String DB_NAME = "bluekingstocks.sql";
    private final BlueKingPresenter presenter;
    private Database database;

    public BlueKingStocks(BlueKingPresenter presenter) {
        this.presenter = presenter;
        presenter.setEventListener(this);
    }
    public void start() throws SQLException {
        // create a database connection
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);
        database = new Database(connection);
        database.start();
        List<Stock> allStocks = database.getAllStocks();
        logger.info("There are " + allStocks.size() + " Stocks in the database");
        presenter.showStockList(allStocks);

    }

    public void updateXetra() throws IOException, SQLException {
        List<Stock> stockList = StocksAPI.getListedCompaniesFromXetra();
        database.updateListedCompanies(stockList);
    }

    @Override
    public void onStockSelection(Stock stock) {
        try {
            StockCandles stockCandles = StocksAPI.getIntradayStock(stock.getSymbol(), Interval.MIN1, OutputSize.COMPACT);
            presenter.showStockCandles(stockCandles);

        } catch (IOException e) {
            logger.error("Couldn't get information for " + stock);
            e.printStackTrace();
        }

    }
}
