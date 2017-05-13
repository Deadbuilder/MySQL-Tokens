package org.db.ms.INTERFACE;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.db.ms.CORE.FetchData;
import org.db.ms.CORE.Tokens;
import org.db.ms.HANDLERS.TokensHandler;
import org.db.ms.UTILS.Language;

public class InterfaceManager
implements Listener
{
private static InterfaceManager instance;
private Plugin plugin;
private ArrayList<Player> viewing = new ArrayList<Player>();
FetchData fd = new FetchData();
TokensHandler th = new TokensHandler();

public InterfaceManager(Plugin plugin)
{
  instance = this;
  this.plugin = plugin;
}

public static InterfaceManager get()
{
  return instance;
}

@EventHandler
public void onClose(InventoryCloseEvent event)
{
  Player player = (Player)event.getPlayer();
  if (this.viewing.contains(player)) {
    this.viewing.remove(player);
  }
}

@EventHandler
public void onClickCheck(InventoryClickEvent event)
{
  Player player = (Player)event.getWhoClicked();
  
  
}

@EventHandler
public void onClick(InventoryClickEvent event)
{
  Player player = (Player)event.getWhoClicked();
  if (this.viewing.contains(player))
  {
    event.setCancelled(true);
    int i = event.getSlot() + 1;
    if ((this.plugin.getConfig().isSet(Integer.toString(i) + ".buyable")) && 
      (!this.plugin.getConfig().getBoolean(Integer.toString(i) + ".buyable"))) {
      return;
    }
    if (this.plugin.getConfig().isSet(Integer.toString(i) + ".cost"))
    {
      if (player.hasPermission("thugbucks.purchased." + Integer.toString(i)))
      {
        Language.ALREADY_PURCHASED.sendTo(player);
        return;
      }
      int cost = this.plugin.getConfig().getInt(Integer.toString(i) + ".cost");
      if ((Tokens.getInstance().getAPI()).hasEnough(player, cost))
      {
        Tokens.getInstance().getAPI().takeTokens(player, cost);
        
        for (String command : this.plugin.getConfig().getStringList(Integer.toString(i) + ".commands"))
        {
          command = command.replace("%player%", player.getName());
          Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
        String message = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString(Integer.toString(i) + ".message"));
        player.sendMessage(message);
        if ((this.plugin.getConfig().isSet(Integer.toString(i) + ".onetime")) && 
          (this.plugin.getConfig().getBoolean(Integer.toString(i) + ".onetime"))) {
          Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " add" + " thugbucks.purchased." + Integer.toString(i));
        }
      }
      else
      {
        Language.INSUFFICIENT_THUGBUCKS.sendTo(player, new String[] { "%tb%", Integer.toString(cost) });
      }
    }
  }
}

public void display(Player player)
{
  String title = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("gui-title"));
  Inventory gui = Bukkit.createInventory(player, 45, title);
  for (int i = 0; i <= gui.getSize(); i++) {
    if (this.plugin.getConfig().isSet(Integer.toString(i) + ".cost"))
    {
      ItemStack item = new ItemStack(Material.valueOf(this.plugin.getConfig().getString(Integer.toString(i) + ".type").toUpperCase()));
      if (this.plugin.getConfig().isSet(Integer.toString(i) + ".durability")) {
        item.setDurability((short)this.plugin.getConfig().getInt(Integer.toString(i) + ".durability"));
      }
      ItemMeta meta = item.getItemMeta();
      meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString(Integer.toString(i) + ".name")));
      if (this.plugin.getConfig().isSet(Integer.toString(i) + ".lore"))
      {
        ArrayList<String> lore = new ArrayList();
        for (String line : this.plugin.getConfig().getStringList(Integer.toString(i) + ".lore")) {
          lore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        meta.setLore(lore);
      }
      item.setItemMeta(meta);
      gui.setItem(i - 1, item);
    }
  }
  this.viewing.add(player);
  player.openInventory(gui);
}
}
