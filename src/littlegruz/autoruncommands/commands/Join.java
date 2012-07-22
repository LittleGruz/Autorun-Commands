package littlegruz.autoruncommands.commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
      if(commandLabel.compareToIgnoreCase("addjoincommand") == 0){
         if(sender.hasPermission("autoruncommands.setjoin"))
            addJoinCommand(sender, args, "normal", plugin.getPlayerJoinMap());
         else
            sender.sendMessage("You don't have sufficient permissions");
      }
      else if(commandLabel.compareToIgnoreCase("addfirstjoincommand") == 0){
         if(sender.hasPermission("autoruncommands.setjoin"))
            addJoinCommand(sender, args, "first", plugin.getPlayerJoinMap());
         else
            sender.sendMessage("You don't have sufficient permissions");
      }
      else if(commandLabel.compareToIgnoreCase("removejoincommand") == 0){
         if(sender.hasPermission("autoruncommands.removejoin")){
            if(args.length == 1){
               if(plugin.getPlayerJoinMap().remove(args[0]) != null)
                  sender.sendMessage("Player join command removed");
               else
                  sender.sendMessage("No join commands with that identifier found");
            }
            else
               sender.sendMessage("Wrong number of arguments");
         }
         else
            sender.sendMessage("You don't have sufficient permissions");
      }
      else if(commandLabel.compareToIgnoreCase("displayjoincommands") == 0){
         if(sender.hasPermission("autoruncommands.displayjoin")){
            sender.sendMessage("Identifier | Join type");
            Iterator<Map.Entry<String, String>> it = plugin.getPlayerJoinMap().entrySet().iterator();
            while(it.hasNext()){
               Entry<String, String> mp = it.next();
               sender.sendMessage(mp.getKey() + " | " + mp.getValue());
            }
         }
         else
            sender.sendMessage("You don't have sufficient permissions");
      }
      else if(commandLabel.compareToIgnoreCase("setjoincommanddelay") == 0){
         if(sender.hasPermission("autoruncommands.addjoin")){
            if(args.length == 1){
               try{
               plugin.setJoinDelay(Integer.parseInt(args[0]));
               plugin.getConfig().set("join_delay", Integer.parseInt(args[0]));
               
               if(Integer.parseInt(args[0]) == 0)
                  sender.sendMessage("Join command delay disabled");
               else
                  sender.sendMessage("Join command delay set to " + args[0] + " seconds");
               }catch(NumberFormatException e){
                  sender.sendMessage("Letters are not numbers moron...");
               }
               
               plugin.saveConfig();
            }
            else
               return false;
         }
         else
            sender.sendMessage("You don't have sufficient permissions");
      }
      return true;
   }
   
   private void addJoinCommand(CommandSender sender, String[] args, String type, HashMap<String, String> map){
      if(args.length == 1){
         
         if(plugin.getCommandMap().get(args[0]) != null){
            map.put(args[0], type);
            sender.sendMessage("Player join command set");
         }
         else if(plugin.getCommandMap().get(args[0] + "[op]") != null){
            map.put(args[0] + "[op]", type);
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

}
