package chat.hackathon.gupshup.io.groupmessaging;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView mMessagesRecyclerView;
    private ChatAdapter mChatAdapter;

    private List<ChatMessage> chatMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setupRecyclerView();
        fetchMessages(0L);
    }

    private void fetchMessages(Long from) {
        Call<List<ChatMessage>> call = ApiClientImpl.getApiClient().getChatMessages(from);
        call.enqueue(new Callback<List<ChatMessage>>() {
            @Override
            public void onResponse(Call<List<ChatMessage>> call,
                                   Response<List<ChatMessage>> response) {
                ChatActivity.this.chatMessages = response.body();
            }

            @Override
            public void onFailure(Call<List<ChatMessage>> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Failed to fetch messages", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupRecyclerView() {
        if (mChatAdapter == null) {
            mMessagesRecyclerView = findViewById(R.id.message_list_view);
            mChatAdapter = new ChatAdapter(this, chatMessages);
        }
    }
}
