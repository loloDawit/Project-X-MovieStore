/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.ui.DeleteCustomer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import movie.store.database.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class deleteCustomerController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private JFXButton cancelButton;
    
    DatabaseHandler handler; 

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       handler = DatabaseHandler.getIntance();
    }    
     
    
    // new bug 
    // deleteing the first column has issue 
    // program crashes
    @FXML
    private void deleteCustomer(ActionEvent event) {
        String mName = name.getText(); 
        String mID = id.getText();

        
        Boolean flag = mName.isEmpty() || mID.isEmpty(); 
        if(flag){
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setHeaderText(null);
            alert.setContentText("Please Enter All Fields.");
            alert.showAndWait();
            return;
        }
        String st = "DELETE FROM CUSTOMER WHERE Customer_id = '"+ mID +"'" ;
        System.out.println(st);
        if(handler.execUpdate(st)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION); 
            alert.setHeaderText(null);
            alert.setContentText("Deleted");
            alert.showAndWait();
            //return;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setHeaderText(null);
            alert.setContentText("Error Occured");
            alert.showAndWait();
           
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage)rootPane.getScene().getWindow(); 
        stage.close();
        
        
    }

    private void loadData() {
        
    }
    
}
