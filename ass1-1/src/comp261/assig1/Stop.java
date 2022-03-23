package comp261.assig1;

import java.util.ArrayList;
import java.util.List;


// decide the data structure for stops
public class Stop {
    //probably always have these three    
    private GisPoint loc;
    private String name;
    private String id;

    //Todo: add additional data structures
    
	private String code;
	private String desc;
	private String url;
	private String locType;
	private String stationParent;
	private String stopTimezone;

    public List<Trip> stopTrips = new ArrayList<>();
    public List<Edge> incoming = new ArrayList<>();
    public List<Edge> outgoing = new ArrayList<>();

    //private ArrayList<Trip> allTrips = new ArrayList<>();

        
    // Constructor
        public Stop(String Id, String Name, GisPoint gisPoint, String stopCode,
		 String stopDesc , String Url, String LocType, String sParent, String timeZone) {
            this.id = Id;
            this.name = Name;
            this.loc = gisPoint;
			this.code = stopCode;
			this.desc = stopDesc;
			this.url = Url;
			this.locType = LocType;
			this.stationParent = sParent;
			this.stopTimezone = timeZone;
        }

    // add getters and setters etc
    public String getID(){
		return this.id;
	}

	public String getName(){
		return this.name;
	}

    public int setId(String Id){
		this.id = Id;
        return 1;
	}

	public int setName(String Name){
		this.name = Name;
        return 1;
	}

    public GisPoint getPoint(){
        return this.loc;
    }

    public int setPoint(GisPoint gP){
        this.loc = gP;
        return 1;
    }

    public void addTrip(Trip t){
        this.stopTrips.add(t);
    }

    public void addOut(Edge e){
        this.outgoing.add(e);
    }

    public void addIn(Edge e){
        this.incoming.add(e);
    }

     




    }
