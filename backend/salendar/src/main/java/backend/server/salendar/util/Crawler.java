package backend.server.salendar.util;

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
import java.util.Date;

public class Crawler {

    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0 Safari/605.1.15";

    public static void crawlOliveYoung() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        String eventUrl = "https://www.oliveyoung.co.kr/store/main/getEventList.do"; //올리브영 이벤트리스트 페이지

        // SSL check
        if(eventUrl.indexOf("https://") >= 0){
            //System.out.println("****** SSL CHECK ******");
            Crawler.setSSL();
        }

        Document doc = null;
        try {
            doc = Jsoup.connect(eventUrl)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .userAgent(USER_AGENT)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .get();
            //System.out.println(doc);    //  Document에는 페이지의 전체 소스가 저장되어있음

            //select를 이용하여 원하는 태그를 선택
            Element eventList = doc.select("#Container > div > div.event_tab_cont > ul.event_thumb_list").get(0);
            Elements eventsDate = eventList.select("li > a > p.evt_date");
            Elements eventsDesc = eventList.select("li > a > p.evt_desc");

            int size = eventsDate.size();

            int eventIdx = 0;
            boolean[] chkPeriod = new boolean[size];
            for(Element e : eventsDate){
                //System.out.println((eventIdx) +". " + e.text());   // 1. 21.01.01- 21.06.30 <-- 이런식으로 세일 기간 들어가있음

                /* 받아온 세일 기간을 잘라서 세일이 몇일동안 진행되는지 확인하기 */
                int index = e.text().indexOf("-");
                String eventStart = e.text().substring(0, index);
                String eventEnd = e.text().substring(index+2);

                SimpleDateFormat inputFormat = new SimpleDateFormat("yy.MM.dd");
                SimpleDateFormat sixFormat = new SimpleDateFormat("yyMMdd");

                Date eventStartDate = inputFormat.parse(eventStart);
                Date eventEndDate = inputFormat.parse(eventEnd);

                long eventPeriod = eventEndDate.getTime() - eventStartDate.getTime();
                long eventPeriodInDays = eventPeriod / (24*60*60*1000);

//                System.out.println(eventIdx + ". eventPeriodIndays: " + eventPeriodInDays);
                /* 주요 변수: eventPeriodInDays: 이벤트 기간 */

                if(eventPeriodInDays <= 10){    //  이벤트 기간이 10일 이하인 것만 이미지 불러오기
                    chkPeriod[eventIdx] = true;
                }
                eventIdx++;
            }

            int descIdx = 0;
            boolean[] chkPercentage = new boolean[size];
            for(Element e : eventsDesc){
//                System.out.println(e.text());
                int index = e.text().indexOf("%");
                if(index != -1) {
                    String percentage = e.text().substring(index-2, index);
                    int per = Integer.parseInt(percentage);
                    if(per <= 70 || per > 0){
                        chkPercentage[descIdx] = true;
                    }
                }
                descIdx++;
            }

            for(int i=0; i<size; i++){
                if(chkPeriod[i] && chkPercentage[i]){
                    Elements tmp = eventList.select("li > a > img");
                    String src = tmp.get(i).attr("data-original");
                    StringBuffer str = new StringBuffer(src);
                    //                    str.insert(0, "http://");   //  src에 따라 붙여도 되고 안붙여도 됨
                    String realSrc = str.toString();
                    //                    System.out.println("realSrc: " + realSrc);

                    URL url = new URL(realSrc);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                    conn.setRequestProperty("Referer", realSrc);
                    BufferedImage img = ImageIO.read(conn.getInputStream());

                    FileOutputStream out = new FileOutputStream(
                            "/Users/jeeyoungkim/Desktop/Ssafy/2학기 프로젝트 1/crawling/올리브영 이벤트 리스트 " + (i) + ".jpg");
                    ImageIO.write(img, "jpg", out);

                }
            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException {
        crawlOliveYoung();
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


