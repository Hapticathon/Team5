package com.whallalabs.testapp2;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.whallalabs.testapp2.utils.MapFactory;
import com.whallalabs.testapp2.utils.Utils;

import nxr.tpad.lib.TPad;
import nxr.tpad.lib.TPadImpl;
import nxr.tpad.lib.views.FrictionMapView;

public class MainActivity extends ActionBarActivity {

    TPad _tpad;
    FrictionMapView _frictionMapView;
    FrictionMapView _frictionMapView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _frictionMapView = (FrictionMapView) findViewById(R.id.frictionmap);
      //  Bitmap bm = Utils.drawableToBitmap(getResources().getDrawable(R.drawable.test1));
      //          _frictionMapView.setDataBitmap(bm);

        initFrictionLayout();
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


    private void initFrictionLayout(){
        _tpad = new TPadImpl(this);
        _frictionMapView.setTpad(_tpad);

        String[][] map = MapFactory.getMap(MapFactory.MapType.BEACON);

        map.getClass();

    }

    private void initTochEvents(){

    }
}
