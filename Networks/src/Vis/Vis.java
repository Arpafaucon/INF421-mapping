/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vis;

import Carte.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author arpaf
 */
public class Vis {

    public static void save(List<Vertex> plottedVertex, Vertex centralVertex, String filename) {
    	System.out.println(System.getProperty("user.dir"));
        File file = new File("vis/" + filename + ".js");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write("var plottedPoints = [\n");
                int n = plottedVertex.size();
                String line;
                for (int i = 0; i < n; i++) {
                    line = "\t" + plottedVertex.get(i).toGeoString() + (i < n - 1 ? ',' : "") + "\n";
                    bw.write(line);
                }   bw.write("];\n");
                bw.write("var centralMarker =");
                System.out.println("central vertex...");
                bw.write(centralVertex.toGeoString() + ";");
//            Map.Entry curEntry;
//            Vertex curVertex = new Vertex(0, 0, 0);
//            while (entries.hasNext()) {
//                curEntry = (Map.Entry) entries.next();
//                //Long key = (Long) thisEntry.getKey();
//                curVertex = (Vertex) curEntry.getValue();
//                line = "\t" + curVertex.toGeoString() + (entries.hasNext() ? ',' : "") + "\n";
//                bw.write(line);
//                // ...
//            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void saveJSON(List<Vertex> plottedVertex, Vertex centralVertex, String filename) {
    	System.out.println(System.getProperty("user.dir"));
        File file = new File("vis/" + filename + ".json");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write("{\"plottedPoints\" : [\n");
                int n = plottedVertex.size();
                String line;
                for (int i = 0; i < n; i++) {
                    line = "\t" + plottedVertex.get(i).toGeoString() + (i < n - 1 ? ',' : "") + "\n";
                    bw.write(line);
                }   bw.write("],\n");
                bw.write("\"centralMarker\" :");
                bw.write(centralVertex.toGeoString() + "}");
//            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(Carte carte, String filename) {
        ArrayList<Vertex> plottedVertex = new ArrayList<>(carte.vertices.values());
        Vertex centralVertex = plottedVertex.get(0);
        Vis.save(plottedVertex, centralVertex, filename);
    }
    
    public static void savePath(List<Vertex> path, String filename){
        Vis.save(path, path.get(0), filename);
    }
    
    public static void savePerimeter(Vertex startVertex, List<Edge> edges, String filename){
        List<Vertex> points = new ArrayList<>();
        for(Edge e : edges){
            points.add(e.endVertex);
        }
        Vis.save(points, startVertex, filename);
    }
}
