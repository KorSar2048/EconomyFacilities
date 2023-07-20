package server.economy_fac.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import server.economy_fac.Economy_fac;

public class setrec extends ProtoCom {
    public setrec() {
        super("recset");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if(args.length==0){
            sender.sendMessage("Попробуйте добавить аргументы!");
            return;
        }
        int slot = Integer.parseInt(args[0]);
        int rec = Integer.parseInt(args[1]);
        if(slot>3 || slot<0){
            sender.sendMessage(ChatColor.RED+"Неверно указан слот!");
            return;
        }
        if(!Economy_fac.getData().getConfig().contains(sender.getName()+slot)){
            sender.sendMessage(ChatColor.RED+"Этот слот свободен!");
            return;
        }
        Economy_fac.getData().getConfig().set(((Player)sender).getName()+"."+slot+".rec",rec);
        sender.sendMessage("Suc some");
        return;
    }
}
