package chickenzero.ht.com.lienquanwallpaper;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import chickenzero.ht.com.lienquanwallpaper.customize.SpacesItemDecoration;
import chickenzero.ht.com.lienquanwallpaper.models.Wallpaper;
import chickenzero.ht.com.lienquanwallpaper.service.ListWallpaperRequest;
import chickenzero.ht.com.lienquanwallpaper.service.ServiceGenerator;
import chickenzero.ht.com.lienquanwallpaper.utils.ConnectivityReceiver;
import chickenzero.ht.com.lienquanwallpaper.view.adapter.GalleryAdapter;
import chickenzero.ht.com.lienquanwallpaper.view.fragments.SlideshowDialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    private ArrayList<Wallpaper> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    public AdRequest adRequest;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mAdView = (AdView)findViewById(R.id.adView);
        setSupportActionBar(toolbar);
        images = new ArrayList<>();
        // init ad
        MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.unit_ad_unit_id));
        adRequest = new AdRequest.Builder().addTestDevice("867826023574924").build();
        mAdView.loadAd(adRequest);

        mAdapter = new GalleryAdapter(getApplicationContext(), images);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2); //new StaggeredGridLayoutManager(2,1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(this, R.dimen.padding_smaller);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mAdapter);

        showLoading(R.string.cmn_loading);
        getListWallpaper();

        ConnectivityReceiver.connectivityReceiverListener = this;

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images.get(position));
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment =  SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void getListWallpaper(){
        Call<List<Wallpaper>> call = ServiceGenerator.leagueService.loadWallpaperList();
        call.enqueue(new Callback<List<Wallpaper>>() {
            @Override
            public void onResponse(Call<List<Wallpaper>> call, Response<List<Wallpaper>> response) {
                hideLoading();
                images = (ArrayList<Wallpaper>) response.body();
                Collections.shuffle(images);
                mAdapter.setImages(images);
            }

            @Override
            public void onFailure(Call<List<Wallpaper>> call, Throwable t) {
                hideLoading();
            }
        });
    }

    public void showLoading(final int message) {
        try {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        pDialog.show();
                        pDialog.setMessage(getString(message));
                    } catch (Exception error) {
                        error.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoading(final String message) {
        try {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        pDialog.show();
                        pDialog.setMessage(message);
                    } catch (Exception error) {
                        error.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoading() {
        try {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        pDialog.show();
                    } catch (Exception error) {
                        error.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void hideLoading() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (pDialog != null) {
                        pDialog.hide();
                    }
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected){
            getListWallpaper();
        }else{
            Toast.makeText(this,getString(R.string.cmn_no_internet_access),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!ConnectivityReceiver.isConnected()){
            Toast.makeText(this,getResources().getString(R.string.cmn_no_internet_access),Toast.LENGTH_LONG).show();
        }
    }
}
