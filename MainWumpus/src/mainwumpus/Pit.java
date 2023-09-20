package mainwumpus;

import mainwumpus.Objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pit {
    private int number;
    private int row;
    private int col;
    private List<Pit> linkedPits;
    private List<Objects> gameObjects;

    public Pit(int number, int row, int col) {
        this.number = number;
        this.row = row;
        this.col = col;
        this.linkedPits = new ArrayList<>();
        this.gameObjects = new ArrayList<>();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pit pit = (Pit) o;
        return number == pit.number;
    }

    public void addLink(Pit linkedCave) {
        this.linkedPits.add(linkedCave);
    }

    public List<Pit> getLinkedCaves() {
        return linkedPits;
    }

    public int getNumber() {
        return number;
    }

    public List<Objects> getGameObjects() {
        return this.gameObjects;
    }

    public void addGameObject(Objects gameObject) {
        this.gameObjects.add(gameObject);
        Collections.sort(this.gameObjects);
    }

    public void removeGameObject(Objects gameObject) {
        this.gameObjects.remove(gameObject);
    }
}
