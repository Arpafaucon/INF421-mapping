/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Carte;

/**
 *
 * @author arpaf
 */
public class Edge{
    long start;
    long end;
    int length;
    
    public Vertex startVertex;
    public Vertex endVertex;
    
    public Edge(long start, long end, int length){
        this.start = start;
        this.end = end;
        this.length = length;
    }
    
    public Edge(Vertex startVertex, Vertex endVertex, int length){
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.start = startVertex.id;
        this.end = endVertex.id;
        this.length = length;
    }
    
    @Override
    public String toString(){
        return "E::"+start+"-"+end+"::"+length;
    }
    
    public String toDataString(){
        return "a " + start + " " + end + " " + length;
    }
    
    public Vertex distanceexacte(double t, long indice){ //on doit avoir t dans [0,1]
    	Vertex start = this.startVertex;
    	Vertex end = this.endVertex;
    	int l = this.length;
    	int l1=(int) t*l;
    	int l2=(int) (1-t)*length;
    	int lat=(int) (t*start.lat+(1-t)*end.lat);
    	int lng=(int) (t*start.lng+(1-t)*end.lng);
    	
    	Vertex nouv = new Vertex(indice, lat, lng);
    	nouv.comingEdges.add(new Edge(start, nouv, l1));
    	nouv.leavingEdges.add(new Edge(nouv, end, l2));
    	return nouv;
    }
    

}
