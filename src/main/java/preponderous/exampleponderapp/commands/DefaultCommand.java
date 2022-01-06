package preponderous.exampleponderapp.commands;

import preponderous.ponder.system.abs.AbstractCommand;
import preponderous.ponder.system.abs.AbstractCommandSender;

import java.util.ArrayList;
import java.util.Arrays;

public class DefaultCommand extends AbstractCommand {
    public DefaultCommand() {
        super(new ArrayList<>(Arrays.asList("default")), new ArrayList<>(Arrays.asList("epa.default")));
    }

    @Override
    public boolean execute(AbstractCommandSender abstractCommandSender) {
        abstractCommandSender.sendMessage("Example Ponder Application");
        abstractCommandSender.sendMessage("Developer: Daniel Stephenson");
        return true;
    }

    @Override
    public boolean execute(AbstractCommandSender abstractCommandSender, String[] strings) {
        return execute(abstractCommandSender);
    }
}
