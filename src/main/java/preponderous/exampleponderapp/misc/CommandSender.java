package preponderous.exampleponderapp.misc;

import preponderous.ponder.system.abs.AbstractCommandSender;

/**
 * @author Daniel Stephenson
 */
public class CommandSender extends AbstractCommandSender {

    /**
     * This can be used to send a message to the command sender, who in this case is the user of the console.
     * @param message The message to send to the command sender.
     */
    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }
}
