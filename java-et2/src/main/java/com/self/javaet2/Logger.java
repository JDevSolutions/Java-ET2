
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

public class Logger {

    public Logger() throws IOException {

    }
    /* Log function to log completed transactions in the order book.
     * Writes currently to a txt file: logfile.txt.
     */
    public static void Log(int id, String direction, double price, double quantity) {
        try{
            String price_str = Double.toString(price);
            String quantity_str = Double.toString(quantity);
            Instant time = Instant.now();
            String now = time.toString();
            FileWriter myWriter = new FileWriter("logfile.txt", true); // true appends to original file

            myWriter.write("Id: "+ id  + " , " + "Buy/Sell: " + direction +  " , " + "Price: "+ price_str + " , " + "Quantity: " + quantity_str + " , " + "Time: " + now + System.getProperty( "line.separator" ));
            myWriter.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}


