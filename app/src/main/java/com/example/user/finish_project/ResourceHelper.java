package com.example.user.finish_project;

import android.content.Context;

public class ResourceHelper {
    private static Context context;

    public static void setContext(Context newContext){
        context = newContext;
    }

    public static String getString(int id) {
        return context.getResources().getString(id);
    }

    public static String[] getStringArray(int id) {
        return context.getResources().getStringArray(id);
    }
}
