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
public class Edge {
    long start;
    long end;
    int length;
    
    Vertex startVertex;
    Vertex endVertex;
    
    public Edge(long start, long end, int length){
        this.start = start;
        this.end = end;
        this.length = length;
    }
    
    @Override
    public String toString(){
        return "E::"+start+"-"+end+"::"+length;
    }
}
