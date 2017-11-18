package com.ydscience.lifeassistant.presenter;

import android.content.Context;

import com.ydscience.lifeassistant.bean.news.NewDetailes;
import com.ydscience.lifeassistant.interfaces.OnFinishNewInfoListener;
import com.ydscience.lifeassistant.model.NewDetailsModelImpl;
import com.ydscience.lifeassistant.ui.interfaces.IPagerNewFragment;
import com.ydscience.lifeassistant.utils.Commons;

import java.util.ArrayList;

/**
 * Created by ydscience on 2017/6/28.
 */

public class NewDetailsPersenterImpl implements INewDetailsPresenter,OnFinishNewInfoListener {
    private IPagerNewFragment mIPagerNewFragment;
    private Context mContext;
    private NewDetailsModelImpl mINewDetailsModel;
    String acquitreInfoType;
    public NewDetailsPersenterImpl(IPagerNewFragment fragment, Context context) {
        mIPagerNewFragment = fragment;
        mContext = context;
        mINewDetailsModel = new NewDetailsModelImpl();
    }

    @Override
    public void getNewDetails(String newType) {
        acquitreInfoType = newType;
        mINewDetailsModel.getNewDetails(mContext,newType,this);
    }

    @Override
    public void loadSuccess(ArrayList<NewDetailes> newResult) {
        if (acquitreInfoType.equals(Commons.guonei)){
            mIPagerNewFragment.updateNewInfo(newResult,Commons.GUONEINEWLIST);
        }else if(acquitreInfoType.equals(Commons.guoji)){
            mIPagerNewFragment.updateNewInfo(newResult,Commons.GUOJINEWLIST);
        }else if(acquitreInfoType.equals(Commons.junshi)){
            mIPagerNewFragment.updateNewInfo(newResult,Commons.ARYNEWLIST);
        }else if(acquitreInfoType.equals(Commons.keji)){
            mIPagerNewFragment.updateNewInfo(newResult,Commons.TECHNOLOGYNEWLIST);
        }
    }

    @Override
    public void loadFailed(String errorInfo) {
        mIPagerNewFragment.loadError(errorInfo);
    }
}
