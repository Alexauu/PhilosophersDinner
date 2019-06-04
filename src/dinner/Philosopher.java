package dinner;

import House.Room;
import House.Tool;
import Utilities.Utils;


public class Philosopher implements Runnable {

    private Chopstick leftChopstick;  //左手边的筷子
    private Chopstick rightChopstick;  //右手边的筷子
    private int id;  //哲学家编号
    private String name;
    private State state; //哲学家当前动作
    private boolean firstHungry = true;
    private boolean isStopped = true;

    public Philosopher(Chopstick leftChopstick, Chopstick rightChopstick ,int id){
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        state = State.THINKING;
        this.id = id;
        this.name = (id+1)+"号哲学家";
    }

    @Override
    public void run() {
        isStopped = false;
        while (Tool.RUNNING) {
            if(!Tool.LIVING) break;
            think();
            hungry();
            if (Tool.ALLOW_DEADLOCK)
                eatToDeath();
            else
                eat();
        }
        if(Tool.LIVING)  Tool.putDown(this);
            Tool.showOnConsole(name + " 停止了吃饭 \n");
        System.out.println(name + " stopped");
        isStopped = true;
    }

    private void think() {
        try {
            if (state == State.THINKING) {
                firstHungry = true;
                    Tool.showOnConsole(name + " 正在思考... \n");
                    Room.changeFace(id,"thinking");
                Thread.sleep(Utils.randomThink());
                state = State.HUNGRY;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void hungry() {
            if (state == State.HUNGRY && firstHungry) {
                firstHungry = false;
                    Room.changeFace(id,"hungry");
                    Tool.showOnConsole(name + " 饥饿中... \n");
                }
        try {
            Thread.sleep(Utils.randomHungry());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void eatToDeath() {  //吃到死锁
        System.out.println("Eat to death");
        if (leftChopstick.pick(this))         //left chopstick is available
        {
            while(!rightChopstick.pick(this));
//            if (rightChopstick.pick(this)) {
                try {

                    state = State.EATING;
                        Room.changeFace(id,"eating");
                        Tool.showOnConsole(name + " 正在吃饭... \n");
                    Thread.sleep(Utils.randomEat());
                    rightChopstick.drop(this);
                    leftChopstick.drop(this);
                    Room.changeFace(id,"thinking");
                    state = State.THINKING;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
//        }

    private void eat() { //一直吃一直爽
        System.out.println("Eat forever");
        if (leftChopstick.pick(this))            //left chopstick is available
        {
            try {

                if (rightChopstick.pick(this)) { //right chopstick is available
                    try {
                        //Eat

                        state = State.EATING;

                            Tool.showOnConsole(name + " 正在吃饭... \n");
                            Room.changeFace(id,"eating");
                        Thread.sleep(Utils.randomEat());
                        Room.changeFace(id,"thinking");
                        state = State.THINKING;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        rightChopstick.drop(this);
                    }
                }
            } finally {
                leftChopstick.drop(this);
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isStopped() {
        return isStopped;
    }
}
