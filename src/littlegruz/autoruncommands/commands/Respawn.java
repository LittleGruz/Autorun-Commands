package littlegruz.autoruncommands.commands;

import littlegruz.autoruncommands.CommandMain;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Respawn implements CommandExecutor{
private CommandMain plugin;
   
   public Respawn(CommandMain instance){
      plugin = instance;
   }
   
   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
      if(commandLabel.compareToIgnoreCase("setrespawncommand") == 0){
         if(sender.hasPermission("autoruncommands.setrespawn")){
            if(args.length != 0){
               String command = args[0];
               String associate;
               
               if(args.length == 2){
                  if(plugin.getServer().getPlayer(args[1]) == null){
                     sender.sendMessage("No player found with that name");
                     return true;
                  }
                  associate = plugin.getServer().getPlayer(args[1]).getName();
               }
               else
                  associate = sender.getName();
               
               if(plugin.getCommandMap().get(command) != null){
                  if(plugin.getPlayerRespawnMap().get(associate) != null){
                     plugin.getPlayerRespawnMap().remove(associate);
                  }
                  plugin.getPlayerRespawnMap().put(associate, command);
                  sender.sendMessage("Command association successful");
               }
               else if(plugin.getCommandMap().get(command + "[op]") != null){
                  if(plugin.getPlayerRespawnMap().get(associate) != null){
                     plugin.getPlayerRespawnMap().remove(associate);
                  }
                  plugin.getPlayerRespawnMap().put(associate, command + "[op]");
                  sender.sendMessage("OP command association successful");
               }
               else{
                  sender.sendMessage("No command found with that identifier");
                  sender.sendMessage("Try \'/addacommand <identifier> <command> [args]\' first");
               }
            }
            else
               sender.sendMessage("Not enough arguments");
         }
         else
            sender.sendMessage("You don't have sufficient permissions");
      }
      else if(commandLabel.compareToIgnoreCase("removerespawncommand") == 0){
         if(sender.hasPermission("autoruncommands.removerespawn")){
            String associate;
            
            if(args.length == 1)
               associate = args[0];
            else
               associate = sender.getName();
            
            if(plugin.getPlayerRespawnMap().get(associate) != null){
               plugin.getPlayerRespawnMap().remove(associate);
               sender.sendMessage("Respawn command removed");
            }
            else
               sender.sendMessage(associate + " has no associated respawn command");
         }
         else
            sender.sendMessage("You don't have sufficient permissions");
      }
      else if(commandLabel.compareToIgnoreCase("displayrespawncommand") == 0){
         if(sender.hasPermission("autoruncommands.displayrespawn")){
            if(plugin.getPlayerRespawnMap().get("GLOBAL") != null)
               sender.sendMessage("Your respawn command in use is: /" + plugin.getCommandMap().get(plugin.getPlayerRespawnMap().get("GLOBAL")));
            else if(plugin.getPlayerRespawnMap().get(sender.getName()) != null)
               sender.sendMessage("Your respawn command in use is: /" + plugin.getCommandMap().get(plugin.getPlayerRespawnMap().get(sender.getName())));
            else
               sender.sendMessage("You have no associated respawn command");
         }
         else
            sender.sendMessage("You don't have sufficient permissions");
      }
      return true;
   }

}
