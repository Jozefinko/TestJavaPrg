package SimpleStock;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/* Main requirements class for technical test */

public class StartRequirements {

	public static void main(String[] args) {
		
		final int TIMER_COUNT = 1000; 
		final int PERIOD_TIME_INTERVAL = 15;
		final int SECOND_IN_MINUTE = 60;
		List<Stock> stocks = new ArrayList<Stock>();
		
		stocks.add(new Stock(StockSymbol.TEA, StockType.COMMON, 0, 0, 100));
		stocks.add(new Stock(StockSymbol.POP, StockType.COMMON, 8, 0, 100));
		stocks.add(new Stock(StockSymbol.ALE, StockType.COMMON, 23, 0, 60));
		stocks.add(new Stock(StockSymbol.GIN, StockType.PREFFERED, 8, 2, 100));
		stocks.add(new Stock(StockSymbol.JOE, StockType.COMMON, 13, 0, 250));

		int tradePeriodInMinutes = PERIOD_TIME_INTERVAL;
		Date startDateTrading = new Date();
		Date endDateTrading = new Date(startDateTrading.getTime() + tradePeriodInMinutes * SECOND_IN_MINUTE * TIMER_COUNT);
		TradeExchange tradeExchange = new TradeExchange(stocks, startDateTrading,endDateTrading);

		List<Trade> trades = tradeExchange.Trade();
		System.out.println(String.format("Global Beverage Corporation Exchange is open at: %1s", startDateTrading));
		System.out.println(String.format("Global Beverage Corporation Exchange was closed at: %1s with %2d trades.", endDateTrading, trades.size()));
		System.out.println(String.format("\nLast 10 trades are:"));
		for (Trade trade : trades.subList(Math.max(trades.size() - 10, 0), trades.size())) {
			System.out.println(String.format("Stock %1s with Symbol %2s at %3s with price %4.2f", 
					trade.getTradeOperation(), trade.getStock().getSymbol(), trade.getTimestamp(), trade.getPrice()));
		}
		System.out.println("\nStatistics:");
		System.out.println("|Stock name\t\t|Dividend yield\t\t|PE ratio\t\t|Stock price\t\t|");
		for (Stock stock : stocks) {
			/*by https://en.wikipedia.org/wiki/Ticker_symbol ticker is stock : ticker price = stock price*/
			double stockPrice = Formulas.formulaStockPrice(stock, trades);
			double dividendYield = Formulas.formulaDividend(stock, stockPrice);
			double peRatio = Formulas.formulaPERatio(stock, stockPrice);
			System.out.println(String.format("|%1s\t\t\t|%2.2f\t\t\t|%3.2f\t\t\t|%4.2f\t\t\t|", stock.getSymbol(),dividendYield, peRatio, stockPrice));
		}
		double gbceAllShareIndex = Formulas.formulaGBCEAllShareIndex(trades);
		System.out.println(String.format("\nGlobal Beverage Corporation Exchange All Share Index: %1.2f", gbceAllShareIndex));
	}
}
