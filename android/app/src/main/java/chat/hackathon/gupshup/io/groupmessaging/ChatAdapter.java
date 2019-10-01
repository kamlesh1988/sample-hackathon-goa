package chat.hackathon.gupshup.io.groupmessaging;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int RECEIVED_MESSAGE_VIEW_HOLDER = 51;
    private static final int SENT_MESSAGE_VIEW_HOLDER = 52;


    private Context context;
    private List<ChatMessage> mChatMessages;


    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {
        this.context = context;
        this.mChatMessages = chatMessages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case RECEIVED_MESSAGE_VIEW_HOLDER:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_chat_text_left, parent, false);
                return new ReceivedMessageViewHolder(itemView);
            case SENT_MESSAGE_VIEW_HOLDER:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_chat_text_left, parent, false);
                return new SentMessageViewHolder(itemView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ChatMessage chatMessage = this.mChatMessages.get(position);
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case RECEIVED_MESSAGE_VIEW_HOLDER:
                bindReceivedTextContentViewHolder((ReceivedMessageViewHolder) viewHolder, chatMessage);
            case SENT_MESSAGE_VIEW_HOLDER:
                bindSentTextContentViewHolder((SentMessageViewHolder) viewHolder, chatMessage);

        }
    }

    private void bindSentTextContentViewHolder(SentMessageViewHolder viewHolder, ChatMessage chatMessage) {
        viewHolder.updateMessage(chatMessage.getMsg());
    }

    private void bindReceivedTextContentViewHolder(ReceivedMessageViewHolder viewHolder, ChatMessage chatMessage) {
        viewHolder.updateMessage(chatMessage.getMsg());
    }

    @Override
    public int getItemCount() {
        return mChatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        String from = PreferenceManager.getDefaultSharedPreferences(context).getString("user_name", "");
        if (mChatMessages.get(position).getFrom().equals(from)) {
            return SENT_MESSAGE_VIEW_HOLDER;
        } else {
            return RECEIVED_MESSAGE_VIEW_HOLDER;
        }
    }
}
