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
 * france->V:218228965[48709865,2210247] Palaiseau
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
    final static long idPalaiseau = 218228965;
    final static long idParis = 24923329;
    final static long idPralognan = 604287180;
    final static int MINUTES = 60 * 1000;

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        
        /*Section1 - generating a shortest path between Palaiseau and Pralognan-la-Vanoise*/

//        Carte map = new Carte("data/" + "france" + ".in");
//        map.computeDijkstra(idPalaiseau);
//        Vis.saveJSON(map.shortestPathTo(idPralognan), new Vertex(idPralognan,45395039,6710726), "gotoPralognan");
        
        /*Section 2 - generating an isochrone set of points around Palaiseau distant of 1h30 (90 minutes)*/
        Carte map = new Carte("data/" + "france" + ".in");
        generatePerimeter(map, idParis, 66*MINUTES, "paris1h66m");

        /* Section 2bis - generating 5 random isochrones at once */
//        Carte map = new Carte("data/" + "france" + ".in");
//        multiPerimeter(map, 60*MINUTES, 5, "random-");
        
        /*Section 3 - see intermediary points at 1h of Palaiseau when going to holidays far away (more than 2h)*/
//        Carte map = new Carte("data/" + "france" + ".in");
//        generatePerimeter(map, idPalaiseau, 60*MINUTES, 240*MINUTES, "paris1h4h");


        /*Section 4 - see how far we can be from Paris in durations ranging from 1 minute to 8 hours */
//        List<Integer> dists = new ArrayList<>(Arrays.asList(1,10,30,60, 120, 240, 360, 480));   
//        isochrones("france", idParis, dists, "isochroneParis");

        /*Section 5 - compare with a bond percolation generated map of france */
//        Carte map = new Carte("data/" + "pfra_big" + ".in");
//        multiPerimeter(map, 60*MINUTES, 5, "randomWithPerco-"); 

        /*Section 5bis - generate a custom percolation map with factor p = 60%*/
//        (new Percolation(0.60)).saveData("pfra60_big");
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
