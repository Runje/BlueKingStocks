package Model.Database;

import Model.Stocks.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class Database {
    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private Connection connection;

    public Database(Connection connection) {

        this.connection = connection;
    }

    public void start() throws SQLException
    {
        try (Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            if (!tableExists(StockDatabase.TABLE)) {
                logger.info("Creating Stock Table");
                StockDatabase.createTable(statement);
                //StockDatabase.add(new Stock("GOOGL", "Alphabet A (ex Google) Aktie", "US02079K3059", "A14Y6F"), connection);
            }
        }
        //statement.close();
    }

    public boolean tableExists(String table) throws SQLException
    {
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, table, null);
        if (tables.next()) {
            // Table exists
            return true;
        }
        else {
            // Table does not exist
            return false;
        }
    }

    public void updateListedCompanies(List<Stock> stockList) throws SQLException {
        clearTable(StockDatabase.TABLE);
        // TEST
        List<Stock> stocks = StockDatabase.getAll(connection);
        logger.info("Stock in DB: " + stocks.size());
        for (Stock stock    :stocks
             ) {
            logger.info(stock.toString());
        }

        for(Stock stock: stockList) {
            try {
                logger.info("Adding stock: " + stock.toString());
                StockDatabase.add(stock, connection);
            } catch (SQLException e)
            {
                logger.error("Couldn't add stock: " + stock.toString() + ", error: " + e.getMessage());
            }
        }

        //StockDatabase.addStocks(stockList, connection);
    }

    private void clearTable(String table) throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM " + table);
        }



    }

    public List<Stock> getAllStocks() throws SQLException {
        return StockDatabase.getAll(connection);
    }
}
