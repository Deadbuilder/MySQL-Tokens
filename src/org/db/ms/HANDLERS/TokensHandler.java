package org.db.ms.HANDLERS;

import org.bukkit.entity.Player;
import org.db.ms.UTILS.Chatter;

public class TokensHandler
{
	private static TokensHandler instance;
	
	public static TokensHandler getInstance()
	  {
	    return instance;
	  }
	
  public boolean isInt(String s)
  {
    try
    {
      Integer.parseInt(s);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }
  
  public void giveTokensMSG(Player p, Player target, int tokens)
  {
    p.sendMessage(Chatter.color("&6&lTokens &8&l" + Chatter.arrow() + 
      " &7You have given &e" + tokens + " &7tokens to &e" + 
      target.getName() + "&7."));
    target.sendMessage(Chatter.color("&6&lTokens &8&l" + Chatter.arrow() + 
      " &e" + p.getName() + " &7has given you &e" + tokens + 
      " &7tokens."));
  }
  
  public void takeTokensMSG(Player p, Player target, int tokens)
  {
    p.sendMessage(Chatter.color("&6&lTokens &8&l" + Chatter.arrow() + 
      " &7You have taken &e" + tokens + " &7tokens from &e" + 
      target.getName() + "'s &7account."));
    target.sendMessage(Chatter.color("&6&lTokens &8&l" + Chatter.arrow() + 
      " &e" + p.getName() + " &7has taken &e" + tokens + 
      " &7tokens from your account."));
  }
  
  public void noperm(Player p)
  {
    p.sendMessage(Chatter.color("&c&lPermission &8&l" + Chatter.arrow() + " &7You don't have permission!"));
  }
  
  public void playerOffline(Player p, String s)
  {
    p.sendMessage(Chatter.color("&c&lError &8&l" + Chatter.arrow() + " &e" + s + " &7isn't online."));
  }
  
  public void tokensHelp(Player p)
  {
    p.sendMessage(Chatter.color("&c&m====================================="));
    p.sendMessage(Chatter.color("&7» &6&lMySQL-Tokens&7:"));
    p.sendMessage(Chatter.color(" "));
    p.sendMessage(Chatter.color("&7» &e/tokens &7(Displays your balance)"));
    p.sendMessage(Chatter.color("&7» &e/tokens shop &7(Opens the shop)"));
    p.sendMessage(Chatter.color(" "));
    p.sendMessage(Chatter.color("&7» &6&lAdmin Commands&7:"));
    p.sendMessage(Chatter.color(" "));
    p.sendMessage(Chatter.color("&7» &e/tokens <give|take|add|reset> <user> <amount> (No need for amount with reset.)"));
    p.sendMessage(Chatter.color("&c&m====================================="));
  }
}
