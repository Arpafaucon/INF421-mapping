package Map;

import java.util.List;
import java.util.ArrayList;

public class Vertex {

    long id;
    int lat;
    int lng;
    
    List<Edge> leavingEdges;
    List<Edge> comingEdges;

    public Vertex(long id, int lat, int lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.leavingEdges = new ArrayList<>();
        this.comingEdges = new ArrayList<>();
    }
    
    @Override
    public String toString(){
        return "V : " + id + "[ " + this.lng + " , " + this.lat + " ]";
    }
    
    public String toGeoString(){
        return "[" + Math.round(this.lng * 1e6)*1e-12 + "," + Math.round(this.lat * 1e6)*1e-12 + "]";
    }
}
