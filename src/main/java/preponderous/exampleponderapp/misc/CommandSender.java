package preponderous.exampleponderapp.misc;

import preponderous.ponder.system.abs.AbstractCommandSender;

public class CommandSender extends AbstractCommandSender {

    @Override
    public void sendMessage(String s) {
        System.out.println(s);
    }
}
