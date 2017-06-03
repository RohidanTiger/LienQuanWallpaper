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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import chickenzero.ht.com.lienquanwallpaper.customize.SpacesItemDecoration;
import chickenzero.ht.com.lienquanwallpaper.service.ListWallpaperRequest;
import chickenzero.ht.com.lienquanwallpaper.view.adapter.GalleryAdapter;
import chickenzero.ht.com.lienquanwallpaper.view.fragments.SlideshowDialogFragment;

public class MainActivity extends AppCompatActivity {
    private static final String endpoint = "https://raw.githubusercontent.com/RohidanTiger/LienQuanLeague/master/wallpaper";
    private ArrayList<String> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;

    private LoaderManager.LoaderCallbacks wallpaperListener = new LoaderManager.LoaderCallbacks<List<String>>() {
        @Override
        public Loader<List<String>> onCreateLoader(int id, Bundle args) {
            return new ListWallpaperRequest(MainActivity.this,endpoint);
        }

        @Override
        public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
            hideLoading();
            Log.d("Data",String.valueOf(data.size()));
            images = (ArrayList<String>) data;
            mAdapter.setImages(data);
        }

        @Override
        public void onLoaderReset(Loader<List<String>> loader) {

        }
    };

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

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //SpacesItemDecoration itemDecoration = new SpacesItemDecoration(this, R.dimen.padding_smaller);
        //recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mAdapter);

        showLoading(R.string.lbl_btn_save);

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("images", images);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment =  SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        getSupportLoaderManager().initLoader(1, null, wallpaperListener).forceLoad();
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
