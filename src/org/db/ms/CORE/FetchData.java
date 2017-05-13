package org.db.ms.CORE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.OfflinePlayer;

public class FetchData
{
  MySQL mysql = new MySQL();
  
  public int getTokens(OfflinePlayer p)
  {
    try
    {
      ResultSet r = this.mysql
        .getCurrentConnection()
        .createStatement()
        .executeQuery(
        "SELECT Tokens FROM mTokensData WHERE UUID = '" + 
        p.getUniqueId().toString() + "'");
      if (r.next()) {
        return r.getInt("Tokens");
      }
      r.close();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return -1;
  }
  
  public int getTokensu(UUID p)
  {
    try
    {
      ResultSet r = this.mysql
        .getCurrentConnection()
        .createStatement()
        .executeQuery(
        "SELECT Tokens FROM mTokensData WHERE UUID = '" + 
        p + "'");
      if (r.next()) {
        return r.getInt("Tokens");
      }
      r.close();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return -1;
  }
  
  public boolean isInDb(OfflinePlayer p)
  {
    try
    {
      ResultSet r = this.mysql
        .getCurrentConnection()
        .createStatement()
        .executeQuery(
        "SELECT * FROM mTokensData WHERE UUID = '" + 
        p.getUniqueId().toString() + "'");
      boolean contains = r.next();
      r.close();
      return contains;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public boolean isInDb(UUID uuid)
  {
    try
    {
      ResultSet r = this.mysql
        .getCurrentConnection()
        .createStatement()
        .executeQuery(
        "SELECT * FROM mTokensData WHERE UUID = '" + 
        uuid + "'");
      boolean contains = r.next();
      r.close();
      return contains;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }
}
