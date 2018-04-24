/*
 * Used only on VG Dataset. Place all 6 VG files in the same directory and provide the directory as an argument to main 
 * Reads object by object and writes object by object to avoid heap space error
 * Assumes all VG entries are ordered by image_id (they are)
 */

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
public class Reader {
	
	public void read(String[] filename, String addressto) throws IOException {
		InputStream[] streamset = new InputStream[6];
		JsonReader[] readset = new JsonReader[6];
		
		for(int i=0;i<6;i+=1) {
			streamset[i] = new FileInputStream(filename[i]);
			readset[i] = new JsonReader(new InputStreamReader(streamset[i], "UTF-8"));
			readset[i].beginArray();
		}
		Gson gson = new GsonBuilder().create();
		
	    
	    int counter = 0;
	    
	    
    	FileWriter file = new FileWriter(addressto);
    	BufferedWriter bw = new BufferedWriter(file);
    	bw.write("[");
    	
    	//First entry:
    	counter +=1;
    	
    	HashMap<String, Object> attr = gson.fromJson(readset[0], HashMap.class);
	    HashMap<String, Object> objs = gson.fromJson(readset[1], HashMap.class);	
	    HashMap<String, Object> regdesc = gson.fromJson(readset[2], HashMap.class);
	    HashMap<String, Object> reggraphs = gson.fromJson(readset[3], HashMap.class);	
	    HashMap<String, Object> rs = gson.fromJson(readset[4], HashMap.class);
	    HashMap<String, Object> scenegraphs = gson.fromJson(readset[5], HashMap.class);	
	    
	    // Deal with each map uniquely
	    
	    scenegraphs.put( "scene_relationships", scenegraphs.remove( "relationships" ) );
	    scenegraphs.put( "scene_objects", scenegraphs.remove( "objects" ) );
	    reggraphs.put( "region_graphs", reggraphs.remove( "regions" ));
	    regdesc.remove( "id" );
	    
	    
	    HashMap<String, Object> map = new HashMap<String, Object>();
 	
	    map.putAll(attr);
	    map.putAll(objs);
	    map.putAll(regdesc);
	    map.putAll(reggraphs);
	    map.putAll(rs);
	    map.putAll(scenegraphs);

	    String newobj = gson.toJson(map); 
	    System.out.println("Parsing image " + counter);
    		
    	bw.write(newobj);
    	bw.flush();
    	
	    while(readset[0].hasNext()) {
	    	// Objects after 1st.
	    	counter +=1;
	    	
	    	bw.write(", ");
	    	bw.flush();
	    	
	    	attr = gson.fromJson(readset[0], HashMap.class);
		    objs = gson.fromJson(readset[1], HashMap.class);	
		    regdesc = gson.fromJson(readset[2], HashMap.class);
		    reggraphs = gson.fromJson(readset[3], HashMap.class);	
		    rs = gson.fromJson(readset[4], HashMap.class);
		    scenegraphs = gson.fromJson(readset[5], HashMap.class);	
		    map = new HashMap<String, Object>();
		    
		    //Deal with each map uniquely
		    scenegraphs.put( "scene_relationships", scenegraphs.remove( "relationships" ) );
		    scenegraphs.put( "scene_objects", scenegraphs.remove( "objects" ) );
		    reggraphs.put( "regiongraphs", reggraphs.remove( "regions" ));
		    regdesc.remove( "id" );
		    	
		    map.putAll(attr);
		    map.putAll(objs);
		    map.putAll(regdesc);
		    map.putAll(reggraphs);
		    map.putAll(rs);
		    map.putAll(scenegraphs);
		    
		    
		    newobj = gson.toJson(map); 
	    	
		    System.out.println("Parsing image " + counter);
	    		
	    	bw.write(newobj);
	    	bw.flush();
	    	}

	    bw.write("]");
	    bw.flush();
    	bw.close();
    	file.close();
	    for(int i=0; i<6; i+=1){
	    readset[i].endArray();
	    readset[i].close();
	    }

    	System.out.println("End of work.");
	}
	
	public static void main(String[] args) {
		Reader trial = new Reader();
		if(args.length!=1) {
			System.out.println("Return file directory of VG data.");
			System.exit(1);
		}
		String[] fileset = {"/attributes.json","/objects.json","/region_descriptions.json","/region_graphs.json","/relationships.json","/scene_graphs.json"};
		String[] newfileset = new String[6];
		try {
			for(int i=0;i<6;i+=1) {
				newfileset[i] = args[0];
				newfileset[i] += fileset[i];
				System.out.println(newfileset[i]);
			}
		trial.read(newfileset, args[0] + "/dataout.json");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
