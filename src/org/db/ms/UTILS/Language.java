package org.db.ms.UTILS;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.db.ms.CORE.Tokens;

public enum Language
{
  
  INSUFFICIENT_THUGBUCKS("insufficient-thugbucks", new String[] { "§cYou do not have %tb% to purcahse that." }),
  ALREADY_PURCHASED("already-purchased", new String[] { "§cYou have already purchased that item." }),;
  
  private final String path;
  private String[] messages;
  
  private Language(String path, String... messages)
  {
    this.path = path;
    setMessages(messages);
  }
  
  public String getPath()
  {
    return this.path;
  }
  
  public String getMessage()
  {
    return this.messages[0];
  }
  
  public String getMessage(String... args)
  {
    return replace(this.messages[0], args);
  }
  
  public String[] getMessages()
  {
    return this.messages;
  }
  
  public String[] getMessages(String... args)
  {
    String[] messages = this.messages;
    for (int i = 0; i < messages.length; i++) {
      messages[i] = replace(messages[i], args);
    }
    return messages;
  }
  
  public String toString()
  {
    return this.messages[0];
  }
  
  public void setMessages(String... messages)
  {
    for (int i = 0; i < messages.length; i++) {
      messages[i] = ChatColor.translateAlternateColorCodes('&', messages[i]);
    }
    this.messages = messages;
  }
  
  public void sendTo(CommandSender reciver)
  {
    if (reciver == null) {
      return;
    }
    String[] arrayOfString;
    int j = (arrayOfString = this.messages).length;
    for (int i = 0; i < j; i++)
    {
      String message = arrayOfString[i];
      
      reciver.sendMessage(message);
    }
  }
  
  public void sendTo(CommandSender reciver, String... args)
  {
    if (reciver == null) {
      return;
    }
    String[] arrayOfString;
    int j = (arrayOfString = this.messages).length;
    for (int i = 0; i < j; i++)
    {
      String message = arrayOfString[i];
      
      reciver.sendMessage(replace(message, args));
    }
  }
  
  public void sendTo(Player target, String... args)
  {
    if (target == null) {
      return;
    }
    String[] arrayOfString;
    int j = (arrayOfString = this.messages).length;
    for (int i = 0; i < j; i++)
    {
      String message = arrayOfString[i];
      
      target.sendMessage(replace(message, args));
    }
  }
  
  public static String replace(String message, String... args)
  {
    for (int i = 0; i + 2 <= args.length; i += 2) {
      message = message.replaceAll("(?i)" + Pattern.quote(args[i]), args[(i + 1)].replace("$", "\\$"));
    }
    return message;
  }
  
  public static boolean loadMessages(Tokens instance)
  {
    YamlConfiguration config = getMessageConfig(instance);
    if (config == null) {
      return false;
    }
    Language[] arrayOfLanguage;
    int j = (arrayOfLanguage = values()).length;
    for (int i = 0; i < j; i++)
    {
      Language message = arrayOfLanguage[i];
      if (config.contains(message.getPath()))
      {
        List<String> configMessage = config.getStringList(message.getPath());
        String[] configMessageArray = new String[configMessage.size()];
        
        message.setMessages((String[])configMessage.toArray(configMessageArray));
      }
    }
    saveMessages(instance);
    
    return true;
  }
  
  public static boolean saveMessages(Tokens instance)
  {
    YamlConfiguration config = getMessageConfig(instance);
    if (config == null) {
      return false;
    }
    Language[] arrayOfLanguage;
    int j = (arrayOfLanguage = values()).length;
    for (int i = 0; i < j; i++)
    {
      Language message = arrayOfLanguage[i];
      
      config.set(message.getPath(), message.getMessages());
    }
    File file = new File(instance.getDataFolder(), "messages.yml");
    try
    {
      config.save(file);
      
      instance.getLogger().log(Level.INFO, "Saved the message config!");
    }
    catch (IOException ex)
    {
      instance.getLogger().log(Level.WARNING, "Could not save the message config!");
      instance.getLogger().log(Level.INFO, "Reason: " + ex.getLocalizedMessage());
      
      return false;
    }
    return true;
  }
  
  private static YamlConfiguration getMessageConfig(Tokens instance)
  {
    File file = new File(instance.getDataFolder(), "messages.yml");
    if (!file.exists()) {
      try
      {
        YamlConfiguration yc = new YamlConfiguration();
        Language[] arrayOfLanguage;
        int j = (arrayOfLanguage = values()).length;
        for (int i = 0; i < j; i++)
        {
          Language message = arrayOfLanguage[i];
          
          String[] messages = message.getMessages();
          for (int i1 = 0; i1 < messages.length; i1++) {
            messages[i1] = messages[i1].replace("§", "&");
          }
          yc.set(message.getPath(), Arrays.asList(messages));
        }
        yc.save(file);
        
        instance.getLogger().log(Level.INFO, "Generated the message config!");
      }
      catch (IOException ex)
      {
        instance.getLogger().log(Level.WARNING, "Could not generate the message config!");
        instance.getLogger().log(Level.INFO, "Reason: " + ex.getLocalizedMessage());
        
        return null;
      }
    }
    return YamlConfiguration.loadConfiguration(file);
  }
}
