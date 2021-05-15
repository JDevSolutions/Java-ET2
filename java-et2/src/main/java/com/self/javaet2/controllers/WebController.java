package com.self.javaet2.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.self.javaet2.services.Order;
import com.self.javaet2.services.OrderBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class WebController {

    @Autowired
    private OrderBook book;
    private Object Exception;

    @GetMapping("/trades")
    String printOrderBook() throws JsonProcessingException {
        String orderBookString = book.printOrderBook();
        return orderBookString;
    }

    @GetMapping("/trades/min_buy")
    Double findMinBuy(){
        Double maxBuy = book.findMaxBuy();
        return maxBuy;
    }

    @GetMapping("/trades/max_sell")
    Double findMaxSell(){
        Double minSell = book.findMinSell();
        return minSell;
    }

    @PostMapping("/trades")
    Order submitBuyTrade(@RequestParam String buyOrSell, @RequestParam String limitOrMarket, @RequestParam double size, @RequestParam double limit){
        if (limitOrMarket == "limit"){
            if (buyOrSell == "buy"){
                Order order = book.sendBuyLimitOrder(size, limit);
                return order;
            } else if (buyOrSell == "sell"){
                Order order = book.sendSellLimitOrder(size, limit);
                return order;
            } else {
                return null;// change to exceptions
            }
        } else {
            if (buyOrSell == "buy"){
                Order order = book.sendMarketBuyOrder(size);
                return order;
            } else if (buyOrSell == "sell"){
                Order order = book.sendMarketSellOrder(size);
                return order;
            } else {
                return null; // excpetion needed
            }
        }
    }

    @DeleteMapping("/trades/{id}")
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
