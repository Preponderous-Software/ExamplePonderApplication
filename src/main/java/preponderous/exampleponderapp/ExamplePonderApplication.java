package preponderous.exampleponderapp;

import preponderous.exampleponderapp.commands.HelpCommand;
import preponderous.exampleponderapp.commands.InfoCommand;
import preponderous.exampleponderapp.commands.QuitCommand;
import preponderous.exampleponderapp.misc.CommandSender;
import preponderous.exampleponderapp.utils.Logger;
import preponderous.ponder.system.abs.AbstractCommand;
import preponderous.ponder.system.abs.AbstractCommandSender;
import preponderous.ponder.system.abs.AbstractPonderApplication;
import preponderous.ponder.system.services.CommandService;

import java.util.HashSet;
import java.util.Scanner;

public class ExamplePonderApplication extends AbstractPonderApplication {
    private static ExamplePonderApplication instance;

    private boolean debug = false;
    private boolean running = true;

    private CommandService commandService;
    private final Scanner scanner = new Scanner(System.in);

    public static ExamplePonderApplication getInstance() {
        return instance;
    }

    public ExamplePonderApplication() {
        super("ExamplePonderApplication", "This is an example of an application created with Ponder.");
        onStartup();
    }

    public boolean run(AbstractCommandSender sender) {
        Logger.getInstance().log("Running application.");

        // declare variables to be used in loop
        String line;
        String label;
        String[] args;

        sender.sendMessage("Welcome to an example ponder application. Type help to see a list of useful commands.");
        while (isRunning()) {
            line = getInput();
            if (line == null) {
                return false;
            }

            // handle spaces
            int indexOfFirstSpace = line.indexOf(' ');
            if (indexOfFirstSpace != -1) {
                // spaces found
                label = line.substring(0, indexOfFirstSpace);
                args = line.substring(indexOfFirstSpace).split(" ");
            }
            else {
                // no spaces found
                label = line;
                args = new String[0];
            }

            // handle command
            boolean success = onCommand(sender, label, args);
            if (!success) {
                Logger.getInstance().log("Something went wrong processing the " + label + " command.");
            }
        }
        return true;
    }

    private String getInput() {
        // get input
        if (!scanner.hasNext()) {
            return null;
        }
        return scanner.nextLine();
    }

    @Override
    public void onStartup() {
        instance = this;
        Logger.getInstance().log("Initiating startup.");
        initializeCommandService();
    }

    @Override
    public void onShutdown() {
        Logger.getInstance().log("Initiating shutdown.");
    }

    @Override
    public boolean onCommand(AbstractCommandSender sender, String label, String[] args) {
        Logger.getInstance().log("Interpreting command " + label);
        return commandService.interpretCommand(sender, label, args);
    }

    public boolean isDebugEnabled() {
        return debug;
    }

    public void setDebugEnabled(boolean debug) {
        this.debug = debug;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private void initializeCommandService() {
        HashSet<AbstractCommand> commands = new HashSet<>();
        commands.add(new HelpCommand());
        commands.add(new InfoCommand());
        commands.add(new QuitCommand());
        commandService = new CommandService(commands);
    }

    public static void main(String[] args) {
        ExamplePonderApplication application = new ExamplePonderApplication();
        CommandSender sender = new CommandSender();
        application.run(sender);
    }
}