package view;

import view.commands.*;

public class Provider {

    private final Message message;

    Provider(String username, String received) {
        this.message = parseStringToMessage(username, received);
    }

    /**
     * @param received Сообщение от пользователя в виде строки
     * @return Парсированное сообщение типа Message
     */
    private Message parseStringToMessage(String username, String received) {
        var input = received.split(" ");
        var message = new Message();
        message.setUserName(username);
        switch (input[0]) {
            case "create":
                if (input[1].equals("event")) {
                    if (input.length < 7)
                        return new Message(Operation.fewArguments);
                    message.setOperation(Operation.createEvent);
                    message.setEventName(input[2]);
                    message.setEventTime(input[3] + " " + input[4]);
                    message.setEventPlace(input[5]);
                    message.setEventDescription(input[6]);
                } else {
                    if (input.length < 4)
                        return new Message(Operation.fewArguments);
                    message.setOperation(Operation.createUser);
                    message.setUserName(input[2]);
                    message.setUserPassword(input[3]);
                }
                return message;
            case "login":
                if (input.length < 3)
                    return new Message(Operation.fewArguments);
                message.setOperation(Operation.logIn);
                message.setUserName(input[1]);
                message.setUserPassword(input[2]);
                return message;
            case "help":
                return new Message(Operation.getHelp);
            case "find":
                if (input.length < 2)
                    return new Message(Operation.fewArguments);
                message.setOperation(Operation.findEvent);
                message.setEventName(input[1]);
                return message;
            case "findp":
                if (input.length < 2)
                    return new Message(Operation.fewArguments);
                var params = new String[6];
                if (input.length < 7)
                    System.arraycopy(input, 1, params, 0, input.length - 2);
                message.setOperation(Operation.findEventByParameters);
                message.setEventName(params[0]);
                message.setEventPlace(params[1]);
                message.setEventTime(params[2] + " " + params[3]);
                message.setEventDescription(params[4]);
                message.setEventCategory(params[5]);
                return message;
            case "update":
                if (input.length < 7)
                    return new Message(Operation.fewArguments);
                message.setOperation(Operation.updateEvent);
                message.setEventName(input[2]);
                message.setEventTime(input[3] + " " + input[4]);
                message.setEventPlace(input[5]);
                message.setEventDescription(input[6]);
                return message;
            case "signup":
                if (input.length < 2)
                    return new Message(Operation.fewArguments);
                message.setOperation(Operation.subscribe);
                message.setEventId(input[1]);
                return message;
            case "unsubscribe":
                if (input.length < 2)
                    return new Message(Operation.fewArguments);
                message.setOperation(Operation.unsubscribe);
                message.setEventId(input[1]);
                return message;
            case "remove":
                if (input.length < 2)
                    return new Message(Operation.fewArguments);
                message.setOperation(Operation.removeEvent);
                message.setEventId(input[1]);
                return message;
            case "exit":
                return new Message(Operation.logOut);
            default:
                return new Message(Operation.unknown);
        }
    }

    /**
     * @return Результат операции пользователя
     */
    public String execute() {
        switch (message.getOperation()) {
            case getHelp:
                return new Help().execute(message);
            case findEvent:
                return new FindingEvent().execute(message);
            case subscribe:
                return new Subscribing().execute(message);
            case createUser:
                return new UserCreating().execute(message);
            case createEvent:
                return new EventCreating().execute(message);
            case removeEvent:
                return new RemovingEvent().execute(message);
            case unsubscribe:
                return new Unsubscribing().execute(message);
            case updateEvent:
                return new UpdatingEvent().execute(message);
            case findEventByParameters:
                return new FindingEventParameters().execute(message);
            case unknown:
                return new Unknown().execute(message);
            case fewArguments:
                return new FewArguments().execute(message);
        }
        return "Something goes wrong (-1)";
    }
}