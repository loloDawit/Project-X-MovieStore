/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.ui.Payment;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author dawitabera
 */
public class Customer {
    private final SimpleStringProperty cusID;
    
    public Customer(String pCusID){
        this.cusID = new SimpleStringProperty(pCusID);
    }
    
    public String getCusID() {
        return cusID.get();
    }

    
}
