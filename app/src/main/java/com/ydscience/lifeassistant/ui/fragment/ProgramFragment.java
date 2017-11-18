package com.ydscience.lifeassistant.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by ydscience on 2017/6/10.
 */

public class ProgramFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.toolbar_program) Toolbar mToolbarProgram;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_program_code, null);
        unbinder = ButterKnife.bind(this, view);
        initToolBarListener();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initToolBarListener() {
        if (mToolbarProgram != null) {
            mToolbarProgram.inflateMenu(R.menu.menu_new);
            mToolbarProgram.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.setting:
                            MyLog.print("program设置");
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });
        }
    }
}
