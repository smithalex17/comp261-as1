package comp261.assig1;

// The edge class represents an edge in the graph.

public class Edge {
    private String tripId;
    private String fromId;
    private Stop fromStop;
    private Stop toStop;
    private double sequence;
    private double weight;
    
    

 //todo: add a constructor
 public Edge(String tripid, String fromStopId, double sequence, double weight){
    this.tripId = tripid;
    this.fromId = fromStopId;
    this.sequence = sequence;
    this.weight = weight;
    
}

//todo: add getters and setters
public String getfromId(){
    return fromId;
}

public void addTo(Stop s){
    this.toStop = s;
}

public void addFrom(Stop s){
    this.fromStop = s;
}

public Stop getFrom(){
    return this.fromStop;
}

public Stop getTo(){
    return this.toStop;
}

public int setId(String Id){
    this.tripId = Id;
    return 1;
}

}
