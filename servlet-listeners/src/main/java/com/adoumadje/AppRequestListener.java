package com.adoumadje;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppRequestListener implements ServletRequestListener {
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("request is initialized");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("request is destroyed");
    }
}
