package com.ydscience.lifeassistant.presenter;

import android.content.Context;

import com.ydscience.lifeassistant.bean.news.NewDetailes;
import com.ydscience.lifeassistant.interfaces.OnFinishNewInfoListener;
import com.ydscience.lifeassistant.model.IZhiHuDetailsModel;
import com.ydscience.lifeassistant.model.ZhiHuDetailsModelImpl;
import com.ydscience.lifeassistant.ui.interfaces.IPagerNewFragment;
import com.ydscience.lifeassistant.utils.Commons;

import java.util.ArrayList;

/**
 * Created by ydscience on 2017/7/6.
 */

public class ZhiHuInfoPresenterImpl implements IZhiHuInfoPresenter,OnFinishNewInfoListener {
    private IPagerNewFragment mIPagerNewFragment;
    private Context mContext;
    private IZhiHuDetailsModel mZhiHuDetailsModel;

    public ZhiHuInfoPresenterImpl(IPagerNewFragment IPagerNewFragment, Context context) {
        mIPagerNewFragment = IPagerNewFragment;
        mContext = context;
        mZhiHuDetailsModel = new ZhiHuDetailsModelImpl();
    }

    @Override
    public void getLatestZhiHuNews() {
        mZhiHuDetailsModel.getLatestNews(this,mContext);
    }

    @Override
    public void getOrderByTimeZhiHuNews(String date) {
        mZhiHuDetailsModel.getOrderByDateNews(date,this,mContext);
    }

    @Override
    public void loadSuccess(ArrayList<NewDetailes> newResult) {
        mIPagerNewFragment.updateNewInfo(newResult, Commons.ZHIHULIST);
    }

    @Override
    public void loadFailed(String errorInfo) {
        mIPagerNewFragment.loadError(errorInfo);
    }
}
