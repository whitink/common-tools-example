package com.jetty.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by momo on 2016/10/18.
 */
public class GetData {
    public static void main(String[] args) throws IOException {
        Connection connection = Jsoup.connect("http://localhost:8080/service/1").timeout(10000);
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        Document doc = connection.get();
        System.out.print(doc.text());
    }
}
