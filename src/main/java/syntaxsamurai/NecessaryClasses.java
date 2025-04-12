package syntaxsamurai;

public class NecessaryClasses {

}

class Coordinate {
    int x, y, z;
    public Coordinate(int x, int y, int z) {
        this.x = x; this.y = y; this.z = z;
    }
}

class FoodStorage extends Coordinate {
    char diet;
    public FoodStorage(int x, int y, int z, char diet) {
        super(x, y, z);
        this.diet = diet;
    }
}

class Enclosure extends Coordinate {
    double importance;
    char diet;
    public Enclosure(int x, int y, int z, double importance, char diet) {
        super(x, y, z);
        this.importance = importance;
        this.diet = diet;
    }
}

class Deadzone {
    int x, y, radius;
    public Deadzone(int x, int y, int radius) {
        this.x = x; this.y = y; this.radius = radius;
    }
}

