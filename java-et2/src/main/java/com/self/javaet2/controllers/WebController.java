package com.self.javaet2.controllers;

import static java.lang.String.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.self.javaet2.services.Order;
import com.self.javaet2.services.OrderBook;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebController {

  private final OrderBook book;

  @GetMapping("/trades")
  String printOrderBook() throws JsonProcessingException {
    return book.printOrderBook();
  }

  @GetMapping("/trades/min_buy")
  Double findMinBuy() {
    return book.findMaxBuy();
  }

  @GetMapping("/trades/max_sell")
  Double findMaxSell() {
    return book.findMinSell();
  }

  @PostMapping("/trades")
  Order addNewTrade(@RequestParam String buyOrSell, @RequestParam String limitOrMarket,
      @RequestParam double size, @RequestParam double limit) {
    if ("limit".equalsIgnoreCase(limitOrMarket)) {
      if ("buy".equalsIgnoreCase(buyOrSell)) {
        return book.sendBuyLimitOrder(size, limit);
      } else if ("sell".equals(buyOrSell)) {
        return book.sendSellLimitOrder(size, limit);
      } else {
        final String message = format("buyOrSell: %s, limitOrMarket: %s", buyOrSell, limitOrMarket);
        throw new IllegalArgumentException(message);
      }
    } else {
      if ("buy".equals(buyOrSell)) {
        return book.sendBuyMarketOrder(size);
      } else if ("sell".equals(buyOrSell)) {
        return book.sendSellMarketOrder(size);
      } else {
        final String message = format("buyOrSell: %s, limitOrMarket: %s", buyOrSell, limitOrMarket);
        throw new IllegalArgumentException(message);
      }
    }
  }

  @DeleteMapping("/trades/{id}")
  int deleteTrade(@PathVariable int id, @RequestParam String buyOrSell) {
    if ("buy".equals(buyOrSell)) {
      book.deleteBuyOrder(id);
      return 1;
    } else if ("sell".equals(buyOrSell)) {
      book.deleteSellOrder(id);
      return 1;
    }
    return 0;
  }

  @PutMapping("/trades/{id}")
  Order updateTrade(@PathVariable int id, @RequestParam String buyOrSell,
      @RequestParam double quantity) {
    if ("buy".equals(buyOrSell)) {
      return book.updateBuyOrder(id, quantity);
    } else if ("sell".equals(buyOrSell)) {
      return book.updateSellOrder(id, quantity);
    }
    return null;
  }

}
