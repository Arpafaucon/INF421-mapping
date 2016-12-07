package Carte;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.FileWriter;
//import java.io.BufferedWriter;
import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map.Entry;
import java.util.Scanner;

public class Carte {

    public HashMap<Long, Vertex> vertices;
    public List<Edge> edges;

    public Carte() {
        vertices = new HashMap();
        edges = new ArrayList<>();
    }
    /**
     * 
     * @param filename : fichier à charger
     * @throws FileNotFoundException 
     */
    public Carte(String filename) throws FileNotFoundException {
        
        File file = new File(filename);
        vertices = new HashMap();
        edges = new ArrayList<>();

        String[] lineData;
        Scanner scanner = new Scanner(file);
        Edge curEdge;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lineData = line.split(" ");
            if (lineData[0].equals("v")) {
                //vertex
                vertices.put(Long.parseLong(lineData[1]), new Vertex(Long.parseLong(lineData[1]), Integer.parseInt(lineData[2]), Integer.parseInt(lineData[3])));
            }
            if (lineData[0].equals("a")) {
                //on a une arrete
                curEdge = new Edge(Long.parseLong(lineData[1]), Long.parseLong(lineData[2]), Integer.parseInt(lineData[3]));
                edges.add(curEdge);
                vertices.get(Long.parseLong(lineData[1])).leavingEdges.add(curEdge);
                vertices.get(Long.parseLong(lineData[2])).comingEdges.add(curEdge);
            }
        }
    }

    @Override
    public String toString() {
        String str = "Map Object ( v: " + this.vertices.size() + "; a: " + this.edges.size() + " )";
        return str;
    }

    public void computeDijkstra(int startVertexId) {

    }
}
