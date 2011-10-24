package littlegruz.clicktocommand;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ClickMain extends JavaPlugin{
   Logger log = Logger.getLogger("This is MINECRAFT!");
   private final ClickPlayerListener playerListener = new ClickPlayerListener(this);
   private HashMap<String, String> clickMap;
   private File clickFile;

   public void onDisable(){
      try{
         BufferedWriter bw = new BufferedWriter(new FileWriter(clickFile));
         Iterator<Map.Entry<String, String>> it = clickMap.entrySet().iterator();
         
         //Save the players and corresponding commands
         bw.write("<Player> <Command>\n");
         while(it.hasNext()){
            Entry<String, String> mp = it.next();
            bw.write(mp.getKey() + " " + mp.getValue() + "\n");
         }
         bw.close();
      }catch(IOException e){
         log.info("Error saving Click To Command file");
      }
      log.info("Click To Command is melting! MELTING!");
   }

   public void onEnable(){
      //Create the directory and files if needed
      new File(getDataFolder().toString()).mkdir();
      clickFile = new File(getDataFolder().toString() + "/commands.txt");
      
      //Load the file data
      clickMap = new HashMap<String, String>();
      try{
         BufferedReader br = new BufferedReader(new FileReader(clickFile));
         StringTokenizer st;
         String input;
         String name;
         String args;
         while((input = br.readLine()) != null){
            if(input.compareToIgnoreCase("<Player> <Command>") == 0){
               continue;
            }
            st = new StringTokenizer(input, " ");
            name = st.nextToken();
            args = st.nextToken();
            while(st.hasMoreTokens()){
               args += " " + st.nextToken();
            }
            clickMap.put(name, args);
         }
         
      }catch(FileNotFoundException e){
         log.info("No original Click To Command file, creating new one.");
      }catch(IOException e){
         log.info("Error reading Click To Command file");
      }catch(Exception e){
         log.info("Incorrectly formatted Click To Command file");
      }
      
      //Set up the listeners
      PluginManager pm = this.getServer().getPluginManager();
      pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, playerListener, Event.Priority.Normal, this);
      
      log.info("Click To Command v0.1 is enabled");
   }
   
   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
      if(commandLabel.compareToIgnoreCase("setclickcommand") == 0){
         if(args.length >= 1){
            String command;
            command = args[0];
            for(int i = 1; i < args.length; i++){
               command += " " + args[i];
            }
            if(clickMap.get(sender.getName()) != null){
               clickMap.remove(sender.getName());
            }
            clickMap.put(sender.getName(), command);
            sender.sendMessage("Command association successful");
         }
         else
            sender.sendMessage("Not enough arguments");
      }
      else if(commandLabel.compareToIgnoreCase("removeclickcommand") == 0){
         if(clickMap.get(sender.getName()) != null){
            clickMap.remove(sender.getName());
            sender.sendMessage("Command removed");
         }
         else
            sender.sendMessage("You have no associated command");
      }
      else if(commandLabel.compareToIgnoreCase("displayclickcommand") == 0){
         if(clickMap.get(sender.getName()) != null)
            sender.sendMessage("You command in use is: /" + clickMap.get(sender.getName()));
         else
            sender.sendMessage("You have no associated command");
      }
      
      return true;
   }

   public HashMap<String, String> getClickMap(){
      return clickMap;
   }

}
