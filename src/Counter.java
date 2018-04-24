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
		
	    Gson gson = new GsonBuilder().create();
	    read.beginArray();
	    int counter = 0;
	    while(read.hasNext()) {
	    	counter +=1;
	    	System.out.println("Item number: " + counter);
	    	//JsonObject obj = gson.fromJson(read, JsonObject.class);
		    
	    	//System.out.println(obj);
	    	
		    read.beginObject();
		    
		    while(read.hasNext()) {
		    try {	
		    	System.out.println(read.nextName());
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
	}
	
	
	
	
	public static void main(String[] args) {
		Counter trial = new Counter();
		try {
		trial.read("sidewalk.json");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
