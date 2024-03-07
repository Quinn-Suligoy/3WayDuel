import java.util.Random;
public class Player {
    private int accuracy;
    private boolean alive;
    private boolean controlled;   
    private int num; 
    private static int count;

    public Player(int acc, boolean cont) {
        accuracy = acc;
        alive = true;
        controlled = cont;
        num = count++;
    }
    
    public String shoot(Player target) {
        Random rand = new Random();
        if (this.equals(target)) {
            return ("\n\t" + this.getName() + " Missed on purpose\n");
        } else {
            if (this.accuracy > rand.nextInt(100)) {
                target.alive = false;
                return ("\n\t" + this.getName() + " Hit " + target.getName() + "\n");
            } else {
                return ("\n\t" + this.getName() + " Missed " + target.getName() + "\n ");
            }
        }
    }

    public String autoShoot(Player[] players) {
        Player target = (players[0] == this ? players[1] : players[0]); 
        for (Player p : players) {
            if (p.getAccuracy() > target.getAccuracy() & p != this & p.getAlive()) {
                target = p;
            }
        }
        return shoot(target);
    }

    public void kill () {
        this.alive = false;
    }

    public Player getTarget(Player[] players) {
        Player target = (players[0] == this ? players[1] : players[0]); 
        for (Player p : players) {
            if (p.getAccuracy() > target.getAccuracy() & p != this & p.getAlive()) {
                target = p;
            }
        }
        return target;
    }

    public boolean getAlive() {
        return this.alive;
    }

    public int getAccuracy() {
        return this.accuracy;
    }

    public boolean getControlled() {
        return this.controlled;
    }

    public String getName() {
        return "Player " + num;
    }

    public String toString() {
        if (alive) {
            return ("Player: " + num + " Accuracy: " + accuracy + " Controlled: " + controlled);
        } else {
            return "Player: " + num + " DEAD";
        }
    }

    public Player clone() {
        Player clone = new Player(accuracy, controlled);
        return clone;
    }

    public static void resetCount() {
        count = 0;
    }

    public int getNum() {
        return num;
    }
}
