package Carte;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
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
        //try {
        // Create a new Scanner object which will read the data
        // from the file passed in. To check if there are more 
        // line to read from it we check by calling the 
        // scanner.hasNextLine() method. We then read line one 
        // by one till all line is read.
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
        //on  a créé les vertex et les aretes
        //on repasse pour les leaving et coming
        //} catch (FileNotFoundException e) {
        //  e.printStackTrace();
        // 
    }

    @Override
    public String toString() {
        String str = "Map Object ( v: " + this.vertices.size() + "; a: " + this.edges.size() + " )";
        return str;
    }

    public void computeDijkstra(int startVertexId) {

    }

    public void saveVertexToVisFile(String filename) {
        System.out.println(this.toString());
        File file = new File("vis/" + filename + ".js");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("var plottedPoints = [\n");
            int n = this.vertices.size();

            Iterator entries = this.vertices.entrySet().iterator();
            String line;
            Entry curEntry;
            Vertex curVertex = new Vertex(0, 0, 0);
            while (entries.hasNext()) {
                curEntry = (Entry) entries.next();
                //Long key = (Long) thisEntry.getKey();
                curVertex = (Vertex) curEntry.getValue();
                line = "\t" + curVertex.toGeoString() + (entries.hasNext() ? ',' : "") + "\n";
                bw.write(line);
                // ...
            }

//            if (n > 0) {
//                bw.write("\t" + this.vertices.get(0).toGeoString() + "\n");
//                for (int i = 1; i < this.vertices.size(); i++) {
//                    bw.write("\t" + this.vertices.get(i).toGeoString() + ",\n");
//                }
//            } else {
//                System.out.println("Warning : empty vertices list");
//            }
//            for(Vertex v : this.vertices){
//                bw.write("\t" + v.toGeoString() + ",\n"); 
//            }
            bw.write("];\n");
            bw.write("var centralMarker =");
            System.out.println("central vertex...");
            bw.write(curVertex.toGeoString() + ";");
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
