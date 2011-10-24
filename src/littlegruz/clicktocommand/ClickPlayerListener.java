package littlegruz.clicktocommand;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerListener;

public class ClickPlayerListener extends PlayerListener{
   private ClickMain plugin;
   
   public ClickPlayerListener(ClickMain instance){
      plugin = instance;
   }
   
   public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
      //Executes the assigned command if one is set or a global command is set
      if(event.getRightClicked() instanceof Player){
         Player player = (Player) event.getRightClicked();
         String command;
         if(plugin.getClickMap().get("GLOBAL") != null){
            command = plugin.getClickMap().get("GLOBAL").replace("potato", player.getName());
            plugin.getServer().dispatchCommand(event.getPlayer(), command);
         }
         else if(plugin.getClickMap().get(event.getPlayer().getName()) != null){
            command = plugin.getClickMap().get(event.getPlayer().getName()).replace("potato", player.getName());
            plugin.getServer().dispatchCommand(event.getPlayer(), command);
         }
      }
      /*else{
         String command;
         if(plugin.getClickMap().get("GLOBAL") != null){
            command = plugin.getClickMap().get("GLOBAL").replace("potato", event.getRightClicked().toString());
            plugin.getServer().dispatchCommand(event.getPlayer(), command);
         }
         else if(plugin.getClickMap().get(event.getPlayer().getName()) != null){
            command = plugin.getClickMap().get(event.getPlayer().getName()).replace("potato", "Little_Gruz");
            plugin.getServer().dispatchCommand(event.getPlayer(), command);
         }
      }*/
   }
}
