/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.ui.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import movie.store.database.DatabaseHandler;
import movie.store.settings.Preferences;
import movie.store.ui.CustomerView.CustomerViewController;
import movie.store.ui.main.MainController;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class LoginController implements Initializable {
    
    private CustomerViewController load; 
    
    DatabaseHandler handler;
    Connection conn;
    PreparedStatement pst = null; 
    ResultSet rs = null; 

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    
    Preferences preferences; 
    @FXML
    private Label titleLabel;
    @FXML
    private Label errorLable;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preferences = Preferences.getPreferences(); 
        handler = DatabaseHandler.getIntance();
    }    

    @FXML
    public void handleLogin(ActionEvent event) {
        
        titleLabel.setText("Project X Login");
        titleLabel.setStyle("-fx-background-color:black");
        
        String lUsername = username.getText(); 
        String lPassword = DigestUtils.shaHex(password.getText());
        
        if(lUsername.equals(preferences.getUsername()) && lPassword.equals(preferences.getPassword())){
            closeStage();
            loadMain();
        }else{
            try {
            handler = DatabaseHandler.getIntance();
            String qu = "SELECT *FROM CUSTOMER WHERE Customer_fname = ? AND Customer_id =?"; 
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/dawitabera/ProjectXDB.db");
            pst = conn.prepareStatement(qu);
            pst.setString(1, username.getText());
            pst.setString(2, password.getText());
            rs = pst.executeQuery();
           
            if(rs.next()){
                closeStage();
                //loadCustomerView();
                loadCustomerUI();
                
            }else{
                System.out.println("error");
                errorLable.setText("Check Username/Password and try again");
                titleLabel.setStyle("-fx-background-color:red");
            }
            rs.close();
            } catch (SQLException e) {
                System.out.println("SQL ERROR");
                System.err.println(e);
            }
        }
    }
    void loadMain(){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/movie/store/ui/main/Main.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("P R O J E C T  X   M O V I E  D A T A B A S E   S Y S T E M");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    void loadCustomerUI(){
         try {
            Parent parent = FXMLLoader.load(getClass().getResource("/movie/store/ui/CustomerView/CustomerView.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("P R O J E C T  X   M O V I E  D A T A B A S E   S Y S T E M");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    @FXML
    private void handleCancel(ActionEvent event) {
        handler.closeConnection();
        System.exit(0);
    }

    private void closeStage() {
        ((Stage)username.getScene().getWindow()).close();
    }

    public void init(CustomerViewController aThis) {
        load = aThis;
       
    }
    
}
