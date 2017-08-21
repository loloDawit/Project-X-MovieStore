/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlQuery;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import movie.store.database.DatabaseHandler;
import movie.store.ui.listMembers.MemberListController;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class DBqueriesController implements Initializable {
    ObservableList<sqlQuery> list = FXCollections.observableArrayList();

    DatabaseHandler databaseHandler;
    @FXML
    private TableColumn<sqlQuery, String> cusid;
    @FXML
    private TableColumn<sqlQuery, String> movieid;
    @FXML
    private TableColumn<sqlQuery, String> movietype;
    @FXML
    private TableColumn<sqlQuery, String> subid;
    @FXML
    private TableColumn<sqlQuery, String> subtype;
    @FXML
    private TableColumn<sqlQuery, String> price;
    @FXML
    private TableView<sqlQuery> tableview;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        iniCol();
        loadData();
        ///
        
    } 
    private void iniCol(){
        cusid.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        movieid.setCellValueFactory(new PropertyValueFactory<>("movieID"));
        movietype.setCellValueFactory(new PropertyValueFactory<>("movietype"));
        subid.setCellValueFactory(new PropertyValueFactory<>("subid"));
        subtype.setCellValueFactory(new PropertyValueFactory<>("type"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    public void loadData(){
        databaseHandler = DatabaseHandler.getIntance();
        String qString = "SELECT c.Customer_id,m.Movie_id, m.Movie_type, "
                + "s.sub_id,s.type,s.price FROM Movie m Join sub_type s ON "
                + "m.Movie_type = s.sub_id JOIN Subscription su ON su.sub_plan = "
                + "s.sub_id JOIN CUSTOMER c ON c.Customer_id = su.Customer_id";
        ResultSet rs = databaseHandler.execQuery(qString);
        
        try {
            while(rs.next()){
                list.add(new sqlQuery(
                        rs.getString("Customer_id"),
                        rs.getString("Movie_id"),
                        rs.getString("Movie_type"),
                        rs.getString("sub_id"),
                        rs.getString("type"),
                        rs.getString("price"))
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
        private final SimpleStringProperty movieID;
        private final SimpleStringProperty movietype;
        private final SimpleStringProperty subid;
        private final SimpleStringProperty type;
        private final SimpleStringProperty price; 
        
        public sqlQuery(String cusID,String movID,String movType,String msubID,String mtype, String mprice){
            
             this.customerID = new SimpleStringProperty(cusID);
             this.movieID = new SimpleStringProperty(movID);
             this.movietype = new SimpleStringProperty(movType);
             this.subid = new SimpleStringProperty(msubID);
             this.type = new SimpleStringProperty(mtype); 
             this.price = new SimpleStringProperty(mprice);  
             
        }

        public String getCustomerID() {
            return customerID.get();
        }

        public String getMovieID() {
            return movieID.get();
        }

        public String getMovietype() {
            return movietype.get();
        }

        public String getSubid() {
            return subid.get();
        }

        public String getType() {
            return type.get();
        }

        public String getPrice() {
            return price.get();
        }
        
        
        
    }
    
    
}
