package com.earthforge.efcore.api;

public abstract class AbstractEFAPI {
    private static AbstractEFAPI instance = null;
    public static AbstractEFAPI Instance() {
        if (instance != null) {
            return instance;
        } else {
            try {
                Class<?> c = Class.forName("com.earthforge.efcore.api.EFAPI");
                instance = (AbstractEFAPI) c.getMethod("Instance").invoke(null);

            } catch (Exception ignored) {}
            return instance;
        }
    }
}
