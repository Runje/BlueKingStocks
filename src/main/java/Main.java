import Model.BlueKingStocks;
import Model.Communication.StocksAPI;
import Model.FxMarketPxFeeder;
import Presenter.BlueKingPresenter;
import View.BlueKingStocksView;
import View.BlueKingView;
import View.HeaderView;
import View.JfreeCandlestickChart;
import org.jfree.chart.ChartPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class Main extends JPanel{
    private static Logger logger = LoggerFactory.getLogger("Main");


    public static void main(String[] args) throws IOException {
        logger.debug("START");
        BlueKingView view = new BlueKingStocksView();
        BlueKingPresenter presenter = new BlueKingPresenter(view);
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                view.start();

            }
        });

        try {
            new BlueKingStocks(presenter).start();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
