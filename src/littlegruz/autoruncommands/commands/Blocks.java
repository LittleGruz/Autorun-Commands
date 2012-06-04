package littlegruz.autoruncommands.commands;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import littlegruz.autoruncommands.CommandMain;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Blocks implements CommandExecutor{
private CommandMain plugin;
   
   public Blocks(CommandMain instance){
      plugin = instance;
   }
   
   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
      if(commandLabel.compareToIgnoreCase("setcommandblock") == 0){
         if(sender.hasPermission("autoruncommands.setblock")){
            if(args.length != 0){
               if(plugin.getCommandMap().get(args[0]) != null){
                  plugin.setBlockCommand(args[0]);
                  plugin.setPlaceBlock(true);
                  sender.sendMessage("Right click with your fist to apply \'"
                  + plugin.getCommandMap().get(args[0]) + "\'");
               }
               else if(plugin.getCommandMap().get(args[0] + "[op]") != null){
                  plugin.setBlockCommand(args[0] + "[op]");
                  plugin.setPlaceBlock(true);
                  sender.sendMessage("Right click with your fist to apply the OP command \'"
                  + plugin.getCommandMap().get(args[0] + "[op]").replace("[op]", "") + "\'");
               }
               else{
                  sender.sendMessage("No command found with that identifier");
                  sender.sendMessage("Try \'/addacommand <identifier> <command> [args]\' first");
               }
            }
            else
               sender.sendMessage("No autorun command given");
         }
      }
      else if(commandLabel.compareToIgnoreCase("displaycommandblocks") == 0){
         if(sender.hasPermission("autoruncommands.displayblocks")){
            sender.sendMessage("Location (world_name,x,y,z | Identifier");
            Iterator<Map.Entry<Location, String>> it = plugin.getBlockCommandMap().entrySet().iterator();
            while(it.hasNext()){
               Entry<Location, String> mp = it.next();
               sender.sendMessage(mp.getKey().getWorld().getName() + "," + mp.getKey().getBlockX() + "," + mp.getKey().getBlockY() + "," + mp.getKey().getBlockZ() + " | " + mp.getValue());
            }
         }
      }

      return true;
   }

}
