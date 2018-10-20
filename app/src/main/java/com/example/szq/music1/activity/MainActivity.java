package com.example.szq.music1.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.szq.music1.R;
import com.example.szq.music1.base.BasePager;
import com.example.szq.music1.pager.AudioPager;
import com.example.szq.music1.pager.HomePagePager;
import com.example.szq.music1.pager.NetAudioPager;
import com.example.szq.music1.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by szq on 18-9-20.
 * 作用:主页面
 */

public class MainActivity extends FragmentActivity{


    private RadioGroup rg_bottom_tag;

    /**
     * 页面的集合
     */
    private ArrayList<BasePager> basePagers;

    /**
     * 选中的位置
     */
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rg_bottom_tag = (RadioGroup) findViewById(R.id.rg_bottom_tag);

        basePagers = new ArrayList<>();
        basePagers.add(new AudioPager(this));//添加本地音乐页面-0
        basePagers.add(new NetAudioPager(this));//添加网络音乐页面-1
        basePagers.add(new HomePagePager(this));//添加个人主页页面-2


        //设置RadioGroup的监听
        rg_bottom_tag.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

        rg_bottom_tag.check(R.id.rb_audio);//默认选中首页

    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId){
                default:
                    position = 0;
                    break;
                case R.id.rb_net_audio://网络音乐
                    position = 1;
                    break;
                case R.id.rb_home_page://个人主页
                    position = 2;
                    break;
            }

            setFragment();


        }
    }

    /**
     * 把页面添加到Fragment中
     */

    private void setFragment() {
        //1.得到FragmentManger 导入ｖ４的包
        FragmentManager manager = getSupportFragmentManager();
        // 2.开启事务
        FragmentTransaction ft = manager.beginTransaction();
        // 3.替换
        MyFragment myFragment = MyFragment.newInstance(getBasePager());
        ft.replace(R.id.fl_main_content,myFragment);
        // ４．提交事务
        ft.commit();

    }




    public static class MyFragment extends Fragment {
        private static BasePager basePager;

        public MyFragment(){

        }

        public static final MyFragment newInstance(BasePager page) {
            MyFragment fragment = new MyFragment();
            Bundle bundle = new Bundle();
            basePager = page;
            fragment.setArguments(bundle);
            return fragment ;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            if(basePager != null){
                //各个页面的视图
                return basePager.rootview;
            }
            return null;
        }
    }


    private BasePager getBasePager(){
        LogUtil.e("position xiao == "+position);
        BasePager basePager = basePagers.get(position);
        //!basepager.isInitData是判断BasePager是否已经存在位置
        //如果存在就不需要重新获取数据，优化了程序
        //isInitData是boolean变量，初始默认值时false
        if(basePager !=null && !basePager.isInitData){
            //请求数据或者绑定互联网
            basePager.initData();
            basePager.isInitData = true;
        }else{
         LogUtil.e("basePager == null !!! ");
        }
        return basePager;
    }

    /**
     * 是否已经退出
     */
    private boolean isExit = false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode ==KeyEvent.KEYCODE_BACK){
            if(position != 0){//不是第一页面
                position = 0;
                rg_bottom_tag.check(R.id.rb_audio);//首页
                return true;
            }else  if(!isExit){
                isExit = true;
                Toast.makeText(MainActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                },2000);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
