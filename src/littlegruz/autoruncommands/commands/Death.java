package littlegruz.autoruncommands.commands;

import littlegruz.autoruncommands.CommandMain;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Death implements CommandExecutor{
private CommandMain plugin;
   
   public Death(CommandMain instance){
      plugin = instance;
   }
   
   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
      if(commandLabel.compareToIgnoreCase("setdeathcommand") == 0){
         if(sender.hasPermission("autoruncommands.setdeath")){
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
                  if(plugin.getPlayerDeathMap().get(associate) != null){
                     plugin.getPlayerDeathMap().remove(associate);
                  }
                  plugin.getPlayerDeathMap().put(associate, command);
                  sender.sendMessage("Command association successful");
               }
               else if(plugin.getPlayerDeathMap().get(command + "[op]") != null){
                  if(plugin.getPlayerDeathMap().get(associate) != null){
                     plugin.getPlayerDeathMap().remove(associate);
                  }
                  plugin.getPlayerDeathMap().put(associate, command + "[op]");
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
      else if(commandLabel.compareToIgnoreCase("removedeathcommand") == 0){
         if(sender.hasPermission("autoruncommands.removedeath")){
            String associate;
            
            if(args.length == 1)
               associate = args[0];
            else
               associate = sender.getName();
            
            if(plugin.getPlayerDeathMap().get(associate) != null){
               plugin.getPlayerDeathMap().remove(associate);
               sender.sendMessage("Command removed");
            }
            else
               sender.sendMessage(associate + " has no associated death command");
         }
         else
            sender.sendMessage("You don't have sufficient permissions");
      }
      else if(commandLabel.compareToIgnoreCase("displaydeathcommand") == 0){
         if(sender.hasPermission("autoruncommands.displaydeath")){
            if(plugin.getPlayerDeathMap().get("GLOBAL") != null)
               sender.sendMessage("Your death command in use is: /" + plugin.getCommandMap().get(plugin.getPlayerDeathMap().get("GLOBAL")));
            else if(plugin.getPlayerDeathMap().get(sender.getName()) != null)
               sender.sendMessage("Your death command in use is: /" + plugin.getCommandMap().get(plugin.getPlayerDeathMap().get(sender.getName())));
            else
               sender.sendMessage("You have no associated death command");
         }
         else
            sender.sendMessage("You don't have sufficient permissions");
      } 
         
      return false;
   }

}
