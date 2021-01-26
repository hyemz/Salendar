package backend.server.salendar.util;

import backend.server.salendar.domain.Sale;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import javax.net.ssl.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Crawler {

    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0 Safari/605.1.15";

    @SneakyThrows
    public static List<Sale> crawlOliveyoung() {
        List<Sale> result = new ArrayList<>();
        String eventUrl = "https://www.oliveyoung.co.kr/store/main/getEventList.do";

        if (eventUrl.indexOf("https://") >= 0) {
            Crawler.setSSL();
        }

        Document doc = Jsoup.connect(eventUrl)
                .header("Content-Type", "application/json;charset=UTF-8")
                .userAgent(USER_AGENT)
                .ignoreContentType(true)
                .get();

        Elements eventLists = doc.select("#Container > div > div.event_tab_cont > ul.event_thumb_list > li");

        for (Element e : eventLists) {
//            {
//                "saleNo":1,
//                    "saleTitle":String,
//                    "saleStore":Integer,
//                    "saleLink":String,
//                    "saleDsc":String,
//                    "saleBigImg":String,
//                    "saleThumbnail":String,
//                    "saleStartDate":Date,
//                    "saleEndDate":Date
//            }
            Sale curSale = new Sale();
            curSale.setSaleTitle(e.select("a > p.evt_tit").text());
            curSale.setSaleDsc(e.select("a > p.evt_desc").text());
            curSale.setSaleThumbnail(e.select("a > img").attr("data-original"));
            curSale.setSaleLink("https://www.oliveyoung.co.kr/store/" + e.select("input").get(2).attr("value"));

            Elements eventDate = e.select("a > p.evt_date");
            SimpleDateFormat inputFormat = new SimpleDateFormat("yy.MM.dd");

            int index = eventDate.text().indexOf("-");
            String eventStart = eventDate.text().substring(0, index);
            String eventEnd = eventDate.text().substring(index + 2);

            Date eventStartDate = inputFormat.parse(eventStart);
            Date eventEndDate = inputFormat.parse(eventEnd);

            curSale.setSaleStartDate(eventStartDate);
            curSale.setSaleEndDate(eventEndDate);

            curSale.setSaleBigImg(Jsoup.connect(curSale.getSaleLink())
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .userAgent(USER_AGENT)
                    .ignoreContentType(true)
                    .get()
                    .select(".bigImg > img").attr("src"));

            result.add(curSale);
        }
        return result;
    }

    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException {
        crawlOliveyoung();
    }

    // SSL 우회 등록
    public static void setSSL() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    }

}


