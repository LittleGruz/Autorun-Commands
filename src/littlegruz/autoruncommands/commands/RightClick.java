package littlegruz.autoruncommands.commands;

import littlegruz.autoruncommands.CommandMain;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RightClick implements CommandExecutor{
private CommandMain plugin;
   
   public RightClick(CommandMain instance){
      plugin = instance;
   }
   
   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
      if(commandLabel.compareToIgnoreCase("setclickcommand") == 0){
         if(sender.hasPermission("autoruncommands.setclick")){
            if(args.length != 0){
               String command = args[0];
               String associate;
               
               if(args.length == 2)
                  associate = args[1];
               else
                  associate = sender.getName();
               
               if(plugin.getCommandMap().get(command) != null){
                  if(plugin.getPlayerClickMap().get(associate) != null){
                     plugin.getPlayerClickMap().remove(associate);
                  }
                  plugin.getPlayerClickMap().put(associate, command);
                  sender.sendMessage("Command association successful");
               }
               else if(plugin.getCommandMap().get(command + "[op]") != null){
                  if(plugin.getPlayerClickMap().get(associate) != null){
                     plugin.getPlayerClickMap().remove(associate);
                  }
                  plugin.getPlayerClickMap().put(associate, command + "[op]");
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
      else if(commandLabel.compareToIgnoreCase("removeclickcommand") == 0){
         if(sender.hasPermission("autoruncommands.removeclick")){
            String associate;
            
            if(args.length == 1)
               associate = args[0];
            else
               associate = sender.getName();
            
            if(plugin.getPlayerClickMap().get(associate) != null){
               plugin.getPlayerClickMap().remove(associate);
               sender.sendMessage("Command removed");
            }
            else
               sender.sendMessage(associate + " has no associated command");
         }
         else
            sender.sendMessage("You don't have sufficient permissions");
      }
      else if(commandLabel.compareToIgnoreCase("displayclickcommand") == 0){
         if(sender.hasPermission("autoruncommands.displayclick")){
            if(plugin.getPlayerClickMap().get("GLOBAL") != null)
               sender.sendMessage("Your command in use is: /" + plugin.getCommandMap().get(plugin.getPlayerClickMap().get("GLOBAL")));
            else if(plugin.getPlayerClickMap().get(sender.getName()) != null)
               sender.sendMessage("Your command in use is: /" + plugin.getCommandMap().get(plugin.getPlayerClickMap().get(sender.getName())));
            else
               sender.sendMessage("You have no associated command");
         }
         else
            sender.sendMessage("You don't have sufficient permissions");
      }
      return false;
   }

}
