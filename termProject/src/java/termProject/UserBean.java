
package termProject;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.sql.DataSource;


@Named(value = "user")
@SessionScoped
public class UserBean implements Serializable
{
  
  /***********************************
   * DataMembers
   ***********************************/

  @Resource(name="jdbc/jsfUserInfo")
  private DataSource ds;
 
  private String lastName;
  private String firstName;
  private String email;
  private String password;
  private String confPasswd;
  private String phoneNumber;
  private String gender;
  private String[] language;
  private String mylanguage;
  private String homeTown;
  
  private String strError;
  private PreparedStatement psContact;
  private String myContactInsert; 
  private String passwdMsg;
  
  
  /***********************************
   * Constructor
   ***********************************/
  public UserBean() {}


  /************************************
   * Getters and Setters
   ************************************/
      

  public String getPasswdMsg() {
    return passwdMsg;
  }

  public void setPasswdMsg(String passwdMsg) {
    this.passwdMsg = passwdMsg;
  }
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email.trim();
  }


  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber.trim();
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender.trim();
  }

  public String[] getLanguage() {
    return language;
  }

  public void setLanguage(String[] language) {
    this.language = language;
  }

  public String getHomeTown() {
    return homeTown;
  }

  public void setHomeTown(String homeTown) {
    this.homeTown = homeTown.trim();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password.trim();
  }

  public String getStrError() {
    return strError;
  }

  public void setStrError(String strError) {
    this.strError = strError;
  }

  public String getConfPasswd() {
    return confPasswd;
  }

  public void setConfPasswd(String confPasswd) {
    this.confPasswd = confPasswd.trim();
  }

  public String getMylanguage() {
    return mylanguage;
  }

  public void setMylanguage(String mylanguage) {
    this.mylanguage = mylanguage;
  }

  
  
  /**************************
   * Action methods
   **************************/
  
 public String doInsert()
  {
    
    if(!password.equals(confPasswd))
    {
      passwdMsg = "Your passwords don't match up";
        
      return "";        
    }
    else
    {
      passwdMsg = "";
    }
    myContactInsert = "Insert Into contacts" + 
            "(lastname, firstname, email, phonenumber, contLang, gender, hometown, password)" +
            " values(?,?,?,?,?,?,?,?)";

    Connection conn;
    boolean commited = false;
    try
    {
      conn = ds.getConnection();
      try
      {
        if(ds == null)
      {
        throw new SQLException("No Data Source"); 
      }
      conn = ds.getConnection();
      if(conn == null)
      {
        throw new SQLException("No Connection");
      }
      
      conn.setAutoCommit(false);
      
      psContact = conn.prepareStatement(myContactInsert);

      psContact.setString(1, lastName);
      psContact.setString(2, firstName);
      psContact.setString(3, email);
      psContact.setString(4, phoneNumber);
      psContact.setString(5, retrieveLanguage());
      psContact.setString(6, gender);
      psContact.setString(7, homeTown);
      psContact.setString(8, password);
      
     
      psContact.execute();
      
      conn.commit();
      commited = true;
      }
      finally
      {
        if(!commited)
        {
          conn.rollback();
        }
        
        conn.close();
      }
    }
    catch(SQLException ex)
    {
      strError = ex.toString();
      return "error";
      
    }
    return "results";
  }
 
  public String retrieveLanguage()
  {
    return Arrays.toString(language);
  }
  
}

