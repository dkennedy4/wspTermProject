/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termProject;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 *
 * @author Tresspasser
 */
@Named(value = "login")
@SessionScoped
public class LoginBean implements Serializable
{

  /****************************
   * DataMembers
   ***************************/
//  @Inject
//  private FileBean file;

  @Inject
  private UserBean user;
  
//  @Inject 
//  private FileUploadBean upload;
  
  private String email;
  private String userPassword;
  private String errMsg;
  private boolean loggedIn;
  private String query = "select * from contacts where email = ? and password = ?";
  // data source for connection pool
  @Resource(name="jdbc/jsfUserInfo")
  private DataSource ds;
  
  
  /*****************************
   * Constructor
   *****************************/
  public LoginBean() {
  }


  /*****************************
   * Getters and Setters
   *****************************/
  
    public String getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }
  
  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword.trim();
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isLoggedIn()
  {
    return loggedIn;
  }
  
  /************************
   * Action methods
   ************************/
  
  public String login()
  {
    if(email.isEmpty() || userPassword.isEmpty())
    {
      return "index";
    }
    
    try
    {
      doLogin();
    }
    catch(SQLException e)
    {
      errMsg = e.toString();
      return "error";
    }
    
    if(loggedIn)
    {
//      file.setId(userId);
      return "welcome";
    }
    else
    {
      FacesContext.getCurrentInstance().addMessage(null, 
              new FacesMessage("Sorry no such username and password pair for " + 
              email));
    }
    return "";
  }
  
  public void doLogin() throws SQLException
  {
    if(ds == null)
    {
      throw new SQLException("No Data Source");
    }
    
    Connection conn = ds.getConnection();
    if(conn == null)
    {
      throw new SQLException("No Connection");
    }
    
    try
    {
      conn.setAutoCommit(false);
      try
      { 
        PreparedStatement loginQuery = conn.prepareStatement(query);
        
        loginQuery.setString(1, email);
        loginQuery.setString(2, userPassword);
        
        loginQuery.executeQuery();
        
        ResultSet result = loginQuery.executeQuery();
        
        if(!result.next())
        {
          errMsg = "help";
          loggedIn = false;
        }
        
        else 
        {
          loggedIn = true;
          
          user.setFirstName(result.getString("firstname"));
          user.setLastName(result.getString("lastname"));

          user.setEmail(result.getString("email"));
          user.setGender(result.getString("gender"));
          user.setPhoneNumber(result.getString("phonenumber"));
          user.setMylanguage(result.getString("contlang"));
          user.setHomeTown(result.getString("hometown"));
          
//          file.setUserId(result.getInt("userid"));
//          
//          upload.setUserId(result.getInt("userid"));
        }
 
      }
      finally
      {
        conn.close();
      }
    } 
    catch(SQLException e)
    {
      System.out.println("Database Error");
    }
  
  }
  
  public void logout() throws IOException
  {
    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
    loggedIn = false;
    user.setFirstName("");
    user.setLastName("");
    user.setEmail("");
    user.setPhoneNumber("");
    user.setGender("");
    user.setHomeTown("");
    user.setPassword("");
    user.setConfPasswd("");
    context.redirect("index.xhtml");
  }
  
  public void valid() throws IOException
  {
    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
    if(!loggedIn)
    {
      context.redirect("login.xhtml");
    }
  }
}
          