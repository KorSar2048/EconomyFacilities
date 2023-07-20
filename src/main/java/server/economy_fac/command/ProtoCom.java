package server.economy_fac.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import server.economy_fac.Economy_fac;

public abstract class ProtoCom implements CommandExecutor {

    public ProtoCom(String command){
        PluginCommand pluginCommand = Economy_fac.getInstance().getCommand(command);
        if(pluginCommand!=null) {
            pluginCommand.setExecutor(this);
        }
    }

    public abstract void execute(CommandSender sender, String label, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        execute(sender, label, args);
        return true;
    }
}
