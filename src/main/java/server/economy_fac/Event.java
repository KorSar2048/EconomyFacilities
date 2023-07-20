package server.economy_fac;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.Set;

public class Event implements Listener {
    @EventHandler
    public void placeblock(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        World world = block.getWorld();
        int x= block.getX();
        int y= block.getY();
        int z= block.getZ();
        Set<String> names=Economy_fac.getData().getConfig().getKeys(false);
        if(block.getType().equals(Material.CHEST)){
        for (String nik : names) {
            for(int i=1;i<4;i++) {
                if((Economy_fac.getData().getConfig().getInt(nik+"."+i+".x")==x)&&(Economy_fac.getData().getConfig().getInt(nik+"."+i+".y")==y)&&(Economy_fac.getData().getConfig().getInt(nik+"."+i+".z")==z)){
                    Economy_fac.getData().getConfig().set(nik+"."+i+"."+"x","null");
                    Economy_fac.getData().getConfig().set(nik+"."+i+"."+"y","null");
                    Economy_fac.getData().getConfig().set(nik+"."+i+"."+"z","null");
                    Economy_fac.getData().getConfig().set(nik+"."+i+"."+"type","null");
                    Economy_fac.getData().getConfig().set(nik+"."+i+"."+"rec","null");
                    event.setDropItems(false);
                }
            }
        }
        }
    }
    }
