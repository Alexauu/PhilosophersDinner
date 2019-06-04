package Utilities;

import java.util.Random;

public class Utils {

    private static int THINKING = 12;
    private static int HUNGRY  = 2;
    private static int EATING = 12;

    public static int randomThink(){
        int randomTime = new Random().nextInt(THINKING);
        return (randomTime+1)*1000; //1 to THINKING_BOUNDARY sec
    }

    public static int randomHungry() {
        int randomTime = new Random().nextInt(HUNGRY);
        return (randomTime+1)*1000;
    }

    public static int randomEat(){
        int randomTime = new Random().nextInt(EATING);
        return (randomTime+1)*1000;
    }

}
