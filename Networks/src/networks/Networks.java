/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networks;

import Carte.Carte;
import Carte.Edge;

import Carte.Vertex;
import Vis.Vis;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;

/**
 *
 * @author arpaf
 */
public class Networks {

    //private static final int ATTEMPTS = 10;
    private static final int TEMPSM = 120;

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        Random rand = new Random();

        Carte map = new Carte("data/" + "france" + ".in");
        multiPerimeter(map, 3600000, 10, "france");
//        System.out.println("done building map");
//        //map.computeDijkstra(ids[start]);
//        //List<Edge> medge = map.computeDijkstraWithPerimeter(ids[start], 360000);
//        List<Vertex> mvertex = map.computeDijkstraWithPerimeterExact(ids[start], 360000);
//        
//        //System.out.println(medge.size());
//        System.out.println("computed Dijkstra");
//        //Vis.savePerimeter(map.vertices.get(ids[start]), medge, "test");
//        //Vis.savePath(map.shortestPathTo(ids[end]), "test");
//        Vis.save(mvertex, map.vertices.get(ids[start]), "test");
//        //System.out.println(map.vertices.toString());
//        //Vis.save(map, "test");
//        //map.saveVertexToVisFile("test");
//        // map.listVertex();
    }

    public static void generatePerimeter(Carte map, long idStart, int length, String filename) {
        List<Vertex> mvertex = map.computeDijkstraWithPerimeterExact(idStart, length);
        Vis.saveJSON(mvertex, map.vertices.get(idStart), filename);
    }
    
    public static void multiPerimeter(Carte map, int length, int n, String filenameSeed){
        Random rand = new Random();
        Long[] ids = (Long[]) map.vertices.keySet().toArray(new Long[0]); //id des points
        int idn = ids.length;
        
        for (int i = 1; i <= n; i++) {
            if(i>1){
                map.purge();
            }
            generatePerimeter(map, ids[rand.nextInt(idn)], length, filenameSeed+i); 
        }
        
    }
}
