package littlegruz.autoruncommands.commands;

import littlegruz.autoruncommands.CommandMain;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Config implements CommandExecutor{
   private CommandMain plugin;
   
   public Config(CommandMain instance){
      plugin = instance;
   }
   
   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
      if(sender.hasPermission("autoruncommands.config")){
         if(commandLabel.compareToIgnoreCase("reloadautorunconfig") == 0){
            plugin.reloadConfig();
            
            if(plugin.getConfig().isBoolean("first_join"))
               plugin.setFirstJoin(plugin.getConfig().getBoolean("first_join"));
            if(plugin.getConfig().isInt("join_delay"))
               plugin.setJoinDelay(plugin.getConfig().getInt("join_delay"));
            
            sender.sendMessage("Config reloaded");
         }
         else if(commandLabel.compareToIgnoreCase("togglefirstjoincommands") == 0){
            if(plugin.isFirstJoin()){
               plugin.setFirstJoin(false);
               plugin.getConfig().set("first_join", false);
               sender.sendMessage("First join commands disabled");
            }
            else{
               plugin.setFirstJoin(true);
               plugin.getConfig().set("first_join", true);
               sender.sendMessage("First join commands enabled");
            }
            
            plugin.saveConfig();
         }
         else
            return false;
      }
      return true;
   }
}
