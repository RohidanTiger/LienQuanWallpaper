package chickenzero.ht.com.lienquanwallpaper.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;

/**
 * NetworkUtil checks available network
 * 
 * @author android.dev
 */
public class NetworkUtility {

	/**
	 * Check network connection
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager conMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo i = conMgr.getActiveNetworkInfo();
		if (i == null) {
			return false;
		}
		if (!i.isConnected()) {
			return false;
		}
		if (!i.isAvailable()) {
			return false;
		}
		return true;
	}

	public static boolean isNetworkAvailable(Fragment fragment) {
		return isNetworkAvailable(fragment.getActivity());
	}
}
