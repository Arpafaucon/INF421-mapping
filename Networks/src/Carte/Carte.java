package Carte;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
//import java.io.IOException;
//import java.io.FileWriter;
//import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
//import java.util.Iterator;
//import java.util.Map.Entry;
import java.util.Scanner;

public class Carte implements Serializable{

    public HashMap<Long, Vertex> vertices;
    public List<Edge> edges;
    //public HashMap<Long, DjikInfo> djikstra; //essai de découplage de la carte et du Djikstra
    
    private final int PROGRESS = 10;

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
                if(firstEdge){
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
        
        int n = queue.size()/PROGRESS;
        
        
        while(!queue.isEmpty()){
            v = queue.poll();
            for(Edge leaving : v.leavingEdges){
                if(queue.size()%n==0){
                System.out.print("=");
            }
                
                voisin = leaving.endVertex;
                altDistance = v.dist + leaving.length;
                if(altDistance < voisin.dist){
                    //on a trouvé un chemi plus court. On modifie l'object (et on le desinsere reinsere pour cela)
                    queue.remove(voisin);
                    voisin.pred = v;
                    voisin.dist = altDistance;
                    queue.add(voisin);
                }
                
            }
        }
        }
        public List<Edge> computeDijkstraLimite(long startVertexId, int limite) {
            Vertex v;
            Vertex voisin;
            int altDistance;
            List<Edge> res=new LinkedList<Edge>();
            
            vertices.get(startVertexId).dist = 0;
            PriorityQueue<Vertex> queue = new PriorityQueue(vertices.values());
            System.out.println("built queue");
            
            while(!queue.isEmpty()){
                v = queue.poll();
                for(Edge leaving : v.leavingEdges){
                    voisin = leaving.endVertex;
                    altDistance = v.dist + leaving.length;
                    int temp =v.dist;
                    if(altDistance < voisin.dist){
                        //on a trouvé un chemi plus court. On modifie l'object (et on le desinsere reinsere pour cela)
                        queue.remove(voisin);
                        voisin.pred = v;
                        voisin.dist = altDistance;
                        queue.add(voisin);
                    }
                   if ((v.dist>limite)&&(temp<limite) ){
                	   res.add(leaving);
                   }
                }
            }
            return (res);
        
       
    }
        
    
    public List<Vertex> computeDijkstraWithPerimeter(long startVertexId, int distanceLimit) {
        List<Edge> markedEdge = new LinkedList<>();
        Vertex v;
        Vertex voisin;
        int altDistance;
        boolean tooFar = false;
        
        vertices.get(startVertexId).dist = 0;
        PriorityQueue<Vertex> queue = new PriorityQueue(vertices.values());
        System.out.print("depilig queue: ");
        int n = queue.size()/PROGRESS;
        
        while(!queue.isEmpty() && !tooFar){
            v = queue.poll();
            if(v.dist > distanceLimit){
                System.out.println("got out of perimeter");
                tooFar = true;
                break;
            }
            if(queue.size()%n==0){
                System.out.print("=");
            }
            for(Edge leaving : v.leavingEdges){
                voisin = leaving.endVertex;
                altDistance = v.dist + leaving.length;
                if(altDistance < voisin.dist){
                    //on a trouvé un chemi plus court. On modifie l'object (et on le desinsere reinsere pour cela)
                    queue.remove(voisin);
                    voisin.pred = v;
                    voisin.dist = altDistance;
                    
                    queue.add(voisin);
                    
                    if(v.dist < distanceLimit && altDistance > distanceLimit){
                        //on passe la limite horaire
                        markedEdge.add(leaving);
                    }
                    else if(altDistance < distanceLimit){
                        //markedEdge.remove(leaving);
                    }
                }
            }
        }
        
        List<Vertex> markedPlots = new LinkedList<>();
        int d1, d2, dd, lat, lng;
        double c1, c2;
        for(Edge e : markedEdge){
            d1 = (distanceLimit - e.startVertex.dist);
            d2 = e.endVertex.dist - distanceLimit;
            dd = d1 + d2;
            c1 = 1.*d1 / dd;
            c2 = 1.*d2 / dd;
            lat = (int)(c1* e.startVertex.lat + c2 * e.endVertex.lat);
            lng = (int)(c1* e.startVertex.lng + c2 * e.endVertex.lng);
            System.out.println("plot " + c1+" " +" "+ c2 +" "+ dd +" "+ lat +" "+ lng + " " +e.startVertex.toString() + " " + e.endVertex.toString());
            markedPlots.add(new Vertex(0, lat, lng));
        }
        
        return markedPlots;
        //on a la distance de tous les points.
    }
    
    public List<Vertex> computeDijkstraWithPerimeterExact(long startVertexId, int distanceLimit) {
        List<Vertex> markedEdge = new LinkedList<>();
        Vertex v;
        Vertex voisin;
        int altDistance;
        long indice =0; //indice des points intermediaires qu'on va cr�er
        vertices.get(startVertexId).dist = 0;
        PriorityQueue<Vertex> queue = new PriorityQueue(vertices.values());
        System.out.print("depilig queue: ");
        int n = queue.size()/5;
        
        
        
        
        while(!queue.isEmpty()){
            v = queue.poll();
            if(queue.size()%n==0){
                System.out.print("=");
            }
            for(Edge leaving : v.leavingEdges){
                voisin = leaving.endVertex;
                altDistance = v.dist + leaving.length;
                if(altDistance < voisin.dist){
                    //on a trouvé un chemin plus court. On modifie l'object (et on le desinsere reinsere pour cela)
                    queue.remove(voisin);
                    voisin.pred = v;
                    voisin.dist = altDistance;
                    
                    queue.add(voisin);
                    
                    if(v.dist < distanceLimit && altDistance > distanceLimit){
                    	indice+=1;
                        //on passe la limite horaire
                    	double t = (distanceLimit-v.dist)/(altDistance-v.dist);
                    	 
                        markedEdge.add(leaving.distanceexacte(1-t, indice));
                   
                    }
                }
            }
        }
        
        return markedEdge;
        //on a la distance de tous les points.
    }
    
    public List<Vertex> shortestPathTo(long endVertexId){
        List<Vertex> path = new LinkedList<>();
        Vertex v = vertices.get(endVertexId);
        path.add(0, v);
        while(v.pred != null){
            v = v.pred;
            path.add(0, v);
        }
        return path;
    }
}
