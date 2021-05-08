package controller;

import com.self.javaet2.JavaEt2Application;
import com.self.javaet2.Order;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    @GetMapping("/print_order_book")
    void printOrderBook(){
        JavaEt2Application.book.printOrderBook();
        return;
    }

    @GetMapping("/find_max_buy")
    Double findMinBuy(){
        Double maxBuy = JavaEt2Application.book.findMaxBuy();
        return maxBuy;
    }

    @GetMapping("/find_max_buy")
    Double findMaxSell(){
        Double minSell = JavaEt2Application.book.findMinSell();
        return minSell;
    }

    @PostMapping("/submit_buy_trade")
    Order submitBuyTrade(@RequestParam double size, @RequestParam double limit){
        Order order = JavaEt2Application.book.sendBuyLimitOrder(size, limit);
        return order;
    }

    @PostMapping("/submit_sell_trade")
    Order submitSellTrade(@RequestParam double size, @RequestParam double limit){
        Order order = JavaEt2Application.book.sendSellLimitOrder(size, limit);
        return order;
    }


}
