package Map;

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

public class Map {

    public HashMap<Long, Vertex> vertices;
    public List<Edge> edges;

    public Map() {
        vertices = new HashMap();
        edges = new ArrayList<>();
    }

    @Override
    public String toString() {
        String str = "Map Object ( v: " + this.vertices.size() + "; a: " + this.edges.size() + " )";
        return str;
    }

    public void listVertex() {
        for (int i = 0; i < this.vertices.size(); i++) {
            System.out.println(i + ">" + this.vertices.get(i).toString() + "\n");
        }
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
            String line = "";
            Entry curEntry;
            Vertex curVertex = new Vertex(0,0,0);
            while (entries.hasNext()) {
                Entry thisEntry = (Entry) entries.next();
                //Long key = (Long) thisEntry.getKey();
                curVertex = (Vertex) thisEntry.getValue();
                line = "\t" + curVertex.toGeoString() + (entries.hasNext()?',':"") + "\n";
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
