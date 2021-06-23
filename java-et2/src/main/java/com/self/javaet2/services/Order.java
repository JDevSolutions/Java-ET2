package com.self.javaet2.services;

import java.time.Instant;

public class Order {

  private final TradeAction tradeAction;
  private final double price;
  private final Instant entryTime; // time Order is created
  private final int tradeId;
  private double quantity;

  Order(int tradeId, TradeAction tradeAction, double quantity, double price) {
    this.entryTime = Instant.now();
    this.quantity = quantity;
    this.price = price;
    this.tradeAction = tradeAction;
    this.tradeId = tradeId;
  }

  public TradeAction getTradeAction() {
    return tradeAction;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public double getPrice() {
    return price;
  }

  public int getTradeId() {
    return tradeId;
  }

  public Instant getEntryTime() {
    return entryTime;
  }


}
