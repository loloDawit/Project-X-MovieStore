/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.ui.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class SplashFXMLController implements Initializable {

    @FXML
    private StackPane rootPane;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        new splashScreen().start();
        
    }    
    
    
    class splashScreen extends Thread{
        @Override
        public void run(){
            try {
                Thread.sleep(5000);
                
                Platform.runLater(() -> {
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/movie/store/ui/login/login.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(SplashFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    Scene scene = new Scene (root);
                    Stage primaryStage = new Stage();
                    
                    primaryStage.setTitle("P R O J E C T  X   M O V I E  D A T A B A S E   S Y S T E M ");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    
                    rootPane.getScene().getWindow().hide();
                });
                
                
            } catch (InterruptedException e) {
            }
        }
    }
    
    private void loadSplashScreen(){
        try {
            StackPane pane = FXMLLoader.load(getClass().getResource("SplashFXML.fxml"));
            rootPane.getChildren().setAll(pane); 
            
            
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3),pane); 
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(SplashFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
