package chickenzero.ht.com.lienquanwallpaper.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by QuyDV on 6/3/17.
 */

public class PrefManager {
    private static final String TAG = PrefManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AwesomeWallpapers";

    // Google's username
    private static final String KEY_GOOGLE_USERNAME = "google_username";

    // No of grid columns
    private static final String KEY_NO_OF_COLUMNS = "no_of_columns";

    // Gallery directory name
    private static final String KEY_GALLERY_NAME = "gallery_name";

    // gallery albums key
    private static final String KEY_ALBUMS = "albums";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

    }

    /**
     * Storing google username
     * */
    public void setGoogleUsername(String googleUsername) {
        editor = pref.edit();

        editor.putString(KEY_GOOGLE_USERNAME, googleUsername);

        // commit changes
        editor.commit();
    }


    /**
     * store number of grid columns
     * */
    public void setNoOfGridColumns(int columns) {
        editor = pref.edit();

        editor.putInt(KEY_NO_OF_COLUMNS, columns);

        // commit changes
        editor.commit();
    }
    /**
     * storing gallery name
     * */
    public void setGalleryName(String galleryName) {
        editor = pref.edit();

        editor.putString(KEY_GALLERY_NAME, galleryName);

        // commit changes
        editor.commit();
    }

}
