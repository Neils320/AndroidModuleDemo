package com.wss.common.net;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.tamic.novate.Novate;
import com.tamic.novate.callback.ResponseCallback;

import java.io.File;
import java.util.List;

/**
 * Describe：网络请求帮助类
 * Created by 吴天强 on 2017/9/19.
 */

public class HttpUtils {
    public static final String TAG = HttpUtils.class.getSimpleName();

    private static final int REQUEST_GET = 0;
    private static final int REQUEST_POST = 1;
    private static final int REQUEST_JSON = 2;
    private Novate.Builder builder;
    private static HttpUtils httpUtils;

    private HttpUtils(Context context) {
        builder = new Novate.Builder(context);
        builder.baseUrl(NetConfig.Url.getBaseUrl());
    }

    public static synchronized HttpUtils create(Context context) {
        if (httpUtils == null) {
            httpUtils = new HttpUtils(context);
        }
        return httpUtils;
    }


    ////////////////////////////////////////// GET请求 /////////////////////////////////////////////

    /**
     * Get无参无Tag
     *
     * @param url      请求地址
     * @param callback 回调
     */
    public void getRequest(String url, ResponseCallback callback) {
        request(REQUEST_GET, url, new RequestParam(), null, callback);
    }

    /**
     * Get 无参有Tag
     *
     * @param url      请求地址
     * @param tag      标签
     * @param callback 回调
     */
    public void getRequest(String url, String tag, ResponseCallback callback) {
        request(REQUEST_GET, url, new RequestParam(), tag, callback);
    }

    /**
     * Get有参无Tag
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 回调
     */
    public void getRequest(String url, RequestParam params, ResponseCallback callback) {
        request(REQUEST_GET, url, params, null, callback);
    }

    /**
     * Get有参有Tag
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param tag      标签
     * @param callback 回调
     */
    public void getRequest(String url, RequestParam params, String tag, ResponseCallback callback) {
        request(REQUEST_GET, url, params, tag, callback);
    }


    ////////////////////////////////////////// POST请求 /////////////////////////////////////////////

    /**
     * Post 无参无TAG
     *
     * @param url      请求地址
     * @param callback 回调
     */
    public void postRequest(String url, ResponseCallback callback) {
        request(REQUEST_POST, url, new RequestParam(), null, callback);
    }

    /**
     * Post 无参有TAG
     *
     * @param url      请求地址
     * @param tag      标签
     * @param callback 回调
     */
    public void postRequest(String url, String tag, ResponseCallback callback) {
        request(REQUEST_POST, url, new RequestParam(), tag, callback);
    }

    /**
     * Post 有参无TAG
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 回调
     */
    public void postRequest(String url, RequestParam params, ResponseCallback callback) {
        request(REQUEST_POST, url, params, null, callback);
    }

    /**
     * Post 有参有TAG
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param tag      标签
     * @param callback 回调
     */
    public void postRequest(String url, RequestParam params, String tag, ResponseCallback callback) {
        request(REQUEST_POST, url, params, tag, callback);
    }

    ////////////////////////////////////////// JSON格式请求 /////////////////////////////////////////////

    /**
     * Post 无参无TAG
     *
     * @param url      请求地址
     * @param callback 回调
     */
    public void jsonRequest(String url, ResponseCallback callback) {
        request(REQUEST_JSON, url, new RequestParam(), null, callback);
    }

    /**
     * Post 无参有TAG
     *
     * @param url      请求地址
     * @param tag      标签
     * @param callback 回调
     */
    public void jsonRequest(String url, String tag, ResponseCallback callback) {
        request(REQUEST_JSON, url, new RequestParam(), tag, callback);
    }

    /**
     * Post 有参无TAG
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 回调
     */
    public void jsonRequest(String url, RequestParam params, ResponseCallback callback) {
        request(REQUEST_JSON, url, params, null, callback);
    }

    /**
     * Post 有参有TAG
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param tag      标签
     * @param callback 回调
     */
    public void jsonRequest(String url, RequestParam params, String tag, ResponseCallback callback) {
        request(REQUEST_JSON, url, params, tag, callback);
    }


    /**
     * 最终请求
     *
     * @param methodType 请求类型 get  post
     * @param url        接口
     * @param params     参数
     * @param tag        tag
     * @param callback   请求回调
     */
    private void request(int methodType, String url, RequestParam params, String tag, ResponseCallback callback) {
        Logger.e(TAG, NetConfig.Url.getBaseUrl() + url + params.toJson());
//        builder.header("");
        switch (methodType) {
            case REQUEST_POST:
                builder.build().rxPost(tag, url, params.getParameter(), callback);
                break;
            case REQUEST_GET:
                builder.build().rxGet(tag, url, params.getParameter(), callback);
                break;
            case REQUEST_JSON:
                builder.build().rxJson(tag, url, params.toJson(), callback);
                break;
            default:
                builder.build().rxGet(tag, url, params.getParameter(), callback);
                break;
        }
    }

    /**
     * 上传单个文件
     *
     * @param url      接口
     * @param file     文件
     * @param callback 回调
     */
    public void upLoadFile(String url, File file, ResponseCallback callback) {
        upLoadFile(url, file, null, callback);
    }

    /**
     * 上传单个文件
     *
     * @param url      接口
     * @param file     文件
     * @param tag      标签
     * @param callback 回调
     */
    public void upLoadFile(String url, File file, String tag, ResponseCallback callback) {
        //使用Part 方式上传文件
        Logger.e(TAG, NetConfig.Url.getBaseUrl() + url + file.getAbsolutePath());
        builder.build().rxUploadWithPart(url, file, callback);
    }

    /**
     * 上传多个文件
     *
     * @param url      接口
     * @param files    文件
     * @param callback 回调
     */
    public void upLoadFile(String url, List<File> files, ResponseCallback callback) {
        upLoadFile(url, files, null, callback);
    }


    /**
     * 上传多个文件
     *
     * @param url      接口
     * @param files    文件
     * @param tag      标签
     * @param callback 回调
     */
    public void upLoadFile(String url, List<File> files, String tag, ResponseCallback callback) {
        builder.build().rxUploadWithPartListByFile(tag, url, files, callback);
    }


}