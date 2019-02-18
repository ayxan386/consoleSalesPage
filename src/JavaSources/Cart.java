package JavaSources;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;

//Cart with dynamic array to hold all chosen items from Buyer class
public class Cart {
    private ArrayList<JSONObject> mycart;
    private double totalCost;
    private String ownerName;
    public Cart(String name)
    {
        mycart = new ArrayList<>();
        totalCost = 0;
        this.ownerName = name;
    }
    public void add(JSONObject obj)
    {
        this.totalCost += Double.parseDouble(obj.get("value").toString());
        mycart.add(obj);
    }
    public void add(JSONArray objs)
    {
        for(Object obj : objs)
        {
            this.add((JSONObject)obj);
        }
    }
    public void viewCart()
    {
        if(mycart.isEmpty())
        {
            System.out.println("Your cart is currently empty");
            return;
        }
        System.out.println("\n\n\nDear " + this.getOwnerName() + "\nyour cart currently has:");
        for(int i=0;i<mycart.size();i++)
        {
            JSONObject obj = mycart.get(i);
            System.out.println("Slot #" + i+1);
            for(Object key : obj.keySet())
            {
                System.out.println(key + " : " + obj.get(key));
            }
            System.out.println("__________________________________");
            System.out.println("Total cost : " + this.getTotalCost());
        }
    }
    public double getTotalCost() {
        return totalCost;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void removeItem() throws IOException {
        viewCart();
        System.out.println("What is the number of slot of item?");
        int i = Integer.parseInt(Main.bf.readLine());
        mycart.remove(--i);
    }
}
