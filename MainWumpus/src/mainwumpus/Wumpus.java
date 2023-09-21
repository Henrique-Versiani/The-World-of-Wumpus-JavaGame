
package mainwumpus;

class Wumpus extends Enemy {
    private boolean isAlive = true;

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    public void kill() {
        isAlive = false;
    }
}
