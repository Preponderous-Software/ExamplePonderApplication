package preponderous.exampleponderapp.commands;

import preponderous.ponder.system.abs.ApplicationCommand;
import preponderous.ponder.system.abs.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Daniel Stephenson
 */
public class QuitCommand extends ApplicationCommand {

    public QuitCommand() {
        super(new ArrayList<>(Arrays.asList("quit")), new ArrayList<>(Arrays.asList("epa.quit")));
    }

    @Override
    public boolean execute(CommandSender sender) {
        sender.sendMessage("Goodbye!");
        System.exit(0);
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] strings) {
        return execute(sender);
    }
}