package com.opengl.youyang.opengltest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.opengl.youyang.opengltest.render.FirstRender;
import com.opengl.youyang.opengltest.view.Myglsurfaceview;

public class MainActivity extends Activity{
    Myglsurfaceview view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean supportES2 = isSupportES2();
        setContentView(R.layout.activity_main);
        view = (Myglsurfaceview) findViewById(R.id.test);

        findViewById(R.id.jumptexture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setClass(MainActivity.this,TextureActivity.class);
                startActivity(intent);

            }
        });
        if (supportES2) {
            view.setEGLContextClientVersion(2);
            view.setRenderer(new FirstRender(this,view));
        } else {
            Toast.makeText(this, "不支持OpenGL ES2.0", Toast.LENGTH_SHORT).show();
            return;
        }
        view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 先测试是否为模拟器，如果是的话就假设其能运行OpenGL。不是模拟器的话就判断其是否支持OpenGL ES2.0
     *
     * @return
     */
    private boolean isSupportES2() {
        final ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = manager.getDeviceConfigurationInfo();
        return configurationInfo.reqGlEsVersion >= 0x20000
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic"))
                || (Build.FINGERPRINT.startsWith("unknow"))
                || (Build.MODEL.contains("google_sdk"))
                || (Build.MODEL.contains("Emulator"))
                || (Build.MODEL.contains("Android SDK built for x86"))
        );
    }

    @Override
    protected void onPause() {
        super.onPause();

        view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        view.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();
    }

}
