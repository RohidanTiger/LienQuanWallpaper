package chickenzero.ht.com.lienquanwallpaper.service;

import java.util.List;

import chickenzero.ht.com.lienquanwallpaper.models.Wallpaper;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by QuyDV on 6/5/17.
 */

public interface WallpaperAPI {
    @GET("LienQuanLeague/master/wallpaper.json")
    Call<List<Wallpaper>> loadWallpaperList();
}
