package view.commands;

import view.Message;

public class FewArguments extends Command {
    @Override
    public String execute(Message message) {
        return "Too few arguments. Try to type \"help\"";
    }
}
