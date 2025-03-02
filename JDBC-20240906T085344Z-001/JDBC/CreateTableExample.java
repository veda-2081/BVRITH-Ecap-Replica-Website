import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class CreateTableExample {
   public static void main(String args[]) throws SQLException {
      //Registering the Driver
      Class.forName("com.mysql.cj.jdbc.Driver"); 
      //Getting the connection
      Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vnky","root","root");  
//here vnky is database name, root is username and password  
      System.out.println("Connection established......");
      //Creating the Statement
      Statement stmt = con.createStatement();
      //Query to create a table
      String q = "CREATE TABLE CUSTOMERS("
         + "ID INT NOT NULL, "
         + "NAME VARCHAR (20) NOT NULL, "
         + "AGE INT NOT NULL, "
         + "SALARY DECIMAL (18, 2), "
         + "ADDRESS CHAR (25) , "
         + "PRIMARY KEY (ID))";
      stmt.execute(q);
      System.out.println("Table Created......");
   }
}