package com.nenguou.dayuandaily.Utils;

/**
 * Created by binguner on 2018/2/24.
 */

public interface RetrofitCallbackListener {

    /**
     * 0 ok
     * 1 not ok
     */
    void onFinish(int status);
    void onError(Exception e);
}
