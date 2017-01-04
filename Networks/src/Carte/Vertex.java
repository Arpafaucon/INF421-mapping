package Carte;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Vertex implements Comparable<Vertex>, Serializable{

    long id;
    int lat;
    int lng;

    List<Edge> leavingEdges;
    List<Edge> comingEdges;

    //Dijkstra
    Vertex pred;
    int dist;

    public Vertex(long id, int lat, int lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.leavingEdges = new ArrayList<>();
        this.comingEdges = new ArrayList<>();

        this.dist = Integer.MAX_VALUE;
        this.pred = null;
    }

    @Override
    public String toString() {
        return "V:" + id + "[" + this.lng + "," + this.lat + "]";
    }

    public String toGeoString() {
        return "[" + Math.round(this.lng) * 1e-6 + "," + Math.round(this.lat) * 1e-6 + "]";
    }
    

    @Override
    public int compareTo(Vertex v) {
        return (Integer.compare(this.dist, v.dist));
    }
    
    public static List<Vertex> geoScan(List<Vertex> lv, double lat, double lng, double e){
        int ilng = (int) (1e6*lng);
        int ilat = (int) (1e6*lat);
        int ie = (int) (1e6*e);
        List<Vertex> res = new ArrayList<Vertex>();
        for(Vertex v : lv){
            if(v.lat >= ilat - ie && v.lat <= ilat + ie &&v.lng >= ilng - ie && v.lng <= ilng + ie){
                res.add(v);
                System.out.println(v.toString());
            }
        }
        return res;
    }
}
