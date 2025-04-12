package syntaxsamurai;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFileHandler {


    public static void readFile() throws IOException {
        FileReader fr;
        BufferedReader br;
        FileWriter fw;
        BufferedWriter bw;

        fr = new FileReader("1.txt");
        br = new BufferedReader(fr);

        // Line 1: Zoo dimensions
        String[] zooDim = parseTuple(br.readLine());
        int zooX = Integer.parseInt(zooDim[0]);
        int zooY = Integer.parseInt(zooDim[1]);
        int zooZ = Integer.parseInt(zooDim[2]);

        // Line 2: Drone Depot
        String[] depotCoords = parseTuple(br.readLine());
        Coordinate droneDepot = new Coordinate(
                Integer.parseInt(depotCoords[0]),
                Integer.parseInt(depotCoords[1]),
                Integer.parseInt(depotCoords[2])
        );

        // Line 3: Battery Distance
        int batteryCapacity = Integer.parseInt(br.readLine());

        // Line 4: Food Storages
        List<FoodStorage> foodStorages = parseFoodStorages(br.readLine());

        // Line 5: Enclosures
        List<Enclosure> enclosures = parseEnclosures(br.readLine());

        // Line 6: Deadzones
        List<Deadzone> deadzones = parseDeadzones(br.readLine());

        br.close();

        // Print to check
        System.out.println("Zoo Size: " + zooX + "x" + zooY + "x" + zooZ);
        System.out.println("Drone Depot: (" + droneDepot.x + "," + droneDepot.y + "," + droneDepot.z + ")");
        System.out.println("Battery Capacity: " + batteryCapacity);
        System.out.println("Food Storages: " + foodStorages.size());
        System.out.println("Enclosures: " + enclosures.size());
        System.out.println("Deadzones: " + deadzones.size());
    }

    private static String[] parseTuple(String line) {
        return line.replaceAll("[()]", "").split(",");
    }

    private static List<FoodStorage> parseFoodStorages(String line) {
        List<FoodStorage> list = new ArrayList<>();
        Matcher m = Pattern.compile("\\((\\d+),(\\d+),(\\d+),([cho])\\)").matcher(line);
        while (m.find()) {
            list.add(new FoodStorage(
                    Integer.parseInt(m.group(1)),
                    Integer.parseInt(m.group(2)),
                    Integer.parseInt(m.group(3)),
                    m.group(4).charAt(0)
            ));
        }
        return list;
    }

    private static List<Enclosure> parseEnclosures(String line) {
        List<Enclosure> list = new ArrayList<>();
        Matcher m = Pattern.compile("\\((\\d+),(\\d+),(\\d+),(\\d+\\.\\d+),([cho])\\)").matcher(line);
        while (m.find()) {
            list.add(new Enclosure(
                    Integer.parseInt(m.group(1)),
                    Integer.parseInt(m.group(2)),
                    Integer.parseInt(m.group(3)),
                    Double.parseDouble(m.group(4)),
                    m.group(5).charAt(0)
            ));
        }
        return list;
    }

    private static List<Deadzone> parseDeadzones(String line) {
        List<Deadzone> list = new ArrayList<>();
        Matcher m = Pattern.compile("\\((\\d+),(\\d+),(\\d+)\\)").matcher(line);
        while (m.find()) {
            list.add(new Deadzone(
                    Integer.parseInt(m.group(1)),
                    Integer.parseInt(m.group(2)),
                    Integer.parseInt(m.group(3))
            ));
        }
        return list;
    }
}
