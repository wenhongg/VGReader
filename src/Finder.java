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
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.util.regex.Pattern;

/**

 * Finder.class returns all entries containing query keyword.
 * These entries are written into another json file with the keyword as its filename.
 * Reads from file it assumes to be a json array.
 * Intended to work on the combined json file produced by Reader.class  
 * NOTE: searching non-specific words will result in all data sets returned.
 */
public class Finder {
	//String[] restrict = {"synset"};
	List<String> answers;
	/**
	 * This class instantly searches the main file and creates new file with the desired results.
	 * @param filename the name of the main json file
	 * @param query the list of queries
	 * @throws IOException if the merged file cannot be found
	 */
	public Finder(String filename, List<String> query) throws IOException {
		answers = query;
		
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
	    
	    boolean containsall = true;
	    for(int i=0;i<answers.size(); i+=1) {
	    	if(!str.contains(answers.get(i))) {
	    		containsall = false;
	    		break;
	    	}
	    }
	    
		if(containsall) {
			counter2 += 1;
			bw.write(str);
			System.out.println("Image " + counter + " has a match.");
		}
	    while(read.hasNext()) {
	    	counter +=1;
	    	obj = gson.fromJson(read, JsonObject.class);
		    str = gson.toJson(obj);
		    
		    containsall = true;
		    for(int i=0;i<answers.size(); i+=1) {
		    	if(!str.contains(answers.get(i))) {
		    		containsall = false;
		    		break;
		    	}
		    }
		    
			if(containsall) {
				counter2 += 1;
				bw.write(",");
				bw.write(str);
				System.out.println("Image " + counter + " has a match.");
			} else {
				//System.out.println("Image " + counter + " NO MATCH.");
			}
		    
	    	
	    }
	   // read.endArray();
	    read.close();
	    bw.write("]");
	    bw.flush();
	    bw.close();
	    System.out.println(counter + " images searched, " + counter2 + " matches found.");
	}
	

	/**
	 * Supply the query words as arguments to this method. The VG json should be vg_merged.json in the same directory as the code
	 * @param args type queries and separate using a "-": e.g. dog-cat, grey wolf-blue dog-green bird
	 */
	public static void main(String[] args) {
		System.out.println();
		System.out.println("Starting...");
		try {
			String[] xx = args.toString().split("-");
			List<String> queries = new ArrayList<String>();
			for(String x: xx) {
				queries.add(x);
			}
			new Finder("vg_merged.json", queries);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
