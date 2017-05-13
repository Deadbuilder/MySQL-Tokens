package org.db.ms.CORE;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class SetData
{
  MySQL mysql = new MySQL();
  FetchData fd = new FetchData();
  
  public void giveTokens(final OfflinePlayer p, final int tokens)
  {
    Bukkit.getScheduler().runTaskAsynchronously(Tokens.getInstance(), 
      new Runnable()
      {
        public void run()
        {
          try
          {
            PreparedStatement sql = SetData.this.mysql
              .getCurrentConnection()
              .prepareStatement(
              "INSERT INTO `mTokensData` (UUID, Tokens) VALUES (?,?) ON DUPLICATE KEY UPDATE Tokens = ?");
            sql.setString(1, p.getUniqueId().toString());
            sql.setInt(2, tokens);
            sql.setInt(3, tokens + SetData.this.fd.getTokens(p));
            sql.execute();
            sql.close();
          }
          catch (SQLException e)
          {
            e.printStackTrace();
          }
        }
      });
  }
  
  public void giveTokensU(final UUID p, final int tokens)
  {
    Bukkit.getScheduler().runTaskAsynchronously(Tokens.getInstance(), 
      new Runnable()
      {
        public void run()
        {
          try
          {
            PreparedStatement sql = SetData.this.mysql
              .getCurrentConnection()
              .prepareStatement(
              "INSERT INTO `mTokensData` (UUID, Tokens) VALUES (?,?) ON DUPLICATE KEY UPDATE Tokens = ?");
            sql.setString(1, p.toString());
            sql.setInt(2, tokens);
            sql.setInt(2, tokens + SetData.this.fd.getTokensu  (p));
            sql.execute();
            sql.close();
          }
          catch (SQLException e)
          {
            e.printStackTrace();
          }
        }
      });
  }
  public void takeTokens(final OfflinePlayer p, final int tokens)
  {
    Bukkit.getScheduler().runTaskAsynchronously(Tokens.getInstance(), 
      new Runnable()
      {
        public void run()
        {
          try
          {
            PreparedStatement sql = SetData.this.mysql
              .getCurrentConnection()
              .prepareStatement(
              "INSERT INTO `mTokensData` (UUID, Tokens) VALUES (?,?) ON DUPLICATE KEY UPDATE Tokens = ?");
            sql.setString(1, p.getUniqueId().toString());
            sql.setInt(2, SetData.this.fd.getTokens(p) - tokens);
            sql.setInt(3, SetData.this.fd.getTokens(p) - tokens);
            sql.execute();
            sql.close();
          }
          catch (SQLException e)
          {
            e.printStackTrace();
          }
        }
      });
  }
  
  public void resetTokens(final OfflinePlayer p)
  {
    Bukkit.getScheduler().runTaskAsynchronously(Tokens.getInstance(), 
      new Runnable()
      {
        public void run()
        {
          try
          {
            PreparedStatement sql = SetData.this.mysql
              .getCurrentConnection()
              .prepareStatement(
              "INSERT INTO `mTokensData` (UUID, Tokens) VALUES (?,?) ON DUPLICATE KEY UPDATE Tokens = ?");
            sql.setString(1, p.getUniqueId().toString());
            sql.setInt(2, 0);
            sql.setInt(3, 0);
          }
          catch (SQLException e)
          {
            e.printStackTrace();
          }
        }
      });
  }
}
