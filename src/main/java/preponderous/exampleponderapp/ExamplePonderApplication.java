package preponderous.exampleponderapp;

import preponderous.exampleponderapp.commands.DefaultCommand;
import preponderous.exampleponderapp.misc.CommandSender;
import preponderous.ponder.system.abs.AbstractCommand;
import preponderous.ponder.system.abs.AbstractCommandSender;
import preponderous.ponder.system.abs.AbstractPonderApplication;
import preponderous.ponder.system.services.CommandService;

import java.util.HashSet;
import java.util.Scanner;

public class ExamplePonderApplication extends AbstractPonderApplication {
    private CommandService commandService;

    public ExamplePonderApplication() {
        super("ExamplePonderApplication", "This is an example of an application created with Ponder.");
        onStartup();
    }

    public boolean run() {
        boolean running = true;
        CommandSender sender = new CommandSender();
        Scanner scanner = new Scanner(System.in);
        while (running) {
            if (scanner.hasNext()) {
                String line = scanner.nextLine();
                int indexOfFirstSpace = line.indexOf(' ');
                if (indexOfFirstSpace == -1) {
                    boolean success = onCommand(sender, line, new String[0]);
                    if (!success) {
                        sender.sendMessage("Something went wrong processing your command.");
                    }
                    continue;
                }
                String label = line.substring(0,indexOfFirstSpace );
                line = line.substring(indexOfFirstSpace);
                String[] args = line.split(" ");
                boolean success = onCommand(sender, label, args);
                if (!success) {
                    sender.sendMessage("Something went wrong processing your command.");
                }
            }
            else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onStartup() {
        initializeCommandService();
    }

    @Override
    public void onShutdown() {

    }

    @Override
    public boolean onCommand(AbstractCommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            DefaultCommand defaultCommand = new DefaultCommand();
            return defaultCommand.execute(sender);
        }
        return commandService.interpretCommand(sender, label, args);
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

    public static void main(String[] args) {
        ExamplePonderApplication application = new ExamplePonderApplication();
        application.run();
    }
}