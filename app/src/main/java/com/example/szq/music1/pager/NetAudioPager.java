package com.example.szq.music1.pager;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.szq.music1.R;
import com.example.szq.music1.adapter.NetAudioPagerAdapter;
import com.example.szq.music1.base.BasePager;
import com.example.szq.music1.domain.NetAudioPagerData;
import com.example.szq.music1.utils.CacheUtils;
import com.example.szq.music1.utils.Constants;
import com.example.szq.music1.utils.LogUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by szq on 18-9-20.
 * 作用:网络音乐页面
 */

public class NetAudioPager extends BasePager{


    @ViewInject(R.id.listview)
    private ListView mListView;


    @ViewInject(R.id.tv_nonet)
    private TextView tv_nonet;


    @ViewInject(R.id.pb_loading)
    private ProgressBar pb_loading;
    /**
     * 页面的数据
     */
    private List<NetAudioPagerData.ListEntity> datas;

    private NetAudioPagerAdapter adapter;


    public NetAudioPager(Context context) {
        super(context);
    }

    /**
     * 初始化当前页面的控件，由父类调用
     * @return
     */
    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.netaudio_pager,null);
        x.view().inject(NetAudioPager.this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("网络音乐页面的数据被初始化了");
        String savaJson = CacheUtils.getString(context,Constants.ALL_RES_URL);
        if(!TextUtils.isEmpty(savaJson)){
            //解析数据
            processData(savaJson);
        }
        //联网
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.ALL_RES_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求数据成功=="+result);
                //保持数据
                CacheUtils.putString(context,Constants.ALL_RES_URL,result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("请求数据失败=="+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

                LogUtil.e("onCancelled=="+cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished==");
            }
        });
    }

    /**
     * 解析json数据和显示数据
     * 解析数据：１.GsonFomat生成bean对象；２.用gson解析数据
     * @param json
     */
    private void processData(String json) {
        //解析数据
        NetAudioPagerData data = parsedJson(json);
        datas =  data.getList();

        if(datas !=null && datas.size() >0 ){
            //有数据
            tv_nonet.setVisibility(View.GONE);
            //设置适配器
            adapter = new NetAudioPagerAdapter(context,datas);
            mListView.setAdapter(adapter);
        }else{
            tv_nonet.setText("没有对应的数据...");
            //没有数据
            tv_nonet.setVisibility(View.VISIBLE);
        }

        pb_loading.setVisibility(View.GONE);

    }

    /**
     * Gson解析数据
     * @param json
     * @return
     */
    private NetAudioPagerData parsedJson(String json) {
        return new Gson().fromJson(json,NetAudioPagerData.class);
    }

}
