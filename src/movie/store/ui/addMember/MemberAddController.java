/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.ui.addMember;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import movie.store.database.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class MemberAddController implements Initializable {
    DatabaseHandler handler; 
    

    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXTextField email;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXTextField fname;
    @FXML
    private JFXTextField minit;
    @FXML
    private JFXTextField lastname;
    @FXML
    private TextField city;
    @FXML
    private TextField state;
    @FXML
    private TextField zip;
    @FXML
    private ComboBox<String> subtype;
    @FXML
    private DatePicker dateofbirth;
    @FXML
    private JFXTextField address;
    private JFXTextField ssn;
    @FXML
    private DatePicker subdate;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = DatabaseHandler.getIntance();
        loadComboBox();
    }    

    @FXML
    private void addCustomer(ActionEvent event) {
        String mfName = fname.getText(); 
        String mminit = minit.getText();
        String mlname = lastname.getText();
        String mID = id.getText();
        String mcity = city.getText(); 
        String mstate = state.getText(); 
        String mzip = zip.getText();
        String mPhone = phone.getText();
        String mEmail = email.getText();
        String maddress = address.getText(); 
        LocalDate mdate = dateofbirth.getValue(); 
        LocalDate msubDate = subdate.getValue(); 
        
        Boolean flag = mfName.isEmpty() || mminit.isEmpty() || mlname.isEmpty() ||
                mID.isEmpty() || mPhone.isEmpty() || mEmail.isEmpty(); 
        if(flag){
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setHeaderText(null);
            alert.setContentText("Please Enter All Fields.");
            alert.showAndWait();
            return;
        }
         String qu = "INSERT INTO CUSTOMER VALUES ("
                + "'" + mID + "',"
                + "'" + mfName + "',"
                + "'" + mminit + "'," 
                + "'" + mlname + "'," 
                + "'" + mPhone + "'," 
                + "'" + mEmail + "'," 
                + "'" + maddress + "'," 
                + "'" + mcity + "'," 
                + "'" + mstate + "'," 
                + "'" + mzip + "',"
                + "'" + mdate +"',"
                + "'" + msubDate + "'"
                + ")";
         
        System.out.println(qu);
        if(handler.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION); 
            alert.setHeaderText(null);
            alert.setContentText("Saved");
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
        // create stage object
        // type cast since we are accessing the window
        Stage stage = (Stage) rootPane.getScene().getWindow(); 
        stage.close();
    }

    @FXML
    private void deleteCustomer(ActionEvent event) {
        String mfName = fname.getText(); 
        String mID = id.getText();

        
        Boolean flag = mID.isEmpty(); 
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

    private void loadComboBox() {
        ObservableList<String> options = FXCollections.observableArrayList(
            "HD", 
            "Non-HD",
            "Tv-Shows");
        //final ComboBox cb = new ComboBox(options); 
       subtype.setItems(options);
    }
    
}