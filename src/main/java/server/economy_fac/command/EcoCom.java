package server.economy_fac.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import server.economy_fac.Economy_fac;
import java.util.Set;

public class EcoCom extends ProtoCom {
    public EcoCom() {
        super("eco");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if(args.length==0){
            sender.sendMessage("Попробуйте добавить аргументы!");
        }
        if (args[0].equalsIgnoreCase("reload")) {
            /*if (!sender.hasPermission("ecofac.reload")) {
                sender.sendMessage(ChatColor.RED + "У вас нет права на эту команду :(");
                return;
            }*/
            Economy_fac.getInstance().reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Плагин был перезагружен!");
            return;
        }
        if (args[0].equalsIgnoreCase("slots")){
            String nik = ((Player)sender).getName();
            sender.sendMessage(ChatColor.AQUA+"Игрок:  "+ChatColor.WHITE+nik);
            for(int i=1;i<4;i++) {
                if(Economy_fac.getData().getConfig().contains(nik+"."+i+"."+"type") && !Economy_fac.getData().getConfig().getString(nik+"."+i+"."+"type").equalsIgnoreCase(null)) {
                    sender.sendMessage(ChatColor.GOLD + "Слот:  " + ChatColor.WHITE + i);
                    sender.sendMessage(ChatColor.AQUA + "Тип:  " + ChatColor.WHITE + Economy_fac.getData().getConfig().getString(nik + "." + i + "." + "type"));
                    sender.sendMessage(ChatColor.AQUA + "X:  " + ChatColor.WHITE + Economy_fac.getData().getConfig().getString(nik + "." + i + "." + "x"));
                    sender.sendMessage(ChatColor.AQUA + "Y:  " + ChatColor.WHITE + Economy_fac.getData().getConfig().getString(nik + "." + i + "." + "y"));
                    sender.sendMessage(ChatColor.AQUA + "Z:  " + ChatColor.WHITE + Economy_fac.getData().getConfig().getString(nik + "." + i + "." + "z"));
                    sender.sendMessage(ChatColor.AQUA + "Рецепт:  " + ChatColor.WHITE + Economy_fac.getData().getConfig().getString(nik + "." + i + "." + "rec"));
                } else{
                    sender.sendMessage(ChatColor.GREEN+"Слот "+i+" свободен");
                }
            }
            return;
        }
        if (args[0].equalsIgnoreCase("types")) {
            Set<String> names=Economy_fac.getInstance().getConfig().getConfigurationSection("builds").getKeys(false);
            String name;
            for(String types : names){
                name = Economy_fac.getInstance().getConfig().getString("builds."+types+".holot");
                sender.sendMessage(name+ " - название в команде: "+types);
            }
            return;
        }
        sender.sendMessage(ChatColor.RED + "Неизвестная команда!");
    }
}
