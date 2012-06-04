package littlegruz.autoruncommands.listeners;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import littlegruz.autoruncommands.CommandMain;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

public class CommandServerListener implements Listener {
   private CommandMain plugin;
   
   public CommandServerListener(CommandMain instance){
      plugin = instance;
   }
   
   @EventHandler
   public void onPluginEnable(PluginEnableEvent event){
      // Run the startup tasks one second after everything has loaded
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

         public void run() {
            StringTokenizer st = new StringTokenizer(plugin.getStartupCommands(), ":");
            if(!plugin.isStartupDone()){
               while(st.countTokens() > 0)
                  plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), plugin.getCommandMap().get(st.nextToken()));
               plugin.setStartupDone(true);
            }
         }
     }, 20L);
      
      /* Start the repeating tasks one and a half seconds after everything has
       * loaded.
       * I heard you like scheduled tasks, so I put a scheduled task in your
       * scheduled task */
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

         public void run() {
            int id, interval;
            Iterator<Map.Entry<String, Integer>> it = plugin.getRepeatMap().entrySet().iterator();
            
            // Attach the remainder times onto the tasks that need to repeat
            try{
               BufferedReader br = new BufferedReader(new FileReader(plugin.getRemainderFile()));
               StringTokenizer st;
               String input;
               
               while((input = br.readLine()) != null){
                  if(input.compareToIgnoreCase("<Command> <Remainder>") == 0){
                     continue;
                  }
                  st = new StringTokenizer(input, " ");
                  plugin.getRunningRepeatMap().put(st.nextToken(), st.nextToken());
               }
               
            }catch(FileNotFoundException e){
               plugin.getServer().getLogger().info("No original repeating task remaining file, creating new one.");
            }catch(Exception e){
               plugin.getServer().getLogger().info("Incorrectly formatted repeating task remaining file");
            }
            
            // The running of the tasks
            while(it.hasNext()){
               Entry<String, Integer> mp = it.next();
               final String command = mp.getKey();
               long time = new Date().getTime();
               time /= 1000;
               
               interval = mp.getValue();
               
               if(plugin.getCommandMap().get(command) != null
                     || plugin.getCommandMap().get(command + "[op]") != null){
                  id = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin,  new Runnable() {

                     public void run() {
                        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), plugin.getCommandMap().get(command));
                     }
                  }, Integer.parseInt(plugin.getRunningRepeatMap().get(command)) * 20, interval * 20);
                  
                  plugin.getRunningRepeatMap().put(command, Integer.toString(id) + "|" + Long.toString(time));
               }
            }
         }
     }, 30L);
   }
}
