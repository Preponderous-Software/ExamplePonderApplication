package preponderous.exampleponderapp;

import preponderous.exampleponderapp.commands.HelpCommand;
import preponderous.exampleponderapp.commands.InfoCommand;
import preponderous.exampleponderapp.commands.QuitCommand;
import preponderous.exampleponderapp.misc.CommandSenderImpl;
import preponderous.exampleponderapp.utils.Logger;
import preponderous.ponder.system.abs.ApplicationCommand;
import preponderous.ponder.system.abs.CommandSender;
import preponderous.ponder.system.abs.PonderApplication;
import preponderous.ponder.system.services.CommandService;

import java.util.HashSet;
import java.util.Scanner;

/**
 * @author Daniel Stephenson
 */
public class ExamplePonderApplication extends PonderApplication {
    private static ExamplePonderApplication instance;

    private boolean debug = false;
    private boolean running = true;

    private CommandService commandService;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * This can be utilized to access the self-managed instance of the application.
     * @return The self-managed instance of the application.
     */
    public static ExamplePonderApplication getInstance() {
        return instance;
    }

    /**
     * Initializes values and calls the onStartup method.
     */
    public ExamplePonderApplication() {
        super("ExamplePonderApplication", "This is an example of an application created with Ponder.");
        onStartup();
    }

    /**
     * The primary method for the application. This is where the running loop can be found.
     * @param user The user of the application.
     * @return Whether the program exited successfully.
     */
    public boolean run(CommandSender user) {
        Logger.getInstance().log("Running application.");

        // declare variables to be used in loop
        String line;
        String label;
        String[] args;

        user.sendMessage("Welcome to an example ponder application. Type help to see a list of useful commands.");
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
            boolean success = onCommand(user, label, args);
            if (!success) {
                Logger.getInstance().log("Something went wrong processing the " + label + " command.");
            }
        }
        return true;
    }

    /**
     * This can be used to get input from the console.
     * @return The inputted string.
     */
    private String getInput() {
        // get input
        if (!getScanner().hasNext()) {
            return null;
        }
        return getScanner().nextLine();
    }

    /**
     * This will be called when the application starts up.
     */
    @Override
    public void onStartup() {
        instance = this;
        Logger.getInstance().log("Initiating startup.");
        initializeCommandService();
    }

    /**
     * This will be called when the application shuts down.
     */
    @Override
    public void onShutdown() {
        Logger.getInstance().log("Initiating shutdown.");
    }

    /**
     * This will be called when a command is entered into the application.
     * @param sender The sender of the command.
     * @param label The label of the command.
     * @param args The arguments of the command.
     * @return Whether the execution of command was successful.
     */
    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        Logger.getInstance().log("Interpreting command " + label);
        return getCommandService().interpretCommand(sender, label, args);
    }

    /**
     * This can be used to check whether the debug flag is enabled.
     * @return Whether the debug flag is enabled.
     */
    public boolean isDebugEnabled() {
        return debug;
    }

    /**
     * This can be used to set the debug flag to true or false.
     * @param debug Desired value for the debug flag.
     */
    public void setDebugEnabled(boolean debug) {
        this.debug = debug;
    }

    /**
     * This can be used to check if the application is running.
     * @return Whether the application is running.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * This can be used to set the running flag of the application.
     * @param running Desired value for the running flag.
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Initializes the command service with the application's commands.
     */
    private void initializeCommandService() {
        HashSet<ApplicationCommand> commands = new HashSet<>();
        commands.add(new HelpCommand());
        commands.add(new InfoCommand());
        commands.add(new QuitCommand());
        setCommandService(new CommandService((commands)));
    }

    /**
     * This can be used to access Ponder's command service.
     * @return This application's managed instance of Ponder's command service.
     */
    private CommandService getCommandService() {
        return commandService;
    }

    /**
     * This can be used to set this application's managed instance of Ponder's command service.
     * @param commandService The instance of Ponder's command service to use.
     */
    private void setCommandService(CommandService commandService) {
        this.commandService = commandService;
    }

    /**
     * This can be used to access this application's managed scanner.
     * @return This application's managed scanner.
     */
    private Scanner getScanner() {
        return scanner;
    }

    /**
     * Instantiates and runs the application.
     * @param args The arguments given to the program.
     */
    public static void main(String[] args) {
        ExamplePonderApplication application = new ExamplePonderApplication();
        CommandSenderImpl sender = new CommandSenderImpl();
        application.run(sender);
    }
}