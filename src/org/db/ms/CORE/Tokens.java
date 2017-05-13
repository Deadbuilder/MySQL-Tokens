package org.db.ms.CORE;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.db.ms.API.MySQLTokensAPI;
import org.db.ms.CMDS.TokensCMD;
import org.db.ms.INTERFACE.InterfaceManager;
import org.db.ms.LISTENERS.Join;
import org.db.ms.UTILS.Language;

public class Tokens
  extends JavaPlugin
{
  private MySQLTokensAPI api = new MySQLTokensAPI();
  private MySQL mysql = new MySQL();
  private static Tokens instance;
  
  public void onEnable()
  {
	  
	  // Only reimplement if you are using the following depend
	  
/*	  if(Bukkit.getServer().getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
	        try
	        {
	          PlaceholderAPI.registerPlaceholder(this, "mysqltokens", new PlaceholderReplacer()
	          {
	            public String onPlaceholderReplace(PlaceholderReplaceEvent e)
	            {
	              Player p = e.getPlayer();
	              return !p.isOnline() ? "" : Integer.toString(instance.getAPI().getTokens(p));
	            }
	          });
	        }
	        catch (Exception ex)
	        {
	          ex.printStackTrace();
	        }
*/
	        new InterfaceManager(this);
	        PluginManager pm = getServer().getPluginManager();
	        pm.registerEvents(new Join(), this);
	        pm.registerEvents(InterfaceManager.get(), this);
    Language.loadMessages(this);
    Language.saveMessages(this);
            
    instance = this;
    loadCommands();
    getConfig().options().copyDefaults(true);
    saveDefaultConfig();
    this.mysql.checkTable();
	  }
 // }
  
  public void onDisable()
  {
    instance = null;
  }
  
  private void loadCommands()
  {
    getCommand("tokens").setExecutor(new TokensCMD());
    
  }
  
  public static Tokens getInstance()
  {
    return instance;
  }
  
  public MySQLTokensAPI getAPI()
  {
    return this.api;
  }
}
