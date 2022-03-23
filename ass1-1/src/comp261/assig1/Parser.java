package comp261.assig1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This utility class parses the files, and return the relevant data structure.
 * Internally it uses BufferedReaders instead of Scanners to read in the files,
 * as Scanners are slow.
 * 
 * @author Simon
 */
public class Parser {

    // read the stop file
    // tab separated stop descriptions
    // stop_id	stop_code	stop_name	stop_desc	stop_lat	stop_lon	zone_id	stop_url	location_type	parent_station	stop_timezone

	public static ArrayList<Stop> parseStops(File nodeFile){
    	// data types to be returned to the graph 
        ArrayList<Stop> stops = new ArrayList<>();
		try {
			// make a reader
			BufferedReader br = new BufferedReader(new FileReader(nodeFile));
			br.readLine(); // throw away the top line of the file
			String line;
			// read in each line of the file
            int count = 0;
			while ((line = br.readLine()) != null) {
				// tokenise the line by splitting it on tabs
				String[] tokens = line.split("[\t]");
                if (tokens.length >= 6) {
                    // process the tokens
                    String stopId = tokens[0];
                    String code = tokens[1];
                    String stopName = tokens[2];
                    String stopDesc = tokens[3];
                    double lat = Double.valueOf(tokens[4]);
                    double lon = Double.valueOf(tokens[5]);
                     String url = tokens[6];
                     String locType = tokens[7];
                     String stationParent = tokens[8];
                     String stopTimezone = tokens[9];

                    
                    //Todo: Decide how to store the stop data
                   stops.add(new Stop(stopId, stopName, new GisPoint(lon, lat),
                    code, stopDesc, url, locType, stationParent, stopTimezone));
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException("file reading failed.");
        }
        return stops;
    }

    // parse the trip file
    // header: stop_pattern_id,stop_id,stop_sequence,timepoint
    public static ArrayList<Trip> parseTrips(File tripFile){
        Map<String, Trip> alltrips = new HashMap<>();
        ArrayList<Trip> trips = new ArrayList<>();
        ArrayList<Edge> edges;
		try {
			// make a reader
			BufferedReader br = new BufferedReader(new FileReader(tripFile));
			br.readLine(); // throw away the top line of the file.
			String line;
			// read in each line of the file
            while ((line = br.readLine()) != null) {
                edges = new ArrayList<>();
                // tokenise the line by splitting it at ",".
                String[] tokens = line.split("[,]");
                if (tokens.length >= 4) {
                    // process the tokens
                    String stopPatternId = tokens[0];
                    String stopId = tokens[1];
                    int stopSequence = Integer.parseInt(tokens[2]);
                    double timepoint = Double.parseDouble(tokens[3]);
                    Edge e = new Edge(stopPatternId, stopId, stopSequence, timepoint);
                    if(alltrips.containsKey(stopPatternId)){
                        Trip t = alltrips.get(stopPatternId);
                        t.addEdge(e);
                    }else{
                        alltrips.put(stopPatternId, new Trip(stopPatternId, stopPatternId));
                    }
                    // Decide how to store the trip data                   
                    //trips.add(new Trip(stopId, stopPatternId, stops));
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException("file reading failed.");
        }
        trips = new ArrayList<>(alltrips.values());
        return trips;
    }
}





// code for COMP261 assignments