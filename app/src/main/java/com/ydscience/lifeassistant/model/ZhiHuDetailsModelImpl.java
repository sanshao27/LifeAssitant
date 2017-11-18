package com.ydscience.lifeassistant.model;

import android.content.Context;
import android.text.TextUtils;

import com.ydscience.lifeassistant.R;
import com.ydscience.lifeassistant.api.zhihu.ZhiHuRequest;
import com.ydscience.lifeassistant.bean.news.NewDetailes;
import com.ydscience.lifeassistant.bean.zhihu.ZhiHuDaily;
import com.ydscience.lifeassistant.bean.zhihu.ZhiHuDailyItem;
import com.ydscience.lifeassistant.interfaces.OnFinishNewInfoListener;
import com.ydscience.lifeassistant.utils.MyLog;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ydscience on 2017/7/6.
 */

public class ZhiHuDetailsModelImpl implements IZhiHuDetailsModel {

    @Override
    public void getLatestNews(final OnFinishNewInfoListener listener, final Context context) {
        ZhiHuRequest.getZhiHuInfoApi().getLastDaily()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhiHuDaily>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onNext(@NonNull ZhiHuDaily zhiHuDaily) {
                        MyLog.print("最新消息的时间为"+zhiHuDaily.getDate()+" "+ zhiHuDaily.getStories().get(0).getTitle());
                        parseData(zhiHuDaily,context,listener);
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

    @Override
    public void getOrderByDateNews(String date, final OnFinishNewInfoListener listener, final Context context) {
        ZhiHuRequest.getZhiHuInfoApi().getTheDaily(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhiHuDaily>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ZhiHuDaily zhiHuDaily) {
                        MyLog.print("根据时间加载消息的时间为"+zhiHuDaily.getDate());
                        parseData(zhiHuDaily,context,listener);
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

    private void parseData(ZhiHuDaily zhiHuDaily,Context context,OnFinishNewInfoListener listener){
        ArrayList<NewDetailes> lists = new ArrayList<NewDetailes>();
        if (!TextUtils.isEmpty(zhiHuDaily.getDate())){
            for (ZhiHuDailyItem item : zhiHuDaily.getStories()){
                NewDetailes newDetailes = new NewDetailes();
                newDetailes.setDate(zhiHuDaily.getDate());
                newDetailes.setAuthour_name("知乎网");
                newDetailes.setTitle(item.getTitle());
                if (item.getImages().length > 0){
                    String[] images = item.getImages();
                    newDetailes.setThumbnail_pic_s(images[0]);
                }
                newDetailes.setUrl(item.getId());//用于标识知乎
                lists.add(newDetailes);
                listener.loadSuccess(lists);
            }
        }else {
            listener.loadFailed(context.getString(R.string.ask_failed));
        }
    }
}
