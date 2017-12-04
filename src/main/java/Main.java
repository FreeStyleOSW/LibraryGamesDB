import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    private Stage primaryStage;
    private AnchorPane rootLayout;
// komentarz
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Biblioteka Gier");
        this.primaryStage.getIcons().add(new Image("/image/gamesfolder.png"));
        this.primaryStage.setResizable(false);
        initRootLayout();
    }
    private void initRootLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/RootLayout.fxml"));
        rootLayout = (AnchorPane) loader.load();
        BackgroundImage myBI = new BackgroundImage(new Image("image/backgroundImage.jpg"),
                BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
        rootLayout.setBackground(new Background(myBI));
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}