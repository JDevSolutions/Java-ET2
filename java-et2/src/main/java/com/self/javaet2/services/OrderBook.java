package com.self.javaet2.services;

import static com.self.javaet2.services.TradeAction.BUY;
import static com.self.javaet2.services.TradeAction.SELL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedList;
import java.util.TreeMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/* The OrderBook Class is formed from two OrderTrees, one for the buys,
 * the other for the sells.
 */

@Slf4j
@Service
public class OrderBook {

  private static int MAX_TRADE_ID = 0; // current largest trade id, links to Order class
  private final OrderTree buys = new OrderTree();
  private final OrderTree sells = new OrderTree();

  OrderBook() {
    reset();
  }

  public void reset() {
    buys.reset();
    sells.reset();
    MAX_TRADE_ID = 0;
  }

  public Order sendBuyLimitOrder(double size, double limit) {
    //create new order
    Order newOrder = new Order(newTradeId(), BUY, size, limit);

    // iterates over the OrderBook checking off possible trades
    while (size > 0 && sells.getDepth() > 0 && sells.minPrice() != null && limit >= sells
        .minPrice()) {
      size = sendBuyOrder(size, newOrder);
    }
    // leave remainder of original limit order on the book
    if (size > 0) {
      buys.addOrder(newOrder);
    }
    return newOrder;
  }

  private double sendBuyOrder(double size, Order newOrder) {
    LinkedList<Order> curList = sells.minPriceList();
    int i = 0;
    while (i < curList.size() && size > 0) {
      Order curOrder = curList.get(i);
      double curQuantity = curOrder.getQuantity();
      if (size >= curQuantity) {
        size -= curOrder.getQuantity();
        // delete order to tidy up OrderBook
        sells.deleteOrder(curOrder.getTradeId());

      } else {
        curOrder.setQuantity(curQuantity - size); // resize order previously sitting on OrderBook
        size = 0;
      }
      Logger.Log(newOrder.getTradeId(), "buy", curOrder.getPrice(), size);
      Logger.Log(curOrder.getTradeId(), "sell", curOrder.getPrice(), size);
      i++;
    }
    return size;
  }

  private int newTradeId() {
    final int temp = MAX_TRADE_ID;
    MAX_TRADE_ID = temp + 1;
    return temp;
  }

  public Order sendSellLimitOrder(double size, double limit) {
    Order newOrder = new Order(newTradeId(), SELL, size, limit);

    while (size > 0 && buys.getDepth() > 0 && buys.maxPrice() != null && buys.maxPrice() >= limit) {
      size = sendSellOrder(size, newOrder);
    }
    // leave remainder of limit order on the book
    if (size > 0) {
      sells.addOrder(newOrder);
    }
    return newOrder;
  }

  private double sendSellOrder(double size, Order newOrder) {
    LinkedList<Order> curList = buys.maxPriceList();
    int i = 0;
    while (i < curList.size() && size > 0) {
      Order curOrder = curList.get(i);
      double curQuantity = curOrder.getQuantity();
      if (size >= curQuantity) {
        size -= curOrder.getQuantity();
        buys.deleteOrder(curOrder.getTradeId());
        Logger.Log(newOrder.getTradeId(), "sell", curOrder.getPrice(), size);
        Logger
            .Log(curOrder.getTradeId(), "buy", curOrder.getPrice(), size); //check log timing here
      } else {
        curOrder.setQuantity(curQuantity - size);
        size = 0;
        Logger.Log(newOrder.getTradeId(), "buy", curOrder.getPrice(), size);
        Logger.Log(curOrder.getTradeId(), "sell", curOrder.getPrice(), size);
      }
      i++;
    }
    return size;
  }

  public Order sendBuyMarketOrder(double size) {
    Order newOrder = new Order(newTradeId(), BUY, size,
        Double.POSITIVE_INFINITY); //limit is positive infinity as market order will "walk the book"

    // iterate through the book, ticking off trades that are possible, up to the size specified in the function
    while (size > 0 && sells.getDepth() > 0) {
      size = sendBuyOrder(size, newOrder);
    }
    return newOrder;
  }

  public Order sendSellMarketOrder(double size) {
    Order newOrder = new Order(newTradeId(), SELL, size, -Double.POSITIVE_INFINITY);

    while (size > 0 && buys.getDepth() > 0) {
      size = sendSellOrder(size, newOrder);
    }
    return newOrder;
  }

  public Double findMaxBuy() {
    return buys.maxPrice();
  }

  public Double findMinSell() {
    return sells.minPrice();
  }

  public void deleteBuyOrder(int id) {
    buys.deleteOrder(id);
  }

  public void deleteSellOrder(int id) {
    sells.deleteOrder(id);
  }

  public Order updateBuyOrder(int id, double quantity) {
    return buys.updateOrder(id, quantity);
  }

  public Order updateSellOrder(int id, double quantity) {
    return sells.updateOrder(id, quantity);
  }

  public String printOrderBook() throws JsonProcessingException {
    TreeMap<Double, LinkedList<Order>> buysPriceTree = buys.getPriceMap();

    TreeMap<Double, LinkedList<Order>> sellsPriceTree = sells.getPriceMap();

    // write the order book to a json string
    ObjectMapper mapper = new ObjectMapper();
    String jsonBuys = mapper.writeValueAsString(buysPriceTree);
    String jsonSells = mapper.writeValueAsString(sellsPriceTree);
    return jsonBuys + jsonSells;
  }

}
