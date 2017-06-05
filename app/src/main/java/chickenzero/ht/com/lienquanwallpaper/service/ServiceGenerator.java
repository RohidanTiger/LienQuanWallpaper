package chickenzero.ht.com.lienquanwallpaper.service;

import chickenzero.ht.com.lienquanwallpaper.constant.Constant;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by QuyDV on 6/5/17.
 */

public class ServiceGenerator {
    private static Retrofit wallService = new Retrofit.Builder().baseUrl(Constant.BASE_URL).
            addConverterFactory(GsonConverterFactory.create()).build();
    public static WallpaperAPI leagueService = wallService.create(WallpaperAPI.class);
}
