package littlegruz.autoruncommands.listeners;

import littlegruz.autoruncommands.CommandMain;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
            if(command.contains("[op]"))
               plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
            else
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
            command = plugin.getCommandMap().get(plugin.getPlayerClickMap().get("Little_Gruz")).replace("potato", "Little_Gruz");
            if(command.contains("[op]"))
               plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
            else
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
      
      if(!loc.equals(plugin.getPlayerPosMap().get(event.getPlayer().getName()))){
         //If the player is above a command block then execute the command
         if(plugin.getBlockCommandMap().get(loc) != null){
            String command;
            command = plugin.getBlockCommandMap().get(loc);
            plugin.getPlayerPosMap().put(event.getPlayer().getName(), loc);
            if(command.contains("[op]"))
               plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), plugin.getCommandMap().get(command).replace("potato", event.getPlayer().getName()));
            else
               plugin.getServer().dispatchCommand(event.getPlayer(), plugin.getCommandMap().get(command).replace("potato", event.getPlayer().getName()));
         }
         else
            plugin.getPlayerPosMap().put(event.getPlayer().getName(), null);
      }
   }
   
   public void onPlayerJoin(PlayerJoinEvent event){
      plugin.getPlayerPosMap().put(event.getPlayer().getName(), null);
   }
   
   public void onPlayerQuit(PlayerQuitEvent event){
      plugin.getPlayerPosMap().remove(event.getPlayer().getName());
   }
}
