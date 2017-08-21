/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.ui.listMembers;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
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
public class MemberListController implements Initializable {
     ObservableList<Customer> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Customer> tableView;
    //private TableColumn<Customer, String> nameCol;
   // private TableColumn<Customer, String> idCol;
    @FXML
    private TableColumn<Customer, String> phoneCol;
    @FXML
    private TableColumn<Customer, String> emailCol;
    @FXML
    private JFXTextField searchInput;
    @FXML
    private TableColumn<Customer, String> fnamecol;
    @FXML
    private TableColumn<Customer, String> minit;
    @FXML
    private TableColumn<Customer, String> lastcol;
    @FXML
    private TableColumn<Customer, String> addresscol;
    @FXML
    private TableColumn<Customer, String> citycol;
    @FXML
    private TableColumn<Customer, String> statecol;
    @FXML
    private TableColumn<Customer, String> zipcol;
    @FXML
    private TableColumn<Customer, String> dobcol;
    @FXML
    private TableColumn<Customer, String> subdatecol;
    @FXML
    private TableColumn<Customer, String> idcol;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol(); 
        loadData(); 
    }    

    private void initCol() {

        fnamecol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        minit.setCellValueFactory(new PropertyValueFactory<>("minit"));
        lastcol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        addresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
        citycol.setCellValueFactory(new PropertyValueFactory<>("city"));
        statecol.setCellValueFactory(new PropertyValueFactory<>("State"));
        zipcol.setCellValueFactory(new PropertyValueFactory<>("Zip"));
        dobcol.setCellValueFactory(new PropertyValueFactory<>("bdate"));
        subdatecol.setCellValueFactory(new PropertyValueFactory<>("sub_Date"));
    }
    
    private void loadData() {
        DatabaseHandler handler =  DatabaseHandler.getIntance();
        String qu = "SELECT *FROM CUSTOMER";
        ResultSet rs = handler.execQuery(qu);
        try {
            // accessing the data from the database
            while(rs.next()){
                
                list.add(new Customer(
                        rs.getString("Customer_fname"),
                        rs.getString("Customer_minit"),
                        rs.getString("Customer_lname"),
                        rs.getString("Customer_id"),
                        rs.getString("Customer_phone"),
                        rs.getString("Customer_email"),
                        rs.getString("Address"),
                        rs.getString("City"),
                        rs.getString("State"),
                        rs.getString("Zip"),
                        rs.getString("DOB"),
                        rs.getString("Subscription_date"))
                );
                tableView.setItems(list);
                
            } 
            rs.close();
            
        } catch (SQLException ex) {
            System.err.println(ex);
        } 
        
    }

    @FXML
    private void searchOperation(ActionEvent event) {
        // filtered list to search the data 
        FilteredList<Customer> filteredData = new FilteredList<>(list, e ->true);
        searchInput.setOnKeyTyped(e ->{
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            
            filteredData.setPredicate((Predicate<? super Customer>)customer ->{
                // add the observable data to the list if its empty or null
                if(newValue == null || newValue.isEmpty()){
                    return  true; 
                }
                
                // search the data with respect to ID and name 
                String lowerCaseFilter = newValue.toLowerCase(); 
                // search with respect to ID
                if(customer.getId().contains(newValue)){
                    return true; 
                }else if(customer.getFname().toLowerCase().contains(lowerCaseFilter)){
                    return true; 
                }
                
                return false; 
                
            });
        });
        // print only the sorted list to the table 
        SortedList<Customer> sortedData = new SortedList<>(filteredData); 
        // add the sorted list to the table 
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        // set the table data as sorted list 
        tableView.setItems(sortedData);
            
        });
        
    
        
    }
    
     public static  class Customer{
        private final SimpleStringProperty id; 
        private final SimpleStringProperty fname; 
        private final SimpleStringProperty minit;
        private final SimpleStringProperty lname;  
        private final SimpleStringProperty phone; 
        private final SimpleStringProperty email; 
        private final SimpleStringProperty address; 
        private final SimpleStringProperty city; 
        private final SimpleStringProperty state;  
        private final SimpleStringProperty zip; 
        private final SimpleStringProperty bdate; 
        private final SimpleStringProperty sub_Date;  
        
        public Customer(String mfname, String init,String mlname,String mid,
                String mphone, String memail,String maddress,String mcity,
                String mstate,String mzip,String mdate,String msub_Date){
            
            this.fname = new SimpleStringProperty(mfname);
            this.id = new SimpleStringProperty(mid);
            this.minit = new SimpleStringProperty(init);
            this.lname = new SimpleStringProperty(mlname);
            this.phone = new SimpleStringProperty(mphone);
            this.email = new SimpleStringProperty(memail);
            this.address = new SimpleStringProperty(maddress);
            this.city = new SimpleStringProperty(mcity);
            this.state = new SimpleStringProperty(mstate);
            this.zip = new SimpleStringProperty(mzip);
            this.bdate = new SimpleStringProperty(mdate);
            this.sub_Date = new SimpleStringProperty(msub_Date);
        }

        public String getId() {
            return id.get();
        }

        public String getFname() {
            return fname.get();
        }

        public String getMinit() {
            return minit.get();
        }

        public String getLname() {
            return lname.get();
        }

        public String getPhone() {
            return phone.get();
        }

        public String getEmail() {
            return email.get();
        }

        public String getAddress() {
            return address.get();
        }

        public String getCity() {
            return city.get();
        }

        public String getState() {
            return state.get();
        }

        public String getZip() {
            return zip.get();
        }

        public String getBdate() {
            return bdate.get();
        }

        public String getSub_Date() {
            return sub_Date.get();
        }
       
    }
    
}
