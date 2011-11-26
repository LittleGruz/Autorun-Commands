package littlegruz.autoruncommands.listeners;

import littlegruz.autoruncommands.CommandMain;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

public class CommandPlayerListener extends PlayerListener{
   private CommandMain plugin;
   
   public CommandPlayerListener(CommandMain instance){
      plugin = instance;
   }
   
   public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
      //Executes the assigned command if one is set or a global command is set
      if(event.getRightClicked() instanceof Player){
         Player player = (Player) event.getRightClicked();
         String command;
         if(plugin.getPlayerClickMap().get("GLOBAL") != null){
            command = plugin.getCommandMap().get(plugin.getPlayerClickMap().get("GLOBAL")).replace("potato", player.getName());
            plugin.getServer().dispatchCommand(event.getPlayer(), command);
         }
         else if(plugin.getPlayerClickMap().get(event.getPlayer().getName()) != null){
            command = plugin.getCommandMap().get(plugin.getPlayerClickMap().get(event.getPlayer().getName())).replace("potato", player.getName());
            plugin.getServer().dispatchCommand(event.getPlayer(), command);
         }
      }
      /*This code is for quick testing
      else{
         String command;
         if(plugin.getPlayerClickMap().get("GLOBAL") != null){
            command = plugin.getCommandMap().get(plugin.getPlayerClickMap().get("GLOBAL")).replace("potato", event.getRightClicked().toString());
            plugin.getServer().dispatchCommand(event.getPlayer(), command);
         }
         else if(plugin.getPlayerClickMap().get(event.getPlayer().getName()) != null){
            command = plugin.getCommandMap().get(plugin.getPlayerClickMap().get(event.getPlayer().getName())).replace("potato", "Little_Gruz");
            plugin.getServer().dispatchCommand(event.getPlayer(), command);
         }
      }*/
   }
   
   public void onPlayerInteract(PlayerInteractEvent event){
      //Check if the block should have a command associated
      if(plugin.isPlaceBlock()){
         //If it is air, then a fist was used when right clicking
         if(event.getMaterial().compareTo(Material.AIR) == 0
                 && event.getAction().compareTo(Action.RIGHT_CLICK_BLOCK) == 0){
            event.getPlayer().sendMessage("Command set to block");
            plugin.getBlockCommandMap().put(event.getClickedBlock().getLocation(), plugin.getBlockCommand());
            plugin.setPlaceBlock(false);
         }
      }
   }
   
   public void onPlayerMove(PlayerMoveEvent event){
      Location loc;
      loc = event.getPlayer().getLocation().getBlock().getLocation();
      loc.setY(loc.getY() - 1);
      
      //If the player is above a command block then execute the command
      //TODO: For this, need to create some way of saving where everyone is
      if(plugin.getBlockCommandMap().get(loc) != null
            && !loc.equals(plugin.getLastBlock())
            && event.getPlayer().getName().compareToIgnoreCase(plugin.getLastPlayer()) != 0){
         String command;
         command = plugin.getBlockCommandMap().get(loc);
         plugin.getServer().dispatchCommand(event.getPlayer(), plugin.getCommandMap().get(command).replace("potato", event.getPlayer().getName()));
         plugin.setLastBlock(loc.clone());
      }
   }
}
