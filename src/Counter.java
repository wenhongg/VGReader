/**
 *  Produced April-May of 2018. 
 *  
 *  This query API was created for the Visual Genome database.
 *  
 *  
 *  This is code produced by a self taught programmer who has yet to matriculate in university.
 *  Therefore if there were things I could have done better or techniques I could have used, please let me know, thank you.
 *  @author LAM WEN HONG
 */
import java.io.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

/**
 * This class I used for debugging. Javadocs anyhow.
 *
 */
public class Counter {
	/**
	 * Reads the file and returns details
	 * @param filename filename of the VG data to be checked
	 * @throws IOException if the file cannot be found
	 */
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
	
	
	
	/**
	 * Visual
	 * @param args Genome
	 */
	public static void main(String[] args) {
		Counter trial = new Counter();
		try {
		trial.read("rawdata/dataout.json");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
