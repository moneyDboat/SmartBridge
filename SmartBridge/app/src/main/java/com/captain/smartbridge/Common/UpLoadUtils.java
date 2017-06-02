package com.captain.smartbridge.Common;

/**
 * Created by fish on 17-6-2.
 * upload picture to qonou
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import java.io.ByteArrayOutputStream;


//使用见Superman MainActivity 418行
public class UpLoadUtils {
    private static class UploadManagerHolder {
        static Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
//                .recorder(recorder)  // recorder分片上传时，已上传片记录器。默认null
//                .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
//                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        private static UploadManager uploadManager = new UploadManager(config);
    }

    public static void upLoadImage(String path, String key, String token, UpCompletionHandler completionHandler, UploadOptions options) {
        byte[] data = compressImage(path);
        getInstance().put(data, key, token, completionHandler, options);
    }

    private UpLoadUtils() {
    }

    public static final UploadManager getInstance() {
        return UploadManagerHolder.uploadManager;
    }

    private static byte[] compressImage(String imagePath) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        //设置为true获取图片的初始大小
        opts.inJustDecodeBounds = true;
        Bitmap image = BitmapFactory.decodeFile(imagePath, opts);
        int imageHeight = opts.outHeight;
        int imageWidth = opts.outWidth;

        opts.inJustDecodeBounds = false;

        //控制图片高宽中较低的一个在500像素左右
        if (Math.min(imageHeight, imageWidth) > 500) {
            float ratio = Math.max(imageHeight, imageWidth) / 500;
            opts.inSampleSize = Math.round(ratio);
        }
        Bitmap finalImage = BitmapFactory.decodeFile(imagePath, opts);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //降低画质
        finalImage.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
}
