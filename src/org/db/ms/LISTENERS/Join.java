package org.db.ms.LISTENERS;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.db.ms.CORE.FetchData;
import org.db.ms.CORE.SetData;

public class Join
  implements Listener
{
  SetData sd = new SetData();
  FetchData fd = new FetchData();
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e)
  {
    Player p = e.getPlayer();
    if (!this.fd.isInDb(p)) {
      this.sd.giveTokens(p, 0);
    }
  }
}
