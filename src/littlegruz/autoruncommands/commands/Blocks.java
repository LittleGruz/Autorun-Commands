package littlegruz.autoruncommands.commands;

import littlegruz.autoruncommands.CommandMain;

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

      return false;
   }

}
