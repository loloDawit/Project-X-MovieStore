/*
 * Class to add new movies to the database. 
 */
package movie.store;

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
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import movie.store.database.DatabaseHandler;
import movie.store.ui.main.AlertError;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class FXMLDocumentController implements Initializable {
    AlertError err; 

    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField actor;
    @FXML
    private JFXTextField director;

    DatabaseHandler databaseHandler;
    @FXML
    private AnchorPane rootPane;
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
        
        // check all the fields are provided 
        Boolean flag = mID.isEmpty() || mActor.isEmpty() || mDirector.isEmpty() 
                || mTitle.isEmpty();
        
        if(flag){
            err.displayEnterAllFields();
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
            err.displaySavedInfo();
        }else{
            err.displayError();
        } 
            
    }

    @FXML
    private void cancel(ActionEvent event) {
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
    // Comobox to store the subscription keyes 
    private void loadComboBox() {
        ObservableList<String> options = FXCollections.observableArrayList();
        String query = "SELECT type FROM Sub_Type"; 
        ResultSet rs  = databaseHandler.execQuery(query); 
        try {
            while(rs.next()){
                String hType = rs.getString("type"); 
                options.add(hType);
            }
        } catch (SQLException e) {
        }
       type.setItems(options);
    }

    @FXML
    private void DeletMovie(ActionEvent event) { 
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
            err.displayDeleted();
            //return;
        }else{
            err.displayError();
        }
    }
}
