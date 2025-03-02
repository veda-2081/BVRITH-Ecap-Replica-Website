import java.sql.*;  
class NewClass
{  
public static void main(String args[])
{  
try{  
Class.forName("com.mysql.cj.jdbc.Driver");  
Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vnky","root","root");  
//here sonoo is database name, root is username and password  
 Statement stmt = con.createStatement();
      //Query to create a table
      String query ="create table t(tno int);";  
      
      stmt.execute(query);
      System.out.println("Table Created......");
//while(rs.next())  
//System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
con.close();  
}catch(Exception e){ System.out.println(e);}  
}  
} 