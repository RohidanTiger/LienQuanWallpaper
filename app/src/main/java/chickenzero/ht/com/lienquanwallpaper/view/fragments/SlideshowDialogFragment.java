package chickenzero.ht.com.lienquanwallpaper.view.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import chickenzero.ht.com.lienquanwallpaper.MainActivity;
import chickenzero.ht.com.lienquanwallpaper.R;
import chickenzero.ht.com.lienquanwallpaper.models.Wallpaper;
import chickenzero.ht.com.lienquanwallpaper.utils.ConnectivityReceiver;
import chickenzero.ht.com.lienquanwallpaper.utils.PicassoLoader;
import chickenzero.ht.com.lienquanwallpaper.utils.Utils;

/**
 * Created by QuyDV on 6/3/17.
 */

public class SlideshowDialogFragment extends DialogFragment {
    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private Wallpaper image;
    private LinearLayout llSetWallpaper;
    private Utils utils;
    private PhotoView imageview;
    public MainActivity context;
    private AdView mAdView;
    private Target mTarget;


    public static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
        imageview = (PhotoView) v.findViewById(R.id.image);
        mAdView = (AdView) v.findViewById(R.id.adView);
        utils = new Utils(getActivity());

        llSetWallpaper = (LinearLayout) v.findViewById(R.id.llSetWallpaper);

        image = (Wallpaper) getArguments().getSerializable("images");
        PicassoLoader.getInstance(getContext()).with(getContext()).load(image.getFullsize()).into(imageview);
        //loadAvatar(image.getFullsize());

        llSetWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Bitmap bitmap = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
                    utils.setAsWallpaper(bitmap);
                }catch (Exception e){
                    Toast.makeText(getContext(), getContext().getString(R.string.toast_wallpaper_set_failed), Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAdView.loadAd(context.adRequest);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (MainActivity) getActivity();
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!ConnectivityReceiver.isConnected()){
            Toast.makeText(getContext(),getResources().getString(R.string.cmn_no_internet_access),Toast.LENGTH_LONG).show();
        }
    }

    private void loadAvatar(String url) {
        mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
        PicassoLoader.getInstance(getContext()).load(url).into(mTarget);
    }
}