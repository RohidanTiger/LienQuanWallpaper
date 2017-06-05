package chickenzero.ht.com.lienquanwallpaper.models;

import java.io.Serializable;

/**
 * Created by QuyDV on 6/5/17.
 */

public class Wallpaper implements Serializable{
    private String id;
    private String thumbnail;
    private String fullsize;

    public Wallpaper() {
    }

    public Wallpaper(String id, String thumbnail, String fullsize) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.fullsize = fullsize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFullsize() {
        return fullsize;
    }

    public void setFullsize(String fullsize) {
        this.fullsize = fullsize;
    }
}
