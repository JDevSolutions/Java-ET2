package com.self.javaet2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.self.javaet2.Order;
import org.springframework.web.bind.annotation.*;
import static com.self.javaet2.JavaEt2Application.book;

@RestController
public class WebController {

    @GetMapping("/order_book")
    String printOrderBook() throws JsonProcessingException {
        String orderBookString = book.printOrderBook();
        return orderBookString;
    }

    @GetMapping("/min_buy")
    Double findMinBuy(){
        Double maxBuy = book.findMaxBuy();
        return maxBuy;
    }

    @GetMapping("/max_sell")
    Double findMaxSell(){
        Double minSell = book.findMinSell();
        return minSell;
    }

    @PostMapping("/buy_limit_trade")
    Order submitBuyTrade(@RequestParam double size, @RequestParam double limit){
        Order order = book.sendBuyLimitOrder(size, limit);
        return order;
    }

    @PostMapping("/sell_limit_trade")
    Order submitSellTrade(@RequestParam double size, @RequestParam double limit){
        Order order = book.sendSellLimitOrder(size, limit);
        return order;
    }

    @PostMapping("/buy_market_trade")
    Order submitBuyMarketTrade(@RequestParam double size){
        Order order = book.sendMarketBuyOrder(size);
        return order;
    }

    @PostMapping("/sell_market_trade")
    Order submitSellMarketTrade(@RequestParam double size){
        Order order = book.sendMarketSellOrder(size);
        return order;
    }

    // add delete and update orders by id


}
