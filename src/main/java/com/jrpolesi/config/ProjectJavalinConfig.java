package com.jrpolesi.config;

public class ProjectJavalinConfig {
    public static final int PORT = 7000;

    public static void setConfig(io.javalin.config.JavalinConfig config) {
        config.http.defaultContentType = "application/json;charset=utf-8";
    }
}
