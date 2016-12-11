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

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException{

        Random rand = new Random();

        Carte map = new Carte("data/"+"malta"+".in");
        Long[] ids = (Long[])map.vertices.keySet().toArray(new Long[0]);

        int idn = ids.length;
        int start = rand.nextInt(idn); //tirage au sort de deux points
        int end = rand.nextInt(idn);
        System.out.println("vertex number: " + ids.length + "--" + ids[0] + "//" + ids[ids.length -1]);
        //map.computeDijkstra(ids[start]);
        List<Edge> medge = map.computeDijkstraWithPerimeter(ids[start], 3600000);
        List<Vertex> mvertex = map.computeDijkstraWithPerimeterExact(ids[start], 360000);
        System.out.println(medge.size());
        System.out.println("computed Dijkstra");
        Vis.savePerimeter(map.vertices.get(ids[start]), medge, "test");
        Vis.savePath(map.shortestPathTo(ids[end]), "test");
        Vis.savePath(mvertex, "test");
        System.out.println(map.vertices.toString());
        Vis.save(map, "test");
        //map.saveVertexToVisFile("test");
       // map.listVertex();
   }
    
}
