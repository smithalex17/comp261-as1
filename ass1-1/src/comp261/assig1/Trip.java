package comp261.assig1;

import java.util.ArrayList;
import java.util.List;

public class Trip {
    private String stop_pattern_id;
	private String tripId;
	private List<Stop> stops = new ArrayList<Stop>();

    private String stopId;
	private int stopSequence;
	private double timePoint;
    private List<Stop> allStops = new ArrayList<Stop>();
	private List<Edge> tripEdges = new ArrayList<>();
	
	public Trip(String patternId, String id) {
		this.stop_pattern_id = patternId;
		this.tripId = id;
	}

	public void addEdge(Edge e){
		this.tripEdges.add(e);
	}

	public List<Edge> getEdges(){
		return this.tripEdges;
	}

	public void addStop(Stop s){
		this.allStops.add(s);
	}

	public String getID(){
		return this.stop_pattern_id;
	}

	public String getStopID(){
		return this.stopId;
	}

	public double getTimePoint(){
		return this. timePoint;
	}

	public List<Stop> getAllStops(){return this.allStops;}

	public Stop getTo(){
		return null;
	}

	public Stop getFrom(){
		return null;
	}

}
