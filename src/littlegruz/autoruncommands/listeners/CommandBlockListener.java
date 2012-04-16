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
         plugin.getBlockCommandMap().remove(event.getBlock().getLocation());
      }
   }
}
