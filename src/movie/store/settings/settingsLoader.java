/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.settings;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import movie.store.database.DatabaseHandler;


public class settingsLoader extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/movie/store/settings/settings.fxml"));
        
        Scene scene = new Scene (root); 
        primaryStage.setTitle("settings ");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        new Thread(() -> {
            DatabaseHandler.getIntance();
        }).start();
        Preferences.initConfig();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}