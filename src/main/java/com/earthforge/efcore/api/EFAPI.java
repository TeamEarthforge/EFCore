package com.earthforge.efcore.api;

public class EFAPI extends AbstractEFAPI {
    private static AbstractEFAPI Instance;

    private EFAPI() {}

    public static AbstractEFAPI Instance() {
        if (EFAPI.Instance == null) {
            Instance = new EFAPI();
        }
        return Instance;
    }


}
