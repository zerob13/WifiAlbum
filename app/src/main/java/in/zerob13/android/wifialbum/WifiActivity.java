package in.zerob13.android.wifialbum;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.shamanland.fab.FloatingActionButton;

import in.zerob13.android.wifialbum.httpserver.WebServerService;


public class WifiActivity extends Activity {
    TextView text;
    Intent ser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        text = (TextView) findViewById(R.id.text1);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setSize(FloatingActionButton.SIZE_MINI);
        fab.setColor(Color.RED);
        fab.initBackground();
        fab.setImageResource(R.drawable.dot);
        ser = new Intent(this, WebServerService.class);
//        startService(ser);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText("test");
                getApplicationContext().startService(ser);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wifi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
