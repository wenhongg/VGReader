/*
 * Finder.class returns all entries containing query keyword.
 * These entries are written into another json file with the keyword as its filename.
 * Reads from file it assumes to be a json array.
 * Intended to work on the combined json file produced by Reader.class  
 * NOTE: searching non-specific words will result in all data sets returned.
 */

import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.util.regex.Pattern;

// Use "" for the search term.
public class Finder {
	//String[] restrict = {"synset"};
	
	public Finder(String filename, String query) throws IOException {
		
		
		InputStream inps = new FileInputStream(filename);
		JsonReader read = new JsonReader(new InputStreamReader(inps, "UTF-8"));
		
		Gson gson = new GsonBuilder().create();
		
		//Begins to read from json file.
	    read.beginArray();
	    int counter = 0;
	    int counter2 = 0;
	    
	    //Prepares writer to write to new .json
	    FileWriter file = new FileWriter(query + ".json");
    	BufferedWriter bw = new BufferedWriter(file);
    	bw.write("[");
	    
    	//First instance here:
    	counter+=1;
	    JsonObject obj = gson.fromJson(read, JsonObject.class);
	    String str = gson.toJson(obj);
	    
	    //Do checks on string for query and selectively add.
	    if(str.contains(query)) {
		    counter2 += 1;
	    	bw.write(str);
	    	System.out.println("Image " + counter + " has a match.");
	    }
    	
	    while(read.hasNext()) {
	    	counter+=1;
		    obj = gson.fromJson(read, JsonObject.class);
		    str = gson.toJson(obj);
		    
		    //Do checks on string and selectively add.
		    
		    if(str.contains(query)) {
			    counter2 += 1;
			    bw.write(", ");
		    	bw.write(str);
		    	System.out.println("Image " + counter + " has a match.");
		    } 
		    
	    	
	    }
	   // read.endArray();
	    read.close();
	    bw.write("]");
	    bw.flush();
	    
	    System.out.println(counter + " images searched, " + counter2 + " matches found.");
	}
	

	
	public static void main(String[] args) {
		System.out.println();
		System.out.println("Starting...");
		try {
			new Finder("objects.json", "building");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
