/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.ui.Subscription;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import movie.store.database.DatabaseHandler;
import movie.store.ui.main.MainController;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class NewSubscriptionController implements Initializable {
    DatabaseHandler dbHandler; 

    @FXML
    private TextField subID;
    @FXML
    private ComboBox<String> cusID;
    @FXML
    private ComboBox<String> subPlan;
    @FXML
    private TableColumn<Subscription,String> subIDCol;
    @FXML
    private TableColumn<Subscription,String> cusIDCol;
    @FXML
    private TableColumn<Subscription,String> subplanCol;
    @FXML
    private TableView<Subscription> subTableView;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbHandler = DatabaseHandler.getIntance();
        initColSubscription();
        subIDCBox();
        cusCBox();
    }   
    
    private void initColSubscription() {
        subIDCol.setCellValueFactory(new PropertyValueFactory<>("subID"));
        cusIDCol.setCellValueFactory(new PropertyValueFactory<>("cusID"));
        subplanCol.setCellValueFactory(new PropertyValueFactory<>("subType"));
    }

    @FXML
    private void saveSubscription(ActionEvent event) {
        String sSubID = subID.getText(); 
        String sCusID = cusID.getValue(); 
        String sSubPlan = subPlan.getValue(); 
        
        Boolean flag = sSubID.isEmpty()||sCusID.isEmpty()||sSubPlan.isEmpty();
        
        if(flag){
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setHeaderText(null);
            alert.setContentText("Please Enter All Fields.");
            alert.showAndWait();
            return;
        }
        try {
            String qu = "INSERT INTO Subscription VALUES ("
                + "'" + sSubID + "',"
                + "'" + sCusID + "',"
                + "'" + sSubPlan + "'" 
                + ")";
            
        System.out.println(qu);
        if(dbHandler.execAction(qu)){
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
    private void deleteSubscription(ActionEvent event) {
        String sSubID = subID.getText(); 
        String sCusID = cusID.getValue(); 
        String sSubPlan = subPlan.getValue(); 
        
        Boolean flag = sSubID.isEmpty()||sCusID.isEmpty()||sSubPlan.isEmpty();
        
        if(flag){
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setHeaderText(null);
            alert.setContentText("Please Enter All Fields.");
            alert.showAndWait();
            return;
        }
        try {
            String qu = "DELETE FROM Subscription WHERE Subscription_id = " + sSubID;
            
        System.out.println(qu);
        if(dbHandler.execAction(qu)){
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
    private void cancel(ActionEvent event) {
        
        Stage stage = (Stage) rootPane.getScene().getWindow(); 
        stage.close();
        
    }

    @FXML
    private void loadSubscription(ActionEvent event) {
        ObservableList<Subscription> loadData = FXCollections.observableArrayList(); 
        String qu = "SELECT s.Subscription_id,c.Customer_fname,c.Customer_id,su.type FROM Subscription s,Customer c, sub_type su WHERE c.Customer_id = s.Customer_id AND s.Sub_plan = su.sub_id"; 
        ResultSet rs = dbHandler.execQuery(qu); 
        
        try {
            while(rs.next()){
                
                String sSubid = rs.getString("Subscription_id");
                String sSubtype = rs.getString("type"); 
                String cCustomerID = rs.getString("Customer_id");
                String cCusfname = rs.getString("Customer_fname"); 
                loadData.addAll(new Subscription(sSubid,cCustomerID,cCusfname, sSubtype));
                System.out.println(sSubid + " + " + cCusfname + " + " + cCustomerID+ " + "+sSubtype);
                
            }
            subTableView.getItems().setAll(loadData);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void cusCBox(){
        ObservableList options = FXCollections.observableArrayList();
        String qSubID = "SELECT Customer_id From Customer";
        ResultSet rs = dbHandler.execQuery(qSubID);
        try {
            while (rs.next()) {
                String mCusID = rs.getString("Customer_id");
                
                options.addAll(mCusID);
            }
        } catch (SQLException e) {
        }
        
        cusID.setItems(options);
    }
    public void subIDCBox(){
        ObservableList options = FXCollections.observableArrayList();
        String qSubID = "SELECT sub_id FROM Sub_type";
        ResultSet rs = dbHandler.execQuery(qSubID);
        try {
            while (rs.next()) {
                String mSubID = rs.getString("sub_id");
                
                options.add(mSubID);
                System.out.println(mSubID);
            }
        } catch (SQLException e) {
        }
        
        subPlan.setItems(options);
        System.out.println(options);
        
    }
        
    
}
