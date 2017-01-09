/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Carte;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * objetcif : construire uencarte de France sur quadrillage qui puisse étudier les phénomènes e persolation
 * 
 */
public class Percolation {
  
    final int N_VERT = 235*4;
    final int N_HORZ = 239*4;
//    final int N_VERT = 940;
//    final int N_HORZ = 956;
    final int GPS_N = 51070000;
    final int GPS_S = 42540000;
    final int GPS_O = -4790000;
    final int GPS_E = 8110000;
    
    final int GPS_UV = (GPS_N - GPS_S)/N_VERT;
    final int GPS_UH = (GPS_E - GPS_O)/N_HORZ;
    
    final int LEN_MS = 60000; //1 minute par km
    
    ArrayList<Vertex> vertices = new ArrayList<>();
    ArrayList<Edge> edges = new ArrayList<>();
    
    public Percolation(double p){
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        genVertices();
        genEdges(p);
    }
    
    public void genVertices(){
        Vertex v;
        for (int i = 0; i < N_VERT; i++) {
            for (int j = 0; j < N_HORZ; j++) {
                v = new Vertex(N_HORZ*i + j, GPS_S + GPS_UV * i, GPS_O + GPS_UH * j);
                vertices.add(v);
            }
        }
    }
    
    public void genEdges(double p){
        Random rand = new Random();
        Edge e;
        for (int i = 0; i < N_VERT; i++) {
            for (int j = 0; j < N_HORZ; j++) {
                if(i>0 && rand.nextDouble() < p){ //edge vers le haut
                    e = new Edge(N_HORZ*i + j, N_HORZ*(i-1) + j, LEN_MS);
                    edges.add(e);
                }
                if(i<N_VERT-1  && rand.nextDouble() < p){//vers le bas
                    e = new Edge(N_HORZ*i + j, N_HORZ*(i+1) + j, LEN_MS);
                    edges.add(e);
                }
                if(j>0  && rand.nextDouble() < p){ //edge vers la gauche
                    e = new Edge(N_HORZ*i + j, N_HORZ*i + j-1, LEN_MS);
                    edges.add(e);
                }
                if(j<N_HORZ-1  && rand.nextDouble() < p){//vers la droite
                    e = new Edge(N_HORZ*i + j, N_HORZ*i + j+1, LEN_MS);
                    edges.add(e);
                }
            }
        }
    }
    
    public void saveData(String filename){
        String totalFilename = "data/" + filename + ".in";
        File file = new File(totalFilename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                for(Vertex v : vertices){
                    bw.write(v.toDataString() + "\n");
                }
                for(Edge e : edges){
                    bw.write(e.toDataString() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("saved : " + totalFilename);
    }
}
