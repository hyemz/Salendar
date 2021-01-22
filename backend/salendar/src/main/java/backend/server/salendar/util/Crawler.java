package backend.server.salendar.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

public class Crawler {

    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0 Safari/605.1.15";

    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException {

        // Jsoup를 이용해서 https://wonlog11.cafe24.com/board/gallery/read.html?no=295747&board_no=8 크롤링
        // String eventUrl = "크롤링 할 url 지정";
        String eventUrl = "https://www.oliveyoung.co.kr/store/planshop/getPlanShopDetail.do?dispCatNo=500000101480031&trackingCd=Main_Planshop_PROD"; //올리브영
        // String eventUrl = "http://lalavla.gsretail.com/lalavla/ko/customer-engagement/event/detail/publishing?pageNum=1&eventCode=8825846418976"; // 랄라블라
        // String eventUrl = "https://tonystreet.com/event/event_event_view.do?i_sEventcd=EVT20201222000181949"; // 토니모리 이미지 받아오기는 실패. body에 url은 받아와짐.
        // String eventUrl = "https://www.innisfree.com/kr/ko/ProductView.do?prdSeq=29077"; // 이니스프리 이미지 받아오기는 실패. body에 url은 받아와짐.
        // String eventUrl = "https://www.naturecollection.com/mall/event/event-view.jsp?seq=4018"; // 더페이스샵

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
            System.out.println(doc);    //  Document에는 페이지의 전체 소스가 저장라

            //select를 이용하여 원하는 태그를 선택
            Elements element = doc.select("#contents > div.tabWrap.pdtTabWrap.fixed > div.tabContents > section.tabCont.active > div > div > div > div.prdType.sec01.w780 > img");
            for(int i=0; i<element.size(); i++){
                System.out.println("html src:  " + element.get(i).attr("src"));
                String src = element.get(i).attr("src");

                StringBuffer str = new StringBuffer(src);
                //str.insert(0, "http://");   //  src에 따라 붙여도 되고 안붙여도 됨
                String realSrc = str.toString();
                System.out.println("realSrc: " + realSrc);

                URL url = new URL(realSrc);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                conn.setRequestProperty("Referer", realSrc);
                BufferedImage img = ImageIO.read(conn.getInputStream());

                FileOutputStream out = new FileOutputStream(
                        "/Users/jeeyoungkim/Desktop/Ssafy/2학기 프로젝트 1/crawling/토니모리 second trial" + (i + 1) + ".jpg");
                ImageIO.write(img, "jpg", out);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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


