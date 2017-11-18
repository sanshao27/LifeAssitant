package com.ydscience.lifeassistant.presenter;

import android.content.Context;

import com.ydscience.lifeassistant.bean.zhihu.ZhiHuStory;
import com.ydscience.lifeassistant.interfaces.OnFinishUrlLoadListener;
import com.ydscience.lifeassistant.model.IZhiHuLoadUrlModel;
import com.ydscience.lifeassistant.model.ZhiHuLoadUrlModelImpl;
import com.ydscience.lifeassistant.ui.interfaces.IZhiHuLoadUrlView;

/**
 * Created by ydscience on 2017/7/8.
 */
public class ZhiHuLoadUrlPresenterImpl implements IZhiHuLoadUrPresenter,OnFinishUrlLoadListener{
    private Context mContext;
    private IZhiHuLoadUrlView mIZhiHuLoadUrlView;
    private IZhiHuLoadUrlModel mIZhiHuLoadUrlModel;

    public ZhiHuLoadUrlPresenterImpl(Context context, IZhiHuLoadUrlView IZhiHuLoadUrlView) {
        mContext = context;
        mIZhiHuLoadUrlView = IZhiHuLoadUrlView;
        mIZhiHuLoadUrlModel = new ZhiHuLoadUrlModelImpl();
    }

    @Override
    public void getUrl(String id, Context context) {
        mIZhiHuLoadUrlModel.getUrl(id,this,context);
    }

    @Override
    public void loadSuccess(ZhiHuStory zhiHuStory) {
        mIZhiHuLoadUrlView.loadSuccess(zhiHuStory);
    }

    @Override
    public void loadFailed(String errorInfo) {
        mIZhiHuLoadUrlView.loadFailed(errorInfo);
    }
}
