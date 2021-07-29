package de.sixbits.platform.helpers;

import android.content.Context;
import android.net.ConnectivityManager;

public class ContextHelper {
    public static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
