/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.ui.listmovie;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.layout.AnchorPane;
import movie.store.FXMLDocumentController;
import movie.store.database.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class MovieListController implements Initializable {
    
   // DatabaseHandler handler; 
    ObservableList<Movie> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Movie> tableView;
    @FXML
    private TableColumn<Movie, String> titleCol;
    @FXML
    private TableColumn<Movie, String> idCol;
    @FXML
    private TableColumn<Movie, String> actorCol;
    @FXML
    private TableColumn<Movie, String> directorCol;
    private TableColumn<Movie, String> availabilityCol;
    @FXML
    private JFXTextField searchInput;
    @FXML
    private TableColumn<Movie, String> genrecol;
    @FXML
    private TableColumn<Movie, String> yearcol;
    @FXML
    private TableColumn<Movie, String> Copycol;
    @FXML
    private TableColumn<Movie, String> typecol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol(); 
       // DatabaseHandler handler = DatabaseHandler.getIntance(); 
        loadData(); 
    }    
    // column initilizte 
    private  void initCol(){
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        actorCol.setCellValueFactory(new PropertyValueFactory<>("actor"));
        directorCol.setCellValueFactory(new PropertyValueFactory<>("director"));
        genrecol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        yearcol.setCellValueFactory(new PropertyValueFactory<>("year"));
        typecol.setCellValueFactory(new PropertyValueFactory<>("mtype"));
        Copycol.setCellValueFactory(new PropertyValueFactory<>("availabilty"));
        
         
    }
    // extract data from the DB and put in to the table
    // create a DB object

    private void loadData() {
        DatabaseHandler handler = DatabaseHandler.getIntance();
        String qu = "SELECT * FROM MOVIE";
        ResultSet rs = handler.execQuery(qu);
        try {
            // accessing the data from the database
            while(rs.next()){
                String titlex = rs.getString("Movie_title");
                String id = rs.getString("Movie_id");
                String actor = rs.getString("Movie_actor");
                String director = rs.getString("Movie_director");
                String mtype = rs.getString("Movie_type");
                String year = rs.getString("Movie_year");
                String genre = rs.getString("Movie_genre");
                String copy = rs.getString("Movie_copy");
                
                list.add(new Movie(titlex, id, actor, director, genre,year,mtype,copy));
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        tableView.getItems().setAll(list);
    }

    @FXML
    private void searchOperation(ActionEvent event) {
          // filtered list to search the data 
        FilteredList<Movie> filteredData = new FilteredList<>(list, e ->true);
        searchInput.setOnKeyTyped(e ->{
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            
            filteredData.setPredicate((Predicate<? super Movie>)movie ->{
                // add the observable data to the list if its empty or null
                if(newValue == null || newValue.isEmpty()){
                    return  true; 
                }
                
                // search the data with respect to ID and name 
                String lowerCaseFilter = newValue.toLowerCase(); 
                // search with respect to ID
                if(movie.getId().contains(newValue)){
                    return true; 
                }else if(movie.getDirector().toLowerCase().contains(lowerCaseFilter)){
                    return true; 
                }else if(movie.getActor().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                    
                }
               
                return false; 
                
            });
        });
        // print only the sorted list to the table 
        SortedList<Movie> sortedData = new SortedList<>(filteredData); 
        // add the sorted list to the table 
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        // set the table data as sorted list 
        tableView.setItems(sortedData);
            
        });
    }
    
    public static  class Movie{
        private final SimpleStringProperty title; 
        private final SimpleStringProperty id; 
        private final SimpleStringProperty actor; 
        private final SimpleStringProperty director; 
        private final SimpleStringProperty availabilty; 
        private final SimpleStringProperty genre; 
        private final SimpleStringProperty mtype; 
        private final SimpleStringProperty year; 
        
        public Movie(String title, String id, String actor, String director, String genre,String year,String type,String copy){
            this.title = new SimpleStringProperty(title);
            this.id = new SimpleStringProperty(id);
            this.actor = new SimpleStringProperty(actor);
            this.director = new SimpleStringProperty(director);
            this.availabilty = new SimpleStringProperty(copy);
            this.genre = new SimpleStringProperty (genre);
            this.mtype = new SimpleStringProperty (type);
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

        public String getAvailabilty() {
            return availabilty.get();
        }

        public String getGenre() {
            return genre.get();
        }

        public String getMtype() {
            return mtype.get();
        }

        public String getYear() {
            return year.get();
        }
        


        
        
    }
    
}
