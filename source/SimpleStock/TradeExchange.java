package SimpleStock;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/* Class for generate trades in exchange market */

public class TradeExchange implements ITradeExchange {

	private List<Stock> stocks;
	private Date startDateTrading;
	private Date endDateTrading;
	private Random random;

	private static final int SPLIT_TIME_COUNT = 50; // if more then less trades
	
	public TradeExchange(List<Stock> stocks, Date startDateTrading, Date endDateTrading) {
		super();
		this.stocks = stocks;
		this.startDateTrading = startDateTrading;
		this.endDateTrading = endDateTrading;
		this.random = new Random();
	}

	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public Date getStartDateTrading() {
		return startDateTrading;
	}

	public void setStartDateTrading(Date startDateTrading) {
		this.startDateTrading = startDateTrading;
	}

	public Date getEndDateTrading() {
		return endDateTrading;
	}

	public void setEndDateTrading(Date endDateTrading) {
		this.endDateTrading = endDateTrading;
	}

	@Override
	public List<Trade> Trade() {
		int maxTradesCount = (int) ((endDateTrading.getTime() - startDateTrading.getTime()) / SPLIT_TIME_COUNT);
		int tradesCount = random.nextInt(maxTradesCount) + 1;

		List<Trade> trades = new ArrayList<Trade>();
		for (int i = 0; i < tradesCount; i++) {
			Stock stock = stocks.get(random.nextInt(stocks.size()));
			Date tradeTime = new Date();
			double quantity = random.nextInt(SPLIT_TIME_COUNT) + 1;
			TradeOperation tradeAction = (random.nextDouble() >= 0.5) ? TradeOperation.BUY : TradeOperation.SELL;
			double price = stock.getParValue() * 0.5 + (random.nextDouble() * (double) (stock.getParValue() * 2 - stock.getParValue() * 0.5));
			Trade trade = new Trade(stock, tradeTime, quantity, tradeAction, price);
			trades.add(trade);
		}
		return trades;
	}

}
