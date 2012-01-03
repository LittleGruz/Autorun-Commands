package littlegruz.autoruncommands.listeners;

import littlegruz.autoruncommands.CommandMain;

import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.server.ServerListener;

public class CommandServerListener extends ServerListener {
   private CommandMain plugin;
   
   public CommandServerListener(CommandMain instance){
      plugin = instance;
   }

   public void onServerListPing(ServerListPingEvent event){
      plugin.getServer().broadcastMessage("Pinged");
   }
   
   public void onMapInitialize(MapInitializeEvent event){
      plugin.getServer().broadcastMessage("Init");
   }
   
   public void onPluginEnable(PluginEnableEvent event){
      plugin.getServer().broadcastMessage("Plugin enable");
   }
}
