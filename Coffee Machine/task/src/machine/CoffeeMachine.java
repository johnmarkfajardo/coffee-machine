package machine;

import java.util.ArrayList;
import java.util.Scanner;

class CoffeeType {
    private final String name;
    private final int water; // ml of water required
    private final int milk; // ml of milk required
    private final int beans; // grams of beans required
    private final int price; // coffee cost

    public CoffeeType(String name, int water, int milk, int beans, int price) {
        this.name = name;
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getBeans() {
        return beans;
    }

    public int getPrice() {
        return price;
    }
}

public class CoffeeMachine {
    private int water = 400; // starting ml of water in coffee machine
    private int milk = 540; // starting ml of milk in coffee machine
    private int beans = 120; // starting grams of coffee beans in coffee machine
    private int money = 550; // starting money in $
    private int disposableCups = 9;
    public ArrayList<CoffeeType> coffeeTypes = new ArrayList<>();

    enum Action {
        BUY, FILL, TAKE, REMAINING, EXIT, IDLE
    }

    private Action currentAction;

    private Scanner scanner;

    public CoffeeMachine() {
        CoffeeType espresso = new CoffeeType("espresso", 250, 0, 16, 4);
        CoffeeType latte = new CoffeeType("latte", 350, 75, 20, 7);
        CoffeeType cappuccino = new CoffeeType("cappuccino", 200, 100, 12, 6);

        this.coffeeTypes.add(espresso);
        this.coffeeTypes.add(latte);
        this.coffeeTypes.add(cappuccino);

        this.scanner = new Scanner(System.in);

        do {
            this.currentAction = Action.IDLE;
            this.displayActions();

            String str = this.scanner.next();

            for (Action state : Action.values()) {
                if (state.name().equalsIgnoreCase(str)) {
                    this.currentAction = state;
                    break;
                }
            }

            switch (this.currentAction) {
                case BUY:
                    this.buyMenu(scanner);
                    break;
                case FILL:
                    this.fillMenu(scanner);
                    break;
                case TAKE:
                    this.takeMenu();
                    break;
                case REMAINING:
                    this.printStatus();
                    break;
                default:  // nothing
                    break;
            }
        } while (!this.currentAction.equals(Action.EXIT));
    }

    public static void main(String[] args) {
        new CoffeeMachine();
    }

    public void displayActions() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    public void takeMenu() {
        System.out.println("I gave you $" + this.money);
        this.money = 0;
        System.out.println();
    }

    public void fillMenu(Scanner scanner) {
        System.out.println("\nWrite how many ml of water you want to add:");
        int waterToAdd = scanner.nextInt();
        this.water += Math.max(waterToAdd, 0);
        System.out.println("Write how many ml of milk you want to add:");
        int milkToAdd = scanner.nextInt();
        this.milk += Math.max(milkToAdd, 0);
        System.out.println("Write how many grams of coffee beans you want to add:");
        int beansToAdd = scanner.nextInt();
        this.beans += Math.max(beansToAdd, 0);
        System.out.println("Write how many disposable cups of coffee you want to add:");
        int cupsToAdd = scanner.nextInt();
        this.disposableCups += Math.max(cupsToAdd, 0);
        System.out.println();
    }

    public void printStatus() {
        System.out.println("\nThe coffee machine has:");
        System.out.println(this.water + " ml of water");
        System.out.println(this.milk + " ml of milk");
        System.out.println(this.beans + " g of coffee beans");
        System.out.println(this.disposableCups + " disposable cups");
        System.out.printf("$%d of money\n\n", this.money);
    }

    public void buyMenu(Scanner scanner) {
        final String espresso = "1";
        final String latte = "2";
        final String cappuccino = "3";
        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
        String coffeeType = "";

        switch (scanner.next()) {
            case espresso:
                coffeeType = "espresso";
                break;
            case latte:
                coffeeType = "latte";
                break;
            case cappuccino:
                coffeeType = "cappuccino";
                break;
            default:
                System.out.println("error!");
                break;
        }

        if (!coffeeType.isEmpty()) {
            for (CoffeeType type : this.coffeeTypes) {
                if (type.getName().equals(coffeeType)) {
                    if (this.water < type.getWater()) {
                        System.out.println("Sorry, not enough water!\n");
                        break;
                    }

                    if (this.milk < type.getMilk()) {
                        System.out.println("Sorry, not enough milk!\n");
                        break;
                    }

                    if (this.beans < type.getBeans()) {
                        System.out.println("Sorry, not enough coffee beans!\n");
                        break;
                    }

                    if (this.disposableCups <= 0) {
                        System.out.println("Sorry, not enough disposable cups!\n");
                        break;
                    }

                    System.out.println("I have enough resources, making you a coffee!\n");

                    this.water -= type.getWater();
                    this.milk -= type.getMilk();
                    this.beans -= type.getBeans();
                    this.disposableCups--;
                    this.money += type.getPrice();
                    break;
                }
            }
        }
    }
}
