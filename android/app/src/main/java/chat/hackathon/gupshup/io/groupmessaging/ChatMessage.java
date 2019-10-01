package chat.hackathon.gupshup.io.groupmessaging;

import com.google.gson.annotations.SerializedName;

public class ChatMessage {
    @SerializedName("id")
    private Long id;
    @SerializedName("from")
    private String from;
    @SerializedName("msg")
    private String msg;
    @SerializedName("ts")
    private Long ts;

    public Long getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getMsg() {
        return msg;
    }

    public Long getTs() {
        return ts;
    }
}
