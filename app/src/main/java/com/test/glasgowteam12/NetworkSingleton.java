package com.test.glasgowteam12;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import static android.content.ContentValues.TAG;

/**
 * Created by Daumantas on 2017-11-04.
 */

public class NetworkSingleton {

        private static NetworkSingleton mInstance;
        private RequestQueue requestQueue;
        private static Context mCtx;
        private ImageLoader mImageLoader;


        private NetworkSingleton(Context context)
        {
            mCtx = context;
            requestQueue = getRequestQueue();
        }

        public static synchronized NetworkSingleton getInstance() {
            return mInstance;
        }

        public RequestQueue getRequestQueue()
        {
            if(requestQueue==null)
            {
                requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext(), new HurlStack(null, ClientSSLSocketFactory.getSocketFactory(mCtx)));
            }
            return requestQueue;
        }
        public static synchronized NetworkSingleton getInstance(Context context)
        {
            if(mInstance==null)
            {
                mInstance = new NetworkSingleton(context);
            }
            return mInstance;
        }

        public <T> void addToRequestQueue(Request<T> req, String tag) {
            // set the default tag if tag is empty
            req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
            getRequestQueue().add(req);
        }

        public <T>void addToRequestque(Request<T> request)
        {
            request.setTag(TAG);
            requestQueue.add(request);
        }

        public void cancelPendingRequests(Object tag) {
            if (requestQueue != null) {
                requestQueue.cancelAll(tag);
            }
        }


}
