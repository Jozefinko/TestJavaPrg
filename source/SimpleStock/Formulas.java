package SimpleStock;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.junit.Test;

import SimpleStock.Stock;
import SimpleStock.StockSymbol;
import SimpleStock.StockType;
import SimpleStock.TradeOperation;
import SimpleStock.Trade;

/* Class with formulas function */

public class Formulas {
	
	private static double formulaDividendYieldCommon(double lastDividend, double tickerPrice) {
		return lastDividend / tickerPrice;
	}

	private static double formulaDividentYieldPreffered(double fixedDividend, double parValue, double tickerPrice) {
		return (fixedDividend * parValue) / tickerPrice;
	}

	public static double formulaDividend(Stock stock, double price) {
		if (stock.getType() == StockType.COMMON)
			return formulaDividendYieldCommon(stock.getLastDividend(), price);
		else
			return formulaDividentYieldPreffered(stock.getFixedDividend(), stock.getParValue(), price);
	}

	@Test
	public void testFormulaDividend() {
		Stock stockCommon = new Stock(StockSymbol.POP, StockType.COMMON, 8, 0, 100);
        double dividendCommon = formulaDividend(stockCommon, 100);
        assertEquals(0.08, dividendCommon, 0.0001);
        Stock stockPreferred = new Stock(StockSymbol.GIN, StockType.PREFFERED, 8, 2, 100);
        double dividendPreffered = formulaDividend(stockPreferred, 100);
        assertEquals(2, dividendPreffered, 0.0001);
	}
	private static double formulaPERatio(double dividend, double tickerPrice) {
		return dividend != 0 ? tickerPrice / dividend : 0;
	}

	public static double formulaPERatio(Stock stock, double price) {
		return formulaPERatio(stock.getLastDividend(), price);
	}

	@Test
	public void testFormulaPERatio() {
		Stock stock = new Stock(StockSymbol.POP, StockType.COMMON, 8, 0, 100);
        double peRatio = formulaPERatio(stock, 105);
        assertEquals(13.125, peRatio, 0.0001 );
        Stock stockWithDividendZero = new Stock(StockSymbol.POP, StockType.COMMON, 0, 0, 100);
        double peRatioWithDividendZero = formulaPERatio(stockWithDividendZero, 105);
        assertEquals(0, peRatioWithDividendZero, 0.0001);
	}
	
	public static double formulaStockPrice(Stock stock, List<Trade> trades) {
		double sumPriceTimesQuantity = 0;
		double sumQuantity = 0;

		for (Trade trade : trades) {
			if (trade.getStock() != stock)
				continue;

			sumPriceTimesQuantity += trade.getPrice() * trade.getQuantityOfShares();
			sumQuantity += trade.getQuantityOfShares();
		}

		return sumPriceTimesQuantity / sumQuantity;
	}	

	@Test
	public void testFormulaStockPrice() {
		Stock stock = new Stock(StockSymbol.POP, StockType.COMMON, 8, 0, 100);
        List<Trade> trades = new ArrayList<Trade>();
        trades.add(new Trade(stock, new Date(), 10, TradeOperation.BUY, 80));
        trades.add(new Trade(stock, new Date(), 15, TradeOperation.BUY, 160));
        trades.add(new Trade(stock, new Date(), 25, TradeOperation.SELL, 240));
        double stockPrice = formulaStockPrice(stock, trades);
        assertEquals(184, stockPrice, 0.0001);
	}

	private static double formulaGeometricMean(List<Double> tradePrices) {
		if (tradePrices == null || tradePrices.isEmpty()) {
			return 0;
		}

		double sum = 0;
		for (double tradePrice : tradePrices) {
			sum += Math.log(tradePrice) / Math.log(2);
		}

		sum *= 1.0 / tradePrices.size();
		double geometricMean = Math.pow(2.0, sum);

		return geometricMean;
	}
	
	public static double formulaGBCEAllShareIndex(List<Trade> trades) {
		List<Double> tradePrices = new ArrayList<Double>();
		for (Trade trade : trades) {
			tradePrices.add(trade.getPrice());
		}

		return formulaGeometricMean(tradePrices);
	}
	
	@Test
	public void testFormulaGBCEAllShareIndex() {
		Stock stock = new Stock(StockSymbol.TEA, StockType.COMMON, 0, 0, 100);
		List<Trade> trades = new ArrayList<Trade>();
		trades.add(new Trade(stock, new Date(), 10, TradeOperation.BUY, 50));
		trades.add(new Trade(stock, new Date(), 20, TradeOperation.BUY, 100));
		double gbceAllShareIndex = formulaGBCEAllShareIndex(trades);
		assertEquals(70.7106, gbceAllShareIndex, 0.0001);
	}
}
