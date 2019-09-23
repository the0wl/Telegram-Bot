// Kelvin  I. Seibt - 20/09/2019 :: Criação

package telegram.api;

public class TelegramAPIMessage {
    
    private int message_id;
    private TelegramAPIUser from;
    private TelegramAPIChat chat;
    private int date;
    private String text;

    public TelegramAPIMessage(int message_id, TelegramAPIUser from, TelegramAPIChat chat, int date, String text) {
        this.message_id = message_id;
        this.from = from;
        this.chat = chat;
        this.date = date;
        this.text = text;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public TelegramAPIUser getFrom() {
        return from;
    }

    public void setFrom(TelegramAPIUser from) {
        this.from = from;
    }

    public TelegramAPIChat getChat() {
        return chat;
    }

    public void setChat(TelegramAPIChat chat) {
        this.chat = chat;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
