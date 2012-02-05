package littlegruz.autoruncommands.listeners;

import java.util.StringTokenizer;

import littlegruz.autoruncommands.CommandMain;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

public class CommandServerListener implements Listener {
   private CommandMain plugin;
   
   public CommandServerListener(CommandMain instance){
      plugin = instance;
   }
   
   @EventHandler
   public void onPluginEnable(PluginEnableEvent event){
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

      public void run() {
         StringTokenizer st = new StringTokenizer(plugin.getStartupCommands(), ":");
         if(!plugin.isStartupDone()){
            while(st.countTokens() > 0)
               plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), plugin.getCommandMap().get(st.nextToken()));
            plugin.setStartupDone(true);
         }
      }
  }, 20L);
   }
}
