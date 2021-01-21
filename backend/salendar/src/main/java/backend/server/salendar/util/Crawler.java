package backend.server.salendar.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Crawler {

    public static void main(String[] args) {

        // Jsoup를 이용해서 https://wonlog11.cafe24.com/board/gallery/read.html?no=295747&board_no=8 크롤링
        String eventUrl = "https://wonlog11.cafe24.com/board/gallery/read.html?no=295747&board_no=8"; //    크롤링할 url지정
        Document doc = null;

        try {
            doc = Jsoup.connect(eventUrl).get();
            //System.out.println(doc);    //  Document에는 페이지의 전체 소스가 저장된다
            //select를 이용하여 원하는 태그를 선택한다. select는 원하는 값을 가져오기 위한 중요한 기능이다.
            Elements element = doc.select("#BoardDelForm > div > div.ec-base-table.typeWrite > table > tbody > tr:nth-child(3) > td > div > img");
            for(int i=0; i<element.size(); i++){
                System.out.println(element.get(i).attr("src"));
                String src = element.get(i).attr("src");
                StringBuffer str = new StringBuffer(src);
                str.insert(0, "http:");
                String realSrc = str.toString();

                URL url = new URL(realSrc);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                conn.setRequestProperty("Referer", realSrc);
                BufferedImage img = ImageIO.read(conn.getInputStream());

                FileOutputStream out = new FileOutputStream(
                        "/Users/jeeyoungkim/Desktop/Ssafy/2학기 프로젝트 1/crawling/IMAG01_" + (i + 1) + ".jpg");
                ImageIO.write(img, "jpg", out);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


