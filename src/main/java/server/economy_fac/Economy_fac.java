package server.economy_fac;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import server.economy_fac.command.Build;
import server.economy_fac.command.EcoCom;
import server.economy_fac.command.setrec;

import java.util.*;


public final class Economy_fac extends JavaPlugin {
    private static Economy_fac instance;
    private Data data;
    public void onEnable() {
        Random rand = new Random();
        instance = this;
        saveDefaultConfig();
        data = new Data("Data.yml");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, ()->{
            Set<String> names = getData().getConfig().getKeys(false);
            for (String nik : names) {
                for(int i=1;i<4;i++) {
                    if(getData().getConfig().contains(nik+"."+i+"."+"type")){
                        String type = getData().getConfig().getString(nik+"."+i+"."+"type");
                        World world = Bukkit.getWorld("world");
                        int x=getData().getConfig().getInt(nik+"."+i+"."+"x");
                        int y=getData().getConfig().getInt(nik+"."+i+"."+"y");
                        int z=getData().getConfig().getInt(nik+"."+i+"."+"z");
                        int n=getInstance().getConfig().getInt("builds."+type+".len");
                        int rec=getData().getConfig().getInt(nik+"."+i+"."+"rec");
                        int rmod = 0;
                        HashMap<Integer,ItemStack> owerflow;
                        Location location = new Location(world, x, y, z);
                        List<String> mat = new ArrayList<>();
                        List<Integer> am = new ArrayList<>();
                        List<String> ent = new ArrayList<>();
                        List<Integer> nent = new ArrayList<>();
                        List<String> prd = new ArrayList<>();
                        List<Integer> nprd = new ArrayList<>();
                        Chest chest;
                        mat=getInstance().getConfig().getStringList("builds."+type+".materials");
                        am=getInstance().getConfig().getIntegerList("builds."+type+".count");
                        boolean flag = true;
                        for(int j=0;j<n;j++){
                            int c=Economy_fac.count(getInstance().getConfig().getInt("builds."+type+".radius"),x,y,z,Material.getMaterial(mat.get(j).toUpperCase()),world);
                            if(c<am.get(j)) {
                                flag = false;
                            }
                        }
                        if(flag){
                            chest=(Chest)location.getBlock().getState();
                            Inventory inv = chest.getInventory();
                            if(rec!=0) {
                                rmod=getInstance().getConfig().getInt("builds." + type + ".recipes." + rec + ".rmod");
                                ent = getInstance().getConfig().getStringList("builds." + type + ".recipes." + rec + ".ent");
                                prd = getInstance().getConfig().getStringList("builds." + type + ".recipes." + rec + ".prd");
                                nent = getInstance().getConfig().getIntegerList("builds." + type + ".recipes." + rec + ".nent");
                                nprd = getInstance().getConfig().getIntegerList("builds." + type + ".recipes." + rec + ".nprd");
                                flag = true;
                                for(int k=0;k<ent.size();k++) {
                                    if (!inv.containsAtLeast(new ItemStack(Material.getMaterial(ent.get(k).toUpperCase())), nent.get(k))) {
                                        flag = false;
                                    }
                                }
                                if(flag){
                                    for (int k = 0; k < ent.size(); k++) {
                                        inv.removeItem(new ItemStack(Material.getMaterial(ent.get(k).toUpperCase()), nent.get(k)));
                                    }
                                    for (int k = 0; k < prd.size(); k++) {
                                        owerflow=inv.addItem(new ItemStack(Material.getMaterial(prd.get(k).toUpperCase()), nprd.get(k)+rand.nextInt(10)*rmod));
                                        if(!owerflow.isEmpty()){
                                            for(ItemStack it:owerflow.values())
                                            world.dropItem(location,it);
                                        }
                                    }
                                }
                            } else{
                            flag = true;
                            rec=1;
                            while(getInstance().getConfig().contains("builds." + type + ".recipes." + rec)) {
                                flag = true;
                                rmod=getInstance().getConfig().getInt("builds." + type + ".recipes." + rec + ".rmod");
                                ent = getInstance().getConfig().getStringList("builds." + type + ".recipes." + rec + ".ent");
                                prd = getInstance().getConfig().getStringList("builds." + type + ".recipes." + rec + ".prd");
                                nent = getInstance().getConfig().getIntegerList("builds." + type + ".recipes." + rec + ".nent");
                                nprd = getInstance().getConfig().getIntegerList("builds." + type + ".recipes." + rec + ".nprd");
                                for (int k = 0; k < ent.size(); k++) {
                                    if (!inv.containsAtLeast(new ItemStack(Material.getMaterial(ent.get(k).toUpperCase())), nent.get(k))) {
                                        flag = false;
                                    }
                                }
                                if(flag){
                                    rec=0-rec;
                                } else{
                                    rec++;
                                }
                            }
                            if(flag){
                                    for (int k = 0; k < ent.size(); k++) {
                                        inv.removeItem(new ItemStack(Material.getMaterial(ent.get(k).toUpperCase()), nent.get(k)));
                                    }
                                    for (int k = 0; k < prd.size(); k++) {
                                        owerflow=inv.addItem(new ItemStack(Material.getMaterial(prd.get(k).toUpperCase()), nprd.get(k)+rmod*rand.nextInt(10)));
                                        if(!owerflow.isEmpty()){
                                            for(ItemStack it:owerflow.values())
                                                world.dropItem(location,it);
                                        }
                                    }
                                }
                            }
                        } else{
                            location.getBlock().setType(Material.AIR);
                            getData().getConfig().set(nik+"."+i+"."+"x",null);
                            getData().getConfig().set(nik+"."+i+"."+"y",null);
                            getData().getConfig().set(nik+"."+i+"."+"z",null);
                            getData().getConfig().set(nik+"."+i+"."+"type",null);
                            getData().getConfig().set(nik+"."+i+"."+"rec",null);
                        }
                    }
                }
            }
            for (Player p:Bukkit.getOnlinePlayers()){
                p.sendMessage("Сбор налогов!");
            }
        },0,200);
        Bukkit.getPluginManager().registerEvents(new Event(), this);
        new EcoCom();
        new Build();
        new setrec();

    }

    public static Economy_fac getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
    }

    public static int count(int r, int x, int y, int z, Material material, World world) {
        int a = 0;
        for (int i = -r; i <= r; i++) {
            for (int j = -r; j <= r; j++) {
                for (int k = -r; k <= r; k++) {
                    if (Objects.requireNonNull(Bukkit.getWorld(world.getName())).getBlockAt(x + i, y + j, z + k).getType().equals(material)){
                        a = a + 1;
                    }
                }
            }
        }
        return a;
    }

    public static boolean near(Location player, int r){
        Set<String> names = getData().getConfig().getKeys(false);
        int r2;
        if(names.isEmpty()){
            return true;
        }
        for (String nik : names) {
            for(int i=1;i<4;i++) {
                if(getData().getConfig().contains(nik+"."+i+".type") && !getData().getConfig().getString(nik+"."+i+".type").equalsIgnoreCase("null")){
                    Location location = new Location(player.getWorld(), getData().getConfig().getInt(nik + "."+i+".x"),getData().getConfig().getInt(nik + "."+i+".y"), getData().getConfig().getInt(nik + "."+i+".z"));
                    r2=getInstance().getConfig().getInt("builds."+Economy_fac.getData().getConfig().getString(nik+"."+i+"."+"type")+".radius");
                    if(player.distanceSquared(location)<2*(r*r+r2*r2+r*r2)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static Data getData() {
        return instance.data;
    }
}