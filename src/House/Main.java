package House;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox leftBox = new VBox();
        VBox rightBox = new VBox();
        rightBox.setPrefWidth(200);
        rightBox.setPadding(new Insets(20));
        HBox pane = new HBox();
        pane.getChildren().addAll(leftBox,rightBox);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        Room.room(leftBox);
        Tool.tool(rightBox);
        primaryStage.setWidth(810);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);

        primaryStage.setTitle("哲学家聚餐");
        primaryStage.getIcons().add(new Image(Main.class.getResource("/icon/dinner.png").toExternalForm()));
        primaryStage.show();

    }
    @Override
    public void stop() throws Exception {
        Tool.RUNNING = false;
        super.stop();
    }

}
