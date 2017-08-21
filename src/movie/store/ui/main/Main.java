/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.ui.main;

/**
 *
 * @author dawitabera
 */
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import movie.store.database.DatabaseHandler;


public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("SplashFXML.fxml"));
        
        // rootPane.getChildren().setAll(pane); 
            
            
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3),root); 
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);
            
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3),root); 
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);
            
            fadeIn.play();
            fadeIn.setOnFinished((e)->{ 
                fadeOut.play();
            });
        Scene scene = new Scene (root); 
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("P R O J E C T  X   M O V I E  D A T A B A S E   S Y S T E M ");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        new Thread(() -> {
            DatabaseHandler.getIntance();
        }).start();
        DatabaseHandler.getIntance();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}