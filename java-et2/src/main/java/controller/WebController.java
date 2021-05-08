package controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/get_trade")
    Order submitTrade(){
        return ...;
    }

    @PostMapping(/"submit_trade")
    Trade submitTrade(){
        return ...;
    }

    @DeleteMapping("/delete_trade"){
        return ....;
    }
}
