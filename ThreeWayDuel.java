import java.io.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ThreeWayDuel {
   public static void main(String[]args) {
        Scanner scan = new Scanner(System.in);
        while(mainMenu(scan));
        scan.close();
   }
   
   public static Player[] createPlayers(Scanner scan){
        System.out.println("How many players?");
        int n = scan.nextInt();
        Player[] players = new Player[n];
        Player.resetCount();
        for(int i = 0; i < n; i++) {
            System.out.println("Enter accuracy and if the player is controlled separated by a space  ex: 50 y");
            players[i] = new Player(scan.nextInt(), (scan.next().equals("y")));
        }
        return players;
   }

   public static boolean playGame(Player[] ogPlayers, Scanner scan) {
        clearScreen();
        int round = 0;
        Player[] players = clonePlayers(ogPlayers);
        String statusUpdates = "";
        while (gameRunning(players)) {
            for (Player p : players) {
                if (p.getAlive()) {
                    if(p.getControlled()) {
                        printPlayers(players);
                        System.out.println("\t" + p.getName() + " Who to shoot? Enter yourself to miss");
                        statusUpdates += p.shoot(players[scan.nextInt()]);
                    } else {
                        statusUpdates += p.autoShoot(players);
                    }
                }
            } 
            clearScreen();
            System.out.println("Round " + round++);
            System.out.println(statusUpdates);
            statusUpdates = "";
        }
        System.out.println("Winner! " + getWinner(players).getName());
        System.out.println("Play Again ? y/n yy for same config");
        String response = scan.next();
        if (response.equals("yy")) {
            playGame(ogPlayers, scan);
        }
        return response.equals("y") | response.equals("yy");
   }

   public static boolean gameRunning(Player[] players) {
        int alive = 0;
        for(Player p: players) {
            alive += p.getAlive() ? 1 : 0;
        }
        return alive > 1;
   }

   public static void printPlayers(Player[] players) {
        for (Player p : players) {
            System.out.println("\t" + p);
        }
        System.out.println();
   }

   public static void clearScreen() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
   }

   public static Player getWinner(Player[] players) {
        for (Player p : players) {
            if (p.getAlive()) {
                return p;
            }
        }
        return null;
   }

   public static Player[] loadConfig(Scanner config) {
        Player.resetCount();
        Player[] players = new Player[config.nextInt()];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(config.nextInt(), config.next().equals("y"));
        }
        return players;
   }

   public static Player[] clonePlayers(Player[] players) {
        Player[] cloneArray = new Player[players.length];
        Player.resetCount();
        for (int i = 0; i < players.length; i++) {
            cloneArray[i] = players[i].clone();
        }
        return cloneArray;
   }

   public static void printTitle() {
        Scanner title = null;
        try{
            title = new Scanner(new File("Title.txt"));
        } catch (FileNotFoundException e) {
            System.out.print(e);
        }
        clearScreen();
        while (title.hasNextLine()) {
            System.out.println(title.nextLine());
        }
   }

   public static boolean mainMenu(Scanner scan) {
        Player[] players = null;
        printTitle();
        System.out.println("\nWelcome to the 3 Way Duel Simulator!\nEnter the name of a preset config or type manual to set up your own: ");
        boolean valid = false;
        while(!valid) {
            String response = scan.next();
            if (response.equals("manual")) {
                players = createPlayers(scan);
                valid = true;
            } else {
                Scanner configScanner = null;
                try {
                    configScanner = new Scanner(new File("PreLoads/" + response + ".txt"));
                    valid = true;
                    players = loadConfig(configScanner);
                } catch (FileNotFoundException e) {
                    System.out.println("Preset not found, please try again:");
                }
            }
        }
        return playGame(players, scan);
   }
    //Im Going Insane and not gonna mess with percentages
//    public static double getPercentage (Player[] players, Player current, Player target) {
//         int acc = current.getAccuracy()/100;
//         Player[] clone = clonePlayers(players);
//         double finalChance = 1.0;
//         clone[target.getNum()].kill();
//         finalChance *= (acc);
//         while(gameRunning(clone)) {

//         }
//         return finalChance;
//    }

//    public static Player getNextPlayer (Player[] players, Player current) {
//         if (gameRunning(players)) {
//             for (int i = 0; i < players.length; i++) {
//                 int nextIndex = current.getNum() + 1;
//                 if (nextIndex > players.length-1) {
//                     nextIndex = 0;
//                 }
//                 if (players[nextIndex].getAlive()) {
//                     return players[nextIndex];
//                 }
//             }
//         }
//         return null;
//    }
}