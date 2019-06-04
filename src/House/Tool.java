package House;

import dinner.Chopstick;
import dinner.Philosopher;
import dinner.Waitress;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Tool {
    public static boolean RUNNING;
    public static boolean LIVING;
    public static boolean ALLOW_DEADLOCK;
    public static Button reStart;
    private static Thread[] theads = new Thread[6];
    private static Waitress waitress = new Waitress();


    private static TextArea logTextArea;

    private static CheckBox allowDeadlock;

    private static Philosopher[] philosophers = new Philosopher[5];
    private static Chopstick[] chopsticks = new Chopstick[5];

    public static void tool(VBox right){
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);
        right.setSpacing(20);
        Button start = new Button("开始");
        Button stop = new Button("停止");
        buttonBox.getChildren().addAll(start,stop);
        reStart = new Button("一键复活");
        reStart.setVisible(false);
        reStart.setLayoutX(270);
        reStart.setLayoutY(290);
        right.getChildren().add(buttonBox);
        right.setAlignment(Pos.TOP_CENTER);
        initThead();
        initCheckBox(right);
        initTextArea(right);
        Room.addToRoom(reStart);
        buttonAction(start,stop,reStart);
        addIntroduction(right);
        addAbout(right);
    }
    public static void addAbout(VBox right){
        Button about = new Button("关 于");
        right.getChildren().add(about);
        Stage aboutStage = new Stage();
        editAbout(aboutStage);
        about.setOnAction(event -> {
            aboutStage.show();
        });
    }

    public static void addIntroduction(VBox vBox){
        Rectangle rec = new Rectangle();
        rec.setWidth(180);
        rec.setHeight(60);
        rec.setFill(new ImagePattern(new Image("/icon/introduction.png")));
        vBox.getChildren().add(rec);
    }

    public static void initThead(){
        for (int i = 0; i < 5; i++) {
            chopsticks[i] = new Chopstick(i);
        }
        philosophers[0] = new Philosopher(chopsticks[1],chopsticks[0],0);
        philosophers[1] = new Philosopher(chopsticks[2],chopsticks[1],1);
        philosophers[2] = new Philosopher(chopsticks[3],chopsticks[2],2);
        philosophers[3] = new Philosopher(chopsticks[4],chopsticks[3],3);
        philosophers[4] = new Philosopher(chopsticks[0],chopsticks[4],4);

    }

    public static void showOnConsole(String msg){
        Platform.runLater(() -> {
            logTextArea.appendText(msg);
        });
    }

    public static void buttonAction(Button start, Button stop, Button reStart){
        start.setOnAction(event ->{
            if(willDie()){
                Platform.runLater(() -> {
                    logTextArea.clear();
                    logTextArea.appendText("哲学家已经全部死亡~ \n");
                });

            }
            if(RUNNING || !isAllStopped()) {
                Platform.runLater(() -> {
                    logTextArea.appendText("请等待所有哲学家停止吃饭后再点击“开始” \n");
                });
                return;
            }
            if(!willDie()) {
                Platform.runLater(() -> {
                    logTextArea.clear();
                    logTextArea.appendText("开始模拟哲学家聚餐 \n");

                });
                RUNNING = true;
                LIVING = true;

                if (allowDeadlock.isSelected()) {
                    ALLOW_DEADLOCK = true;
                }else ALLOW_DEADLOCK = false;
                for(int i=0;i<5;i++){
                    theads[i] = new Thread(philosophers[i],philosophers[i].toString());
                }
                theads[5] = new Thread(waitress,"waiter");
                for (Thread t:theads) {
                    t.start();
                }
            }
        });
        stop.setOnAction(event -> {
            Platform.runLater(() -> {
                logTextArea.appendText("停止模拟，等待各位哲学家吃完碗里的饭\n");
                RUNNING = false;
                LIVING = true;
            });

        });
        reStart.setOnAction(event -> {

            Platform.runLater(() -> {
                reLive();

                logTextArea.clear();
                reStart.setVisible(false);
                logTextArea.appendText("五位哲学家已经复活~\n");
            });
            RUNNING = false;
            LIVING = true;

        });
    }

    private static void initTextArea(VBox vBox){

        Text text = new Text("日  志");
        text.setFont(new Font(14));
        logTextArea = new TextArea();
        logTextArea.setPrefWidth(202);
        logTextArea.setPrefHeight(304);
        logTextArea.setEditable(false);
        logTextArea.setMinSize(202,304);
        VBox logBox = new VBox();
        logBox.setAlignment(Pos.CENTER);
        logBox.getChildren().addAll(text,logTextArea);
        vBox.getChildren().add(logBox);
    }

    private static void initCheckBox(VBox vBox){
        allowDeadlock = new CheckBox();
        allowDeadlock.setText("允许死锁");
        allowDeadlock.setSelected(false);
        vBox.getChildren().add(allowDeadlock);
    }

    public static Boolean willDie(){
        int count = 0;
       for(int i=0;i<5;i++){
           if(Room.isHaveOnlyOne(2*i)){
               count++;
           }
       }
        if(count == 5)
            return true;
        return false;
    }

    public static void reLive(){
        initThead();
        Room.revive();
    }

    public static void showButton(){
        reStart.setVisible(true);
    }

    public static void putDown(Philosopher p){
        int index = p.getId();
        chopsticks[index].drop(p);
        chopsticks[(index+1)%5].drop(p);
    }

    public static void editAbout(Stage stage){

        stage.setResizable(false);
        stage.setWidth(500);
        stage.setHeight(800);
        VBox root = new VBox();
        stage.setScene(new Scene(root));
        ImageView imageView = new ImageView(Tool.class.getResource("/icon/Declare.png").toExternalForm());
        root.getChildren().add(imageView);
        imageView.setFitHeight(750);
        imageView.setFitWidth(480);
        root.setAlignment(Pos.CENTER);
        stage.setAlwaysOnTop(true);
        stage.setTitle("关于");
        stage.getIcons().add(new Image(Tool.class.getResource("/icon/dinner.png").toExternalForm()));
    }

    public static boolean isAllStopped(){
        boolean result = true;
        if(!waitress.isIsStopped()) return false;
        for (Philosopher p : philosophers){
            if(!p.isStopped()){
                result = false;
                break;
            }
        }
        return result;
    }

    public static Philosopher[] getPhilosophers() {
        return philosophers;
    }
}
