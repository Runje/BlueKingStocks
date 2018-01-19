package View;

import Model.Stocks.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class HeaderView extends JPanel {
    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
    private OnStockSelection onStockSelection;
    private final JList list;
    private Stock[] stocks;
    public HeaderView() {
        super();
        this.add(new Label("NORTH"));
        /**stocks.addElement(new Stock("GOOGL", "GOOGLE COMPANY", "ISIN", "WKN"));
        stocks.addElement(new Stock("GOOGL2", "GOOGLE COMPANY2", "ISIN", "WKN"));
        stocks.addElement(new Stock("GOOGL3", "GOOGLE COMPANY3", "ISIN", "WKN"));
        stocks.addElement(new Stock("GOOGL4", "GOOGLE COMPANY4", "ISIN", "WKN"));
        stocks.addElement(new Stock("GOOGL5", "GOOGLE COMPANY5", "ISIN", "WKN"));
        stocks.addElement(new Stock("GOOGL6", "GOOGLE COMPANY6", "ISIN", "WKN"));*/
        list = new JList();
        list.setCellRenderer(new StockCellRenderer());
        list.setVisibleRowCount(4);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Stock selectedStock = (Stock) list.getSelectedValue();
                logger.info("Click on " + selectedStock);
                if (onStockSelection != null) {
                    onStockSelection.onStockSelection(selectedStock);
                }
            }
        });
        this.add(new JScrollPane(list), BorderLayout.WEST);
    }

    public void setStocks(Stock[] stocks) {
        this.stocks = stocks;
        list.setListData(stocks);
    }

    public void setOnStockSelection(OnStockSelection onStockSelection) {
        this.onStockSelection = onStockSelection;
    }
}
