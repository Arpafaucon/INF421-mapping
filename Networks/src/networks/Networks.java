/**
 * PROJET INF421 : Networks
 * By Lara Koehler and Grégoire ROUSSEL
 * 
 * Useful vertices id (formatted as map->id[lng,lat])
 * idf->V:330576880[48709849,2211766] Building 72, Polytechnique
 * idf->V:24923329[48853587,2349163] Center of Paris, France
 * 
 * france->V:293346386[45184257,5719177] Grenoble
 * france->V:604287180[45395039,6710726] Pralognan-la-Vanoise
 * france->V:84105394[46237254,3115984] Chantelle
 * 
 * 
 * 
 */
package networks;

import Carte.Carte;

import Carte.Percolation;

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


        
        Carte map = new Carte("data/" + "france" + ".in");
//        System.out.println(map.vertices.size());

//        multiPerimeter(map, 2*60*1000, 2, "perc1-"); 
//        List<Vertex> plot = Vertex.geoScan(new ArrayList<Vertex>(map.vertices.values()),2.349163, 48.853587, 0.05);
//        Vis.saveJSON(plot, new Vertex(0,2349163, 48853587), "perc/paris");

//        List<Integer> dists = new ArrayList<>(Arrays.asList(1,10,30,60, 120, 240, 360, 480));
//        isochrones("france", 24923329, dists, "isofrance3");

        generatePerimeter(map, 84105394, 60*60*1000, 120*60*1000, "chantelle1h2h");

        //generate a Percolation
//        (new Percolation(0.53)).saveData("pfra_med");


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
