package View;

import Model.Stocks.Stock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StockCellRenderer extends JLabel implements ListCellRenderer<Stock> {

    public StockCellRenderer() {

    }
    @Override
    public Component getListCellRendererComponent(JList<? extends Stock> list, Stock value, int index, boolean isSelected, boolean cellHasFocus) {

        if (isSelected) {
            //setBackground(Color.BLACK);
            setForeground(Color.CYAN);
        } else {
            //setBackground(Color.WHITE);
            setForeground(Color.BLACK);
        }
        setText(value.getFullName());
        return this;
    }
}
