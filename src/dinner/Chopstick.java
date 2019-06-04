package dinner;

import House.Room;
import javafx.application.Platform;

import java.util.concurrent.locks.ReentrantLock;

public class Chopstick extends ReentrantLock {


    private int id;
    private boolean taken;
    public Chopstick(int id) {
        this.id = id;
    }


    public boolean pick(Philosopher philosopher) {
        if (tryLock()) {
            taken = true;
            if(philosopher != null) {
                Platform.runLater(() -> {
//                    Tool.showOnConsole(philosopher + " 拿起了筷子 #" + (id + 1) + "\n");
                });

                if(philosopher.getId() == id) {
                    Room.showHands(2*philosopher.getId()+1);
                }else Room.showHands(2*philosopher.getId());

                System.out.println(philosopher + " 拿起了筷子 #" + (id+1));
            }
            Room.setCover(id,true);
            return true;
        }
        return false;
    }

    public boolean drop(Philosopher philosopher) {
        if (isHeldByCurrentThread()) {
            unlock();
            taken = false;
            if(philosopher != null) {
               if(philosopher.getId() == id) {
                    Room.hideHands(2*philosopher.getId()+1);
                }else Room.hideHands(2*philosopher.getId());
                Room.setCover(id,false);
                System.out.println(philosopher + " 放下了筷子 #" + (id+1));
            }
            return true;
        }
        return false;
    }

}
