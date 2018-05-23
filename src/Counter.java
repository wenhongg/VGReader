/*
 * Returns number of objects and top level JsonObject key names. For testing and debugging use.
 */
import java.io.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

// 2417997 image?
// return top level 

public class Counter {
	
	public void read(String filename) throws IOException {

		InputStream inps = new FileInputStream(filename);
		JsonReader read = new JsonReader(new InputStreamReader(inps, "UTF-8"));
		read.beginArray();
	    Gson gson = new GsonBuilder().create();
	    JsonObject obj = gson.fromJson(read, JsonObject.class);
	    String str = gson.toJson(obj);
	    System.out.println("begin");
	    System.out.println(str);
	    /*
	    int counter = 0;
	    while(read.hasNext()) {
	    	counter +=1;
	    	System.out.println("Item number: " + counter);
	    	//JsonObject obj = gson.fromJson(read, JsonObject.class);
		    
	    	//System.out.println(obj);
	    	
		    read.beginObject();
		    
		    while(read.hasNext()) {
		    try {
		    	String x = read.nextName();
		    	System.out.println(x);
		    } catch (Exception e) {
		    		read.skipValue();
		    	}
		    	
		    }
		    read.endObject();
	    	//JsonElement a = gson.toJsonTree(gson.fromJson(read, Object.class));
	    	
	    }
	    read.endArray();
	    read.close();
	    System.out.println("No more objects left.");
	    */
	}
	
	
	
	
	public static void main(String[] args) {
		Counter trial = new Counter();
		try {
		trial.read("rawdata/dataout.json");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
