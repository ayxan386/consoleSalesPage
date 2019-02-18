package JavaSources;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import static JavaSources.Main.bf;
import static JavaSources.Main.dr;
//Parent class of Seller and Buyer
public class Person {
    String name;
    public Person(String name) {
        this.name = name;
    }
    public void lookItem(JSONObject obj)
    {
        System.out.println("\n");
        for(Object key : obj.keySet())
        {
            System.out.println(key + ": " + obj.get(key));
        }
        System.out.println("___________________________________");
    }
}
//Subclass which can add to the item and category database
class Seller extends Person
{
    public Seller(String name) {
        super(name);
    }
    public void sellNewItem() throws IOException {
        JSONObject newObj = new JSONObject();
        System.out.println("What is the name of product");
        newObj.put("name",bf.readLine());
        System.out.println("What is the value of product");
        newObj.put("value",Double.parseDouble(bf.readLine()));
        newObj.put("seller",super.name);
        System.out.println("What is the country of origin of product");
        newObj.put("country of origin",bf.readLine());
        System.out.println("What is the description of product");
        newObj.put("description",bf.readLine());
        newObj.put("id",dr.lastId("./src/DataFiles/itemlist.json") + 1);
        System.out.println("What is the name of category of product");
        dr.readFile("./src/DataFiles/categories.json");
        System.out.println("Could you find related category?");
        String ans = bf.readLine();
        if(ans.equalsIgnoreCase("no"))
        {
            System.out.println("Would you like to add one?");
            ans = bf.readLine();
            if(ans.equalsIgnoreCase("yes"))
            {
                JSONObject cat = new JSONObject();
                System.out.println("Enter the name for it");
                String name = bf.readLine();
                cat.put("name",name);
                cat.put("id",dr.lastId("./src/DataFiles/categories.json"));
                DataBaseWriter dw = new DataBaseWriter();
                dw.writeFile("./src/DataFiles/categories.json",cat);
                newObj.put("category",name);
            }
        }
        else newObj.put("category",bf.readLine());
        DataBaseWriter dw = new DataBaseWriter();
        dw.writeFile("./src/DataFiles/itemlist.json",newObj);
        System.out.println("New item was successfully added");
        System.out.println("Add another one?");
        ans = bf.readLine();
        if(ans.equalsIgnoreCase("yes"))sellNewItem();
    }
}
//Subclass which can look through database and them to cart
class Buyer extends Person
{
    Cart cart;
    public Buyer(String name) {
        super(name);
        cart = new Cart(this.name);
    }
    public void options() throws IOException {
        HashMap<Object,Object> querrymap = new HashMap<>();
        String selected;
        System.out.println("Show all items(1)\n" +
                "Choose from category(2)\n" +
                "Describe item(3)\n" +
                "View my cart(4)\n" +
                "Remove item from cart(5)" +
                "Go back(6)");
        selected = bf.readLine();
        switch (selected)
        {
            case "1": case "Show all items":
            dr.readFile("./src/DataFiles/itemlist.json");
            buyItem();
            break;

            case "2" : case "Choose from category":
            dr.readFile("./src/DataFiles/categories.json");
            System.out.println("Have you chosen the category?");
            String ans = bf.readLine();
            if(ans.equalsIgnoreCase("yes"))
            {
                System.out.println("What is the id of category?");
                Integer id = Integer.parseInt(bf.readLine());
                querrymap.put("id",id);
                JSONObject obj = (JSONObject)dr.readFile("./src/DataFiles/categories.json",querrymap,false).get(0);
                querrymap.clear();
                querrymap.put("category",obj.get("name"));
                dr.readFile("./src/DataFiles/itemlist.json",querrymap,true);
                buyItem();
            }
            break;

            case "3": case "describe item":
            System.out.println("Please enter the description of an item");
                String text = bf.readLine();
                String[] words = text.split(" ");
            JSONArray arr = dr.readFile("./src/DataFiles/itemlist.json",null,false);
            boolean isempty = true;
            for(Object obj: arr)
            {
                JSONObject jobj = (JSONObject) obj;
                String des = jobj.get("description").toString().toLowerCase();
                String name = jobj.get("name").toString().toLowerCase();
                for(String str : words)
                    if(des.contains(str.toLowerCase()) || name.contains(str.toLowerCase())) {
                        lookItem(jobj);
                        isempty = false;
                        break;
                    }
            }
            if(isempty)
            {
                System.out.println("Sorry we currently don't have that item in stock");
            }
            else buyItem();
            break;

            case "4": case"view my cart":
            this.viewCart();
            break;//case 4 end

            case "5": case"remove item from cart":
                cart.removeItem();
                break;

            case "6": case"go back":
                return;

                default:
                    System.out.println("No such command was found");
                    break;
        }//nested switch end
        options();
    }
    public void buyItem() throws IOException {
        HashMap<Object,Object> querrymap = new HashMap<>();
        System.out.println("Have you found the item you have been looking for?");
        String ans = bf.readLine();
        if(ans.equalsIgnoreCase("yes"))
        {
            System.out.println("Could you please enter the id of that item?");
            Integer id = Integer.parseInt(bf.readLine());
            querrymap.put("id",id);
            System.out.println("Is this the item you looking for?");
            dr.readFile("./src/DataFiles/itemlist.json",querrymap,true);
            System.out.println("Would you like to add it to the cart?");
            ans = bf.readLine();
            if(ans.equalsIgnoreCase("yes"))
            {
                cart.add(dr.readFile("./src/DataFiles/itemlist.json",querrymap,false));
                querrymap.clear();
            }
        }
    }
    public void viewCart()
    {
        cart.viewCart();
    }
}