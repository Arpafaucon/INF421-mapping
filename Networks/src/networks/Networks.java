/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networks;
import Map.Map;
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
        Map map = MapLoader.buildMap("data/"+"idf"+".in");
        System.out.println("done building map");
        map.saveVertexToVisFile("test");
    }
    
}
