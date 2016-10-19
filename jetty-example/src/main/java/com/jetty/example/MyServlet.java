package com.jetty.example;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by momo on 2016/10/18.
 */
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<String> list = new ArrayList<String>();

        list.add("A");
        list.add("中国");

        Gson g = new Gson();

        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String json = g.toJson(list);
        out.write(json.getBytes());
        out.flush();
        out.close();
    }
}
