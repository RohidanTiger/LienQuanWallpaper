package chickenzero.ht.com.lienquanwallpaper.service;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.Stack;


public final class FragmentStackManager {


    public interface Callback {

        void onStackChanged(int stackSize, Fragment topFragment);
    }

    /**
     * Create an instance for a specific container.
     */
    public static FragmentStackManager forContainer(FragmentActivity activity,
                                                    int containerId, Callback callback) {
        return new FragmentStackManager(activity, containerId, callback);
    }

    private static final String STATE_STACK = "com.sos.FragmentStackManager.stack";

    public Stack<Fragment> stack = new Stack<Fragment>();

    private boolean isProcessing;
    private Handler myHandler;
    private FragmentActivity activity;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private int containerId;

    private Callback callback;

    private int enterAnimation;
    private int exitAnimation;
    private int popStackEnterAnimation;
    private int popStackExitAnimation;

    private FragmentStackManager(FragmentActivity activity, int containerId,
                                 Callback callback) {
        stack = new Stack<Fragment>();
        myHandler = new Handler();
        this.activity = activity;
        fragmentManager = activity.getSupportFragmentManager();
        this.containerId = containerId;
        this.callback = callback;
    }

    /**
     * Removes all added fragments and clear the stack.
     */

    public void clearAllScreen() {
        // Fragment topFragment = stack.peek();
        fragmentTransaction = fragmentManager.beginTransaction();
        if (stack != null && stack.size() > 0) {
            for (Fragment item : stack) {
                // Hide view
                fragmentTransaction.hide(item);
                //Remove view from activity
                fragmentTransaction.detach(item);
                // Remove fragment state from FragmentManager
                fragmentTransaction.remove(item);

            }
            stack.clear();
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    public int size() {
        return stack.size();
    }

    public Fragment peek() {
        return stack.peek();
    }

    public void push(Fragment fragment, String tag) {
        push(fragment, tag, null, true, true);
    }

    /**
     * Adds a new fragment to the stack and displays it.
     */
    public void push(Fragment fragment, String tag, Bundle args,
                     boolean isAnimation, boolean isAddedToBackStack) {

//        if (!isProcessing) {
            isProcessing = true;
            fragmentTransaction = fragmentManager.beginTransaction();
            if (isAnimation)
                fragmentTransaction.setCustomAnimations(enterAnimation,
                        exitAnimation);

            fragmentTransaction.add(containerId, fragment);
            fragmentTransaction.commitAllowingStateLoss();

            if (args != null)
                fragment.setArguments(args);

            if (isAddedToBackStack)
                stack.add(fragment);
            callback.onStackChanged(size(), peek());
//            myHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    isProcessing = false;
//                }
//            }, isAnimation ? 300 : 0);
//        }
    }

    /**
     * Adds a new fragment to the stack and displays it.
     */
    public void push(Fragment fragment, String tag, Bundle args) {
        push(fragment, tag, args, true, true);
    }

    /**
     * Removes the fragment at the top of the stack and displays the previous
     * one. This will not do anything if there is only one fragment in the
     * stack.
     *
     * @return Whether a transaction has been enqueued.
     */

    public boolean pop(boolean isSlideBack) {
        if (!stack.isEmpty() && stack.size() > 1) {
            fragmentTransaction = fragmentManager.beginTransaction();

            if (isSlideBack)
                fragmentTransaction.setCustomAnimations(popStackEnterAnimation,
                        popStackExitAnimation);
            stack.lastElement().onPause();
            fragmentTransaction.remove(stack.pop());

            callback.onStackChanged(size(), peek());

            stack.lastElement().onResume();

            fragmentTransaction.show(stack.lastElement());
            fragmentTransaction.commit();
            // Fire Event
            return true;
        }

        return false;
    }

    public void setDefaultAnimation(int enter, int exit, int popEnter,
                                    int popExit) {
        enterAnimation = enter;
        exitAnimation = exit;
        popStackEnterAnimation = popEnter;
        popStackExitAnimation = popExit;
    }

}
