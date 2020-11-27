package view;

import view.commands.*;

/**
 * Класс обработчик сообщений
 */
public class Provider {

    private final Message message;

    Provider(String username, String received) {
        this.message = parseStringToMessage(username, received);
    }

    /**
     * Преобразование ввода пользователя в Message
     * @param received    Сообщение от пользователя в виде строки
     * @return            Преобразованное сообщение типа Message
     */
    private Message parseStringToMessage(String username, String received) {
        var input = received.split(" ");
        var message = new Message();
        message.setUserName(username);
        switch (input[0]) {
            case "/start":
                message.setOperation(Operation.createUser);
                message.setUserName(username);
                return message;
            case "help":
                return new Message(Operation.getHelp);
            case "create":
                if (input.length < 7)
                    return new Message(Operation.fewArguments);
                message.setOperation(Operation.createEvent);
                message.setEventName(input[1]);
                message.setEventTime(input[2] + " " + input[3]);
                message.setEventPlace(input[4]);
                message.setEventDescription(input[5]);
                return message;
            case "own":
                if (input.length > 1)
                    return new Message(Operation.fewArguments);
                message.setOperation(Operation.getOwn);
                return message;
            case "subs":
                if (input.length > 1)
                    return new Message(Operation.fewArguments);
                message.setOperation(Operation.getSub);
                return message;
            case "find":
                if (input.length < 2)
                    return new Message(Operation.fewArguments);
                message.setOperation(Operation.findEvent);
                message.setEventName(input[1]);
                return message;
            //TODO: Починить поиск
            case "findby":
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
            case "sub":
                if (input.length < 2)
                    return new Message(Operation.fewArguments);
                message.setOperation(Operation.subscribe);
                message.setEventId(input[1]);
                return message;
            case "unsub":
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
     * Исполнение операции команды пользователя
     * @return            Результат выполнения команды
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
            case getOwn:
                return new GetOwnEvents().execute(message);
            case getSub:
                return new GetSubscribes().execute(message);
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