/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networks;

import Map.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class MapLoader {

    public static Map buildMap(String filename) throws FileNotFoundException{
        File file = new File(filename);
        String[] lineData;
        Map map = new Map();
        System.out.println(Integer.MAX_VALUE);
        //try {
            // Create a new Scanner object which will read the data
            // from the file passed in. To check if there are more 
            // line to read from it we check by calling the 
            // scanner.hasNextLine() method. We then read line one 
            // by one till all line is read.
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //System.out.println(line);
                lineData = line.split(" ");
                //System.out.println(lineData[0] + ":" + lineData[1] + ":" + lineData[2] + ":" + lineData[3]);
                if(lineData[0].equals("v")){
                    //vertex
                    map.vertices.add(new Vertex(Long.parseLong(lineData[1]), Integer.parseInt(lineData[2]), Integer.parseInt(lineData[3])));
                }
                if(lineData[0].equals("a")){
                    //on a une arrete
                    map.edges.add(new Edge(Long.parseLong(lineData[1]), Long.parseLong(lineData[2]), Integer.parseInt(lineData[3])));
                }
            }
        //} catch (FileNotFoundException e) {
          //  e.printStackTrace();
       // }
        
        return map;

    }

}
