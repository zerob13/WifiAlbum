package in.zerob13.android.wifialbum.httpserver;

import android.content.Context;
import android.content.res.AssetManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import in.zerob13.android.wifialbum.utils.Utils;

/**
 * Created by zerob13 on 9/15/14.
 */
public class HomeCommandHandler implements HttpRequestHandler {
    private Context context = null;

    public HomeCommandHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response,
                       HttpContext httpContext) throws HttpException, IOException {
        HttpEntity entity = new EntityTemplate(new ContentProducer() {
            public void writeTo(final OutputStream outstream) throws IOException {
                OutputStreamWriter writer = new OutputStreamWriter(outstream, "UTF-8");

                AssetManager manager = context.getAssets();

                InputStream sr = manager.open("pages/index.html");
                String theString = Utils.convertStreamToString(sr);
                String resp = "";
                List<String> listImages = Utils.getCameraImages(context);
                String temple = "<div class=\"col-md-3 col-xs-3 col-sm-3 \">"
                        + "<a href=\"/getImg?img=%s\"><img class=\"img-responsive\" src=\"/getImg?img=%s\"></a></div>";
                for (int i = 0; i < listImages.size(); i++) {
                    if (i % 4 == 0) {
                        if (i != 0) {
                            resp += "</div>";
                        }

                        resp += "<div class=\"row marketing\">";
                    }
                    resp = resp + String.format(temple, listImages.get(i), listImages.get(i));

                }
                theString = String.format(theString, resp);

                writer.write(theString);
                writer.flush();
            }
        });
        response.setHeader("Content-Type", "text/html");
        response.setEntity(entity);
    }

    public Context getContext() {
        return context;
    }
}

