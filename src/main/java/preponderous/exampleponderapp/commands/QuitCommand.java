package preponderous.exampleponderapp.commands;

import preponderous.ponder.system.abs.AbstractCommand;
import preponderous.ponder.system.abs.AbstractCommandSender;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Daniel Stephenson
 */
public class QuitCommand extends AbstractCommand {

    public QuitCommand() {
        super(new ArrayList<>(Arrays.asList("quit")), new ArrayList<>(Arrays.asList("epa.quit")));
    }

    @Override
    public boolean execute(AbstractCommandSender sender) {
        sender.sendMessage("Goodbye!");
        System.exit(0);
        return true;
    }

    @Override
    public boolean execute(AbstractCommandSender sender, String[] strings) {
        return execute(sender);
    }
}