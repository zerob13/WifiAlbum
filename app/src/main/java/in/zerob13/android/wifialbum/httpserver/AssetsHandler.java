package in.zerob13.android.wifialbum.httpserver;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.webkit.MimeTypeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.entity.FileEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import in.zerob13.android.wifialbum.utils.Utils;

/**
 * Created by zerob13 on 14-9-16.
 */
public class AssetsHandler implements HttpRequestHandler {
    private Context context = null;

    public AssetsHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handle(final HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        Map<String, String> parames = Utils.getParametersFromUrl(httpRequest.getRequestLine().getUri());
        String Uri=parames.get("path");
        String type = "text/plain";
        String extension = MimeTypeMap.getFileExtensionFromUrl("file:///android_asset/" + Uri);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        HttpEntity entity = new EntityTemplate(new ContentProducer() {
            public void writeTo(final OutputStream outstream) throws IOException {
                OutputStreamWriter writer = new OutputStreamWriter(outstream, "UTF-8");

                Map<String, String> parames = Utils.getParametersFromUrl(httpRequest.getRequestLine().getUri());
                String Uri=parames.get("path");
                Log.e("zerob13 test", "file:///android_asset/" + Uri);
                AssetManager manager= context.getAssets();

                InputStream sr =manager.open(Uri);
                String theString = Utils.convertStreamToString(sr);

                writer.write(theString);
                writer.flush();
            }
        });
        httpResponse.setHeader("Content-Type",type);
        httpResponse.setEntity(entity);
    }

}
