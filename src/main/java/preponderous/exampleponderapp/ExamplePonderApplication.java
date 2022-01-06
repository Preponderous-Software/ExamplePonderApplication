package preponderous.exampleponderapp;

import preponderous.exampleponderapp.commands.DefaultCommand;
import preponderous.ponder.system.abs.AbstractCommand;
import preponderous.ponder.system.abs.AbstractCommandSender;
import preponderous.ponder.system.abs.AbstractPonderApplication;
import preponderous.ponder.system.services.CommandService;

import java.util.HashSet;

public class ExamplePonderApplication extends AbstractPonderApplication {
    private CommandService commandService;

    public ExamplePonderApplication() {
        super("ExamplePonderApplication", "This is an example of an application created with Ponder.");
        onStartup();
    }

    @Override
    public void onStartup() {
        initializeCommandService();
    }

    @Override
    public void onShutdown() {

    }

    @Override
    public boolean onCommand(AbstractCommandSender abstractCommandSender, String label, String[] args) {
        return commandService.interpretCommand(abstractCommandSender, label, args);
    }

    private void initializeCommandService() {
        HashSet<String> coreCommands = new HashSet<>();
        coreCommands.add("epa");
        coreCommands.add("exampleponderapplication");
        commandService = new CommandService(coreCommands);

        HashSet<AbstractCommand> commands = new HashSet<>();
        commands.add(new DefaultCommand());
        commandService.initialize(commands);
    }
}