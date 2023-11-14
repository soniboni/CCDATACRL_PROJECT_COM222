package PathDatabase;

public class Path {
    public String path;
    public int distance;
    public int duration;
    public int cost;

    public Path(String path, int distance, int duration, int cost){
        this.path = path;
        this.distance = distance;
        this.duration = duration;
        this.cost = cost;
    }
}
