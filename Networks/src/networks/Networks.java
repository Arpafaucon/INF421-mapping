/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networks;
import Carte.Carte;
<<<<<<< HEAD
import Carte.Edge;
=======
>>>>>>> 0199d97e8d6f1938c333bc6d2d5af1ded4e2aac7
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
    public static void main(String[] args) throws FileNotFoundException{

        Random rand = new Random();

        Carte map = new Carte("data/"+"malta"+".in");

        System.out.println("done building map");
        Long[] ids = (Long[])map.vertices.keySet().toArray(new Long[0]); //id des points
        int idn = ids.length;
        System.out.println("vertex number: " + ids.length + "--" + ids[0] + "//" + ids[ids.length -1]);
        //map.computeDijkstra(ids[start]);
        List<Edge> medge = map.computeDijkstraWithPerimeter(ids[start], 3600000);
        List<Vertex> mvertex = map.computeDijkstraWithPerimeterExact(ids[start], 360000);
        System.out.println(medge.size());
        System.out.println("computed Dijkstra");
        //Vis.savePerimeter(map.vertices.get(ids[start]), medge, "test");
        Vis.savePath(map.shortestPathTo(ids[end]), "test");
        Vis.savePath(mvertex, "test");
        System.out.println(map.vertices.toString());
        Vis.save(map, "test");
        //map.saveVertexToVisFile("test");
       // map.listVertex();
   }
    

}
