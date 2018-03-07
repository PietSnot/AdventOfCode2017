/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaranch14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Piet
 */
public class CodyBiggs {

}

class Obstacle implements GameEntity {

    final private String name;
    final private int strength;
    final protected int points;
    private int health = 50;

    protected Obstacle(String name, int strength) {
        this(name, strength, 2 * strength);
    }

    protected Obstacle(String name, int strength, int points) {
        this.name = name;
        this.strength = strength;
        this.points = points;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public int getStrength() {

        return strength;
    }

    @Override
    public int getHealth() {

        return health;
    }

    @Override
    public int getPoints() {

        return points;
    }

    @Override
    public void reduceHealth(int reduced) {

    }

    @Override
    public boolean isAlive() {

        return false;
    }
}

interface GameEntity {

    String getName();

    int getStrength();

    int getHealth();

    int getPoints();

    void reduceHealth(int reduced);

    boolean isAlive();

}

class Troll extends Enemy {

    private final static int TROLL_STRENGTH = 7;
    private final static int TROLL_SPEED = 8;
    private final static int TROLL_POINTS = 8;

    public Troll(int numVials) {
        super("Troll", TROLL_STRENGTH, TROLL_SPEED, TROLL_POINTS, numVials);
    }

}

class Jinn extends Enemy {

    private final static int JINN_STRENGTH = 7;
    private final static int JINN_SPEED = 8;
    private final static int JINN_POINTS = 11;

    public Jinn(int numVials) {
        super("Jinn", JINN_STRENGTH, JINN_SPEED, numVials, JINN_POINTS);
    }
}

class Wall extends Obstacle {

    private final static int WALL_STRENGTH = 10;

    protected Wall() {
        super("Wall", WALL_STRENGTH);
    }
}

class Enemy extends Obstacle implements Warrior, Collector {

    final private int speed;
    private int numVials = 0;
    protected boolean canAttack = false;
    private int totalNumVials;

    public Enemy(String name, int strength, int speed, int numVials) {
        super(name, strength);
        this.speed = speed;
        this.numVials = numVials;
    }

    public Enemy(String name, int strength, int speed, int numVials, int points) {
        super(name, strength, points);
        this.speed = speed;
        this.numVials = numVials;
    }

    // This implements the attack method for the Enemy.  Do not modify.
    public void attack(GameEntity player) {
        Game.getInstance().attack(this, player);
    }

    @Override
    public int getNumVials() {

        return numVials;
    }

    @Override
    public void addVials(int vials) {
        numVials += vials;

    }

    @Override
    public int relinquishVials() {
        numVials = getNumVials() - getNumVials();

        return numVials;
    }

    @Override
    public int getSpeed() {

        return speed;
    }

    @Override
    public boolean canAttack() {

        return canAttack;
    }

}

class Player implements Warrior, Collector {

    private int points;
    private int health = 50;
    private int totalVial;
    private String name;
    private int strength;
    final private int speed;
    private int numVials;
    protected boolean canAttack = false;

    public Player(String name, int strength, int speed) {
        this.speed = speed;
        this.name = name;
        this.strength = strength;
    }

    // DO NOT MODIFY
    public void run(GameEntity opponent) {
        // calls the retreat method in Game
        Game.getInstance().retreat(this, opponent);
    }

    // DO NOT MODIFY
    public void attack(GameEntity opponent) {
        // The player calls the 'attack' method in Game.
        Game.getInstance().attack(this, opponent);
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public int getStrength() {

        return strength;
    }

    @Override
    public int getHealth() {

        return health;
    }

    @Override
    public int getPoints() {

        return points;
    }

    @Override
    public void reduceHealth(int reduced) {
        health = health - reduced;

    }

    @Override
    public boolean isAlive() {

        return false;
    }

    @Override
    public int getNumVials() {

        return numVials;
    }

    @Override
    public void addVials(int vials) {
        numVials += vials;

    }

    @Override
    public int relinquishVials() {
        numVials -= numVials;
        return getNumVials();
    }

    @Override
    public int getSpeed() {

        return speed;
    }

    @Override
    public boolean canAttack() {

        return canAttack;
    }

    public void Drink() {
        int MAX_HEALTH = 150;

        if (getHealth() < MAX_HEALTH) {
            health = getHealth() + 100;
        }

        if (getHealth() > MAX_HEALTH) {
            health = MAX_HEALTH;
        }
    }
}

class Game {

    static private Game instance;

    private Game() {
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        {
            System.out.println("Game class object created!");
        }
        return instance;
    }
    final public int ATTACK_LOSS_FACTOR = 5;
    final public int DEFEND_LOSS_FACTOR = 6;
    final public int MAX_STRENGTH = 10;
    final public int DEFAULT_STRENGTH = 6;
    final public int MAX_ABILITIES = 15;

    Scanner console = new Scanner(System.in);
    Random rand = new Random();

    ArrayList<Obstacle> opponents = new ArrayList<>();

    // TODO -- add the code needed to make the Game a singleton
    // TODO, fix the loadChallenge method such that it reads from
    // the filename.
    public void loadChallenges(String filename) throws FileNotFoundException {
        int size;
        Scanner input = new Scanner(new File(filename));
        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] opp = line.split(",");
            System.out.println(opp.length);

            //Obstacle o = new Obstacle(opp[0], Integer.parseInt(opp[1]));
            /*if(opp[0].equals("Troll")){
                opponents.add(new Troll(1));
                opponents.add(new Troll(0));
                opponents.add(new Troll(1));
                opponents.add(new Troll(0));
            }else if(opp[0].equals("Jinn")) {
                opponents.add(new Jinn(1));
                opponents.add(new Jinn(0));
            }else if(opp[0].equals("Wall")) {
                size = opponents.size();
                 
            }
             */
        }
        input.close();
    }// end loadChallenges

    public void play() {
        Player player;
        String name;
        int strength;
        Obstacle badGuy = null;

        // have we loaded the opponents?
        if (opponents.size() == 0) {
            System.err.println("No opponents have been loaded");
            return;
        }

        System.out.print("Please enter a name for your player. ");
        name = console.nextLine();
        System.out.printf("Please enter the strength for %s.[1-10] ", name);
        strength = console.nextInt();
        console.nextLine();

        if (strength > MAX_STRENGTH || strength < 1) {
            System.err.printf("invalid strength, setting to default(%d)%n", DEFAULT_STRENGTH);
            strength = DEFAULT_STRENGTH;
        }

        player = new Player(name, strength, MAX_ABILITIES - strength);

        // while loop to play game
        while (opponents.size() > 0) {
            int selection;
            // get the opponent at the top of the list
            if (badGuy == null || !badGuy.isAlive()) {
                badGuy = opponents.remove(opponents.size() - 1);
            }

            // display information to the user
            show(player);
            show(badGuy);

            selection = getMenuSelection();
            switch (selection) {
                case 'R':
                    System.out.println("RETREAT!");
                    retreat(player, badGuy);
                    if (!badGuy.isAlive()) {
                        // announce the defeat and award points
                        //System.out.printf("Congratulations!  You have defeated %s%n", badGuy.getName());
                        //player.addPoints(badGuy.getPoints());
                    } else {
                        // return the opponent to the list
                        opponents.add(rand.nextInt(opponents.size() - 1), badGuy);
                        badGuy = null;
                    }
                    break;
                case 'A':
                    System.out.println("ATTACK!");
                    player.attack(badGuy);
                    if (!badGuy.isAlive()) {
                        System.out.printf("Congratulations!  You have defeated %s%n", badGuy.getName());
                        //player.addPoints(badGuy.getPoints());
                    }
                    break;
                case 'D':
                    System.out.println("DRINK!");
                    //  drink(player);
                    break;
                case 'Q':
                    System.out.println("QUIT!");
                    return;
            }
            if (!player.isAlive()) {
                System.out.printf("Sorry, %s!  You have been killed %n", player.getName());
                return;
            }
        }
    }

    private int getMenuSelection() {
        char selection = 'x';

        System.out.println("What would you like to do?");
        System.out.println("(R)etreat");
        System.out.println("(A)ttack");
        System.out.println("(D)rink vial");
        System.out.println("(Q)uit");
        selection = console.next().charAt(0);
        return Character.toUpperCase(selection);
    }

    private void show(GameEntity o) {
        System.out.println("-----------------------------");
        System.out.printf("Name:     %10s%n", o.getName());
        System.out.printf("Strength: %10d%n", o.getStrength());
        System.out.printf("Health:   %10d%n", o.getHealth());
        System.out.printf("Points:   %10d%n", o.getPoints());
        if (o instanceof Collector) {
            System.out.printf("Vials:   %10d%n", ((Collector) o).getNumVials());
        }
    }

    // Note:  may be called from player (attack) or enemy (retreat)
    public void attack(Warrior attacker, GameEntity defender) {
        GameEntity winner = null; // 
        GameEntity loser = null;

        // determine losses based on random number
        // the plus one assures that some damage is always inflicted
        int defenderLoss = (rand.nextInt(attacker.getStrength()) + 1) * ATTACK_LOSS_FACTOR;

        // defender losses
        System.err.printf("Defender looses %d%n", defenderLoss);
        defender.reduceHealth(defenderLoss);

        // if defender is killed on first strike, attacker has no loss
        // otherwise, defender strikes back and attacker also loses health
        if (defender.isAlive()) {
            int attackerLoss = rand.nextInt(defender.getStrength()) * DEFEND_LOSS_FACTOR;
            System.err.printf("Attacker looses %d%n", attackerLoss);
            attacker.reduceHealth(attackerLoss);
            // if the attacker was killed, make the defender the winner
            if (!attacker.isAlive()) {
                winner = defender;
                loser = attacker;
            }
        } else {
            winner = attacker;
            loser = defender;
        }

        // If we have a winner (still alive) and they collects vials, move them to the winner
        if (winner != null && winner.isAlive() && winner instanceof Collector && loser instanceof Collector) {
            ((Collector) winner).addVials(((Collector) loser).relinquishVials());
        }
    }

    // called from player class
    public void retreat(Player player, GameEntity defender) {
        // can defender attack?
        if (defender instanceof Warrior) {
            Warrior pursuer = (Warrior) defender;
            // chase attacker
            // the player with the greater speed has an advantage, but doesn't necessarily win
            if (player.getSpeed() * rand.nextDouble() < pursuer.getSpeed() * rand.nextDouble()) {
                pursuer.attack(player);
            }
        }
    }

    //private void drink(Player player)
    {
        //player.drink();
    }
}

interface Warrior extends GameEntity {

    int getSpeed();

    boolean canAttack();

    void attack(GameEntity game);

}

interface Collector {

    int getNumVials();

    void addVials(int vials);

    int relinquishVials();

}
