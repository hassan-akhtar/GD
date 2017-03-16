package com.ets.gd.NetworkLayer.Service;

import android.content.Context;

public class GSDServiceFactory {

    private static GSDService service;

    public static GSDService getService(Context context) {

        if (service == null) {
            service = new GSDServiceImpl(context);
        }
        return service;
    }

}
