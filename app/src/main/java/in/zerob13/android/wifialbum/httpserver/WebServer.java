package in.zerob13.android.wifialbum.httpserver;

import android.content.Context;

import org.apache.http.HttpException;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.DefaultHttpServerConnection;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpRequestHandlerRegistry;
import org.apache.http.protocol.HttpService;
import org.apache.http.protocol.ResponseConnControl;
import org.apache.http.protocol.ResponseContent;
import org.apache.http.protocol.ResponseDate;
import org.apache.http.protocol.ResponseServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by zerob13 on 9/15/14.
 */
public class WebServer {
    public static boolean RUNNING = false;
    public static int serverPort = 8888;

    private static final String ALL_PATTERN = "/";
    private static final String HOME_PATTERN = "/home.html";
    private static final String FILE_PATTERN= "/getImg";
    private static final String ASSETS_PATTERN= "/getAsset";

    private Context context = null;

    private BasicHttpProcessor httpproc = null;
    private BasicHttpContext httpContext = null;
    private HttpService httpService = null;
    private HttpRequestHandlerRegistry registry = null;

    private ServerSocket serverSocket;

    public WebServer(Context context) {
        this.setContext(context);
        httpproc = new BasicHttpProcessor();
        httpContext = new BasicHttpContext();

        httpproc.addInterceptor(new ResponseDate());
        httpproc.addInterceptor(new ResponseServer());
        httpproc.addInterceptor(new ResponseContent());
        httpproc.addInterceptor(new ResponseConnControl());

        httpService = new HttpService(httpproc,
                new DefaultConnectionReuseStrategy(), new DefaultHttpResponseFactory());

        registry = new HttpRequestHandlerRegistry();

        registry.register(FILE_PATTERN, new FileHandler(context));
        registry.register(ASSETS_PATTERN, new AssetsHandler(context));
        registry.register(ALL_PATTERN, new HomeCommandHandler(context));


        httpService.setHandlerResolver(registry);
    }

    public void runServer() {
        try {
            serverSocket = new ServerSocket(serverPort);

            serverSocket.setReuseAddress(true);

            while (RUNNING) {
                try {
                    final Socket socket = serverSocket.accept();

                    DefaultHttpServerConnection serverConnection = new DefaultHttpServerConnection();

                    serverConnection.bind(socket, new BasicHttpParams());

                    httpService.handleRequest(serverConnection, httpContext);

                    serverConnection.shutdown();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (HttpException e) {
                    e.printStackTrace();
                }
            }

            serverSocket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RUNNING = false;
    }

    public synchronized void startServer() {
        RUNNING = true;
        runServer();
    }

    public synchronized void stopServer() {
        RUNNING = false;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

}
