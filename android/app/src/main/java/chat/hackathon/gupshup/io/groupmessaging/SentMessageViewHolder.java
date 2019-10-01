package chat.hackathon.gupshup.io.groupmessaging;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class SentMessageViewHolder extends RecyclerView.ViewHolder {
    private TextView messageView;

    public SentMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        this.messageView = itemView.findViewById(R.id.messageText);
    }

    public void updateMessage(String message) {
        messageView.setText(message);
    }
}
