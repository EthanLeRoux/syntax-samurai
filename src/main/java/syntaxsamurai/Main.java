package syntaxsamurai;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        TextFileHandler th = new TextFileHandler();
        th.readFile();

        List<int[]> path = generateDronePath(
                th.edxDroneDepot,
                th.exEnclosures,
                th.exFoodStorages,
                th.exBattCap
        );

        System.out.println(formatPath(path));
        FileWriter fw = new FileWriter("lvl2.txt");
        fw.write(formatPath(path));
        fw.close();

    }

    public static String formatPath(List<int[]> path) {
        StringBuilder sb = new StringBuilder();
        sb.append("[[");

        for (int i = 0; i < path.size(); i++) {
            int[] coord = path.get(i);
            sb.append("(").append(coord[0]).append(",").append(coord[1]).append(")");
            if (i < path.size() - 1) {
                sb.append(",");
            }
        }

        sb.append("]]");
        return sb.toString();
    }

    public static List<int[]> generateDronePath(
            Coordinate droneDepot,
            List<Enclosure> enclosures,
            List<FoodStorage> foodStorages,
            int battCap
    ) {
        List<int[]> path = new ArrayList<>();
        Set<Enclosure> visited = new HashSet<>();


        enclosures.sort((a, b) -> Double.compare(b.importance, a.importance));

        Coordinate current = droneDepot;
        char currentFood = ' ';

        path.add(new int[]{droneDepot.x, droneDepot.y});

        for (Enclosure e : enclosures) {
            if (visited.contains(e)) continue;


            if (e.diet != currentFood) {
                FoodStorage fs = findClosestFoodStorage(current, foodStorages, e.diet);
                if (fs != null) {
                    path.add(new int[]{fs.x, fs.y});
                    current = fs;
                    currentFood = fs.diet;
                }
            }

            if(battCap<=0){
                path.add(new int[]{current.x, current.y});
            }

            path.add(new int[]{e.x, e.y});
            visited.add(e);
            current = e;
            System.out.println("Current Battery Power level: { "+battCap + " }");
        }


        path.add(new int[]{droneDepot.x, droneDepot.y});

        return path;
    }

    private static FoodStorage findClosestFoodStorage(Coordinate from, List<FoodStorage> storages, char diet) {
        FoodStorage closest = null;
        double minDist = Double.MAX_VALUE;
        for (FoodStorage fs : storages) {
            if (fs.diet == diet) {
                double dist = distance(from, fs);
                if (dist < minDist) {
                    minDist = dist;
                    closest = fs;
                }
            }
        }
        return closest;
    }

    private static double distance(Coordinate a, Coordinate b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }
}
