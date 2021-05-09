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

    @PostMapping("/buy_limit_trades")
    Order submitBuyTrade(@RequestParam double size, @RequestParam double limit){
        Order order = book.sendBuyLimitOrder(size, limit);
        return order;
    }

    @PostMapping("/sell_limit_trades")
    Order submitSellTrade(@RequestParam double size, @RequestParam double limit){
        Order order = book.sendSellLimitOrder(size, limit);
        return order;
    }

    @PostMapping("/buy_market_trades")
    Order submitBuyMarketTrade(@RequestParam double size){
        Order order = book.sendMarketBuyOrder(size);
        return order;
    }

    @PostMapping("/sell_market_trades")
    Order submitSellMarketTrade(@RequestParam double size){
        Order order = book.sendMarketSellOrder(size);
        return order;
    }

    @DeleteMapping("/trades/{id}") //check
    int deleteTrade(@PathVariable int id, @RequestParam String buyOrSell){
        if (buyOrSell=="buy"){
            book.deleteBuyOrder(id);
            return 1;
        } else if (buyOrSell=="sell") {
            book.deleteSellOrder(id);
            return 1;
        }
        return 0;
    }

    @PutMapping("/trades/{id}")
    Order updateTrade(@PathVariable int id, @RequestParam String buyOrSell, @RequestParam double quantity){
        if (buyOrSell=="buy"){
            Order updatedOrder = book.updateBuyOrder(id, quantity);
            return updatedOrder;
        } else if (buyOrSell=="sell") {
            Order updatedOrder = book.updateSellOrder(id,quantity);
            return updatedOrder;
        }
        return null;
    }

}
