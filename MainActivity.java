package com.quasartec.apis;

import android.graphics.Point;
import android.opengl.GLSurfaceView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.quasartec.apis.ABM.World;
import com.quasartec.apis.openGL.GLRenderer;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    static public WeakReference<MainActivity> mn;
    public static Point size;
    static ConstraintSet set;
    ConstraintLayout game;
    protected GLRenderer renderer;
    public GLSurfaceView glSurfaceView;
    private boolean rendererSet;

    World world;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

  world =new World();
world.init();

        /*
         PrimeRun p = new PrimeRun(143);
     new Thread(p).start();
         */
    }

    public void onPause(){
        super.onPause();
world.pause();

    }

    public void onResume(){
        super.onResume();
        world.resume();

    }
}
