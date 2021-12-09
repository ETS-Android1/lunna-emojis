package md.ke.lunnaemojis.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import java.io.File;
import java.io.FileOutputStream;

import md.ke.lunnaemojis.App;

public class Files {

    public static void installEmojis(Context context, String emojiName, List<>){

        App.setCustomEmojiName(emojiName);

        for(int i = 0; i <= 55; i++){
            final Resources resources = context.getResources();
            final int resId = resources.getIdentifier("emoji_"+App.getCustomEmojiName()+"_" + i, "drawable", context.getPackageName());
            Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), resId, null);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            File file = new File(getCustomEmojiDirectory(), "emoji_"+App.getCustomEmojiName()+"_"+i + ".png");
            if (!file.exists()) {
                Log.e("path", file.toString());
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static File getCustomEmojiDirectory() {

        File baseDir = App.getAppContext().getFilesDir();
        Log.e("BASE DIR PATH:", baseDir.getAbsolutePath());

        File fileSpecificFolder = new File(baseDir, "LUNNA_EMOJIS");

        Log.e("FILE PATH:", fileSpecificFolder.getAbsolutePath());
        Log.e("isFILE:", String.valueOf(fileSpecificFolder.isFile()));

        if (fileSpecificFolder.exists())
            return fileSpecificFolder;
        if (fileSpecificFolder.isFile())
            fileSpecificFolder.delete();
        if (fileSpecificFolder.mkdirs())
            return fileSpecificFolder;

        return baseDir;

    }

}
