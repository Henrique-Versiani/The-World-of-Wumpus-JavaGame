
package mainwumpus;

import mainwumpus.Pit;

public abstract class Objects implements Comparable<Objects> {
    String id;
    private Pit pit;
    private int precedence;


    public void setId(String id) {
        this.id=id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Objects Objects = (Objects) o;
        return id.equals(Objects.id);
    }

    public void setPit(Pit pit) {
        this.pit = pit;
    }

    public Pit getCave() {
        return pit;
    }

    public void setPrecedence(int precedence) {
        this.precedence=precedence;
    }

    public int getPrecedence() {
        return precedence;
    }

    public int compareTo(Objects objects){
        return this.precedence-objects.getPrecedence();
    }
}
