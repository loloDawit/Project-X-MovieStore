/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.ui.Payment;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import movie.store.database.DatabaseHandler;
import movie.store.ui.main.MainController;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class AddPaymentController implements Initializable {
     DatabaseHandler dBHandler;

    @FXML
    private JFXTextField customerID;
    @FXML
    private TableColumn<Payment, String> payIDCol;
    @FXML
    private TableColumn<Payment, String> payMethoCol;
    @FXML
    private TableColumn<Payment, String> payDateCol;
    @FXML
    private TableColumn<Payment, String> cusIDCol;
    private TableColumn<Payment, String> subIDCol;
    @FXML
    private TableColumn<Payment, String> subTypeCol;
    @FXML
    private TableView<Payment> payTableView;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dBHandler = DatabaseHandler.getIntance();
        initColPayment();
        
    }    

    @FXML
    private void addPayment(ActionEvent event) {
        loadWindow("/movie/store/ui/Payment/newPayment/newPayment.fxml", "Add New Payment");
        
    }


    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow(); 
        stage.close();
        
    }
    
    private void initColPayment() {
        payIDCol.setCellValueFactory(new PropertyValueFactory<>("payID"));
        payMethoCol.setCellValueFactory(new PropertyValueFactory<>("payMethod"));
        payDateCol.setCellValueFactory(new PropertyValueFactory<>("payDate"));
        cusIDCol.setCellValueFactory(new PropertyValueFactory<>("cusID"));
        subTypeCol.setCellValueFactory(new PropertyValueFactory<>("paySubtype"));
        
    }

    @FXML
    private void loadPaymentTable(ActionEvent event) {
        ObservableList<Payment> loadData = FXCollections.observableArrayList(); 
        String pCusID = customerID.getText(); 
        String qu = "SELECT *FROM Payment WHERE Customer_id = "+pCusID;
        
        ResultSet rs = dBHandler.execQuery(qu); 
        Boolean flag = pCusID.isEmpty();
        
        if(flag){
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Customer ID");
            alert.showAndWait();
            return;
        }
        
        try {
            while(rs.next()){
                
                String pPayID = rs.getString("Payment_id");
                String pPMethod = rs.getString("Payment_method"); 
                String pPayDate = rs.getString("Payment_date");
                String ppCusID = rs.getString("Customer_id"); 
                String pSubID = rs.getString("Subscription_id");
                
                loadData.addAll(new Payment(pPayID,pPMethod,pPayDate, ppCusID,pSubID));
                System.out.println(pPayID + " + " + pPMethod + " + " + pPayDate+ " + "+pCusID + " + "+pSubID);
                
            }
            payTableView.getItems().setAll(loadData);
        } catch (SQLException ex) {
            System.err.println("SQL Error: "+ex);
            
        }
    }
    
    void loadWindow(String loc, String title){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
