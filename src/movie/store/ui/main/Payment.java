/*
 * This class is used to populate the contentets Payement entity from the database 
 * in to a table.
 */
package movie.store.ui.main;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author dawitabera
 */
public class Payment{
    private final SimpleStringProperty payDate;
    private final SimpleStringProperty paySubtype;
    private final SimpleStringProperty cusID; 
    private final SimpleStringProperty payID;
    private final SimpleStringProperty payMethod;
    
    public Payment(String pID,String pMethod, String pDate,String pCusID,String pSubType){
        this.cusID = new SimpleStringProperty(pCusID);
        this.payMethod = new SimpleStringProperty(pMethod);
        this.payDate = new SimpleStringProperty(pDate);
        this.paySubtype = new SimpleStringProperty(pSubType);
        this.payID = new SimpleStringProperty(pID);
    }
    public String getPayDate() {
        return payDate.get();
    }

    public String getPaySubtype() {
        return paySubtype.get();
    }

    public String getCusID() {
        return cusID.get();
    }

    public String getPayID() {
        return payID.get();
    }

    public String getPayMethod() {
        return payMethod.get();
    }
}
