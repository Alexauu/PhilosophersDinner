package dinner;

import House.Room;
import House.Tool;
import javafx.application.Platform;

public class Waitress implements Runnable{

    private  static boolean isStopped = true;

    @Override
    public void run() {
        isStopped = false;
        while (Tool.RUNNING) {
                if (Tool.willDie()) {
                        Tool.RUNNING = false;
                        Tool.LIVING = false;
                try {
                    Thread.sleep(1800);
                        Tool.showOnConsole("此时每位哲学家各拿了一根筷子，\n碍于面子，每个人都不愿放下手中\n的筷子，最终全部人都饿死了...\n");
                    for(int i=0;i<5;i++) {
                        Room.changeFace(i, "death");
                    }
                    Platform.runLater(() -> {Tool.showButton();});

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        isStopped = true;
    }

    public static boolean isIsStopped() {
        return isStopped;
    }
}
