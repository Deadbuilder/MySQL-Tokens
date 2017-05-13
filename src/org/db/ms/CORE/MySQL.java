package org.db.ms.CORE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL
{
  private Connection con;
  
  public synchronized void openConnection()
  {
    try
    {
      this.con = DriverManager.getConnection("jdbc:mysql://" + 
        Tokens.getInstance().getConfig().getString("host") + ":" + 
        Tokens.getInstance().getConfig().getString("port") + "/" + 
        Tokens.getInstance().getConfig().getString("database"), 
        Tokens.getInstance().getConfig().getString("user"), 
        Tokens.getInstance().getConfig().getString("password"));
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public synchronized void closeConnection()
  {
    try
    {
      if ((!this.con.isClosed()) || (this.con != null)) {
        this.con.close();
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public synchronized void checkTable()
  {
    try
    {
      getCurrentConnection().createStatement().execute("CREATE TABLE IF NOT EXISTS `mTokensData` (\n`UUID` varchar(36) NOT NULL,\n`Tokens` int(11) unsigned NOT NULL,\nPRIMARY KEY  (`UUID`)\n)");
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public Connection getCurrentConnection()
  {
    try
    {
      if ((this.con == null) || (this.con.isClosed())) {
        this.con = DriverManager.getConnection(
          "jdbc:mysql://" + 
          Tokens.getInstance().getConfig()
          .getString("host") + 
          ":" + 
          Tokens.getInstance().getConfig()
          .getString("port") + 
          "/" + 
          Tokens.getInstance().getConfig()
          .getString("database"), 
          Tokens.getInstance().getConfig().getString("user"), 
          Tokens.getInstance().getConfig().getString("password"));
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return this.con;
  }
}
