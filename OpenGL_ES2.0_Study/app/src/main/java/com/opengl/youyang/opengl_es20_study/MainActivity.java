package com.opengl.youyang.opengl_es20_study;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends Activity {

    private GLSurfaceView glSurfaceView;
    private boolean renderSet = false;


    @TargetApi(Build.VERSION_CODES.FROYO)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //第一步就是检验手机是否支持OpenGL ES2.0
        glSurfaceView = new GLSurfaceView(this);
        final boolean isSuppotES2=isSupportES2();
        if(isSuppotES2){
            glSurfaceView.setEGLContextClientVersion(2);
            glSurfaceView.setRenderer(new FirstRender(this));//设置渲染器
            renderSet=true;
        }else{
            Toast.makeText(this,"该手机不支持OpenGL ES 2.0",Toast.LENGTH_LONG).show();
            return;
        }
        //第二步 处理与OpenGL相关的生命周期的方法


        setContentView(glSurfaceView);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isSupportES2() {
        return ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion >= 0x20000;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(renderSet){
            glSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(renderSet){
            glSurfaceView.onPause();
        }
    }
}
