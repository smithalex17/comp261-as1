package comp261.assig1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    
    //Todo add your data structures here
    private Map<String, Stop> allStops = new HashMap<>();
    private Map<String, Trip> allTrips = new HashMap<>();

    private List<Edge> allEdges = new ArrayList<>();

    ArrayList<Stop> stops; 
    ArrayList<Trip> trips;
    
// constructor post parsing
    public Graph() {

    }

    // constructor with parsing
    public Graph(File stopFile, File tripFile) {
        //Todo: instantiate your data structures here
        this.stops = new ArrayList<>();
        this.trips = new ArrayList<>();
        //Then you could parse them using the Parser
        stops = Parser.parseStops(stopFile);
        trips = Parser.parseTrips(tripFile);

        //buildStopList();
        buildTripData();
    }

    // buildStoplist from other data structures
    private void buildStopList() {
       // Todo: you could use this sort of method to create additional data structures
        for(Stop s : stops){
            allStops.put(s.getID(), s);
        }

    }
 
    // buildTripData into stops
    private void buildTripData(){
        Set<Edge> allEdge = new HashSet<>();
        // Todo: this could be used for trips
        for(Trip t : trips){
            if(allTrips.containsKey(t.getID())){
                t.addStop(allStops.get(t.getStopID()));
            }else{
                allTrips.put(t.getID(), t);
            }
        }
        for(Trip t : allTrips.values()){
            for(Stop s : t.getAllStops()){
                s.addTrip(t);
            }
            List<Edge> temp = t.getEdges();
            for(int i = 0; i < temp.size()-1; i++){
                Edge e = temp.get(i);
                String stopId = e.getfromId();
                if(allStops.containsKey(e.getfromId())){
                    e.addFrom(allStops.get(e.getfromId()));
                    e.addTo(allStops.get(temp.get(i+1).getfromId()));
                }
            }
                Edge e = temp.get(temp.size()-1);
                if(allStops.containsKey(e.getfromId())){
                    e.addFrom(allStops.get(temp.get(temp.size()-2).getfromId()));
                    e.addTo(allStops.get(temp.get(temp.size()-1).getfromId()));
                }
            
        }

        


        allEdges.addAll(allEdge);
    }

    // Todo: getters and setters

    public ArrayList<Stop> getStopList() {
        return stops;
    }   

    public ArrayList<Trip> getTripList() {
        return trips;
    }

    public ArrayList<Edge> getEdges(){
        return null;
    }



 

}
