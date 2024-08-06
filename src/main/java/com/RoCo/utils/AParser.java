package com.RoCo.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class AParser {
    public static Document getPageA() throws IOException {
        try {
            String link = "https://www.anekdot.ru/id/" + (1 + (int) (Math.random() * 1010000)) + "/"; //1072178/;
            Document page = Jsoup.parse(new URL(link), 3000);
            return page;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getAnekBody(Document page) throws IOException {
        Element block = page.select("div[class=a_id_item a_mt10]").first();
        Elements anek = block.select("div[class=text]");
        return anek.text().replace("<br>", "\n");
    }

}