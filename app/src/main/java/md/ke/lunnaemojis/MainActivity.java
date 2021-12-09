package md.ke.lunnaemojis;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.material.MaterialEmojiLayoutFactory;

import md.ke.toaster.ToasterMessage;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";

    ChatAdapter chatAdapter;
    EmojiPopup emojiPopup;

    EmojiEditText editText;
    ViewGroup rootView;
    ImageView emojiButton;

    @Override @SuppressLint("SetTextI18n") protected void onCreate(final Bundle savedInstanceState) {
        getLayoutInflater().setFactory2(new MaterialEmojiLayoutFactory((LayoutInflater.Factory2) getDelegate()));
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ToasterMessage.s(this, " TOASTING ");

        chatAdapter = new ChatAdapter();

        final Button button = findViewById(R.id.main_activity_material_button);
        button.setText("\uD83D\uDE18\uD83D\uDE02\uD83E\uDD8C");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Files.installEmojis(MainActivity.this);
            }
        });

        editText = findViewById(R.id.main_activity_chat_bottom_message_edittext);
        rootView = findViewById(R.id.main_activity_root_view);
        emojiButton = findViewById(R.id.main_activity_emoji);
        final ImageView sendButton = findViewById(R.id.main_activity_send);

        emojiButton.setColorFilter(ContextCompat.getColor(this, R.color.emoji_icons), PorterDuff.Mode.SRC_IN);
        sendButton.setColorFilter(ContextCompat.getColor(this, R.color.emoji_icons), PorterDuff.Mode.SRC_IN);

        final CheckBox forceEmojisOnly = findViewById(R.id.main_activity_force_emojis_only);
        forceEmojisOnly.setOnCheckedChangeListener((ignore, isChecked) -> {
            if (isChecked) {
                editText.clearFocus();
                emojiButton.setVisibility(GONE);
                editText.disableKeyboardInput(emojiPopup);
            } else {
                emojiButton.setVisibility(VISIBLE);
                editText.enableKeyboardInput();
            }
        });

        emojiButton.setOnClickListener(ignore -> emojiPopup.toggle());

        sendButton.setOnClickListener(ignore -> {
            final String text = editText.getText().toString().trim();

            if (text.length() > 0) {
                chatAdapter.add(text);

                editText.setText("");
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.main_activity_recycler_view);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        setUpEmojiPopup();
    }

    private void setUpEmojiPopup() {
        emojiPopup = EmojiPopup.Builder.fromRootView(rootView)
                .setOnEmojiBackspaceClickListener(ignore -> Log.d(TAG, "Clicked on Backspace"))
                .setOnEmojiClickListener((ignore, ignore2) -> Log.d(TAG, "Clicked on emoji"))
                .setOnEmojiPopupShownListener(() -> emojiButton.setImageResource(R.drawable.ic_keyboard))
                .setOnSoftKeyboardOpenListener(ignore -> Log.d(TAG, "Opened soft keyboard"))
                .setOnEmojiPopupDismissListener(() -> emojiButton.setImageResource(R.drawable.emoji_google_category_smileysandpeople))
                .setOnSoftKeyboardCloseListener(() -> Log.d(TAG, "Closed soft keyboard"))
                .setKeyboardAnimationStyle(R.style.emoji_fade_animation_style)
                .setPageTransformer(new PageTransformer())
                //.setRecentEmoji(NoRecentEmoji.INSTANCE) // Uncomment this to hide recent emojis.
                .build(editText);
    }

}
