package littlegruz.autoruncommands.listeners;

import java.util.StringTokenizer;

import littlegruz.autoruncommands.CommandMain;

import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

public class CommandServerListener extends ServerListener {
   private CommandMain plugin;
   
   public CommandServerListener(CommandMain instance){
      plugin = instance;
   }
   
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
