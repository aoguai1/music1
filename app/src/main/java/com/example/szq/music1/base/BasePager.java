package com.example.szq.music1.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by szq on 18-9-20.
 * 基类，公共类
 *
 * AudioPager
 *
 * NetAudioPager
 *
 * HomePagePager
 * 继承BasePager
 */

public abstract class BasePager {

    /**
     * 上下文
     */

    public final Context context;
    /**
     * 接收各个页面的实例
     */
    public View rootview;
    public boolean isInitData;

    public BasePager(Context context){
        this.context = context;
        rootview = initView();
    }


    /**
     * 强制子页面实现该方法，实现想要的特定的效果
     *
     * @return
     */


    public abstract View initView();


    /**
     * 当子页面需要初始化数据，联网请求数据，或者绑定数据的时候要重写该方法
     */
    public void initData(){

    }
}
