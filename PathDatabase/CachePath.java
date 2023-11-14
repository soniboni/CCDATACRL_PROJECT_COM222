package PathDatabase;

import java.io.*;
import java.util.*;

public class CachePath {
    // Responsible for caching all the possible path in teh given map
    // for easier retrieval. Avoid running multiple times
    HashMap<String, List<Path>> graph = new HashMap<>();
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;

    public static void main(String[] args){
        new CachePath();
    }

    public CachePath(){
        initializeGraph();
        computePaths();
    }

    public void initializeGraph(){
        try (FileReader fileReader = new FileReader("src/PathDatabase/CountryValueData")){

            bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");

                graph.putIfAbsent(data[0], new ArrayList<>());
                graph.putIfAbsent(data[1], new ArrayList<>());

                graph.get(data[0]).add(new Path(data[1], Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]), Integer.parseInt(data[4])));
                graph.get(data[1]).add(new Path(data[0], Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]), Integer.parseInt(data[4])));
            }

            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void computePaths(){
        try (FileWriter fileWriter = new FileWriter("src/PathDatabase/Paths")){
            bufferedWriter = new BufferedWriter(fileWriter);
            // DFS approach in computing all the possible paths
            HashSet<String> visited = new HashSet<>();
            visited.add("philippines");
            getRoutes("philippines", visited, new Path("philippines", 0, 0, 0));
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getRoutes(String prev, HashSet<String> visited, Path path) throws IOException {
        String curr = path.path;
        bufferedWriter.write(curr + "|"
                + path.distance  + "," + path.duration+ "," + path.cost + "\n");

        for (Path p: graph.get(prev)) {
            if (visited.contains(p.path)) continue;
            // Mark as visited for backtracking
            visited.add(p.path);
            // Update the values
            path.path = path.path + "," + p.path;
            path.distance += p.distance;
            path.duration += p.duration;
            path.cost += p.cost;
            getRoutes(p.path, visited, path);
            // Reset for backtracking
            visited.remove(p.path);
            path.path = curr;
            path.distance -= p.distance;
            path.duration -= p.duration;
            path.cost -= p.cost;
        }
    }
}
