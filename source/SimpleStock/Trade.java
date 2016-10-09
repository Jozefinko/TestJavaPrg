package SimpleStock;

import java.util.Date;

/* Class for record trade */

public class Trade {

	private Stock stock;
	private Date timestamp;
	private double quantityOfShares;
	private TradeOperation tradeOperation;
	private double price;

	public Trade(Stock stock, Date timestamp, double quantityOfShares, TradeOperation tradeOperation, double price) {
		super();
		this.stock = stock;
		this.timestamp = timestamp;
		this.quantityOfShares = quantityOfShares;
		this.tradeOperation = tradeOperation;
		this.price = price;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public double getQuantityOfShares() {
		return quantityOfShares;
	}

	public void setQuantityOfShares(double quantityOfShares) {
		this.quantityOfShares = quantityOfShares;
	}

	public TradeOperation getTradeOperation() {
		return tradeOperation;
	}

	public void setTradeOperation(TradeOperation tradeOperation) {
		this.tradeOperation = tradeOperation;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
