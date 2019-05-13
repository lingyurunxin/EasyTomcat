package com.liyuanfeng;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * tomcat启动类
 */
public class MyTomcat {
    private int port = 8088;

    public MyTomcat(int port) {
        this.port = port;
    }

    public void start() {
        //        初始化URL与对应处理的servlet的关系

        Map<String, String> urlServletMap = ProcessThread.urlServletMap;

        initServletMapping(urlServletMap);

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("MyTomcat is start...");

            while (true) {
                Socket socket = serverSocket.accept();

                new Thread(new ProcessThread(socket)).start();


            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != serverSocket) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void initServletMapping(Map<String, String> urlServletMap) {
        for (ServletMapping servletMapping : ServletMappingConfig.servletMappingList) {
            urlServletMap.put(servletMapping.getUrl(), servletMapping.getClazz());
        }
    }

    public static void main(String[] args) {
        new MyTomcat(8088).start();
    }
}
