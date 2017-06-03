package chickenzero.ht.com.lienquanwallpaper.service;

import android.support.v4.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import chickenzero.ht.com.lienquanwallpaper.MainActivity;


/**
 * Created by QuyDV on 5/22/17.
 */

public class ListWallpaperRequest extends AsyncTaskLoader<List<String>> {
    private MainActivity mContext;
    private String mUrl;
    private List<String> listUrl = new ArrayList<>();

    public ListWallpaperRequest(MainActivity context, String url) {
        super(context);
        this.mContext = context;
        mUrl = url;
    }

    @Override
    public void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    public List<String> loadInBackground() {
        URL url = null;
        try {
            url = new URL(mUrl);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            url.openStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null){
                if(inputLine.contains("?dl=0")) inputLine.replace("inputLine","");
                listUrl.add(inputLine);
            }


            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listUrl;
    }

    @Override
    protected void onStopLoading() {
        mContext.hideLoading();
        super.onStopLoading();
    }
}
