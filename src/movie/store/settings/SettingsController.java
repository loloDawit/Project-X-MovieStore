/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.settings;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class SettingsController implements Initializable {

    @FXML
    private JFXTextField ndatswithoutfine;
    @FXML
    private JFXTextField fineperday;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDefaultValues(); 
    }    
    
    private void initDefaultValues() {
        Preferences preferences = Preferences.getPreferences(); 
        ndatswithoutfine.setText(String.valueOf(preferences.getnDayswithoutFine()));
        fineperday.setText(String.valueOf(preferences.getFinePerDay()));
        username.setText(String.valueOf(preferences.getUsername()));
        password.setText(String.valueOf(preferences.getPassword()));
    }
    
    @FXML
    private void handleSaveButton(ActionEvent event) {
        int ndays = Integer.parseInt(ndatswithoutfine.getText()); 
        float fine = Float.parseFloat(fineperday.getText()); 
        String sUsername = username.getText(); 
        String sPassword = password.getText(); 
        
        Preferences preferences = Preferences.getPreferences();
        preferences.setFinePerDay(fine);
        preferences.setnDayswithoutFine(ndays);
        preferences.setUsername(sUsername);
        preferences.setPassword(sPassword);
        
        Preferences.writePreferenceToFile(preferences);
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow(); 
        stage.close();
        
    }

 
}
