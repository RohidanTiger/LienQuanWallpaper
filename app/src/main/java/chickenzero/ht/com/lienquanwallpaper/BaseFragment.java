package chickenzero.ht.com.lienquanwallpaper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by QuyDV on 3/29/17.
 */

public abstract class BaseFragment extends Fragment {
    public MainActivity context;
    public String TAG = null;
    protected View rootView;
    protected Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (MainActivity) getActivity();
        TAG = BaseFragment.class.getSimpleName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getViewContent(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    // ========Common functions========//

    public void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void showToast(final int idString) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, idString, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showToast(final String message) {
        if (message != null) {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Display Toast Message
     *
     * @param message
     * @param timeDisplay in miliseconds
     */

    public void showToast(String message, int timeDisplay) {
        if (message != null)
            Toast.makeText(context, message, timeDisplay).show();
    }

    public boolean canPressBack() {
        return false;
    }

    protected void hideKeyboard(EditText editText) {
        try {
            ((InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    editText.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showKeyboard(EditText edit) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edit, 0);
    }

    protected void hideKeyBoard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            // Check if no view has focus
            View v = context.getCurrentFocus();
            if (v != null) {
                inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Main layout Id for Fragment
    protected abstract int getViewContent();

    //Main layout Id for Fragment
    protected abstract void initUI();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
