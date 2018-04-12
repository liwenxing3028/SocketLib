package com.dydl.electronicdemo.view.fragment.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.dydl.socketlib.common.BasicFragment;

/**
 * Created by lwx on 2018/3/12.
 */

public abstract class BaseFragment extends BasicFragment {

    /**
     * 添加 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 添加 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void addFragment(int containerViewId, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        // 设置tag，不然下面 findFragmentByTag(tag)找不到
        fragmentTransaction.add(containerViewId, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    /**
     * 替换 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void replaceFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    /**
     * 替换 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void replaceFragment(int containerViewId, Fragment fragment, String tag) {
        if (getChildFragmentManager().findFragmentByTag(tag) == null) {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            // 设置tag
            fragmentTransaction.replace(containerViewId, fragment, tag);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // 这里要设置tag，上面也要设置tag
            fragmentTransaction.addToBackStack(tag);
            //fragmentTransaction.setCustomAnimations(R.anim.pop_push_down_out, R.anim.pop_push_up_in);
            fragmentTransaction.commit();
        } else {
            // 存在则弹出在它上面的所有fragment，并显示对应fragment
            getChildFragmentManager().popBackStack(tag, 0);
        }
    }

    /**
     * 移除 Fragment
     *
     * @param fragment
     */
    protected void removeFragment(Fragment fragment, Fragment fragment1) {

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        // 设置tag
        if (fragment != null & fragment1 != null) {
            fragmentTransaction.remove(fragment);
            fragmentTransaction.remove(fragment1);
        } else if (fragment != null) {
            fragmentTransaction.remove(fragment);
        } else if (fragment1 != null) {
            fragmentTransaction.remove(fragment1);
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // 这里要设置tag，上面也要设置tag
        fragmentTransaction.commit();
    }

}
