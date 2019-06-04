package House;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


public class Room {

    private static AnchorPane carpet = new AnchorPane();

    private static Circle[] person = new Circle[5];
    private static Text[] doing = new Text[5];
    private static ImageView cover[] = new ImageView[5];
    private static Circle[] hands = new Circle[10];

    final static ImagePattern hungry = new ImagePattern(new Image("/icon/hungry.png"));
    final static ImagePattern thinking = new ImagePattern(new Image("/icon/thinking.png"));
    final static ImagePattern eating = new ImagePattern(new Image("/icon/eating.png"));
    final static ImagePattern Table = new ImagePattern(new Image("/icon/Table.png"));
    final static ImagePattern Death = new ImagePattern(new Image("/icon/death.png"));
    final static String COVER = Room.class.getResource("/icon/cover.png").toExternalForm();


    public static void room(VBox vBox) {
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.TOP_CENTER);
        setTable(vBox);
        addCover();

    }
    public static void addCover() {
        AnchorPane carpet = Room.getCarpet();
        for(int i=0;i<5;i++){
            cover[i] = new ImageView(COVER);
            cover[i].setVisible(false);
            carpet.getChildren().add(cover[i]);
        }
        cover[0].setX(213);cover[0].setY(192);;
        cover[1].setX(331);cover[1].setY(197);
        cover[2].setX(356);cover[2].setY(304);
        cover[3].setX(274);cover[3].setY(360);
        cover[4].setX(185);cover[4].setY(303);
    }


    public static void setTable(VBox vBox) {

        carpet.setStyle("-fx-background-color: #eaeaea");
        carpet.setPrefWidth(600);
        carpet.setPrefHeight(600);
        vBox.getChildren().add(carpet);
        setPerson();
    }

    public static void setPerson() {
        Circle table = new Circle(300, 300, 150, Table);
        carpet.getChildren().add(table);
        person[0] = new Circle(300, 100, 25, thinking);
        doing[0] = new Text("思考ing");
        doing[0].setX(280);doing[0].setY(70);
        person[1] = new Circle(490, 238, 25, thinking);
        doing[1] = new Text("思考ing");
        doing[1].setX(470);doing[1].setY(208);
        person[2] = new Circle(418, 462, 25, thinking);
        doing[2] = new Text("思考ing");
        doing[2].setX(398);doing[2].setY(432);
        person[3] = new Circle(182, 462, 25, thinking);
        doing[3] = new Text("思考ing");
        doing[3].setX(162);doing[3].setY(432);
        person[4] = new Circle(110, 238, 25, thinking);
        doing[4] = new Text("思考ing");
        doing[4].setX(90);doing[4].setY(208);

        for(int i=0;i<5;i++){
            initHangs(i,person[i].getCenterX(),person[i].getCenterY());
        }

        carpet.getChildren().addAll(person[0], person[1], person[2], person[3], person[4],doing[0],doing[1],
                doing[2],doing[3],doing[4]);
    }


    public static void changeFace(int index,String type) {
        if("thinking".equals(type)) {

            Platform.runLater(() -> {
                person[index].setFill(thinking);
                doing[index].setText("思考ing");
            });

        }
        else if("eating".equals(type)){
            Platform.runLater(() -> {
                person[index].setFill(eating);
                doing[index].setText("吃饭ing");
            });

        }
        else if("hungry".equals(type)) {
            Platform.runLater(() -> {
                person[index].setFill(hungry);
                doing[index].setText("饥饿ing");
            });

        }
        else if("death".equals(type)){
            Platform.runLater(() -> {
                person[index].setFill(Death);
                doing[index].setText("已死亡");
            });

        }
    }

    public static AnchorPane getCarpet(){
        return carpet;
    }

    public static void setCover(int index, Boolean bool){
        cover[index].setVisible(bool);
    }

    public static void revive(){
        for (int i=0;i<5;i++){
            person[i].setFill(thinking);
            doing[i].setText("思考ing");
            cover[i].setVisible(false);
            hideHands(2*i);
            hideHands(2*i+1);
        }
    }

    private static void initHangs(int i,double x, double y){
        int left = 2*i;
        int right = left+1;
        hands[left] = new Circle(x-35,y,8, null);
        hands[right] = new Circle(x+35,y,8,null);
        hands[left].setStroke(null);
        hands[right].setStroke(null);
        carpet.getChildren().addAll(hands[left],hands[right]);
    }

    public static void showHands(int i){
        if(i%2 == 0){ //偶数为左手
            hands[i].setFill(Color.RED);
        }else
            hands[i].setFill(Color.GREEN);
    }
    public static void hideHands(int i){
        hands[i].setFill(null);
    }
    public static boolean isHaveOnlyOne(int i){
        int count = 0;
        if(hands[i].getFill() == null) count++;
        if(hands[i+1].getFill() == null) count++;
        if(count == 1) return true;
        return false;
    }
    public static void addToRoom(Node node){
        carpet.getChildren().add(node);

    }

}
