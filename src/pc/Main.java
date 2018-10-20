package pc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception{
    Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
    primaryStage.setTitle("Mindstorms bluetooth toolkit");
    primaryStage.setScene(new Scene(root));
    primaryStage.setFullScreen(true);
    primaryStage.show();
  }
  
  public static void main(String[] args) {
    launch(args);
  }
}
