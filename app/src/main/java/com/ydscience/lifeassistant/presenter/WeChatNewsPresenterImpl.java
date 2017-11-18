package com.ydscience.lifeassistant.presenter;

import android.content.Context;

import com.ydscience.lifeassistant.bean.news.NewDetailes;
import com.ydscience.lifeassistant.interfaces.OnFinishNewInfoListener;
import com.ydscience.lifeassistant.model.IWeChatDetailsModel;
import com.ydscience.lifeassistant.model.WeChatDetailsModelImpl;
import com.ydscience.lifeassistant.ui.interfaces.IPagerNewFragment;
import com.ydscience.lifeassistant.utils.Commons;

import java.util.ArrayList;

/**
 * Created by ydscience on 2017/7/6.
 */

public class WeChatNewsPresenterImpl implements IWeChatNewsPresenter ,OnFinishNewInfoListener{
    private IPagerNewFragment mIPagerNewFragment;
    private Context mContext;
    private IWeChatDetailsModel mWeChatDetailsModel;
    public WeChatNewsPresenterImpl(IPagerNewFragment IPagerNewFragment, Context context) {
        mIPagerNewFragment = IPagerNewFragment;
        mContext = context;
        mWeChatDetailsModel = new WeChatDetailsModelImpl();
    }

    @Override
    public void getWeChatNewsInfo(int page) {
            mWeChatDetailsModel.getWeChatInfo(page,this,mContext);
    }

    @Override
    public void loadSuccess(ArrayList<NewDetailes> newResult) {
        mIPagerNewFragment.updateNewInfo(newResult, Commons.WECHATLIST);
    }

    @Override
    public void loadFailed(String errorInfo) {
        mIPagerNewFragment.loadError(errorInfo);
    }
}
