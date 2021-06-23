package com.self.javaet2.services;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

/* The OrderTree class creates half an OrderBook from two priceTrees. These are balanced BSTs for efficiency reasons.
 * We also create a priceMap, a hashmap, so that we can find all orders at a certain price quickly.
 * The orderMap is another hashmap for efficiently finding an order in the priceTree given its order id.
 *  The depth is the number of different price levels we have in the book.
 */
public class OrderTree {

  private final TreeMap<Double, LinkedList<Order>> priceTree = new TreeMap<>();
  private final HashMap<Double, LinkedList<Order>> priceMap = new HashMap<>();
  private final HashMap<Integer, Order> orderMap = new HashMap<>();

  public OrderTree() {
    reset();
  }

  public void reset() {
    this.priceTree.clear();
    this.priceMap.clear();
  }

  public void addOrder(Order quote) {
    double qPrice = quote.getPrice();
    if (!priceMap.containsKey(qPrice)) {
      LinkedList<Order> queue = new LinkedList<>();
      priceTree.put(qPrice, queue);
      priceMap.put(qPrice, queue);
    }
    priceMap.get(qPrice).add(quote);
    orderMap.put(quote.getTradeId(), quote);
  }

  public void deleteOrder(int id) {
    Order order = orderMap.get(id);

    LinkedList<Order> priceList = priceTree.get(order.getPrice());
    for (int i = 0; i < priceList.size(); i++) {
      Order order1 = priceList.get(i);
      if (order1.getTradeId() == id) {
        priceList.remove(order1);
        break;
      }
    }
    if (priceList.size() == 0) { // if no more orders at price level delete this price level
      priceTree.remove(order.getPrice());
      priceMap.remove(order.getPrice());
    }
    orderMap.remove(id);
  }

  public Order updateOrder(int id, double quantity) {
    Order order = orderMap.get(id);
    if (order == null) {
      return null;
    }
    LinkedList<Order> priceList = priceTree.get(order.getPrice());
    for (Order curOrder : priceList) {
      if (curOrder.getTradeId() == id) {
        curOrder.setQuantity(quantity);
        return curOrder;
      }
    }
    return null;
  }

  // find max price level in the OrderTree
  public Double maxPrice() {
    if (getDepth() > 0) {
      return priceTree.lastKey();
    } else {
      return null;
    }
  }

  // find min price level in the OrderTree
  public Double minPrice() {
    if (getDepth() > 0) {
      return priceTree.firstKey();
    } else {
      return null;
    }
  }

  // retrieve the order list at a certain price level
  public LinkedList<Order> getQuoteList(double price) {
    return priceMap.get(price);
  }

  // retrieve the order list for the max price
  public LinkedList<Order> maxPriceList() {
    if (getDepth() > 0) {
      return getQuoteList(maxPrice());
    } else {
      return new LinkedList<>();
    }
  }

  // retrieve the order list for the min price
  public LinkedList<Order> minPriceList() {
    if (getDepth() > 0) {
      return getQuoteList(minPrice());
    } else {
      return new LinkedList<>();
    }
  }

  public int getDepth() {
    return this.priceMap.size();
  }

  public TreeMap<Double, LinkedList<Order>> getPriceMap() {
    return priceTree;
  }
}
