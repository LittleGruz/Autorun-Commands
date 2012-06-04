package littlegruz.autoruncommands.commands;

import littlegruz.autoruncommands.CommandMain;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Join implements CommandExecutor{
private CommandMain plugin;
   
   public Join(CommandMain instance){
      plugin = instance;
   }
   
   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
      if(commandLabel.compareToIgnoreCase("setjoincommand") == 0){
         if(sender.hasPermission("autoruncommands.setjoin")){
            if(args.length == 1){
               String command = args[0];
               
               if(plugin.getCommandMap().get(command) != null){
                  plugin.setPlayerJoinCommand(command);
                  sender.sendMessage("Player join command set");
               }
               else if(plugin.getCommandMap().get(command + "[op]") != null){
                  plugin.setPlayerJoinCommand(command + "[op]");
                  sender.sendMessage("OP player join command set");
               }
               else{
                  sender.sendMessage("No command found with that identifier");
                  sender.sendMessage("Try \'/addacommand <identifier> <command> [args]\' first");
               }
            }
            else
               sender.sendMessage("Wrong number of arguments");
         }
         else
            sender.sendMessage("You don't have sufficient permissions");
      }
      else if(commandLabel.compareToIgnoreCase("removejoincommand") == 0){
         if(sender.hasPermission("autoruncommands.removejoin")){
            plugin.setPlayerJoinCommand("chuckTesta");
            sender.sendMessage("Player join command cleared");
         }
         else
            sender.sendMessage("You don't have sufficient permissions");
      }
      else if(commandLabel.compareToIgnoreCase("displayjoincommand") == 0){
         if(sender.hasPermission("autoruncommands.displayjoin")){
            if(plugin.getPlayerJoinCommand().compareTo("chuckTesta") != 0)
               sender.sendMessage("The player join command is: /" + plugin.getCommandMap().get(plugin.getPlayerJoinCommand()));
            else
               sender.sendMessage("No player join command is set");
         }
         else
            sender.sendMessage("You don't have sufficient permissions");
      }
      return true;
   }

}
