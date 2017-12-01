package Auora.model.player;

public class ChatMessage {

    public String chatText;
    private short effects;
    private short numChars;

    public ChatMessage(int effects, int numChars, String chatText) {
        this.effects = (short) effects;
        this.numChars = (short) numChars;
        this.chatText = chatText;
    }

    public short getEffects() {
        return effects;
    }

    public short getNumChars() {
        return numChars;
    }

    public String getChatText() {
        return chatText;
    }

}
