package com.weapes.ntpaprseng.crawler.extract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lawrence on 16/8/17.
 */
public class NatureMetricsWebPage extends WebPage {

    //存储抽取后的字段和数值
    private Map<String, Integer> hashMap= new HashMap<>();

    //选择器路径
    private static String TotalCitations = "#am-container > div.am-cols.cleared > div > div.am-module.citations-container " +
                                           "> div > section > div > ul > li";

    private static String OnlineAttention = "#altmetric-metrics > div.cleared > div.altmetric-key > ul > li";

    private static String PageViews = "#am-container > div.am-module.pageview-metrics-container.page-views " +
                                      "> article > div > div.page-view-header > h2";

    public NatureMetricsWebPage(String html, String metricsUrl) {
        super(html, metricsUrl);
    }

    @Override
    // TODO 抽取Metrics信息
    public ExtractedObject extract() {

        Document element = Jsoup.parse(getText());

        String numbers, numbersString;

        //抽取Total citations信息
        Elements elements1 = element.select(TotalCitations);
        for (Element element2 : elements1){

            numbersString = element2.select("span").text();
            numbers = element2.select("a > div").text();

            if ( numbers != null )
            {
                if (!numbers.equals(""))
                    hashMap.put(numbersString, Integer.parseInt(numbers));
            }
        }

        //抽取Online attention信息
        Elements elements2 = element.select(OnlineAttention);
        for (Element element2 : elements2){

            numbersString = element2.select("div").text();
            numbers = element2.select("div > b").text();

            if ( numbers != null )
            {
                if (!numbers.equals(""))
                    hashMap.put(numbersString, Integer.parseInt(numbers));
            }

        }

        //抽取Page views信息
        Elements elements3 = element.select(PageViews);
        for (Element element2 : elements3){

            numbersString = element2.select("span.type").text();
            numbers = element2.select("span.total").text();
            numbers = numbers.replaceAll(",", "");

            hashMap.put(numbersString, Integer.parseInt(numbers));
        }

        return null;
    }

    public Map<String, Integer> getHashMap(){
        return hashMap;
    }

    @Override
    public List<? extends ExtractedObject> extractAll() {
        return null;
    }

}