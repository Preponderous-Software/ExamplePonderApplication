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
    private CommandService commandService;
    private boolean running = true;

    public static ExamplePonderApplication getInstance() {
        return instance;
    }

    public ExamplePonderApplication() {
        super("ExamplePonderApplication", "This is an example of an application created with Ponder.");
        onStartup();
    }

    public boolean run() {
        Logger.getInstance().log("Running application.");
        CommandSender sender = new CommandSender();
        sender.sendMessage("Welcome to an example ponder application. Type help to see a list of useful commands.");
        Scanner scanner = new Scanner(System.in);
        while (isRunning()) {
            // get input
            if (!scanner.hasNext()) {
                return false;
            }
            String line = scanner.nextLine();
            String label;
            String[] args;

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

            boolean success = onCommand(sender, label, args);
            if (!success) {
                sender.sendMessage("Something went wrong processing your command.");
            }
        }
        return true;
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
        application.run();
    }
}