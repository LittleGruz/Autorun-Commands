package littlegruz.autoruncommands.listeners;

import littlegruz.autoruncommands.CommandMain;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class CommandEntityListener implements Listener {
   private CommandMain plugin;
   
   public CommandEntityListener(CommandMain instance){
      plugin = instance;
   }
   
   @EventHandler
   public void onEntityDeath(EntityDeathEvent event){
      if(event.getEntity() instanceof Player){
         Player playa =  (Player)event.getEntity();
         String command;
         if(plugin.getPlayerDeathMap().get("GLOBAL") != null){
            command = plugin.getCommandMap().get(plugin.getPlayerDeathMap().get("GLOBAL")).replace("potato", playa.getName());
            plugin.getServer().dispatchCommand(playa, command);
         }
         else if(plugin.getPlayerDeathMap().get(playa.getName()) != null){
            command = plugin.getCommandMap().get(plugin.getPlayerDeathMap().get(playa.getName())).replace("potato", playa.getName());
            if(command.contains("[op]"))
               plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
            else
               plugin.getServer().dispatchCommand(playa, command);
         }
      }
   }
}
