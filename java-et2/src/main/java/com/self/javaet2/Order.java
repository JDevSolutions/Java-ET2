import java.time.Instant;

public class Order {
    final private String buyOrSell;
    private double quantity;
    final private double price;
    private final Instant entryTime; // time Order is created
    final private int tradeId;

    Order(String buyOrSell, double quantity, double price){
        this.entryTime = Instant.now();
        this.quantity = quantity;
        this.price = price;
        this.buyOrSell = buyOrSell;
        this.tradeId = OrderBook.getMinTradeId() + 1;
        OrderBook.setMinTradeId(this.tradeId);
    }

    public String getBuyOrSell(){
        return buyOrSell;
    }

    //public void setBuyOrSell(String buyOrSell){
    //    this.buyOrSell = buyOrSell;
    //}

    public double getQuantity(){
        return quantity;
    }

    public void setQuantity(double quantity){
        this.quantity = quantity;
    }

    public double getPrice(){
        return price;
    }

    //public void setPrice(double price){
    //    this.price = price;
    //}

    public int getTradeId(){
        return tradeId;
    }

    //public void setTradeId(int tradeId){
    //    this.tradeId = tradeId;
    //}

    public Instant getEntryTime(){
        return entryTime;
    }


}
