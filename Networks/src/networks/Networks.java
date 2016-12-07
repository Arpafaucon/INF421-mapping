/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networks;
import Carte.Carte;
import Vis.Vis;
import java.io.FileNotFoundException;

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
        Carte map = new Carte("data/"+"malta"+".in");
        System.out.println("done building map");
        System.out.println(map.vertices.toString());
        Vis.save(map, "test");
        //map.saveVertexToVisFile("test");
      // map.listVertex();
    }
    
}
