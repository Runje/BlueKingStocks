package Model.Database;

import Model.Stocks.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDatabase {
    private static Logger logger = LoggerFactory.getLogger("StockDatabase");

    public static final String TABLE = "Stocks";
    public static final String SYMBOL = "symbol";
    public static final String FULL_NAME = "full_name";
    public static final String ISIN = "isin";
    public static final String WKN = "wkn";
    public static final String MarketCap = "market_cap";
    public static final String IPOYear = "ipo_year";
    public static final String Sector = "sector";
    public static final String Industry = "Industry";
    public static final String Market = "market";
    public static final String CREATE = "CREATE TABLE " + TABLE + " (" +
            SYMBOL + " STRING PRIMARY KEY, " +
            FULL_NAME + " TEXT, " +
            ISIN + " TEXT, " +
            WKN + " TEXT " +
            ");";

    public static void createTable(Statement statement) throws SQLException {
        statement.executeUpdate(CREATE);
    }

    public static void add(Stock stock, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into " + TABLE + " values(?,?,?,?)");
        ps.setString(1, stock.getSymbol());
        ps.setString(2, stock.getFullName());
        ps.setString(3, stock.getIsin());
        ps.setString(4, stock.getWkn());
        ps.executeUpdate();
        logger.info("Added Stock " + stock.toString());
    }

    public static void addStocks(List<Stock> stockList, Connection connection) throws SQLException {
        connection.setAutoCommit(false);

        try (PreparedStatement ps = connection.prepareStatement("insert into " + TABLE + " values(?,?,?,?)")) {
            for (int i = 0; i < stockList.size(); i++) {
                Stock stock = stockList.get(i);
                insertStockValuesToPs(stock, ps);
                ps.addBatch();
            }

            ps.executeBatch();
            logger.info("Added stocks: " + stockList.size());
        }
    }

    private static void insertStockValuesToPs(Stock stock, PreparedStatement ps) throws SQLException {
        ps.setString(1, stock.getSymbol());
        ps.setString(2, stock.getFullName());
        ps.setString(3, stock.getIsin());
        ps.setString(4, stock.getWkn());
    }

    public static List<Stock> getAll(Connection connection) throws SQLException {
        String query = "SELECT * FROM " + TABLE;
        try (Statement ps = connection.createStatement()) {
            ResultSet resultSet = ps.executeQuery(query);
            List<Stock> stocks = new ArrayList<>();
            while (resultSet.next()) {
                stocks.add(resultToStock(resultSet));
            }

            return stocks;
        }
    }

    private static Stock resultToStock(ResultSet rs) throws SQLException {
        String symbol = rs.getString(1);
        String name = rs.getString(2);
        String isin = rs.getString(3);
        String wkn = rs.getString(4);
        return new Stock(symbol, name, isin, wkn);
    }
}
