package JavaSources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    static DataBaseReader dr = new DataBaseReader();
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to out Online Sales Market");
        System.out.println("Can I have your name,please?");
        String name = bf.readLine();
            while(true)
            {
                System.out.println("What would you like to do?");
                System.out.println("Options : ");
                System.out.println("Buy item(1)\n" +
                        "Sell item(2)\n" +
                        "Exit(3)");
                String selected = bf.readLine();
                switch (selected.toLowerCase()) {
                    case "1":
                    case "buy item":
                        Buyer buy = new Buyer(name);
                        buy.options();
                        break;//case 1 end
                    case "2":
                    case "sell item":
                        Seller sell = new Seller(name);
                        sell.sellNewItem();
                        break;
                    case "3":
                    case "exit":
                        return;
                    default:
                        System.out.println("No such command was found");
                        break;
                }
                System.out.println("#####################################");
            }

    }
}
