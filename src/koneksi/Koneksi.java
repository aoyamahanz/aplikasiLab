/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Herman Patria
 */
public class Koneksi {
   private static Connection koneksi;
   public static Connection getConnect() {
       try{
           Class.forName("com.mysql.jdbc.Driver");
           try{
               String url = "jdbc:mysql://localhost/lab";
               koneksi = DriverManager.getConnection(url,"root","");
           }catch(SQLException e){
               JOptionPane.showMessageDialog(null,e,"Database tidak ditemukan", 0);
           }
       }catch(ClassNotFoundException e){
           JOptionPane.showMessageDialog(null, e, "Driver tidak ditemukan", 0);
       }
       return koneksi;
   }
}

