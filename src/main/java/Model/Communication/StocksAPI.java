package Model.Communication;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import Model.Stocks.Stock;
import Model.Stocks.StockCandles;
import au.com.bytecode.opencsv.CSVReader;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Thomas on 11.08.2017.
 */

public class StocksAPI {
    private static Logger logger = LoggerFactory.getLogger("StockAPI");
    public static String AV_KEY = "K0UQVK1KC383TWC7";
    public static StockCandles getIntradayStock(String symbol, String interval, String output) throws IOException {
        OkHttpClient client = HttpUtils.getUnsafeOkHttpClient();

        HttpUrl.Builder builder = HttpUrl.parse("https://www.alphavantage.co/").newBuilder();
        builder.addPathSegment("query")
                .addQueryParameter("function","TIME_SERIES_INTRADAY")
                .addQueryParameter("symbol", symbol)
                .addQueryParameter("interval", interval)
                .addQueryParameter("outputsize", output)
                .addQueryParameter("apikey", AV_KEY);
        HttpUrl url = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return StockCandles.fromJSON(result);
    }

    public static List<Stock> getListedCompaniesFromXetra() throws IOException {
        OkHttpClient client = HttpUtils.getUnsafeOkHttpClient();

        HttpUrl.Builder builder = HttpUrl.parse("http://xetra.com/xetra-en/allTradableInstruments.csv").newBuilder();

        HttpUrl url = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();
        logger.debug("Read Xetra CSV");
        return parseXetra(result);

        // XETRA
        //http://xetra.com/xetra-en/allTradableInstruments.csv

        // NASDAQ
        //http://www.nasdaq.com/screening/companies-by-industry.aspx?exchange=NASDAQ&render=download

        // NYSE
        //http://www.nasdaq.com/screening/companies-by-industry.aspx?exchange=NYSE&render=download

        //ASX
        //http://www.asx.com.au/asx/research/ASXListedCompanies.csv
    }

    private static List<Stock> parseXetra(String result) throws IOException {
        CSVReader reader = new CSVReader(new StringReader(result), ';');
        // skip first 4 lines(Last Updated information)
        for (int i = 0; i < 4; i++) {
            reader.readNext();
        }
        String[] header = reader.readNext();

        int isinIndex = -1;
        int wknIndex = -1;
        int symbolIndex = -1;
        int nameIndex = -1;
        for (int i = 0; i < header.length; i++) {
            String item = header[i];
            if (item.equals("ISIN")) {
                isinIndex = i;
            } else if (item.equals("WKN")) {
                wknIndex = i;
            } else if (item.equals("Mnemonic")) {
                symbolIndex = i;
            } else if (item.equals("Instrument")) {
                nameIndex = i;
            }
        }

        int max = Math.max(isinIndex, Math.max(wknIndex, Math.max(symbolIndex, nameIndex)));
        List<Stock> stockList = new ArrayList<>(60000);
        while(true)
        {
            String isin = "";
            String name = "";
            String wkn = "";
            String symbol = "";
            String[] line = reader.readNext();
            if (line == null)
            {
                break;
            }

            for (int i = 0; i < line.length; i++) {
                if (i > max)
                {
                    // no item after this index is interesting
                    break;
                }

                String item = line[i];
                if (i == isinIndex)
                {
                    isin = item;
                } else if (i == symbolIndex)
                {
                    symbol = item;
                } else if (i == wknIndex)
                {
                    wkn = item;
                } else if (i == nameIndex)
                {
                    name = item;
                }
            }

            if (!symbol.equals("")) {
                stockList.add(new Stock(symbol, name, isin, wkn));
            }
        }

        return stockList;
    }
}
