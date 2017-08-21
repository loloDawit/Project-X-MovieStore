/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlQuery.query;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import movie.store.database.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class DBqueriesController implements Initializable {
    ObservableList<sqlQuery> list = FXCollections.observableArrayList();

    DatabaseHandler databaseHandler;

 
    @FXML
    private TableView<sqlQuery> tableview;
    @FXML
    private TableColumn<sqlQuery, String> cusID;
    @FXML
    private TableColumn<sqlQuery, String> city;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniCol();
        loadData();
    }    

    private void iniCol() {
        cusID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
       
    }

    private void loadData() {
        databaseHandler = DatabaseHandler.getIntance();
        String qString = "SELECT DISTINCT c.Customer_id,c.City FROM CUSTOMER c, "
                + "Subscription s JOIN sub_type t ON s.Sub_plan = t.sub_id WHERE"
                + " type = 'HD' AND City = 'Seattle'";
        ResultSet rs = databaseHandler.execQuery(qString);
        
        try {
            while(rs.next()){
                list.add(new sqlQuery(
                        rs.getString("Customer_id"),
                        rs.getString("City"))
                );
                tableview.setItems(list);
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        
        
    }
    public static class sqlQuery{
        
        private final SimpleStringProperty customerID; 
        private final SimpleStringProperty city;
        
 
        
        public sqlQuery(String mID,String mcity){
            
             this.customerID = new SimpleStringProperty(mID);
             this.city = new SimpleStringProperty(mcity);  
             
        }

        public String getCustomerID() {
            return customerID.get();
        }

        public String getCity() {
            return city.get();
        } 
    }
}
