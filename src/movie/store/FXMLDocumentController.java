/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import movie.store.database.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField actor;
    @FXML
    private JFXTextField director;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    DatabaseHandler databaseHandler;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private JFXTextField genre;
    @FXML
    private JFXTextField year;
    @FXML
    private JFXTextField copies;
    @FXML
    private ComboBox<String> type;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = DatabaseHandler.getIntance();
       // checkData();
       loadComboBox();
    }    

    @FXML
    private void addMovie(ActionEvent event) {
        
        String mID = id.getText(); 
        String mTitle= title.getText(); 
        String myear = year.getText(); 
        String mGenre = genre.getText();
        String mDirector = director.getText(); 
        String mActor = actor.getText(); 
        String mcopy = copies.getText(); 
        String mtype = type.getValue(); 
        
        Boolean flag = mID.isEmpty() || mActor.isEmpty() || mDirector.isEmpty() || mTitle.isEmpty();
        if(flag){
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setHeaderText(null);
            alert.setContentText("Please Enter All Fields.");
            alert.showAndWait();
            return;
        }

       String qu = "INSERT INTO MOVIE VALUES ("
               + "'" + mID+"',"
               + "'" + mTitle+"',"
               + "'" + myear+"',"
               + "'" + mGenre+"',"
               + "'" + mDirector+"',"
               + "'" + mActor+"',"
               + "'" + mcopy+"',"
               + "'" + mtype+"'"+
               
               ")";
       
        System.out.println(qu);
        if(databaseHandler.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION); 
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();
        }else{ // error
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setHeaderText(null);
            alert.setContentText("Faild");
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

    private void checkData() {
        String qu = "SELECT title FROM MOVIE";
        ResultSet rs = databaseHandler.execQuery(qu);
        try {
            // accessing the data from the database
            while(rs.next()){
                String titlex = rs.getString("Movie_title");
                System.out.println(titlex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteMovie(ActionEvent event) {
        //String mName = name.getText(); 
        String mID = id.getText();

        
        Boolean flag =mID.isEmpty(); 
        if(flag){
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setHeaderText(null);
            alert.setContentText("Please Enter All Fields.");
            alert.showAndWait();
            return;
        }
        String st = "DELETE FROM MOVIE WHERE id = '"+ mID +"'" ;
        System.out.println(st);
        if(databaseHandler.execUpdate(st)){
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
            "3455", 
            "3456",
            "3457");
        //final ComboBox cb = new ComboBox(options); 
       type.setItems(options);
    }

    @FXML
    private void DeletMovie(ActionEvent event) {
    }
}
