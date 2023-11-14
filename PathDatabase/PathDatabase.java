package PathDatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PathDatabase {
    private HashMap<String, List<Path>> paths = new HashMap<>();
    private HashMap<String, Path> edges = new HashMap<>();
    private static PathDatabase instance;
    private PathDatabase(){
        parsePaths();
        parseEdges();
    }
    // SINGLETON PATTERN
    public static PathDatabase getInstance(){
        if (instance == null) instance = new PathDatabase();
        return instance;
    }
    public void parsePaths(){
        try (FileReader fileReader = new FileReader("src/PathDatabase/Paths")){

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split("\\|");
                String[] values = data[1].split(",");
                String[] countries = data[0].split(",");

                paths.putIfAbsent(countries[countries.length - 1], new ArrayList<>());
                paths.get(countries[countries.length - 1]).
                                add(new Path(data[0], Integer.parseInt(values[0]),
                                Integer.parseInt(values[1]), Integer.parseInt(values[2])));
            }

            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseEdges(){
        try (FileReader fileReader = new FileReader("src/PathDatabase/CountryValueData")){

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");

                Path e = new Path(data[0] + " to " + data[1], Integer.parseInt(data[2]),
                                            Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                Path eOppossite = new Path(data[1] + " to " + data[0], Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                edges.putIfAbsent(e.path, e);
                edges.putIfAbsent(eOppossite.path, eOppossite);
            }

            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Path> getAllPaths(String route){
        String[] countries = route.split(",");
        int n = countries.length;

        List<Path> possiblePaths = paths.get(countries[n - 1]);
        List<Path> truePaths = new ArrayList<>();

        for (Path p: possiblePaths)
            if (isSubsequence(p.path.split(","), countries)) truePaths.add(p);

        return truePaths;
    }

    public HashMap<String, Path> getAllEdges(){
        return edges;
    }

    public boolean isSubsequence(String[] root, String[] toCheck){
        int i = 0;

        for (String s: root){
            if (Objects.equals(s, toCheck[i])) i++;
            if (i == toCheck.length) return true;
        }

        return false;
    }
}
