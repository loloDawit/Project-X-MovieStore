/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie.store.database;

import java.sql.Statement;
//import static java.lang.Class.forName;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
//import jdk.nashorn.internal.ir.Statement;

/**
 *
 * @author dawitabera
 */
public final class DatabaseHandler {
    
    private static DatabaseHandler handler = null; 
    
    private static final String DB_URL = "jdbc:sqlite:/Users/dawitabera/ProjectXDB.db";
    private static Connection conn = null; 
    // connection string 
    // string connStr = "jdbc:derby://localhost:1527/projectXBeta [projectX on PROJECTX]
    private static final String connStr = "jdbc:derby://localhost:1527/projectXBeta [projectX on PROJECTX]";
    private static Statement stmt = null; 
    private static PreparedStatement pst; 
    
    
    // to prevent crash we need to make sure this class can not be access by other 
    // clases, since we cant have instance of this class more than one 
    // therefore make this class private and create another method to instaniate 
    // the object.
    private DatabaseHandler(){
        createConnection();
        //setupMovieTable();
       // setupCustomerTable();
        //setupCheckOutTable();
        
    }
    
    public static DatabaseHandler getIntance(){
        if(handler == null){
           handler = new DatabaseHandler();
        }
        return handler; 
        
    }
    // to prevent crash 
    
    // create DB connection
    void createConnection(){
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("SQLiteDatabae ready...!! ");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "can not laod database", "Database error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
    }
    public void closeConnection(){
            try {
                conn.close();
                System.out.println("Connection Closed...!!");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // create DB table
    void setupMovieTable(){
        String TABLE_NAME = "MOVIE"; 
        try {
            stmt = conn.createStatement();
            
            DatabaseMetaData dbm = conn.getMetaData(); 
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            
            if(tables.next()){
                System.out.println("Table "+ TABLE_NAME + " already exsit. Ready to go!");
            }else{
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "      id varchar(200) primary key,\n"
                        + "      title varchar(200),\n"
                        + "      actor varchar(200),\n"
                        + "      director varchar(100),\n"
                        //+ "      genre varchar(100),\n"
                        + "      isAvail boolean default true"
                        + " )");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() +"- - - setupDatabase--1");
        }finally{
            
        }
    }
    
    public ResultSet execQuery(String query){
        ResultSet result; 
        try {
            stmt = conn.createStatement(); 
            result = stmt.executeQuery(query);
            
        } catch (SQLException e) {
            System.out.println("Exception at execQuery: dataHandler" + e.getLocalizedMessage());
            return null;
        }finally{
            
        }
        return result;
    }
    
    public boolean execAction(String query){
        try {
            stmt = conn.createStatement();
            stmt.execute(query);
            
            return true; 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        }finally{
    }
    }
     public boolean execUpdate(String query){
        try {
            stmt = conn.createStatement();
            //stmt.executeUpdate(query);
            stmt.executeUpdate(query);
               // JOptionPane.showMessageDialog(null, "data " + message);
                System.out.println("data deleted succefully!!");
            
            
            return true; 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error: "+ e.getMessage(), "error occoured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery: dataHandler" + e.getLocalizedMessage());
            return false; 
        }finally{
    }
    }
     void setupCustomerTable() {
        String TABLE_NAME = "CUSTOMER";
        try {
            stmt = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "	id varchar(200) primary key,\n"
                        + "	fname varchar(200),\n"
                        + "	minit varchar(200),\n"
                        + "	lname varchar(200),\n"
                        + "	phone varchar(200),\n"
                        + "	email varchar(100),\n"
                        + "	city varchar(200),\n" 
                        + "	state varchar(200),\n" 
                        + "	zip varchar(100)\n" 
                        + " )");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " --- setupDatabase --2");
        } finally {
        }
    

    }
    //CURRENT_TIMESTAMP
    // table for issued movies 
     void setupCheckOutTable() {
        String TABLE_NAME = "CHECKOUT";
        try {
            stmt = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "	movieID varchar(200) primary key,\n"
                        + "     customerID varchar(200),\n"
                        + "	checkedoutTime timestamp default CURRENT_TIMESTAMP,\n"
                        + "     renew_count integer default 0,\n"
                        + "     FOREIGN KEY (movieID) REFERENCES MOVIE(id), \n"
                        + "     FOREIGN KEY (customerID) REFERENCES CUSTOMER(id)"
                        + " )");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " --- setupDatabase --3");
        } finally {
        }
    }
}
