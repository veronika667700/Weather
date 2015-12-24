package com.example.user.finish_project;

import android.content.Context;

// Класс для того, чтобы доставать из strings.xml строки
// (без него нельзя, так как для того, чтобы достать из него, нужен контекст а он есть не у всех классов)

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
