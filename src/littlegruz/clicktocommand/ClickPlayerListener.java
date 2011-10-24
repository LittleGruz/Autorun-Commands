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
      //Executes the assigned command if one is set
      if(event.getRightClicked() instanceof Player){
         Player player = (Player) event.getRightClicked();
         if(plugin.getClickMap().get(event.getPlayer().getName()) != null){
            String command;
            command = plugin.getClickMap().get(event.getPlayer().getName()).replace("potato", player.getName());
            plugin.getServer().dispatchCommand(event.getPlayer(), command);
         }
      }
      /*else{
         String command;
         command = plugin.getClickMap().get(event.getPlayer().getName()).replace("potato", "little_gruz");
         plugin.getServer().dispatchCommand(event.getPlayer(), command);
      }*/
   }
}
