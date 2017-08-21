/*
 * This class is going to be used to populate the contentets History query from
 * the database in to a table.
 */
package movie.store.ui.main;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author dawitabera
 */
public class History{
    private final SimpleStringProperty movieID;
    private final SimpleStringProperty customerID; 
    private final SimpleStringProperty count;
    private final SimpleStringProperty time; 
    private final SimpleStringProperty movieName;
    private final SimpleStringProperty cusName;
    
    public History(String mID,String cusID, String tDate,String cnt, String mname,String cName){
        this.movieID = new SimpleStringProperty(mID);
        this.customerID = new SimpleStringProperty(cusID);
        this.count = new SimpleStringProperty(cnt);
        this.time = new SimpleStringProperty(tDate); 
        this.movieName = new SimpleStringProperty(mname); 
        this.cusName = new SimpleStringProperty(cName); 
    }
    public String getMovieID() {
        return movieID.get();
    }

    public String getCustomerID() {
        return customerID.get();
    }

    public String getCount() {
        return count.get();
    }

    public String getTime() {
        return time.get();
    }

    public String getMovieName() {
        return movieName.get();
    }
    public String getCusName() {
        return cusName.get();
    }
}
