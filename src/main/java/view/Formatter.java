package view;

import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;

public class Formatter {

    /**
     * Применяет форматирование для сохраняемых данных
     * @param text      Текст, который нужно отформатировать
     * @param entities  Сущности, хранящие информацию о форматировании
     * @return          Отформатированный текст
     */
    public String applyFormatting(String text, List<MessageEntity> entities) {
        if (entities.isEmpty())
            return text;
        StringBuilder sb = new StringBuilder();
        int shift = 0;
        sb.append(text);
        for (MessageEntity entity : entities) {
            switch (entity.getType()) {
                case "bold":
                    sb.insert(entity.getOffset() + shift, "<b>");
                    shift += 3;
                    sb.insert(entity.getLength() + shift, "</b>");
                    shift += 4;
                    break;
                case "italic":
                    sb.insert(entity.getOffset() + shift, "<i>");
                    shift += 3;
                    sb.insert(entity.getOffset() + entity.getLength() + shift, "</i>");
                    shift += 4;
                    break;
                case "underline":
                    sb.insert(entity.getOffset() + shift, "<u>");
                    shift += 3;
                    sb.insert(entity.getOffset() + entity.getLength() + shift, "</u>");
                    shift += 4;
                    break;
                case "strikethrough":
                    sb.insert(entity.getOffset() + shift, "<s>");
                    shift += 3;
                    sb.insert(entity.getOffset() + entity.getLength() + shift, "</s>");
                    shift += 4;
                    break;
                case "code":
                    sb.insert(entity.getOffset() + shift, "<code>");
                    shift += 6;
                    sb.insert(entity.getOffset() + entity.getLength() + shift, "</code>");
                    shift += 7;
                    break;
            }
        }
        return sb.toString();
    }
}
