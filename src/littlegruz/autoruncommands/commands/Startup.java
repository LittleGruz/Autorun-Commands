package littlegruz.autoruncommands.commands;

import java.util.StringTokenizer;

import littlegruz.autoruncommands.CommandMain;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Startup implements CommandExecutor{
private CommandMain plugin;
   
   public Startup(CommandMain instance){
      plugin = instance;
   }
   
   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
      if(commandLabel.compareToIgnoreCase("addstartupcommand") == 0){
         if(sender.hasPermission("autoruncommands.addstartup")){
            if(args.length != 0){
               String command = args[0];
               
               if(plugin.getCommandMap().get(command) != null){
                  if(plugin.addStartupCommand(sender, command))
                     sender.sendMessage("Startup command created");
               }
               else if(plugin.getCommandMap().get(command + "[op]") != null){
                  if(plugin.addStartupCommand(sender, command))
                     sender.sendMessage("OP startup command created");
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
      else if(commandLabel.compareToIgnoreCase("removestartupcommand") == 0){
         if(sender.hasPermission("autoruncommands.removestartup")){
            if(args.length != 0){
               String command = args[0];
               
               if(plugin.getCommandMap().get(command) != null){
                  plugin.removeStartupCommand(sender, command);
                  sender.sendMessage("Startup command removed");
               }
               else if(plugin.getCommandMap().get(command + "[op]") != null){
                  plugin.removeStartupCommand(sender, command + "[op]");
                  sender.sendMessage("OP startup command removed");
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
      else if(commandLabel.compareToIgnoreCase("displaystartupcommands") == 0){
         if(sender.hasPermission("autoruncommands.displaystartup")){
            if(!plugin.getStartupCommands().isEmpty()){
               sender.sendMessage("The commands that run on start up are:");
               StringTokenizer st = new StringTokenizer(plugin.getStartupCommands(), ":");
               while(st.countTokens() > 0)
                  sender.sendMessage(st.nextToken().replace("[op]", ""));
            }
            else
               sender.sendMessage("You have no commands set to run at start up");
         }
         else
            sender.sendMessage("You don't have sufficient permissions");
      }
      
      return true;
   }

}
