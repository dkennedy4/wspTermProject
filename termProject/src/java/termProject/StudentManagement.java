
package termProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author David Kennedy
 */
@Named(value = "Manage")
@Dependent
public class StudentManagement {

  public StudentManagement() {
  }
  
  
    public ResultSet getAllStud() throws SQLException 
  {
    Connection conn = ds.getConnection();
    try 
    {
       psMembers = conn.prepareStatement(query);  
         
       ResultSet result = psMembers.executeQuery();         
       // return ResultSupport.toResult(result);
       CachedRowSet crs = new com.sun.rowset.CachedRowSetImpl();         
       // or use an implementation from your database vendor
       crs.populate(result);
       return crs;
    }
    finally 
    {
      conn.close();
    }
   }
    
    @Resource(name="jdbc/f2lDatabasepool")
    private DataSource ds;
    private String query = "Select * From members";
    private PreparedStatement psMembers;
    
    
}
