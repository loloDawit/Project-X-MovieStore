/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.resources.populateCSV;

import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
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
import movie.store.database.DatabaseHandler;
import movie.store.ui.listmovie.MovieListController;

/**
 * FXML Controller class
 *
 * @author dawitabera
 */
public class AddCSVController implements Initializable {
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
    //private TableColumn<Movie, String> availabilityCol;
    @FXML
    private JFXTextField searchInput;
    @FXML
    private TableColumn<Movie, String> genreCol;
    @FXML
    private TableColumn<Movie, String> yearCol;
    @FXML
    private TableColumn<Movie, String> Copies;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol(); 
       // DatabaseHandler handler = DatabaseHandler.getIntance(); 
        
    }
    // Initializes the table view     
    private void initCol() {
        
       titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
       idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
       actorCol.setCellValueFactory(new PropertyValueFactory<>("actor"));
       directorCol.setCellValueFactory(new PropertyValueFactory<>("director"));
       genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
       yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
       Copies.setCellValueFactory(new PropertyValueFactory<>("copies"));
       
    }

   
    @FXML
    // searchOperation 
    // The function uses the filtered list to perform the search operation using 
    // the movie id, director name or actors name. 
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
                    
                }else if(movie.getYear().contains(newValue)){
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

    @FXML
    private void loadCSV(ActionEvent event) {
        readCSV();
        
    }
    
        public void readCSV(){
        String csvFile = "/Users/dawitabera/Desktop/TheMovieExcelList.csv"; 
        String fileDelimioter = ","; 
        
        BufferedReader bf; 
        try {
            bf = new BufferedReader(new FileReader(csvFile)); 
            String line; 
            while((line = bf.readLine()) != null){
                String[] fields = line.split(fileDelimioter, -1); 
                Movie movie = new Movie(fields[0], fields[1], fields[2], fields[3], fields[4],fields[5],fields[6]);
                list.add(movie);
            }
            
        } catch (IOException e) {
           // Logger.getLogger(AddCSVController.class.getName().log(Level.SEVERE, null, ex);
            
        }
        tableView.getItems().setAll(list);
    }
      
    public static  class Movie{
        
        private final SimpleStringProperty title; 
        private final SimpleStringProperty id; 
        private final SimpleStringProperty actor; 
        private final SimpleStringProperty director;
        private final SimpleStringProperty year; 
        private final SimpleStringProperty genre; 
        private final SimpleStringProperty copies; 
        
        public Movie(String id, String title, String genre, String year, String director, String actor,String copy){
            this.title = new SimpleStringProperty(title);
            this.id = new SimpleStringProperty(id);
            this.actor = new SimpleStringProperty(actor);
            this.director = new SimpleStringProperty(director);
            this.genre = new SimpleStringProperty(genre);
            this.year = new SimpleStringProperty(year);
            this.copies = new SimpleStringProperty(copy);
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

        public String getYear() {
            return year.get();
        }

        public String getGenre() {
            return genre.get();
        }

        public String getCopies() {
            return copies.get();
        }
        
        
        
    }
    
}
