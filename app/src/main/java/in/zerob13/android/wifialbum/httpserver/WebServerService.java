package in.zerob13.android.wifialbum.httpserver;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by zerob13 on 9/15/14.
 */
public class WebServerService extends Service {
    private WebServer server = null;
    private WebServerTask mServerTask = null;

    @Override
    public void onCreate() {
        Log.i("HTTPSERVICE", "Creating and starting httpService");
        super.onCreate();
        mServerTask = new WebServerTask(this);
        mServerTask.execute();


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


    private class WebServerTask extends AsyncTask {

        private Context mContext;
        public WebServerTask(Context aContext){
            mContext=aContext;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            server = new WebServer(mContext);
            server.startServer();
            return null;
        }


    }
}
