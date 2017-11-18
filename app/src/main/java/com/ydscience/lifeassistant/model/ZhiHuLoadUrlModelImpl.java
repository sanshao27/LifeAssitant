package com.ydscience.lifeassistant.model;

import android.content.Context;
import android.text.TextUtils;

import com.ydscience.lifeassistant.R;
import com.ydscience.lifeassistant.api.zhihu.ZhiHuRequest;
import com.ydscience.lifeassistant.bean.zhihu.ZhiHuStory;
import com.ydscience.lifeassistant.interfaces.OnFinishUrlLoadListener;
import com.ydscience.lifeassistant.utils.MyLog;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ydscience on 2017/7/8.
 */

public class ZhiHuLoadUrlModelImpl implements IZhiHuLoadUrlModel {
    @Override
    public void getUrl(String id, final OnFinishUrlLoadListener listener , final Context context) {
        ZhiHuRequest.getZhiHuInfoApi().getZhihuStory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhiHuStory>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ZhiHuStory zhiHuStory) {
                        MyLog.print("url"+zhiHuStory.getShareUrl());
                        if (TextUtils.isEmpty(zhiHuStory.getBody()) &&
                                TextUtils.isEmpty(zhiHuStory.getShareUrl())) {
                            listener.loadFailed(context.getString(R.string.ask_failed));
                        }else {
                            listener.loadSuccess(zhiHuStory);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        listener.loadFailed(context.getString(R.string.ask_failed));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
