package org.db.ms.API;

import org.bukkit.entity.Player;
import org.db.ms.CORE.FetchData;
import org.db.ms.CORE.SetData;

public class MySQLTokensAPI
{
	  private final SetData sd = new SetData();
	  private final FetchData fd = new FetchData();
	  
	  public int getTokens(Player p)
	  {
	    return this.fd.getTokens(p);
	  }
	  
	  public boolean hasTokens(Player p)
	  {
	    return this.fd.isInDb(p);
	  }
	  
	  public void resetTokens(Player p)
	  {
	    this.sd.resetTokens(p);
	  }
	  
	  public void addTokens(Player p, int amount)
	  {
	    this.sd.giveTokens(p, amount);
	  }
	  
	  public void giveTokens(Player p, int amount)
	  {
	    this.sd.giveTokens(p, amount);
	  }
	  
	  public void takeTokens(Player p, int amount)
	  {
	    this.sd.takeTokens(p, amount);
	  }
	  
	  public boolean hasEnough(Player p, int amount)
	  {
	      return ((Integer)this.fd.getTokens(p)).intValue() >= amount;
	  }
	  
	  
}
