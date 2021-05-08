import java.time.Duration;
import java.time.Instant;
import java.util.Random;

/* The PriceProcess creates a "ticking" price time series.
 * The queryPrice function when called generates a random jump, proportional to time and volatility
 * from the last queried time or the first start time of the class.
 */
public class PriceProcess {
    private double price;
    private Instant lastTime;

    PriceProcess(){
        System.out.println("Starting Price Process...");
        this.price = 0;
        this.lastTime = Instant.now();
    }

    public double queryPrice(){
        Instant curTime = Instant.now();
        Duration res = Duration.between(lastTime, curTime);
        Random rn = new Random();
        int maximum = (int) res.getSeconds();
        int minimum = -maximum;
        int range = maximum - minimum + 1;
        int randNum = rn.nextInt(range) + minimum;
        price += randNum*(0.014/Math.sqrt(86400)); //average daily volatility of s&p500 is 1.4%, then divided by sqrt of time
        return price;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getLastTime(){
        return price;
    }

    public void setLastTime(Instant lastTime){
        this.lastTime = lastTime;
    }

}
