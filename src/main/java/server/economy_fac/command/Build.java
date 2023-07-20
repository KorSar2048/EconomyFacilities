package server.economy_fac.command;

/*import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.ItemHologramLine;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;*/
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import server.economy_fac.Economy_fac;

import java.util.ArrayList;
import java.util.List;

public class Build extends ProtoCom {
    public Build() {
        super("build");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        /*if (!sender.hasPermission("ecofac.build")) {
            sender.sendMessage(ChatColor.RED + "У вас нет права на эту команду :(");
            return;
        }*/
        if(args.length<2){
            sender.sendMessage("Попробуйте добавить аргументы!");
            return;
        }
        String name = args[1];
        String slot = args[0];
        if(!Economy_fac.getInstance().getConfig().contains("builds."+name)){
            sender.sendMessage("Такой постройки нет :(");
            return;
        }
        if(!slot.equalsIgnoreCase("1") && !slot.equalsIgnoreCase("2") && !slot.equalsIgnoreCase("3")){
            sender.sendMessage("Неверно указанный слот!");
            return;
        }

        if(Economy_fac.getData().getConfig().contains(((Player)sender).getName()+"."+slot)&& !Economy_fac.getData().getConfig().getString(((Player)sender).getName()+"."+slot+".type").equalsIgnoreCase("null")){
            sender.sendMessage("Этот слот уже занят. Он будет перезаписан.");
        }
        int r= Economy_fac.getInstance().getConfig().getInt("builds."+name+".radius");
        int x = ((Player)sender).getLocation().getBlockX();
        int y = ((Player)sender).getLocation().getBlockY();
        int z = ((Player)sender).getLocation().getBlockZ();
        if(!Economy_fac.near(((Player)sender).getLocation(),r)){
            sender.sendMessage(ChatColor.RED+"Ваша постройка слишком близко к другой!");
            return;
        }
        List<String> mat = new ArrayList<>();
        List<Integer> am = new ArrayList<>();
        mat=Economy_fac.getInstance().getConfig().getStringList("builds."+name+".materials");
        am=Economy_fac.getInstance().getConfig().getIntegerList("builds."+name+".count");
        String material;
        int amount;
        int n=Economy_fac.getInstance().getConfig().getInt("builds."+name+".len");
        int c;
        boolean flag = true;
        for(int i=0;i<n;i++){
            material=mat.get(i);
            amount=am.get(i);
            c=Economy_fac.count(r,x,y,z,Material.getMaterial(material.toUpperCase()),((Player) sender).getWorld());
            if(c<amount) {
                sender.sendMessage(ChatColor.RED+"Не хватает " + (amount - c) + " " + material);
                flag = false;
            }
        }
        if(flag){
            ((Player)sender).getLocation().getBlock().setType(Material.CHEST);
            sender.sendMessage(ChatColor.GREEN+"Постройка успешно создана!");
            Location location = new Location(((Player) sender).getWorld(), x,y+1,z);
            /*HolographicDisplaysAPI api = HolographicDisplaysAPI.get(Economy_fac.getInstance());
            Hologram hologram = api.createHologram(location);
            TextHologramLine text = hologram.getLines().appendText(Economy_fac.getInstance().getConfig().getString("builds."+name+".holot"));

            ItemHologramLine item = hologram.getLines().appendItem(new ItemStack(Material.getMaterial(Economy_fac.getInstance().getConfig().getString("builds."+name+".holoi").toUpperCase())));
             */
            Economy_fac.getData().getConfig().set(((Player)sender).getName()+"."+slot+"."+"type",name);
            Economy_fac.getData().getConfig().set(((Player)sender).getName()+"."+slot+"."+"x",x);
            Economy_fac.getData().getConfig().set(((Player)sender).getName()+"."+slot+"."+"y",y);
            Economy_fac.getData().getConfig().set(((Player)sender).getName()+"."+slot+"."+"z",z);
            Economy_fac.getData().getConfig().set(((Player)sender).getName()+"."+slot+"."+"rec",0);
            //Economy_fac.getData().getConfig().set(((Player)sender).getName()+"."+slot+"."+"holo",hologram);
            Economy_fac.getData().save();
        }
        return;
    }
}