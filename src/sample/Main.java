package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    //This is our PrimaryStage (It contains everything)
    private Stage primaryStage;

    //This is the BorderPane of RootLayout
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;

        this.primaryStage.setTitle("Biblioteka Gier");

        initRootLayout();

//        showGameView();
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/GameView.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    // Shows the Game operations view inside the root layout.
    public void showGameView() {
        try {
            //First, load GameView from GameView.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/GameView.fxml"));
            AnchorPane gameOperationsView = (AnchorPane) loader.load();

            // Set Game Operations view int the center of root layout.
            rootLayout.setCenter(gameOperationsView);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
