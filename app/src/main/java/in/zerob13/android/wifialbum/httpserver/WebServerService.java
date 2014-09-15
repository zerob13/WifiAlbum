package in.zerob13.android.wifialbum.httpserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by zerob13 on 9/15/14.
 */
public class WebServerService extends Service {
    private WebServer server = null;

    @Override
    public void onCreate() {
        Log.i("HTTPSERVICE", "Creating and starting httpService");
        super.onCreate();
        server = new WebServer(this);
        server.startServer();
    }

    @Override
    public void onDestroy() {
        Log.i("HTTPSERVICE", "Destroying httpService");
        server.stopServer();
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
