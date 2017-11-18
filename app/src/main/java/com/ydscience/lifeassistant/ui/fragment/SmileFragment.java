package com.ydscience.lifeassistant.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ydscience.lifeassistant.R;
import com.ydscience.lifeassistant.utils.MyLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 * @author ydscience
 * @date 2017/6/10
 */

public class SmileFragment extends BaseFragment {
    @BindView(R.id.toolbar_smile) Toolbar mToolbarSmile;
    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgament_smile_words,null);
        unbinder = ButterKnife.bind(this, view);
        initToolBarListener();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private void initToolBarListener(){
        if (mToolbarSmile != null){
            mToolbarSmile.inflateMenu(R.menu.menu_new);
            mToolbarSmile.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.setting:
                            MyLog.print("smile设置");
                            break;
                        default:break;
                    }
                    return true;
                }
            });
        }
    }

}