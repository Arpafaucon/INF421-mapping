package Map;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Map {

    public List<Vertex> vertices;
    public List<Edge> edges;

    public Map() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }
    
    @Override
    public String toString(){
        String str = "Map Object ( v: " + this.vertices.size() + "; a: " + this.edges.size() +" )";
        return str;
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
            for (int i = 0; i < this.vertices.size() -1; i++) {
                bw.write("\t" + this.vertices.get(i).toGeoString() + ",\n"); 
            }
//            for(Vertex v : this.vertices){
//                bw.write("\t" + v.toGeoString() + ",\n"); 
//            }
            bw.write("\t" + this.vertices.get(this.vertices.size()-1).toGeoString() + "\n");
            bw.write("];\n");
            bw.write("var centralMarker =\n");
            System.out.println("central vertex...");
            bw.write(this.vertices.get(0).toGeoString() + ";");
            bw.close();
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
