package preponderous.exampleponderapp.commands;

import preponderous.ponder.system.abs.AbstractCommand;
import preponderous.ponder.system.abs.AbstractCommandSender;

import java.util.ArrayList;
import java.util.Arrays;

public class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super(new ArrayList<>(Arrays.asList("help")), new ArrayList<>(Arrays.asList("epa.help")));
    }

    @Override
    public boolean execute(AbstractCommandSender sender) {
        sender.sendMessage("=== Example Ponder Application Commands ===");
        sender.sendMessage("help - View a list of useful commands.");
        sender.sendMessage("info - View information about the application.");
        return true;
    }

    @Override
    public boolean execute(AbstractCommandSender sender, String[] args) {
        return execute(sender);
    }
}