/**
 * PROJET INF421 : Networks
 * By Lara Koehler and Grégoire ROUSSEL
 * 
 * Useful vertices id (formatted as map->id[lng,lat])
 * idf->V:330576880[48709849,2211766] Building 72, Polytechnique
 * idf->V:24923329[48853587,2349163] Center of Paris, France
 * 
 * france->V:293346386[45184257,5719177] Grenoble
 * 
 * 
 * 
 */
package networks;

import Carte.Carte;
import Carte.Edge;

import Carte.Vertex;
import Vis.Vis;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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

////        Random rand = new Random();
       Carte map = new Carte("data/" + "france" + ".in");
//        //multiPerimeter(map, 60*60*1000, 120*60*1000, 10, "france2h1h-");
//        List<Vertex> plot = Vertex.geoScan(new ArrayList<Vertex>(map.vertices.values()),  5.718472, 45.183333,0.001);
//        Vis.saveJSON(plot, new Vertex(0, 2257740, 45707625), "grenoble");
//        List<Integer> dists = new ArrayList<>(Arrays.asList(10,20,30,45,60,120,180));
//        isochrones("france", 24923329, dists, "isoFrance");

//        System.out.println("done building map");
//        //map.computeDijkstra(ids[start]);
      //  List<Edge> medge = map.computeDijkstraWithPerimeter(ids[start], 360000);
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
       generatePerimeter(map,  24923329,  60*60*1000, "question11");
    }

    public static void generatePerimeter(Carte map, long idStart, int length, String filename) {
        List<Vertex> mvertex = map.computeDijkstraIsochrone(idStart, length);
        Vis.saveJSON(mvertex, map.vertices.get(idStart), filename);
    }
    
    public static void generatePerimeter(Carte map, long idStart, int length, int lengthDestination, String filename) {
        List<Vertex> mvertex = map.computeDijkstraIsochrone(idStart, lengthDestination, length);
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
    
    public static void multiPerimeter(Carte map, int length, int lDest,  int n, String filenameSeed){
        
        Random rand = new Random();
        Long[] ids = (Long[]) map.vertices.keySet().toArray(new Long[0]); //id des points
        int idn = ids.length;
        
        for (int i = 1; i <= n; i++) {
            if(i>1){
                map.purge();
            }
            generatePerimeter(map, ids[rand.nextInt(idn)], length, lDest, filenameSeed+i); 
        }
    }
    
    public static void isochrones(String mapName, long idStart, List<Integer> distances, String filenameSeed) throws FileNotFoundException{
        Carte map = new Carte("data/" + mapName + ".in");
        final int MIN = 60 * 1000;
        if(idStart == 0 ){
            Random rand = new Random();
            Long[] ids = (Long[]) map.vertices.keySet().toArray(new Long[0]); //id des points
            int idn = ids.length;
            idStart = ids[rand.nextInt(idn)];
        }
        ArrayList<List<Vertex>> points = new ArrayList<>();
        for (int i = 0; i < distances.size(); i++) {
            if(i>0){
                map.purge();
            }
            points.add(map.computeDijkstraIsochrone(idStart, MIN * distances.get(i)));
        }
        Vis.saveIsochronesJSON(distances, points, map.vertices.get(idStart), filenameSeed);
    }
}
