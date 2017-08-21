/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.ui.CustomerView;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import movie.store.FXMLDocumentController;
import movie.store.database.DatabaseHandler;
import movie.store.ui.listmovie.MovieListController;
import movie.store.ui.login.LoginController;
import movie.store.ui.main.MainController;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class CustomerViewController implements Initializable {
     ObservableList<Movie> list = FXCollections.observableArrayList();
     DatabaseHandler handler ;

    @FXML
    private TextField movieIDinput;
    @FXML
    private Text mTitle;
    @FXML
    private Text mDirector;
    @FXML
    private Text mActor;
    @FXML
    private Text mGenre;
    @FXML
    private Text mYear;
    @FXML
    private TableColumn<Movie, String> title;
    @FXML
    private TableColumn<Movie, String> movieID;
    @FXML
    private TableColumn<Movie, String> actor;
    @FXML
    private TableColumn<Movie, String> director;
    @FXML
    private TableColumn<Movie, String> genre;
    @FXML
    private TableColumn<Movie, String> year;
    @FXML
    private TableView<Movie> tableview;
    DatabaseHandler databaseHandler; 
    @FXML
    private Label cusLable;
    
    @FXML LoginController username;
    @FXML LoginController password; 
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol(); 
        loadData();
        databaseHandler = DatabaseHandler.getIntance();
    }    

    @FXML
    private void loadMovieInfo(ActionEvent event) {
        clearMovieCatche();
        // get text from the text field
        String id = movieIDinput.getText(); 
        String qu = "SELECT * FROM Movie WHERE Movie_id = '" + id + "'";
        ResultSet rs = handler.execQuery(qu); 
        Boolean flag = false; 
        
        try {
            while(rs.next()){
                String movTitle = rs.getString("Movie_title");
                String movDirector = rs.getString("Movie_director");
                String movActor = rs.getString("Movie_actor");
                String movGenre = rs.getString("Movie_genre");
                String movYear = rs.getString("Movie_year"); 
                
                
                mTitle.setText(movTitle); 
                mDirector.setText(movDirector);
               // String status = (mStatus) ? "Available" : "Not Available";
                mActor.setText(movActor);
                mGenre.setText(movGenre);
                mYear.setText(movYear);
                
                flag = true; 
            }
            if(!flag){
                mTitle.setText("No Such Movie");
                
            }
        } catch (SQLException e) {
        }
        
    }
     private  void initCol(){
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        movieID.setCellValueFactory(new PropertyValueFactory<>("id"));
        actor.setCellValueFactory(new PropertyValueFactory<>("actor"));
        director.setCellValueFactory(new PropertyValueFactory<>("director"));
        genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        
         
    }
    private void loadData() {
        handler = DatabaseHandler.getIntance();
        String qu = "SELECT * FROM MOVIE";
        ResultSet rs = handler.execQuery(qu);
        try {
            // accessing the data from the database
            while(rs.next()){
                String titlex = rs.getString("Movie_title");
                String id = rs.getString("Movie_id");
                String actor = rs.getString("Movie_actor");
                String director = rs.getString("Movie_director");
                String year = rs.getString("Movie_year");
                String genre = rs.getString("Movie_genre");
                
                list.add(new Movie(titlex, id, actor, director, genre,year));
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        tableview.getItems().setAll(list);
    }

    private void clearMovieCatche() {
        mActor.setText("");
        mTitle.setText("");
        mDirector.setText("");
        mGenre.setText("");
        mYear.setText("");
    }

    @FXML
    private void performCheckOut(ActionEvent event) {
        String movieIDStr = movieIDinput.getText();
        
        
        
        if(movieIDinput.getText().isEmpty()){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Movie Checkingout Error");
            alert1.setHeaderText(null);
            alert1.setContentText("Both MoiveID and CustomerID Must Be Provided");
            alert1.showAndWait();   
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confrim CheckOut Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to checkout this movie ' "+ mTitle.getText() +" ' " +"?");
        
        TextInputDialog dialog = new TextInputDialog(); 
        dialog.setTitle("Movie Check Out");
        dialog.setHeaderText(null);
        dialog.setContentText("Please type in your ID: ");
        Optional<String>dialogresult = dialog.showAndWait();
        String customerIDStr = dialogresult.get();
        
       
        Optional<ButtonType> response = alert.showAndWait(); 
            
            if(dialogresult.isPresent() ){
                if(response.get() == ButtonType.OK ){
                    String str = "INSERT INTO Checkout(customer_id,movie_id) VALUES ("
                    + "'" + customerIDStr + "',"
                    + "'" + movieIDStr + "')";
                    String str2 = "UPDATE Movie SET Movie_copy = Movie_copy -1 WHERE Movie_id = '" +movieIDStr + "'";
                    System.out.println(str + " and " +str2);
                    
                    if(databaseHandler.execAction(str) && databaseHandler.execAction(str2)){
                        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                        alert1.setTitle("Success");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Movie checkedout Complete");
                        alert1.showAndWait();
                    }else{
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Movie Checkingout Error");
                        alert1.setHeaderText(null);
                        alert1.setContentText("The Movie is Unavailable");
                        alert1.showAndWait();
                    }
                }else{
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Cancelled");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Movie checkingout cancelled");
                    alert1.showAndWait();
                }
            }else{ 
                System.out.println("ERROR NO INPUT");
                
            }
            
    }
    
    public void getUserInfo(String user){
        cusLable.setText(user);
    }

    @FXML
    private void performRenew(ActionEvent event) {
        
    }

    @FXML
    private void performReturn(ActionEvent event) {
        
    }

    @FXML
    private void performlogout(ActionEvent event) {
        closeStage();
        loadLoginUI();
        
    }
    // Function called performlogout method
    // 
    public void loadLoginUI(){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/movie/store/ui/login/login.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("P R O J E C T  X   M O V I E  D A T A B A S E   S Y S T E M");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void closeStage() {
        ((Stage)movieIDinput.getScene().getWindow()).close();
    }
    
        public static  class Movie{
        private final SimpleStringProperty title; 
        private final SimpleStringProperty id; 
        private final SimpleStringProperty actor; 
        private final SimpleStringProperty director;  
        private final SimpleStringProperty genre;  
        private final SimpleStringProperty year; 
        
        public Movie(String title, String id, String actor, String director, String genre,String year){
            this.title = new SimpleStringProperty(title);
            this.id = new SimpleStringProperty(id);
            this.actor = new SimpleStringProperty(actor);
            this.director = new SimpleStringProperty(director);
            this.genre = new SimpleStringProperty (genre);
            this.year = new SimpleStringProperty (year);
        }

        public String getTitle() {
            return title.get();
        }

        public String getId() {
            return id.get();
        }

        public String getActor() {
            return actor.get();
        }

        public String getDirector() {
            return director.get();
        }

        public String getGenre() {
            return genre.get();
        }

        public String getYear() {
            return year.get();
        }
    }
    
}
