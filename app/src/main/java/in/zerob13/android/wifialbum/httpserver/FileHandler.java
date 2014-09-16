package in.zerob13.android.wifialbum.httpserver;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.webkit.MimeTypeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.FileEntity;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import in.zerob13.android.wifialbum.utils.Utils;

/**
 * Created by zerob13 on 14-9-16.
 */
public class FileHandler implements HttpRequestHandler {

    private Context context = null;

    public FileHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
//        HttpParams params = httpRequest.getParams();
//        httpResponse
        Map<String, String> parames = Utils.getParametersFromUrl(httpRequest.getRequestLine().getUri());
        Log.e("zerob13 test", String.valueOf(parames.size()));
        Log.e("zerob13 test", parames.get("img"));
        File imgFile = new File(parames.get("img"));
        String filePath = parames.get("img");
        String type="image/jpeg";
        String extension = MimeTypeMap.getFileExtensionFromUrl(filePath);
        if (extension != null){
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }

        HttpEntity entity = new FileEntity(imgFile,type) {

        };
//        response.setHeader("Content-Type", "text/html");
        httpResponse.setEntity(entity);
    }
}
