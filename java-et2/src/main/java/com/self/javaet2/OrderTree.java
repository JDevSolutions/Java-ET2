import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

/* The OrderTree class creates half an OrderBook from two priceTrees. These are balanced BSTs for efficiency reasons.
 * We also create a priceMap, a hashmap, so that we can find all orders at a certain price quickly.
 * The orderMap is another hashmap for efficiently finding an order in the priceTree given its order id.
 *  The depth is the number of different price levels we have in the book.
 */
public class OrderTree {
    private TreeMap<Double, LinkedList<Order>> priceTree = new TreeMap<>();
    private HashMap<Double, LinkedList<Order>> priceMap = new HashMap<>();
    private HashMap<Integer, Order> orderMap = new HashMap<>();
    private int depth;

    public OrderTree(){
        reset();
    }

    public void reset(){
        this.priceTree.clear();
        this.priceMap.clear();
        this.depth = 0;
    }

    public void addOrder(Order quote){
        double qPrice = quote.getPrice();
        if (!priceMap.containsKey(qPrice)){
            depth += 1; // new price means new price level, hences depth increases
            LinkedList<Order> queue = new LinkedList<>();
            priceTree.put(qPrice, queue);
            priceMap.put(qPrice, queue);
        }
        priceMap.get(qPrice).add(quote);
        orderMap.put(quote.getTradeId(), quote);
    }

    public void deleteOrder(int id){
        Order order = orderMap.get(id);

        LinkedList<Order> priceList = priceTree.get(order.getPrice());
        for (int i = 0; i < priceList.size(); i++){
            Order order1 = priceList.get(i);
            if (order1.getTradeId() == id){
                priceList.remove(order1);
                break;
            }
        }
        if (priceList.size()==0){ // if no more orders at price level delete this price level
            priceTree.remove(order.getPrice());
            priceMap.remove(order.getPrice());
            depth -= 1;
        }
        orderMap.remove(id);
    }

    public void updateOrder(int id, double quantity){
        Order order = orderMap.get(id);
        if (order == null){
            return;
        }
        LinkedList<Order> priceList = priceTree.get(order.getPrice());
        for (Order curOrder : priceList){
            if (curOrder.getTradeId() == id){
                curOrder.setQuantity(quantity);
                break;
            }
        }
    }

    // find max price level in the OrderTree
    public Double maxPrice(){
        if (depth>0){
            return priceTree.lastKey();
        } else {
            return null;
        }
    }

    // find min price level in the OrderTree
    public Double minPrice(){
        if (depth>0){
            return priceTree.firstKey();
        } else {
            return null;
        }
    }

    // retrieve the order list at a certain price level
    public LinkedList<Order> getQuoteList(double price){
        return priceMap.get(price);
    }

    // retrieve the order list for the max price
    public LinkedList<Order> maxPriceList(){
        if (depth>0){
            return getQuoteList(maxPrice());
        } else {
            return null;
        }
    }

    // retrieve the order list for the min price
    public LinkedList<Order> minPriceList(){
        if (depth>0){
            return getQuoteList(minPrice());
        } else {
            return null;
        }
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth){
        this.depth = depth;
    }

    public TreeMap<Double, LinkedList<Order>> getPriceMap() {
        return priceTree;
    }
}
