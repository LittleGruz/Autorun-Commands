package littlegruz.autoruncommands.listeners;

import littlegruz.autoruncommands.CommandMain;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;

public class CommandBlockListener extends BlockListener {
   private CommandMain plugin;
   
   public CommandBlockListener(CommandMain instance){
      plugin = instance;
   }
   
   //If block was removed, get rid of it from the HashMap
   public void onBlockBreak(BlockBreakEvent event){
      if(plugin.getBlockCommandMap().get(event.getBlock().getLocation()) != null){
         plugin.getBlockCommandMap().remove(event.getBlock().getLocation());
      }
   }
}
