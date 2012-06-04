package littlegruz.autoruncommands.listeners;

import littlegruz.autoruncommands.CommandMain;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class CommandBlockListener implements Listener {
   private CommandMain plugin;
   
   public CommandBlockListener(CommandMain instance){
      plugin = instance;
   }
   
   //If block was removed, get rid of it from the HashMap
   @EventHandler
   public void onBlockBreak(BlockBreakEvent event){
      if(plugin.getBlockCommandMap().get(event.getBlock().getLocation()) != null){
         // Only remove a command block if the player is op
         if(event.getPlayer().hasPermission("autoruncommands.removeblock"))
            plugin.getBlockCommandMap().remove(event.getBlock().getLocation());
         else{
            event.getPlayer().sendMessage("You don't have permission to remove this block");
            event.setCancelled(true);
         }
      }
   }
}
