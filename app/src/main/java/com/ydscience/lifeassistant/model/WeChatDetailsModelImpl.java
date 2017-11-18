package com.ydscience.lifeassistant.model;


import android.content.Context;
import android.content.IntentFilter;

import com.ydscience.lifeassistant.R;
import com.ydscience.lifeassistant.api.wechat.WeChatRequest;
import com.ydscience.lifeassistant.bean.news.NewDetailes;
import com.ydscience.lifeassistant.bean.wechat.WeChatNews;
import com.ydscience.lifeassistant.bean.wechat.WeChatState;
import com.ydscience.lifeassistant.interfaces.OnFinishNewInfoListener;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ydscience on 2017/7/6.
 */

public class WeChatDetailsModelImpl implements IWeChatDetailsModel {

    @Override
    public void getWeChatInfo(int page, final OnFinishNewInfoListener listener, final Context context) {
        WeChatRequest.getWeChartApi().getWeChat(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeChatState>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull WeChatState weChatState) {
                        ArrayList<NewDetailes> lists = new ArrayList<NewDetailes>();
                        if (weChatState.getCode() == 200){
                                for(WeChatNews news:weChatState.getNewslist()){
                                    NewDetailes newDetailes = new NewDetailes();
                                    newDetailes.setTitle(news.getTitle());
                                    newDetailes.setAuthour_name(news.getDescription());
                                    newDetailes.setDate(news.getCtime());
                                    newDetailes.setThumbnail_pic_s(news.getPicUrl());
                                    newDetailes.setUrl(news.getUrl());
                                    lists.add(newDetailes);
                                }
                                listener.loadSuccess(lists);
                        }else {
                            listener.loadFailed(context.getString(R.string.ask_failed));
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
