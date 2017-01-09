package Carte;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.FileWriter;
//import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
//import java.util.Iterator;
//import java.util.Map.Entry;
import java.util.Scanner;

public class Carte {

    public HashMap<Long, Vertex> vertices;
    public List<Edge> edges;
    //public HashMap<Long, DjikInfo> djikstra; //essai de découplage de la carte et du Djikstra

    //PROGRESS donne une idée de l'avancement du calcul. En pratique, environ % des points sont traitès (50=>5 barres env)
    private final int PROGRESS = 50;

    public Carte() {
        vertices = new HashMap<>();
        edges = new ArrayList<>();
        //djikstra = new Hashmap<>();
    }

    /**
     *
     * @param filename : fichier à charger
     * @throws FileNotFoundException
     */
    public Carte(String filename) throws FileNotFoundException {
        System.out.println("Building map from source: " + filename);
        File file = new File(filename);
        vertices = new HashMap();
        edges = new ArrayList<>();

        String[] lineData;
        Scanner scanner = new Scanner(file);
        Edge curEdge;

        boolean firstEdge = true;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lineData = line.split(" ");
            if (lineData[0].equals("v")) {
                //vertex
                vertices.put(Long.parseLong(lineData[1]), new Vertex(Long.parseLong(lineData[1]), Integer.parseInt(lineData[2]), Integer.parseInt(lineData[3])));
            }
            if (lineData[0].equals("a")) {
                if (firstEdge) {
                    firstEdge = false;
                    System.out.println("starting Edges");
                }

                //on a une arrete
                curEdge = new Edge(vertices.get(Long.parseLong(lineData[1])), vertices.get(Long.parseLong(lineData[2])), Integer.parseInt(lineData[3]));
                edges.add(curEdge);
                vertices.get(Long.parseLong(lineData[1])).leavingEdges.add(curEdge);
                vertices.get(Long.parseLong(lineData[2])).comingEdges.add(curEdge);
            }
        }
        System.out.println("finished building map");
    }

    @Override
    public String toString() {
        String str = "Map Object ( v: " + this.vertices.size() + "; a: " + this.edges.size() + " )";
        return str;
    }

    public void computeDijkstra(long startVertexId) {
        Vertex v;
        Vertex voisin;
        int altDistance;

        vertices.get(startVertexId).dist = 0;
        PriorityQueue<Vertex> queue = new PriorityQueue(vertices.values());
        System.out.println("built queue");

        int n = queue.size() / PROGRESS;

        while (!queue.isEmpty()) {
            v = queue.poll();
            for (Edge leaving : v.leavingEdges) {
                if (queue.size() % n == 0) {
                    System.out.print("=");
                }

                voisin = leaving.endVertex;
                altDistance = v.dist + leaving.length;
                if (altDistance < voisin.dist) {
                    //on a trouvé un chemi plus court. On modifie l'object (et on le desinsere reinsere pour cela)
                    queue.remove(voisin);
                    voisin.pred = v;
                    voisin.dist = altDistance;
                    queue.add(voisin);
                }

            }
        }
    }

    public List<Vertex> computeDijkstraIsochrone(long startVertexId, int distanceLimit) {
        List<Edge> markedEdge = new ArrayList<>();
        Vertex v;
        Vertex voisin;
        int altDistance;
        boolean tooFar = false;

        vertices.get(startVertexId).dist = 0;
        PriorityQueue<Vertex> queue = new PriorityQueue(vertices.values());
       
        System.out.print("depiling queue: ");
        int n = queue.size() / PROGRESS;

        while (!queue.isEmpty() && !tooFar) {
            v = queue.poll();
            if (v.dist > distanceLimit) {
                System.out.print("x");
                tooFar = true;
                break;
            }
            if (queue.size() % n == 0) {
                System.out.print("=");
            }
            for (Edge leaving : v.leavingEdges) {
                voisin = leaving.endVertex;
                altDistance = v.dist + leaving.length;
                if (altDistance < voisin.dist) {
                    //on a trouvé un chemi plus court. On modifie l'object (et on le desinsere reinsere pour cela)
                    queue.remove(voisin);
                    voisin.pred = v;
                    voisin.dist = altDistance;

                    queue.add(voisin);

                    if (v.dist < distanceLimit && altDistance > distanceLimit) {
                        //on passe la limite horaire
                        markedEdge.add(leaving);
                    }
                }
            }
        }
        System.out.print("g");
        List<Vertex> markedPlots = new LinkedList<>();
        int d1, d2, dd, lat, lng;
        double c1, c2;
        for (Edge e : markedEdge) {
            d1 = (distanceLimit - e.startVertex.dist);
            d2 = e.endVertex.dist - distanceLimit;
            dd = d1 + d2;
            c1 = 1. * d1 / dd;
            c2 = 1. * d2 / dd;
            lat = (int) (c1 * e.startVertex.lat + c2 * e.endVertex.lat);
            lng = (int) (c1 * e.startVertex.lng + c2 * e.endVertex.lng);
//            System.out.println("plot " + c1+" " +" "+ c2 +" "+ dd +" "+ lat +" "+ lng + " " +e.startVertex.toString() + " " + e.endVertex.toString());
            markedPlots.add(new Vertex(0, lat, lng));
        }
        System.out.println("e");
        return markedPlots;
        //on a la distance de tous les points.
    }

    
    public List<Vertex> computeDijkstraIsochrone(long startVertexId, int distanceDestination, int distanceLimit){
        Vertex v;
        Vertex voisin;
        int altDistance;
        boolean tooFar = false;
        List<Vertex> markedPlots = new LinkedList<>();
        List<Vertex> intermediatePlots = new LinkedList<>();

        vertices.get(startVertexId).dist = 0;
        PriorityQueue<Vertex> queue = new PriorityQueue(vertices.values());
        System.out.print("depiling queue: ");
        int n = queue.size() / PROGRESS;

        while (!queue.isEmpty() && !tooFar) {
            v = queue.poll();
            if (v.dist > distanceDestination) {
                System.out.print("x");
                tooFar = true;
                break;
            }
            if (queue.size() % n == 0) {
                System.out.print("=");
            }
            for (Edge leaving : v.leavingEdges) {
                voisin = leaving.endVertex;
                altDistance = v.dist + leaving.length;
                if (altDistance < voisin.dist) {
                    //on a trouvé un chemi plus court. On modifie l'object (et on le desinsere reinsere pour cela)
                    queue.remove(voisin);
                    voisin.pred = v;
                    voisin.dist = altDistance;

                    queue.add(voisin);

                    if (v.dist < distanceDestination && altDistance > distanceDestination) {
                        //on passe la limite horaire
                        markedPlots.add(v);
                    } else if (altDistance < distanceDestination) {
                        //markedEdge.remove(leaving);
                    }
                }
            }
        }
        System.out.print("g");
        for(Vertex vfar : markedPlots){
            intermediatePlots.add(this.getIntermediate(vfar, distanceLimit));
        }
        System.out.println("e");
        return intermediatePlots;
        //on a la distance de tous les points.
    }
    /**
     * Version de Lara
     * moins efficace que computeDjikstraWithParameter parce que ne s'arrete pas une fois que la distance limite est atteinte
     * @param startVertexId
     * @param distanceLimit
     * @return 
     */
    public List<Vertex> computeDijkstraWithPerimeterExact(long startVertexId, int distanceLimit) {
        List<Vertex> markedEdge = new LinkedList<>();
        Vertex v;
        Vertex voisin;
        int altDistance;
        long indice = 0; //indice des points intermediaires qu'on va cr�er
        vertices.get(startVertexId).dist = 0;
        PriorityQueue<Vertex> queue = new PriorityQueue(vertices.values());
        System.out.print("depilig queue: ");
        int n = queue.size() / 5;
        while (!queue.isEmpty()) {
            v = queue.poll();
            if (queue.size() % n == 0) {
                System.out.print("=");
            }
            for (Edge leaving : v.leavingEdges) {
                voisin = leaving.endVertex;
                altDistance = v.dist + leaving.length;
                if (altDistance < voisin.dist) {
                    //on a trouvé un chemin plus court. On modifie l'object (et on le desinsere reinsere pour cela)
                    queue.remove(voisin);
                    voisin.pred = v;
                    voisin.dist = altDistance;

                    queue.add(voisin);

                    if (v.dist < distanceLimit && altDistance > distanceLimit) {
                        indice += 1;
                        //on passe la limite horaire
                        double t = (distanceLimit - v.dist) / (altDistance - v.dist);

                        markedEdge.add(leaving.distanceexacte(1 - t, indice));

                    }
                }
            }
        }

        return markedEdge;
        //on a la distance de tous les points.
    }
    
    /**
     * utilisé pour trouver le point à une distance donnée sur un trajet jusque 'dest'
     * retourne des Vertex SANS ID
     * @param dest
     * @param distance
     * @return 
     */
    private Vertex getIntermediate(Vertex dest, int distance){
        Vertex v1 = dest, v2 = dest;
        int d1, d2, dd, lat, lng;
        double c1, c2;
        while(v1.pred.dist>distance){
            v2 = v1;
            v1 = v1.pred;
        }
        d1 = (distance - v1.dist);
        d2 = v2.dist - distance;
        dd = d1 + d2;
        c1 = 1. * d1 / dd;
        c2 = 1. * d2 / dd;
        lat = (int) (c1 * v1.lat + c2 * v2.lat);
        lng = (int) (c1 * v1.lng + c2 * v2.lng);
//            System.out.println("plot " + c1+" " +" "+ c2 +" "+ dd +" "+ lat +" "+ lng + " " +e.startVertex.toString() + " " + e.endVertex.toString());
        return(new Vertex(0, lat, lng));
    }
    
    public List<Vertex> shortestPathTo(long endVertexId) {
        List<Vertex> path = new LinkedList<>();
        Vertex v = vertices.get(endVertexId);
        path.add(0, v);
        while (v.pred != null) {
            v = v.pred;
            path.add(0, v);
        }
        return path;
    }

    public void purge() {
        for (Vertex v : vertices.values()) {
            v.pred = null;
            v.dist = Integer.MAX_VALUE;
        }
    }
}
