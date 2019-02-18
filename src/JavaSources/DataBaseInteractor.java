package JavaSources;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;

//Main interface for interaction with JSON data base
public interface DataBaseInteractor {

        default void readFile(String filepath){}
        default void writeFile(String filepath){}
}
//Read specified json file
class DataBaseReader implements   DataBaseInteractor
{
        //Reads and returns array of items from database which macth the given criteria querry
        //can also prints matching items if flag is set to true
        public JSONArray readFile(String filepath, HashMap querry,boolean flag)
        {
            JSONArray res = new JSONArray();
                try
                {
                    File f = new File(filepath);
                    BufferedReader bf = new BufferedReader(new FileReader(f));
                    JSONParser parser = new JSONParser();
                    String all = "";
                    while(bf.ready())
                    {
                        all+= bf.readLine();
                    }
                    JSONArray arr = (JSONArray)parser.parse(all);
                    if(querry == null)return arr;
                    for(int i=0;i<arr.size();i++)
                    {
                        JSONObject obj = (JSONObject) arr.get(i);
                        for(Object key : querry.keySet())
                        {
//                            System.out.println(querry.get(key) + " comparing to " + obj.get(key));
//                            System.out.println("Contains ? " + obj.containsKey(key));
//                            System.out.println("Second condition : " + querry.get(key).toString().equals(obj.get(key).toString()));
                            if(obj.containsKey(key) &&
                                    (querry.get(key).toString().equals(obj.get(key).toString()))
                                )
                            {
                                if(flag) {
                                    for (Object key2 : obj.keySet())
                                        System.out.println(key2 + " : " + obj.get(key2));
                                        System.out.println("__________________________________");
                                    }
                                res.add(obj);
                                break;
                            }
                        }
                    }
                }
                catch (FileNotFoundException e)
                {
                    System.out.println("Something got wrong");
                }
                catch (IOException e)
                {
                    System.out.println("Error in input");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return res;
        }
        //Reads and prints the content of json file
        public void readFile(String filepath)
        {
            try
            {
                File f = new File(filepath);
                BufferedReader bf = new BufferedReader(new FileReader(f));
                JSONParser parser = new JSONParser();
                String all = "";
                while(bf.ready())
                {
                    all+= bf.readLine();
                }
                JSONArray arr = (JSONArray)parser.parse(all);
                for(int i=0;i<arr.size();i++)
                {
                    JSONObject obj = (JSONObject) arr.get(i);
                    for(Object key : obj.keySet())
                    {
                        System.out.println(key + " : " + obj.get(key));
                    }
                    System.out.println("__________________________________");
                }
            }
            catch (FileNotFoundException e)
            {
                System.out.println("Something got wrong");
                return;
            }
            catch (IOException e)
            {
                System.out.println("Error in input");
                return;
            } catch (ParseException e) {
                e.printStackTrace();

            }
    }
        //Returns the id of last item in the file
        public int lastId(String pathname)
        {
        JSONArray res = new JSONArray();
        try
        {
            File f = new File(pathname);
            BufferedReader bf = new BufferedReader(new FileReader(f));
            JSONParser parser = new JSONParser();
            String all = "";
            while(bf.ready())
            {
                all+= bf.readLine();
            }
            JSONArray arr = (JSONArray)parser.parse(all);
            JSONObject obj = (JSONObject)arr.get(arr.size() - 1);
            if(obj.containsKey("id"))return Integer.parseInt(obj.get("id").toString());
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Something got wrong");
        }
        catch (IOException e)
        {
            System.out.println("Error in input");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
class DataBaseWriter implements DataBaseInteractor
{
        //Writes to file at filepath the obj Object
        public void writeFile(String filepath,Object obj)
        {
            try
            {
                File f = new File(filepath);
                BufferedReader bf = new BufferedReader(new FileReader(f));
                JSONParser parser = new JSONParser();
                String all = "";
                while(bf.ready())
                {
                    all+= bf.readLine();
                }
                JSONArray arr = (JSONArray)parser.parse(all);
                arr.add((JSONObject)obj);
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write(arr.toString());
                bw.flush();
                bw.close();
            }
            catch (FileNotFoundException e)
            {
                System.out.println("Something got wrong");
                return;
            }
            catch (IOException e)
            {
                System.out.println("Error in input");
                return;
            } catch (ParseException e) {
                e.printStackTrace();

            }
        }
}