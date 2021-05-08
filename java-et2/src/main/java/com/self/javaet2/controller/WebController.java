package com.self.javaet2.controller;

import com.self.javaet2.Order;
import org.springframework.web.bind.annotation.*;

import static com.self.javaet2.JavaEt2Application.book;

@RestController
public class WebController {

    @GetMapping("/print_order_book")
    void printOrderBook(){
        book.printOrderBook();
        return;
    }

    @GetMapping("/find_min_buy")
    Double findMinBuy(){
        Double maxBuy = book.findMaxBuy();

        return maxBuy;
    }

    @GetMapping("/find_max_sell")
    Double findMaxSell(){
        Double minSell = book.findMinSell();
        return minSell;
    }

    @PostMapping("/submit_buy_trade")
    Order submitBuyTrade(@RequestParam double size, @RequestParam double limit){
        Order order = book.sendBuyLimitOrder(size, limit);
        return order;
    }

    @PostMapping("/submit_sell_trade")
    Order submitSellTrade(@RequestParam double size, @RequestParam double limit){
        Order order = book.sendSellLimitOrder(size, limit);
        return order;
    }


}
