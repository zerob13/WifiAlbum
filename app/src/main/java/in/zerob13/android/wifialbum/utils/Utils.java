package in.zerob13.android.wifialbum.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.URLUtil;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zerob13 on 14-9-16.
 */
public class Utils {
    private Utils() {
    }

    /**
     * get Ip Address
     *
     * @return String ip Address
     */
    public static String getIpString() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                    .hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                        .hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()&&!inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return "";
    }

//    public static ArrayList<String> getAllPics(Context aContext){
//        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_ID,
//                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
//
//        Cursor cursor = aContext.getContentResolver().query(uri, projection, null, null, null);
//
//        ArrayList<String> ids = new ArrayList<String>();
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//
//                int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
//
//
//                    columnIndex = cursor.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
//                MediaStore.Images.Thumbnails.get
//
//                }
//            }
//            cursor.close();
//
//    }

    public static final String CAMERA_IMAGE_BUCKET_NAME =
            Environment.getExternalStorageDirectory().toString()
                    + "/DCIM/Camera";
    public static final String CAMERA_IMAGE_BUCKET_ID =
            getBucketId(CAMERA_IMAGE_BUCKET_NAME);

    /**
     * Matches code in MediaProvider.computeBucketValues. Should be a common
     * function.
     */
    public static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }

    public static List<String> getCameraImages(Context context) {
        final String[] projection = {MediaStore.Images.Media.DATA};
        final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        final String[] selectionArgs = {CAMERA_IMAGE_BUCKET_ID};
        final Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
        ArrayList<String> result = new ArrayList<String>(cursor.getCount());
        if (cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            do {
                final String data = cursor.getString(dataColumn);
                result.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public static Map<String, String> getParametersFromUrl(String url) {
        Map<String, String> map = new HashMap<String, String>();

        if (url != null) {
            String[] params = url.split("[&,?]");
            for (String param : params) {
                try {
                    String name = param.split("=")[0];
                    String value = param.split("=")[1];
                    map.put(name, value);
                } catch (Exception e) {
                    Log.d("Error", "No value for parameter");
                }
            }
        }
        return map;
    }

}
