/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.ui.main;

import javafx.scene.control.Alert;

/**
 *
 * @author dawitabera
 */
public class AlertError {
    Alert alert; 
    public AlertError(){
        
    }
    // Display error message
    public void displayEnterAllFields(){
        alert = new Alert(Alert.AlertType.ERROR); 
        alert.setHeaderText(null);
        alert.setContentText("Please Enter All Fields.");
        alert.showAndWait();
    }
    // Display information
    public void displaySavedInfo(){
        alert = new Alert(Alert.AlertType.INFORMATION); 
        alert.setHeaderText(null);
        alert.setContentText("Saved");
        alert.showAndWait();
    }
    // Dispaly error message 
    public void displayError(){
        alert = new Alert(Alert.AlertType.ERROR); 
        alert.setHeaderText(null);
        alert.setContentText("Error Occured");
        alert.showAndWait();
        
    }
    
}
