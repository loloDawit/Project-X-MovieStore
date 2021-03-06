/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.ui.Subscription;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author dawitabera
 */
public class Subscription{
    
    private final SimpleStringProperty subID;
    private final SimpleStringProperty cusID;
    private final SimpleStringProperty cusName;
    private final SimpleStringProperty subType; 
    
    public Subscription(String sID,String cName, String cID,String stype){
        this.cusID = new SimpleStringProperty(cID);
        this.cusName = new SimpleStringProperty(cName);
        this.subID = new SimpleStringProperty(sID);
        this.subType = new SimpleStringProperty(stype);
    }
    public String getSubID() {
            return subID.get();
    }
    public String getCusID() {
            return cusID.get();
    }
    public String getCusName() {
            return cusName.get();
    }
    public String getSubType() {
            return subType.get();
    }
}
