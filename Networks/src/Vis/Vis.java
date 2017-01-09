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
import java.util.List;

/**
 *
 * @author arpaf
 */
public class Vis {
 
    public static void saveJSON(List<Vertex> plottedVertex, Vertex centralVertex, String filename) {
        String totalFilename = "vis/" + filename + ".json";
        File file = new File(totalFilename);
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
        System.out.println("saved : " + totalFilename);
    }
    
        public static void saveIsochronesJSON(final List<Integer> distances, final List<List<Vertex>> plottedVertex, final Vertex centralVertex, final String filename) {
        int m,n;
        String totalFilename = "vis/" + filename + ".json";
        File file = new File(totalFilename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write("{\"plottedPoints\" : [\n");
                n = plottedVertex.size();
                String line;
                for (int i = 0; i < n; i++) {
                    bw.write("\t{\n\t\"distance\" : " + distances.get(i) + ",\n"
                            + "\t\"points\" : [\n");
                    m = plottedVertex.get(i).size();
                    for (int j = 0; j < m; j++) {
                        line = "\t\t" + plottedVertex.get(i).get(j).toGeoString() + (j < m - 1 ? ',' : "") + "\n";
                        bw.write(line);
                    }
                    bw.write("\t\t]\n\t}"+ (i < n - 1 ? ',' : "") + "\n");
                }   
                bw.write("],\n");
                bw.write("\"centralMarker\" :");
                bw.write(centralVertex.toGeoString() + "}");
//            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("saved : " + totalFilename);
    }
}
