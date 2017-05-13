package org.db.ms.CMDS;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.db.ms.CORE.FetchData;
import org.db.ms.CORE.Tokens;
import org.db.ms.HANDLERS.TokensHandler;
import org.db.ms.INTERFACE.InterfaceManager;
import org.db.ms.UTILS.Chatter;

public class TokensCMD
  implements CommandExecutor
{
  FetchData fd = new FetchData();
  TokensHandler th = new TokensHandler();
  
  public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args)
  {
      if (cmd.getName().equalsIgnoreCase("thugbucks"))
      {
       if(sender instanceof Player)
        if (args.length == 0)
        {
        	Player p = (Player)sender;
          sender.sendMessage(Chatter.color("&6&lThugbucks &8&l" + 
            Chatter.arrow() + " &7You have &e" + 
            this.fd.getTokens(p) + " &7thugbucks."));
          return true;
        }
        if(args.length == 1) {
        	Player p = (Player)sender;
        	if(args[0].equalsIgnoreCase("shop")) {
        		if ((sender instanceof Player))
                {
        			InterfaceManager.get().display(p);
                }else{
                	sender.sendMessage(Chatter.color("&c&m====================================="));
                    sender.sendMessage(Chatter.color("&7» &6&lThugbucks&7:"));
                    sender.sendMessage(Chatter.color(" "));
                    sender.sendMessage(Chatter.color("&7» &e/thugbucks &7(Displays your balance)"));
                    sender.sendMessage(Chatter.color("&7» &e/thugbucks shop &7(Opens the shop)"));
                    sender.sendMessage(Chatter.color(("&7» &e/tpay <player> <amount>")));
                    sender.sendMessage(Chatter.color(" "));
                    sender.sendMessage(Chatter.color("&c&m====================================="));
                }
        	}else if(args[0].equalsIgnoreCase("help")) {
        		sender.sendMessage(Chatter.color("&c&m====================================="));
        	    sender.sendMessage(Chatter.color("&7» &6&lThugbucks&7:"));
        	    sender.sendMessage(Chatter.color(" "));
        	    sender.sendMessage(Chatter.color("&7» &e/thugbucks &7(Displays your balance)"));
        	    sender.sendMessage(Chatter.color("&7» &e/thugbucks shop &7(Opens the shop)"));
        	    sender.sendMessage(Chatter.color(" "));
        	    sender.sendMessage(Chatter.color("&c&m====================================="));
        	}else if(sender instanceof ConsoleCommandSender)
        	  {
        		if(args[0].equalsIgnoreCase("help")) {
        	    sender.sendMessage("/thugbucks <give|take|add|reset> <user> <int>(args[2] <int> not needed for reset.)");
        	  }
        	}
        
        if (args.length == 2)
        {
          if (sender.hasPermission("thugbucks.reset"))
          {
            if (args[0].equalsIgnoreCase("reset"))
            {
              Player target = Bukkit.getPlayer(args[1]);
              if (target != null)
              {
                Tokens.getInstance().getAPI().resetTokens(target);
                sender.sendMessage(Chatter.color("&6&lThugbucks &8&l" + 
                  Chatter.arrow() + 
                  " &7You have reset &e" + 
                  target.getName() + "'s &7thugbucks!"));
                return true;
              }else{
            	  if(target.isOnline() == false) {
            		  if(fd.isInDb(target)) {
            			  Tokens.getInstance().getAPI().resetTokens(target);
                          sender.sendMessage(Chatter.color("&6&lThugbucks &8&l" + 
                            Chatter.arrow() + 
                            " &7You have reset &e" + 
                            target.getName() + "'s &7thugbucks!"));
                          return true;
            		  }
            	  }
              }
              this.th.playerOffline(target, args[1]);
            }
            else
            {
            	sender.sendMessage(Chatter.color("&c&m====================================="));
                sender.sendMessage(Chatter.color("&7» &6&lThugbucks&7:"));
                sender.sendMessage(Chatter.color(" "));
                sender.sendMessage(Chatter.color("&7» &e/thugbucks &7(Displays your balance)"));
                sender.sendMessage(Chatter.color("&7» &e/thugbucks shop &7(Opens the shop)"));
                sender.sendMessage(Chatter.color(" "));
                sender.sendMessage(Chatter.color("&c&m====================================="));
            }
          }
          else {
            sender.sendMessage(Chatter.color("&c&lPermission &8&l" + Chatter.arrow() + " &7You don't have permission!"));
          }
        }else if(sender instanceof ConsoleCommandSender)
        {
        	if (args[0].equalsIgnoreCase("reset"))
            {
              Player target = Bukkit.getPlayer(args[1]);
              if (target != null)
              {
                Tokens.getInstance().getAPI().resetTokens(target);
                sender.sendMessage(Chatter.color("&6&lThugbucks &8&l" + 
                  Chatter.arrow() + 
                  " &7You have reset &e" + 
                  target.getName() + "'s &7thugbucks!"));
                return true;
              }
              sender.sendMessage("Incorrect args");
            }            
          }
      
        else if (args.length == 3) {
          if (sender.hasPermission("thugbucks.admin"))
          {
            if (this.th.isInt(args[2]))
            {
              Player target = Bukkit.getPlayer(args[1]);
              int tokens = Integer.valueOf(args[2]).intValue();
              if (args[0].equalsIgnoreCase("give"))
              {
                if (target != null)
                {
                  Tokens.getInstance().getAPI().giveTokens(target, tokens);
                  sender.sendMessage(Chatter.color("&6&lThugbucks &8&l" + Chatter.arrow() + 
                	      " &7You have given &e" + tokens + " &7thugbucks to &e" + 
                	      target.getName() + "&7."));
                	    target.sendMessage(Chatter.color("&6&lThugbucks &8&l" + Chatter.arrow() + 
                	      " &e" + sender.getName() + " &7has given you &e" + tokens + 
                	      " &7thugbucks."));
                }
                else
                {
                  this.th.playerOffline(target, args[1]);
                }
              }
              else if (args[0].equalsIgnoreCase("take"))
              {
                if (target != null)
                {
                  Tokens.getInstance().getAPI().takeTokens(target, tokens);
                  this.th.takeTokensMSG(target, target, tokens);
                }
                else
                {
                  this.th.playerOffline(target, args[1]);
                }
              }
              else if (args[0].equalsIgnoreCase("add")) {
                if (target != null)
                {
                  Tokens.getInstance().getAPI().addTokens(target, tokens);
                  target.sendMessage(
                    Chatter.color("&6&lThugbucks &8&l" + 
                    Chatter.arrow() + 
                    " &e" + 
                    tokens + 
                    " &7have been added to your account."));
                }
                else
                {
                  if(target.isOnline() == false) {
                	  
                  }
                }
              }
            }
            else
            {
              sender.sendMessage(Chatter.color("&c&lError &8&l" + 
                Chatter.arrow() + " &e" + args[2] + 
                " &7isn't a number!"));
            }
          }
          else {
            sender.sendMessage(Chatter.color("&c&lPermission &8&l" + Chatter.arrow() + " &7You don't have permission!"));
          }
        }
      }else if(sender instanceof ConsoleCommandSender) {
    	  Player target = Bukkit.getPlayer(args[1]);
          int tokens = Integer.valueOf(args[2]).intValue();
          if(args[0].equalsIgnoreCase("add")) {
        	  
          }
      }
    }
    else if (args.length == 2)
    {
      if (args[0].equalsIgnoreCase("reset"))
      {
        Player target = Bukkit.getPlayer(args[1]);
        if (target != null) {
          Tokens.getInstance().getAPI().resetTokens(target);
        }
      }
    }
    else if (args.length == 3)
    {
      if (this.th.isInt(args[2]))
      {
        Player target = Bukkit.getPlayer(args[1]);
        int tokens = Integer.valueOf(args[2]).intValue();
        if (args[0].equalsIgnoreCase("give"))
        {
          if (target != null) {
            Tokens.getInstance().getAPI().giveTokens(target, tokens);
          }
        }
        else if (args[0].equalsIgnoreCase("take"))
        {
          if (target != null) {
            Tokens.getInstance().getAPI().takeTokens(target, tokens);
          }
        }
        else if ((args[0].equalsIgnoreCase("add")) && 
          (target != null)) {
          Tokens.getInstance().getAPI().addTokens(target, tokens);
        }
      }
      else
      {
        sender.sendMessage(Chatter.color("&e" + args[2] + 
          " &7isn't a number!"));
      }
    }
    return false;
  }
}
