import  javax.swing.*;
import java.sql.*;

import static java.lang.System.exit;

public class Modele{

    public Connection seConnecter(String url, String user, String password) throws SQLException{
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur lors du chargement" + e);
            exit(0);
        }
        return connection;
    }

    public ResultSet traiterRequete(String req) throws SQLException{
        Statement stmt = null;
        Connection connexion = null;
        ResultSet result = null;

        try {
            String url = "jdbc:mysql://localhost:3306/bddgraph";
            String user = "root";
            String password = "";
            connexion = this.seConnecter(url, user, password);
            stmt = (Statement) connexion.createStatement();
            result = stmt.executeQuery(req);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public void remplirCombo3(JComboBox<String> combo, String req) throws SQLException{
        Statement stmt = null;
        Connection connexion = null;

        try{
            String url = "jdbc:mysql://localhost:3306/bddgraph";
            String user = "root";
            String password = "";

            connexion = this.seConnecter(url, user, password);
            stmt = (Statement) connexion.createStatement();
            ResultSet result = stmt.executeQuery(req);

            while(result.next()){
                combo.addItem(result.getString(1));
            }
            connexion.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public int verifLogin(String req) throws SQLException{
        Statement stmt = null;
        Connection connexion = null;
        int nombre = 0;

        try{
            String url = "jdbc:mysql://localhost:3306/bddgraph";
            String user = "root";
            String password = "";
            connexion = this.seConnecter(url, user, password);
            stmt = (Statement) connexion.createStatement();
            ResultSet result = stmt.executeQuery(req);

            while(result.next()){
                nombre = result.getInt(1);
            }
        }
        catch (SQLException e){
            System.out.println("Connection error" + e);
        }
        return nombre;
    }
}
