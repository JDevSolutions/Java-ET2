import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting Program...");

        OrderBook book = new OrderBook();

        /* Parameters for the main while loop sending orders to the order book.
         * These variables can be changed as desired.
         */
        Random rand = new Random();
        int limitMinimum = 50;
        int limitMaximum = 150;
        int quantityMinimum = 50;
        int quantityMaximum = 150;

        int numberOfOrders = 30;

        int i = 0;
        while (i < numberOfOrders){
            int randLimit = rand.nextInt(limitMaximum-limitMinimum) + limitMinimum;
            int randQuantity = rand.nextInt(quantityMaximum-quantityMinimum) + quantityMinimum;
            int randSign = rand.nextInt(3) - 1; // randSign range between [-1,1]
            int randTime = rand.nextInt(8);
            TimeUnit.SECONDS.sleep(randTime);
            // enforce randSign to be +1 or -1 in order to correspond with buy/sell
            while (randSign == 0){
                randSign = rand.nextInt(3) - 1; // check this is wrong
            }
            if (randSign == 1){
                book.sendBuyLimitOrder(randQuantity, randLimit);
            } else {
                book.sendSellLimitOrder(randQuantity, randLimit);
            }
            i++;
        }

        book.printOrderBook();

    }

    /* Old main function used for testing in development. Creates price process and
     * manually generated orders to be sent to the OrderBook.
     */

    public static void main2(String[] args) throws InterruptedException {
        System.out.println("Starting Program...");
        PriceProcess priceProcess = new PriceProcess();
        OrderBook book = new OrderBook();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(priceProcess.queryPrice());

        book.sendBuyLimitOrder(1000,100);
        book.sendBuyLimitOrder(1000,102);
        book.sendBuyLimitOrder(1000,104);
        book.sendBuyLimitOrder(2050,106);

        book.sendSellLimitOrder(1000,103);
        book.sendSellLimitOrder(1000,108);
        book.sendSellLimitOrder(1000,110);

        book.printOrderBook();
        System.out.println(book.findMaxBuy());
        System.out.println(book.findMinSell());
    }
}
