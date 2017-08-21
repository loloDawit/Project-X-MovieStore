/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import movie.store.ui.main.MainController;
import sqlQuery.DBqueriesController;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class SqlmainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loadFirstQuery(ActionEvent event) {
        loadWindow("/sqlQuery/DBqueries.fxml", "SQL QUERY");
    }

    @FXML
    private void loadSecondQuery(ActionEvent event) {
        loadWindow("/sqlQuery/query/DBqueries.fxml", "SQL QUERY");
    }

    @FXML
    private void loadThirdQuery(ActionEvent event) {
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
    private void showFirstQuery(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); 
        alert.setHeaderText(null);
        alert.setTitle("Show query");
        alert.setContentText("SELECT c.Customer_id,m.Movie_id, m.Movie_type, "
                + "s.sub_id,s.type,s.price FROM Movie m Join sub_type s ON m.Movie_type "
                + "= s.sub_id JOIN Subscription su ON su.sub_plan = s.sub_id JOIN CUSTOMER c "
                + "ON c.Customer_id = su.Customer_id");
        alert.showAndWait();

        
    }

    @FXML
    private void showSecondQuery(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); 
        alert.setHeaderText(null);
        alert.setTitle("Show query");
        alert.setContentText("SELECT DISTINCT c.Customer_id,c.City FROM CUSTOMER c, "
                + "Subscription s JOIN sub_type t ON s.Sub_plan = t.sub_id WHERE"
                + " type = 'HD' AND City = 'Seattle'");
        alert.showAndWait();
        
    }

    @FXML
    private void showThirdQuery(ActionEvent event) {
    }

    @FXML
    private void loadFourthQuery(ActionEvent event) {
    }

    @FXML
    private void showFourthQuery(ActionEvent event) {
    }
}
