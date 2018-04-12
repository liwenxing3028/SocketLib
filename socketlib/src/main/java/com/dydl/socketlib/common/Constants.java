package com.dydl.socketlib.common;

import android.os.Environment;

import java.io.File;
import java.net.Socket;
import java.util.Map;

public class Constants {


    private static String SERVER_IP = "127.0.0.1";
    private static String SERVER_PORT = "8080";

    public static String getServerIp() {
        return SERVER_IP;
    }

    public static void setServerIp(String serverIp) {
        SERVER_IP = serverIp;
    }

    public static String getServerPort() {
        return SERVER_PORT;
    }

    public static void setServerPort(String serverPort) {
        SERVER_PORT = serverPort;
    }
}
