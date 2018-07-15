package com.magicalrice.adolph.module_game.scyscraper;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.magicalrice.adolph.common.base.BaseActivity;
import com.magicalrice.adolph.module_game.R;
import com.magicalrice.adolph.module_game.scyscraper.gameview.AboutSurfaceView;
import com.magicalrice.adolph.module_game.scyscraper.gameview.FailSurfaceView;
import com.magicalrice.adolph.module_game.scyscraper.gameview.HelpSurfaceView;
import com.magicalrice.adolph.module_game.scyscraper.gameview.LoadSurfaceView;
import com.magicalrice.adolph.module_game.scyscraper.gameview.MySurfaceView;
import com.magicalrice.adolph.module_game.scyscraper.gameview.SoundSurfaceView;
import com.magicalrice.adolph.module_game.scyscraper.gameview.StartSurfaceView;
import com.magicalrice.adolph.module_game.scyscraper.gameview.WinSurfaceView;

import java.util.HashMap;

public class ScyScraperActivity extends BaseActivity{
    SoundSurfaceView soundSv;       //声音界面
    StartSurfaceView startSv;       //开始菜单化界面
    LoadSurfaceView loadSv;         //加载界面
    HelpSurfaceView helpSv;         //帮助界面
    AboutSurfaceView aboutSv;       //关于界面
    MySurfaceView mySv;             //游戏界面
    WinSurfaceView winSv;           //胜利界面
    FailSurfaceView failSv;         //失败界面

    static final int START_GAME = 0;    //加载并开始游戏的Message编号
    Handler handler;
    MediaPlayer mpBack;             //游戏背景音乐播放器
    MediaPlayer mpWin;              //游戏获胜音乐播放器
    MediaPlayer mpFail;             //游戏失败音乐播放器
    SoundPool soundPool;            //音乐池
    HashMap<Integer,Integer> soundPoolMap;  //声音池中声音ID与自定义声音ID的Map
    boolean isSound = false;        //是否打开声音标记

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置允许竖屏
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        soundSv = new SoundSurfaceView(this);
        startSv = new StartSurfaceView(this);
        mySv = new MySurfaceView(this);
        helpSv = new HelpSurfaceView(this);
        aboutSv = new AboutSurfaceView(this);
        loadSv = new LoadSurfaceView(this);
        winSv = new WinSurfaceView(this);
        failSv = new FailSurfaceView(this);

        initSound();
        setSoundView();
    }

    @Override
    protected int getContentViewId() {
        return 0;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected String getDemoName() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mySv != null) {
            mySv.onResume();
            if (isSound) {
                if (mySv.isWin) {
                    mpWin.start();
                } else {
                    mpBack.start();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mySv != null) {
            mySv.onPause();
        }
        if (mpBack != null) {
            mpBack.pause();
        }
        if (mpWin != null ){
            mpWin.pause();
        }
        if (mpFail != null){
            mpFail.pause();
        }
    }

    private void initSound() {
        mpBack = MediaPlayer.create(this, R.raw.background);
        mpBack.setLooping(true);

        mpWin = MediaPlayer.create(this,R.raw.win);
        mpWin.setLooping(true);

        mpFail = MediaPlayer.create(this,R.raw.fail);
        mpFail.setLooping(true);

        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC,100);
        soundPoolMap = new HashMap<>();

        soundPoolMap.put(1,soundPool.load(this,R.raw.success,1));
        soundPoolMap.put(2,soundPool.load(this,R.raw.fail,1));
    }

    public void playSound(int sound,int loop) {
        AudioManager mgr = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = streamVolumeCurrent / streamVolumeMax;

        soundPool.play(soundPoolMap.get(sound),volume,volume,1,loop,1f);
    }

    public void setMySurfaceView() {
        mySv.requestFocus();
        mySv.setFocusableInTouchMode(true);
        setContentView(mySv);
    }

    public void setSoundView() {
        soundSv.requestFocus();     //获取焦点
        soundSv.setFocusableInTouchMode(true);      //设置为可触控的
        setContentView(mySv);       //切换到游戏界面
    }

    public void setMenuView() {
        startSv.requestFocus();
        startSv.setFocusableInTouchMode(true);
        setContentView(startSv);
    }

    public void setLoadView() {
        setContentView(loadSv);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case START_GAME:
                        setMySurfaceView();
                        break;
                }
            }
        };

        new Thread(()->{
           try {
               Thread.sleep(1500);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           handler.sendEmptyMessage(START_GAME);
        }).start();
    }

    public void setAboutView() {
        aboutSv.requestFocus();
        aboutSv.setFocusableInTouchMode(true);
        setContentView(aboutSv);
    }

    public void setHelpView() {
        helpSv.requestFocus();
        helpSv.setFocusableInTouchMode(true);
        setContentView(helpSv);
    }

    public void setWinView() {
        winSv.requestFocus();
        winSv.setFocusableInTouchMode(true);
        setContentView(winSv);
    }

    public void setFailVIew() {
        failSv.requestFocus();
        failSv.setFocusableInTouchMode(true);
        setContentView(failSv);
    }
}
