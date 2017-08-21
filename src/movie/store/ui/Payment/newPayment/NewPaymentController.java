/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.ui.Payment.newPayment;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import movie.store.database.DatabaseHandler;
import org.apache.derby.iapi.store.raw.RowLock;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class NewPaymentController implements Initializable {
    DatabaseHandler dBHandler;

    @FXML
    private JFXTextField payID;
    @FXML
    private ComboBox<String> cusID;
    @FXML
    private ComboBox<String> subID;
    @FXML
    private ComboBox<String> payMethod;
    @FXML
    private DatePicker payDate;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        dBHandler = DatabaseHandler.getIntance();
        subIDCBox();
        cusCBox();
        payMethodCBox();
    }    

    @FXML
    private void savePayment(ActionEvent event) {
        String pPayID = payID.getText();
        String pCusID = cusID.getValue(); 
        String pSub = subID.getValue(); 
        String pPayMethod = payMethod.getValue();  
        LocalDate pPayDate = payDate.getValue(); 
        
        Boolean flag = pPayID.isEmpty()||pCusID.isEmpty() || pSub.isEmpty() || pPayMethod.isEmpty();
        if(flag){
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setHeaderText(null);
            alert.setContentText("Please Enter All Fields.");
            alert.showAndWait();
            return;
        }
        try {
            String qu = "INSERT INTO Payment VALUES ("
                + "'" + pPayID + "',"
                + "'" + pPayMethod + "',"
                + "'" + pPayDate + "'," 
                + "'" + pCusID + "'," 
                + "'" + pSub + "'" 
                + ")";
        System.out.println(qu);
        if(dBHandler.execAction(qu)){
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
            
        } catch (Exception e) {
        }
        
    }

    @FXML
    private void deletePayment(ActionEvent event) {
        String pPayID = payID.getText();
        String pCusID = cusID.getValue(); 
        String pSub = subID.getValue(); 
        String pPayMethod = payMethod.getValue();  
        LocalDate pPayDate = payDate.getValue(); 
        
        Boolean flag = pPayID.isEmpty()||pCusID.isEmpty() || pSub.isEmpty() || pPayMethod.isEmpty();
        if(flag){
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setHeaderText(null);
            alert.setContentText("Please Enter All Fields.");
            alert.showAndWait();
            return;
        }
        try {
            String qu = "DELETE FROM Payment WHERE Payment_id = "+ pPayID;
        System.out.println(qu);
        if(dBHandler.execAction(qu)){
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
            
        } catch (Exception e) {
        }
        
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow(); 
        stage.close();
        
    }
    public void subIDCBox(){
        ObservableList options = FXCollections.observableArrayList();
        String qSubID = "SELECT Subscription_id From Subscription";
        ResultSet rs = dBHandler.execQuery(qSubID);
        try {
            while (rs.next()) {
                String mSubID = rs.getString("Subscription_id");
                
                options.add(mSubID);
                System.out.println(mSubID);
            }
        } catch (SQLException e) {
        }
        
        subID.setItems(options);
        System.out.println(options);
        
    }
    public void cusCBox(){
        ObservableList options = FXCollections.observableArrayList();
        String qSubID = "SELECT Customer_id From Customer";
        ResultSet rs = dBHandler.execQuery(qSubID);
        try {
            while (rs.next()) {
                String mCusID = rs.getString("Customer_id");
                
                options.addAll(mCusID);
                System.out.println(mCusID );
            }
        } catch (SQLException e) {
        }
        
        cusID.setItems(options);
        System.out.println(options);
    }
    public void payMethodCBox(){
        ObservableList<String> options = FXCollections.observableArrayList(
               "VISA",
               "MASTER",
               "AMEX");
        payMethod.setItems(options);
    } 

    private void loadCusInfo(ActionEvent event) {
        
       String cInfo = cusID.getValue(); 
       String q = "SELECT c.Customer_fname FROM Subscription s, Customer c WHERE "+cInfo+ " = s.Customer_id";
       System.out.println("testing.."+cInfo);
       ResultSet rs = dBHandler.execQuery(q); 
       try {
           while(rs.next()){
               String cCusFname = rs.getString("Customer_fname");
               
              
           }
       } catch (SQLException e) {
       }
    }
    
    
}
