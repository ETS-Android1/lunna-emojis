package md.ke.lunnaemojis;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatDelegate;

import com.vanniktech.emoji.EmojiManager;
import md.ke.toaster.BuildConfig;

public class App extends Application {

    private static Context context;
    private static String customEmojiName = "google_sheet";

    public static String getCustomEmojiName() {
        return customEmojiName;
    }

    public static void setCustomEmojiName(String name) {
        customEmojiName = name;
    }

    @Override public void onCreate() {
        super.onCreate();
        context = this;

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_AUTO_BATTERY);

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());
        }
    }

    public static Context getAppContext() {
        return context;
    }
}
