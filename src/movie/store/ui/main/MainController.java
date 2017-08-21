/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.ui.main;

import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.table.DefaultTableModel;
import movie.store.database.DatabaseHandler;
import movie.store.ui.CustomerView.CustomerViewController;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class MainController implements Initializable {
     CustomerViewController viewController; 
     AlertError err; 
     
    @FXML
    private TextField movieIDInput;
    @FXML
    private Label movieName;
    @FXML
    private Label movieDirector;
    @FXML
    private Label movieStatus;
    DatabaseHandler databaseHandler; 
    @FXML
    private TextField customerIDInput;
    @FXML
    private Label CustomerName;
    @FXML
    private Label customerContact;
    @FXML
    private JFXTextField movieID;
    @FXML
    private ListView<String> checkoutDataList;
    
    Boolean isReady = false;
    @FXML
    private TableView<History> tableView;
    @FXML
    private TableColumn<History, String> movieIDCol;
    @FXML
    private TableColumn<History, String> customerIDCol;
    @FXML
    private TableColumn<History, String> dateCol;
    @FXML
    private TableColumn<History, String> renewCol;
    @FXML
    private TableColumn<History, String> movieTitleCol;
    private TextField firstname;
    private TextField memberid;
    private TextField email;
    private DatePicker subdate;
    private DatePicker dateofbirth;
    private ComboBox<String> subtype;
    private TextField city;
    private TextField state;
    private TextField zip;
    private TextField minit;
    private TextField lastname;
    private TextField phone;
    private TextField address;
    private ListView<String> checkoutList;
    @FXML
    private Label movieActor;
    @FXML
    private Label customerSubdate;
    @FXML
    private Label cusEmail;
    @FXML
    private TableColumn<History, String> cusfnameCol;
    @FXML
    private TableView<Subscription> subTableView;
    @FXML
    private TableColumn<Subscription, String> subIDCol;
    @FXML
    private TableColumn<Subscription, String> cusNameCol;
    @FXML
    private TableColumn<Subscription, String> custIDCol;
    @FXML
    private TableColumn<Subscription, String> subTypeCol;
    @FXML
    private ComboBox<String> payCmBox;
    @FXML
    private TableColumn<Payment, String> payMethodCol;
    @FXML
    private TableColumn<Payment, String> payDateCol;
    @FXML
    private TableColumn<Payment, String> paySubTypeCol;
    @FXML
    private TextField payCusID;
    @FXML
    private JFXTextField payID;
    @FXML
    private JFXTextField paySubID;
    @FXML
    private DatePicker payDatePicker;
    @FXML
    private TableView<Payment> payTableview;
    @FXML
    private TableColumn<Payment, String> payCusIDCol;
    @FXML
    private TableColumn<Payment, String> payIDCol;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    //@Override;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initColHistory();
        initColSubscription();
        initColPayment();
        loadCBoxPaymentMethod();
        databaseHandler = DatabaseHandler.getIntance();
        viewController = new CustomerViewController();
        err = new AlertError();
    }   

    @FXML
    private void loadAddCustomer(ActionEvent event) {
        loadWindow("/movie/store/ui/addMember/member_add.fxml", "Add New Customer");
    }

    @FXML
    private void loadAddMovie(ActionEvent event) {
        loadWindow("/movie/store/add_movie.fxml", "Add New Movie To DataBase");
    }

    @FXML
    private void loadCustomerTable(ActionEvent event) {
        loadWindow("/movie/store/ui/listMembers/member_list.fxml", "Customers In The DataBase");
    }

    @FXML
    private void loadMovieTable(ActionEvent event) {
        loadWindow("/movie/store/ui/listmovie/movie_list.fxml", "Movies In The DataBase");
    }
     @FXML
    private void loadSetting(ActionEvent event) {
        loadWindow("/sqlUI/sqlmain.fxml", "Perform Complex Queries");
    }
    
    @FXML
    private void loadPayment(ActionEvent event) {
        loadWindow("/movie/store/ui/Payment/AddPayment.fxml", "Add New Payment");
    }

    @FXML
    private void loadSubscription(ActionEvent event) {
        loadWindow("/movie/store/ui/Subscription/newSubscription.fxml", "Add Subscription");
        
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

    @FXML
    private void loadMovieInfo(ActionEvent event) throws ParseException {
        clearMovieCatche();
        // get text from the text field
        String id = movieIDInput.getText(); 
        String qu = "SELECT * FROM Movie WHERE Movie_id = '" + id + "'";
        ResultSet rs = databaseHandler.execQuery(qu); 
        Boolean flag = false; 
        
        try {
            while(rs.next()){
                String mTitle = rs.getString("Movie_title");
                String mDirector = rs.getString("Movie_director");
                String mActor = rs.getString("Movie_actor");
                String mStatus = rs.getString("Movie_copy");
                
                movieName.setText(mTitle); 
                movieDirector.setText(mDirector);
                movieActor.setText(mActor);
               // String status = (mStatus) ? "Available" : "Not Available";
                movieStatus.setText(mStatus);
                flag = true; 
            }
            if(!flag){
                movieName.setText("No Such Movie");
                
            }
        } catch (SQLException e) {
            
        }
    }

    @FXML
    private void loadCustomerInfo(ActionEvent event) {
        clearCustomerCatche();
        // get text from the text field
        String id = customerIDInput.getText(); 
        String qu = "SELECT * FROM CUSTOMER WHERE Customer_id = '" + id + "'";
        ResultSet rs = databaseHandler.execQuery(qu); 
        Boolean flag = false; 
        
        try {
            while(rs.next()){
                String cName = rs.getString("Customer_fname");
                String cContact = rs.getString("Customer_phone");
                String mSubdate = rs.getString("Subscription_date");
                String mCusemail = rs.getString("Customer_email");
                
                CustomerName.setText(cName); 
                customerContact.setText(cContact);
                cusEmail.setText(mCusemail);
                customerSubdate.setText(mSubdate);
                flag = true; 
            }
            if(!flag){
                CustomerName.setText("No Such Member Exists");
                
            }
        } catch (SQLException e) {
        }
        
    }
    
    void clearMovieCatche(){
        movieName.setText("");
        movieDirector.setText("");
        movieStatus.setText("");
        
    }
     void clearCustomerCatche(){
        CustomerName.setText("");
        customerContact.setText("");
       // movieStatus.setText("");
        
    }

     @FXML
    private void loadCheckOutOperation(ActionEvent event) {
        String customerID = customerIDInput.getText(); 
        String movieIDstr = movieIDInput.getText(); 
        
        if(movieIDInput.getText().isEmpty() || customerIDInput.getText().isEmpty()){
            err.display_checkoutEnterFields();  
        }else{ 
            
        err.display_checkoutConfrim(movieName.getText(), CustomerName.getText());
        
        Optional<ButtonType> response = err.alert.showAndWait(); 
        if(response.get() == ButtonType.OK){
            
            String str = "INSERT INTO Checkout(customer_id,movie_id) VALUES ("
                    + "'" + customerID + "',"
                    + "'" + movieIDstr + "')";
            String str2 = "UPDATE Movie SET Movie_copy = Movie_copy -1 WHERE Movie_id = '" +movieIDstr + "'";
            System.out.println(str + " and " +str2);
            
        
            // check if the movie is available before checking out
            if(databaseHandler.execAction(str) && databaseHandler.execAction(str2)){
                err.display_checkoutSuccess();
            }else{
                err.display_checkoutFailed();
            }
        }else{
            err.display_checkoutcancel();
        }
       }
    }

    @FXML
    private void loadMovieInfo2(ActionEvent event) throws ParseException {
        ObservableList <String> checkoutData = FXCollections.observableArrayList(); 
        isReady = false;
        String mID = movieID.getText(); 
        String qu = "SELECT * FROM Checkout WHERE Movie_id = '" + mID +"'"; 
        ResultSet rs = databaseHandler.execQuery(qu); 
        try {
            while(rs.next()){
                String mMovieID = mID;
                String mCustomerID = rs.getString("Customer_id"); 
                Date mCheckOutTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                        parse(rs.getString("checkout_time"));
                int mRenewCount = rs.getInt("renew_count");
                
                checkoutData.add("Checkout Date and Time : "+ mCheckOutTime.toGMTString()); 
                checkoutData.add("Renew Count: "+mRenewCount); 
                
                qu = "SELECT * FROM Movie WHERE Movie_id = '" + mMovieID + "'"; 
                ResultSet rs1 = databaseHandler.execQuery(qu);
                checkoutData.add("Movie Information:- "); 
                while(rs1.next()){
                    checkoutData.add("          Movie Title: " + rs1.getString("Movie_title")); 
                    checkoutData.add("          Movie ID: " + rs1.getString("Movie_id")); 
                    checkoutData.add("          Movie Actor: " + rs1.getString("Movie_actor")); 
                    checkoutData.add("          Movie Director: " + rs1.getString("Movie_director")); 
                }
                qu = "SELECT * FROM CUSTOMER WHERE Customer_id = '" + mCustomerID + "'"; 
                rs1 = databaseHandler.execQuery(qu);
                checkoutData.add("Customer Information:- "); 
                while(rs1.next()){
                    checkoutData.add("          Name : " + rs1.getString("Customer_id")); 
                    checkoutData.add("          phone : " + rs1.getString("Customer_phone")); 
                    checkoutData.add("          Email : " + rs1.getString("Customer_email")); 
                    //checkoutData.add("Movie Director: " + rs.getString("director")); 
                }
                isReady = true;
                       
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        checkoutDataList.getItems().setAll(checkoutData);
        
    }

    @FXML
    private void loadSubmissonOperation(ActionEvent event) {
        if(!isReady){
            err.display_returnEnterFields();
            return;
        }
        err.display_returnConfrim();
        
        Optional<ButtonType> response = err.alert.showAndWait(); 
        if(response.get() == ButtonType.OK){
        String id = movieID.getText(); 
        String act1 = "DELETE FROM Checkout WHERE Movie_id = '" + id + "'"; 
        String act2 = "UPDATE Movie SET Movie_copy = (Movie_copy+1) WHERE Movie_id = '" + id + "'"; 
        
        if(databaseHandler.execAction(act1) && databaseHandler.execAction(act2)){
            err.display_returnSuccess();
        }else{
            err.display_returnFailed();
        }
        }else{
            err.display_returncancel();  
        }
    }

    @FXML
    private void loadRenewOperation(ActionEvent event) {
        Boolean flag = movieID.getText().isEmpty();
        if(flag){
            err.display_renewEnterFields();
            return;
        }
        err.display_renewConfrim();
        
        Optional<ButtonType> response = err.alert.showAndWait(); 
        if(response.get() == ButtonType.OK){
            String act1 = "UPDATE Checkout SET checkout_time = CURRENT_TIMESTAMP, "
                    + "renew_count = (renew_count+1) WHERE Movie_id = '"+ movieID.getText() + "'";
            System.out.println(act1);
            if(databaseHandler.execAction(act1)){
                err.display_renewSuccess();
                
            }else{
                err.display_renewFailed();
            }
        }else{
            err.display_renewCancel();
        }
    }

    @FXML
    private void loadAboutOperation(ActionEvent event) {
        try {
            File pdFile = new File("/Users/dawitabera/Desktop/projectX_Iteration.pdf"); 
            if(pdFile.exists()){
                if(Desktop.isDesktopSupported()){
                    Desktop.getDesktop().open(pdFile);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR); 
                    alert.setHeaderText(null);
                    alert.setTitle("AW Desktop Error");
                    alert.setContentText("PDF File Not Supported");
                    alert.showAndWait();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR); 
                    alert.setHeaderText(null);
                    alert.setTitle("File Not Found Error");
                    alert.setContentText("file does not exist");
                    alert.showAndWait();
                System.out.println("file does not exist");
            }
        } catch (IOException e) {
            System.err.println("error @" +e.getMessage());
        }
    }
    
    /*
     * loadDocOperation
     * the functions uses AWT Desktop- cross paltform to load and open PDF files 
     * from local storage. 
    */
    @FXML
    private void loadDocOperation(ActionEvent event) {
        try {
            File pdFile = new File("/Users/dawitabera/Desktop/Desktop - Dawit’s MacBook Pro/Movie Store/src/movie/store/resources/Final Project Doc.pdf"); 
            if(pdFile.exists()){
                if(Desktop.isDesktopSupported()){
                    Desktop.getDesktop().open(pdFile);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR); 
                    alert.setHeaderText(null);
                    alert.setTitle("AWT Desktop Error");
                    alert.setContentText("PDF File Not Supported");
                    alert.showAndWait();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR); 
                    alert.setHeaderText(null);
                    alert.setTitle("File Not Found Error");
                    alert.setContentText("file does not exist");
                    alert.showAndWait();
                System.out.println("file does not exist");
            }
        } catch (IOException e) {
            System.err.println("error @" +e.getMessage());
        }
        
    }

    @FXML
    private void loadHistoryOperation(ActionEvent event) {
        ObservableList<History> loadData = FXCollections.observableArrayList(); 
        String qu = "SELECT ch.*,c.Customer_fname,m.Movie_title FROM Customer c, "
                + "Checkout ch, Movie m WHERE ch.Customer_id = c.Customer_id "
                + "AND ch.Movie_id = m.Movie_id"; 
        ResultSet rs = databaseHandler.execQuery(qu); 
        
        try {
            while(rs.next()){
                
                String mCheckoutTime = rs.getString("checkout_time");
                String mMovieID = rs.getString("movie_id"); 
                String mCustomerID = rs.getString("customer_id");
                String cCusfname = rs.getString("Customer_fname"); 
                String count = rs.getString("renew_count");
                String mmovieName = rs.getString("Movie_title"); 
                loadData.addAll(new History(mMovieID, mCustomerID, mCheckoutTime, 
                        count, mmovieName,cCusfname));
                System.out.println(mMovieID + " + " + mCustomerID + " + " +
                        mCheckoutTime+ " + "+count + " + " + mmovieName + " + " 
                        + cCusfname);
                
            }
            tableView.getItems().setAll(loadData);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }

    private void initColHistory() {
       movieIDCol.setCellValueFactory(new PropertyValueFactory<>("movieID"));
       customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
       dateCol.setCellValueFactory(new PropertyValueFactory<>("time"));
       renewCol.setCellValueFactory(new PropertyValueFactory<>("count"));
       movieTitleCol.setCellValueFactory(new PropertyValueFactory<>("movieName"));
       cusfnameCol.setCellValueFactory(new PropertyValueFactory<>("cusName"));
    }
    private void initColSubscription() {
       subIDCol.setCellValueFactory(new PropertyValueFactory<>("subID"));
       cusNameCol.setCellValueFactory(new PropertyValueFactory<>("cusID"));
       custIDCol.setCellValueFactory(new PropertyValueFactory<>("cusName"));
       subTypeCol.setCellValueFactory(new PropertyValueFactory<>("subType"));
    }


    
    public void loadComboBox(){
        ObservableList<String> options = FXCollections.observableArrayList(
            "HD", 
            "Non-HD",
            "Tv-Shows");
        final ComboBox cb = new ComboBox(options); 
        subtype.setItems(options);
    }
    
    // for payment
    public void loadCBoxPaymentMethod(){
        ObservableList<String> options = FXCollections.observableArrayList(
               "VISA",
               "MASTER",
               "AMEX");
        payCmBox.setItems(options);
        
    }

    private void addCustomerToDB(ActionEvent event) {
        String mfName = firstname.getText(); 
        String mminit = minit.getText();
        String mlname = lastname.getText();
        String mID = memberid.getText();
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
            err.displayEnterAllFields();
            return;
        }
        try {
            
        } catch (Exception e) {
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
        if(databaseHandler.execAction(qu)){
            err.displaySavedInfo();
        }else{
            err.displayError();
        }
    }

    private void loadCusDB(ActionEvent event) {
        loadWindow("/movie/store/ui/listMembers/member_list.fxml", "Customers In The DataBase");
        
    }

    @FXML
    private void performLogout(ActionEvent event) {
       
        closeStage();
        viewController.loadLoginUI();
        
    }
    public void closeStage() {
        ((Stage)movieID.getScene().getWindow()).close();
    }

    @FXML
    private void loadSubscriptionOperation(ActionEvent event) {
        ObservableList<Subscription> loadData = FXCollections.observableArrayList(); 
        String qu = "SELECT s.Subscription_id,c.Customer_fname,c.Customer_id,su.type "
                + "FROM Subscription s,Customer c, sub_type su WHERE c.Customer_id = "
                + "s.Customer_id AND s.Sub_plan = su.sub_id"; 
        ResultSet rs = databaseHandler.execQuery(qu); 
        
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

    @FXML
    private void paymentSave(ActionEvent event) {
        
        String pCusID = payCusID.getText(); 
        String pPayID = payID.getText(); 
        String pSubID = paySubID.getText();
        LocalDate pDate = payDatePicker.getValue();
        String pMethod = payCmBox.getValue();
        
        Boolean flag = pCusID.isEmpty() || pPayID.isEmpty() || pSubID.isEmpty() ||
                pMethod.isEmpty();
        
        if(flag){
            err.displayEnterAllFields();
            return;
        }try {
            String query = "INSERT INTO Payment VALUES ("
                    + "'" + pPayID + "',"
                    + "'" + pMethod + "',"
                    + "'" + pDate + "'," 
                    + "'" + pCusID + "'," 
                    + "'" + pSubID + "'"
                    +")";
            System.out.println(query);
            if(databaseHandler.execAction(query)){
                err.displaySavedInfo();
            }else{
                err.displayError();
            }
            
        } catch (Exception e) {
        }
        
        
        
    }

    @FXML
    private void paymentCancel(ActionEvent event) {
        payCusID.clear();
        
    }
    /*
     * Initialize the Payment table's content to dispaly the query
    */
    private void initColPayment() {
        
       paySubTypeCol.setCellValueFactory(new PropertyValueFactory<>("paySubtype"));
       payDateCol.setCellValueFactory(new PropertyValueFactory<>("payDate"));
       payCusIDCol.setCellValueFactory(new PropertyValueFactory<>("cusID"));
       payMethodCol.setCellValueFactory(new PropertyValueFactory<>("payMethod"));
       payIDCol.setCellValueFactory(new PropertyValueFactory<>("payID"));
        
    }

    @FXML
    private void paymentLoadTable(ActionEvent event) {
        ObservableList<Payment> loadData = FXCollections.observableArrayList(); 
        String pCusID = payCusID.getText(); 
        String qu = " SELECT *FROM Payment WHERE Customer_id = "+pCusID+"";
        
        ResultSet rs = databaseHandler.execQuery(qu); 
        Boolean flag = pCusID.isEmpty();
        if(flag){
            err.displayEnterAllFields();
        }
        else{
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
            payTableview.getItems().setAll(loadData);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

    @FXML
    private void loadExportToExcel(ActionEvent event) {
    }
    

    @FXML
    private void closeWindow(ActionEvent event) {
        closeStage();
        viewController.loadLoginUI();
        
    }  
}
