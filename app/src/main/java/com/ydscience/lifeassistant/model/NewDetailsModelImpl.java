package com.ydscience.lifeassistant.model;

import android.content.Context;

import com.ydscience.lifeassistant.R;
import com.ydscience.lifeassistant.api.news.CommonNewRequest;
import com.ydscience.lifeassistant.bean.news.NewDetailes;
import com.ydscience.lifeassistant.bean.news.NewResult;
import com.ydscience.lifeassistant.interfaces.OnFinishNewInfoListener;
import com.ydscience.lifeassistant.utils.Commons;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ydscience on 2017/6/28.
 */

public class NewDetailsModelImpl implements INewDetailsModel {
    @Override
    public void getNewDetails(final Context context, final String newType, final OnFinishNewInfoListener listener) {
        CommonNewRequest.getNewApi().getNewInfo(newType, Commons.NEW_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull NewResult newResult) {
                        if (newResult.getReason().equals(context.getString(R.string.ask_result))){
                           listener.loadSuccess(newResult.getResult().getData());
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
