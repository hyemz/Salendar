package backend.server.salendar.util;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Crawler2 {
    public static void crawlNatureRepublic() throws IOException {
        String eventUrl = "https://www.naturerepublic.com/event/goods_event.php?event_ix=762"; //    크롤링할 url지정
        Document doc = Jsoup.connect(eventUrl).get();
        System.out.println(doc);
    }

    ;

    public static void crawlMisha() throws IOException {
        String eventUrl = "https://www.mynunc.com/marketing/event/detail?evtId=EV00001178"; //    크롤링할 url지정
        Document doc = Jsoup.connect(eventUrl).get();
        System.out.println(doc);
    }

    ;

    public static void crawlSKinfood() throws IOException {
        String eventUrl = "https://www.naturerepublic.com/event/goods_event.php?event_ix=762"; //    크롤링할 url지정
        Document doc = Jsoup.connect(eventUrl).get();
        System.out.println(doc);
    }

    ;

    public static void crawlEtudehouse() throws IOException {
        String eventUrl = "https://www.etude.com/kr/ko/display/event_detail?planDisplaySn=4838"; //    크롤링할 url지정
        Document doc = Jsoup.connect(eventUrl).get();
        System.out.println(doc);
    }

    ;

    public static void crawlItsskin() throws IOException {
        String eventUrl = "https://www.itsskin.com/board/view.php?period=current&bdId=event&sno=176"; //    크롤링할 url지정
        Document doc = Jsoup.connect(eventUrl).get();
        System.out.println(doc);
    };

    public static void crawlAritaum() throws IOException {
        String eventUrl = "https://tonystreet.com/event/event_event_view.do?i_sEventcd=EVT20201222000181949"; //    크롤링할 url지정
        Document doc = Jsoup.connect(eventUrl).get();
        System.out.println(doc);
    }

    ;


    public static void main(String[] args) throws IOException {
//        crawlNatureRepublic();
//        crawlMisha();
//        crawlSKinfood();
//        crawlEtudehouse();
//        crawlItsskin();
        crawlAritaum();
//        try {
//            doc = Jsoup.connect(eventUrl).get();
//            Elements element = doc.select("#BoardDelForm > div > div.ec-base-table.typeWrite > table > tbody > tr:nth-child(3) > td > div > img");
//            for (int i = 0; i < element.size(); i++) {
//                System.out.println(element.get(i).attr("src"));
//                String src = element.get(i).attr("src");
//                StringBuffer str = new StringBuffer(src);
//                str.insert(0, "http:");
//                String realSrc = str.toString();
//
//                URL url = new URL(realSrc);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//                conn.setRequestProperty("Referer", realSrc);
//                BufferedImage img = ImageIO.read(conn.getInputStream());
//
//                FileOutputStream out = new FileOutputStream(
//                        "/Users/jeeyoungkim/Desktop/Ssafy/2학기 프로젝트 1/crawling/IMAG01_" + (i + 1) + ".jpg");
//                ImageIO.write(img, "jpg", out);
//            }

//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}


