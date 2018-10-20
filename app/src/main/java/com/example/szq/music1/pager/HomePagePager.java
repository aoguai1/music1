package com.example.szq.music1.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.szq.music1.R;
import com.example.szq.music1.base.BasePager;
import com.example.szq.music1.utils.LogUtil;

/**
 * Created by szq on 18-9-20.
 * 作用:个人主页页面
 */

public class HomePagePager extends BasePager{


    public HomePagePager(Context context) {
        super(context);
    }

    /**
     * 初始化当前页面的控件，由父类调用
     * @return
     */
    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.home_pager,null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("个人主页页面的数据被初始化了");
        //联网
        //个人主页内容0

    }


}
