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

import java.util.ArrayList;
import java.util.List;

import chickenzero.ht.com.lienquanwallpaper.customize.SpacesItemDecoration;
import chickenzero.ht.com.lienquanwallpaper.models.Wallpaper;
import chickenzero.ht.com.lienquanwallpaper.service.ListWallpaperRequest;
import chickenzero.ht.com.lienquanwallpaper.service.ServiceGenerator;
import chickenzero.ht.com.lienquanwallpaper.view.adapter.GalleryAdapter;
import chickenzero.ht.com.lienquanwallpaper.view.fragments.SlideshowDialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String endpoint = "https://raw.githubusercontent.com/RohidanTiger/LienQuanLeague/master/wallpaper";
    private ArrayList<Wallpaper> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        images = new ArrayList<>();
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

        showLoading(R.string.lbl_btn_save);
        getListWallpaper();

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
                mAdapter.setImages(response.body());
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
}
