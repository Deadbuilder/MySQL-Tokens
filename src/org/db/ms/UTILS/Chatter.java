package org.db.ms.UTILS;

import net.md_5.bungee.api.ChatColor;

public class Chatter
{
  public static String color(String msg)
  {
    return ChatColor.translateAlternateColorCodes('&', msg);
  }
  
  public static String arrow()
  {
    return "»";
  }
}
