/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networks;
import Carte.Carte;
import Carte.Edge;
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
        
        Carte map = new Carte("data/"+"france"+".in");
        System.out.println("done building map");
        Long[] ids = (Long[])map.vertices.keySet().toArray(new Long[0]);
        int idn = ids.length;
        int start = rand.nextInt(idn);
        int end = rand.nextInt(idn);
        System.out.println("vertex number: " + ids.length + "--" + ids[0] + "//" + ids[ids.length -1]);
        //map.computeDijkstra(ids[start]);
        List<Edge> medge = map.computeDijkstraWithPerimeter(ids[start], 3600000);
        System.out.println(medge.size());
        System.out.println("computed Dijkstra");
        Vis.savePerimeter(map.vertices.get(ids[start]), medge, "test");
//        Vis.savePath(map.shortestPathTo(ids[end]), "test");
//        System.out.println(map.vertices.toString());
//        Vis.save(map, "test");
        //map.saveVertexToVisFile("test");
      // map.listVertex();
    }
    
}
