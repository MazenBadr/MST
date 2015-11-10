/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MST;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author mazenbadr
 */
public class MST {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        InputStream stream = ClassLoader.getSystemResourceAsStream("edges_.txt");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
        String line;
        Hashtable<Integer, ArrayList<int[]>> graph = new Hashtable<>();
        int size = 0;
        try {
            line = buffer.readLine();
            String[] s = line.trim().split("\\s+");
            size = Integer.valueOf(s[0]);
            while ((line = buffer.readLine()) != null) {
                String[] vals = line.trim().split("\\s+");
                int[] temp = new int[2];
                temp[0] = Integer.parseInt(vals[1]);
                temp[1] = Integer.parseInt(vals[2]);

                int[] temp2 = new int[2];
                temp2[0] = Integer.parseInt(vals[0]);
                temp2[1] = Integer.parseInt(vals[2]);
                ArrayList<int[]> values = new ArrayList<>();
                values.add(temp);

                if (graph.putIfAbsent(Integer.parseInt(vals[0]), values) != null) {
                    graph.get(Integer.parseInt(vals[0])).add(temp);
                }
                values = new ArrayList<>();
                values.add(temp2);
                if (graph.putIfAbsent(Integer.parseInt(vals[1]), values) != null) {
                    graph.get(Integer.parseInt(vals[1])).add(temp2);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MST.class.getName()).log(Level.SEVERE, null, ex);
        }
        int[] costs = new int[graph.size() + 2];
        for (int i = 0; i < costs.length; i++) {
            costs[i] = Integer.MAX_VALUE;
        }
        Long totalCost = Long.valueOf(0);
        HashSet<Integer> explored = new HashSet<>();
        costs[1] = 0;
        while (explored.size() != size) {
            int node = -1;
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < costs.length; i++) {
                if (costs[i] < min && !explored.contains(i)) {
                    min = costs[i];
                    node = i;
                }
            }
            explored.add(node);
            totalCost += costs[node];
            if (graph.get(node) != null) {
                for (int[] vertex : graph.get(node)) {
                    if (!explored.contains(vertex[0]) && vertex[1] < costs[vertex[0]]) {
                        costs[vertex[0]] = vertex[1];
                    }
                }
            }
        }
        System.out.println(totalCost);
    }

}
