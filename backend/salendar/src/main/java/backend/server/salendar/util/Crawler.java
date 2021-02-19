package backend.server.salendar.util;

import backend.server.salendar.domain.Sale;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {


    public static String crawl(String url) {

        //WebDriver 설정
        WebDriver driver;

        //Properties 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("no-sandbox");
        options.addArguments("disable-dev-shm-usage");
        String WEB_DRIVER_ID = "webdriver.chrome.driver";
        String WEB_DRIVER_PATH = "chromedriver";

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        options.setCapability("ignoreProtectedModeSettings", true);
        driver = new ChromeDriver(options);
        driver.get(url);
        String result = driver.getPageSource();
        driver.close();
        return result;
    }

    @SneakyThrows
    public static List<Sale> crawlOliveyoung() {
        List<Sale> result = new ArrayList<>();
        String eventUrl = "https://www.oliveyoung.co.kr/store/main/getEventList.do";
        Crawler.setSSL();
        Document doc = Jsoup.parse(crawl(eventUrl));

        Elements eventLists = doc.select("#Container > div > div.event_tab_cont > ul.event_thumb_list > li");

        for (Element e : eventLists) {
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

            if (saleFilter(curSale)) {
                curSale.setSaleBigImg(Jsoup.parse(crawl(curSale.getSaleLink()))
                        .select(".bigImg > img").attr("src"));

                result.add(curSale);
            }
        }
        return result;
    }

    @SneakyThrows
    public static List<Sale> crawlAritaum() {
        List<Sale> result = new ArrayList<>();
        String eventUrl = "https://www.aritaum.com/event/ev/event_ev_event_list.do";
        Crawler.setSSL();
        Document doc = Jsoup.parse(crawl(eventUrl));

        Elements eventLists = doc.select("#container > div > div.side_content > div > div > ul > li");

        for (Element e : eventLists) {
            Sale curSale = new Sale();
            curSale.setSaleTitle(e.select("div > a > div.event-unit__info > dl > dd").text());
            curSale.setSaleDsc(e.select("div > a > div.event-unit__info > dl > dd").text());
            curSale.setSaleThumbnail(e.select("div > a > div.event-unit__thumb > img").attr("src"));
            curSale.setSaleLink("https://www.aritaum.com" + e.select("div > a").attr("href"));

            Elements eventDate = e.select("div > a > div.event-unit__info > div > dl.period__item.period__date > dd");
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy. MM. dd");

            int index = eventDate.text().indexOf("~");
            String eventStart = eventDate.text().substring(0, index - 1);
            String eventEnd = eventDate.text().substring(index + 2, index + 14);
            Date eventStartDate = inputFormat.parse(eventStart);
            Date eventEndDate = inputFormat.parse(eventEnd);

            curSale.setSaleStartDate(eventStartDate);
            curSale.setSaleEndDate(eventEndDate);

            if (saleFilter(curSale)) {
                curSale.setSaleBigImg(Jsoup.parse(crawl(curSale.getSaleLink()))
                        .select("#container > div.content > div.side_content > div > div.event-detail > div.event-detail__body > div > div.edit_con > div > div > img").attr("src"));

                result.add(curSale);
            }
        }
        return result;
    }

    @SneakyThrows
    public static List<Sale> crawlMissha() {
        List<Sale> result = new ArrayList<>();
        String eventUrl = "https://www.mynunc.com/marketing/event/main";
        Crawler.setSSL();
        Document doc = Jsoup.parse(crawl(eventUrl));

        Elements eventLists = doc.select("#event-list > li");

        for (Element e : eventLists) {
            Sale curSale = new Sale();
            curSale.setSaleTitle(e.select("div.event-info > a > h4").text());
            curSale.setSaleDsc(e.select("div.event-info > a > h4").text());
            curSale.setSaleThumbnail(e.select("div.thumb-img > a > div.img-area > img").attr("src"));
            curSale.setSaleLink("https://www.mynunc.com/marketing/event/detail?evtld=" + e.select("div.thumb-img > a").attr("data-evtid"));

            Elements eventDate = e.select("div.event-info > a > p");
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

            int index = eventDate.text().indexOf("~");
            String eventStart = eventDate.text().substring(0, index - 1);
            String eventEnd = eventDate.text().substring(index + 2);
            Date eventStartDate = inputFormat.parse(eventStart);
            Date eventEndDate = inputFormat.parse(eventEnd);

            curSale.setSaleStartDate(eventStartDate);
            curSale.setSaleEndDate(eventEndDate);

            if (saleFilter(curSale)) {
                curSale.setSaleBigImg(Jsoup.parse(crawl(curSale.getSaleLink()))
                        .select("#content > div.subpage-detail-content.common-interval-top > div > div.detail-content-body > img:nth-child(1)").attr("src"));

                result.add(curSale);
            }
        }
        return result;
    }

    @SneakyThrows
    public static List<Sale> crawlEtude() {
        List<Sale> result = new ArrayList<>();
        String eventUrl = "https://www.etude.com/kr/ko/display/event?displayMenuId=event";
        Crawler.setSSL();
        Document doc = Jsoup.parse(crawl(eventUrl));

        Elements eventLists = doc.select("#ap_container > div.ap_contents.event_main.event_progress > div > div.tab_cont.space > ul > li");

        for (Element e : eventLists) {

            Sale curSale = new Sale();
            curSale.setSaleTitle(e.select("a > div.evt_txt_area > strong").text());
            curSale.setSaleDsc(e.select("a > div.evt_txt_area > strong").text());
            curSale.setSaleThumbnail(e.select("a > div.evt_img_area > img").attr("src"));
            String temp = e.select("a").attr("onclick");
            curSale.setSaleLink("https://www.etude.com/kr/ko/display/event_detail?planDisplaySn=" + temp.substring(temp.indexOf("planDisplaySn") + 15, temp.indexOf("planDisplayTitle") - 2));
            Elements eventDate = e.select("a > div.evt_txt_area > span");
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd");

            int index = eventDate.text().indexOf("~");
            String eventStart = eventDate.text().substring(0, index);
            String eventEnd = eventDate.text().substring(index + 1, index + 11);
            Date eventStartDate = inputFormat.parse(eventStart);
            Date eventEndDate = inputFormat.parse(eventEnd);

            curSale.setSaleStartDate(eventStartDate);
            curSale.setSaleEndDate(eventEndDate);

            if (saleFilter(curSale)) {
                curSale.setSaleBigImg(Jsoup.parse(crawl(curSale.getSaleLink()))
                        .select("#event > div > img").attr("src"));
                result.add(curSale);
            }
        }
        return result;
    }

    @SneakyThrows
    public static List<Sale> crawlLalavla() {
        List<Sale> result = new ArrayList<>();
        String eventUrl = "http://lalavla.gsretail.com/lalavla/ko/customer-engagement/event/current-events";
        Crawler.setSSL();
        Document doc = Jsoup.parse(crawl(eventUrl));

        Elements eventLists = doc.select("#contents > div.cnt > div > div > div > table > tbody > tr");

        for (Element e : eventLists) {
            Sale curSale = new Sale();
            curSale.setSaleTitle(e.select("td.ft_lt > div > p.tit > a").text());
            curSale.setSaleDsc(e.select("td.ft_lt > div > p.tit > a").text());
            curSale.setSaleThumbnail(e.select("td:nth-child(2) > a > img").attr("src"));
            curSale.setSaleLink("http://lalavla.gsretail.com" + e.select("td:nth-child(2) > a").attr("href"));

            Elements eventDate = e.select("td.ft_lt > div > p.period");
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd");

            int index = eventDate.text().indexOf("~");
            String eventStart = eventDate.text().substring(index - 11, index - 1);
            String eventEnd = eventDate.text().substring(index + 2, index + 12);
            Date eventStartDate = inputFormat.parse(eventStart);
            Date eventEndDate = inputFormat.parse(eventEnd);

            curSale.setSaleStartDate(eventStartDate);
            curSale.setSaleEndDate(eventEndDate);
            if (saleFilter(curSale)) {
                curSale.setSaleBigImg(Jsoup.parse(crawl(curSale.getSaleLink()))
                        .select("#contents > div.cnt > div > div.brdwrap > div.tblwrap > div > div.evt_memo span> div > img").attr("src"));
                result.add(curSale);
            }
        }
        return result;
    }

    @SneakyThrows
    public static List<Sale> crawlThefaceshop() {
        List<Sale> result = new ArrayList<>();
        String eventUrl = "https://www.naturecollection.com/mall/event/event.jsp";
        Crawler.setSSL();
        Document doc = Jsoup.parse(crawl(eventUrl));

        Elements eventLists = doc.select("body > div.RootDiv > div.MallDiv > section > div > div.ComEventList_wrap > ul > li");

        for (Element e : eventLists) {
            Sale curSale = new Sale();
            curSale.setSaleTitle(e.select("a > div.con > span").text());
            curSale.setSaleDsc(e.select("a > div.con > span").text());
            curSale.setSaleThumbnail(e.select("a > div.img > img").attr("src"));
            curSale.setSaleLink("https://www.naturecollection.com/mall/event/" + e.select("a").attr("href"));

            Elements eventDate = e.select("a > div.con > p");
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd");

            int index = eventDate.text().indexOf("~");
            String eventStart = eventDate.text().substring(0, index - 1);
            String eventEnd = eventDate.text().substring(index + 2, index + 12);

            Date eventStartDate = inputFormat.parse(eventStart);
            Date eventEndDate = inputFormat.parse(eventEnd);

            curSale.setSaleStartDate(eventStartDate);
            curSale.setSaleEndDate(eventEndDate);

            if (saleFilter(curSale)) {
                curSale.setSaleBigImg(Jsoup.parse(crawl(curSale.getSaleLink()))
                        .select("body > div.RootDiv > div.MallDiv > section > div > table > tbody > tr:nth-child(2) > td > div.pt20.editArea > p:nth-child(2) > img").attr("src"));
                result.add(curSale);
            }
        }
        return result;
    }

    @SneakyThrows
    public static List<Sale> crawlTonymoly() {
        List<Sale> result = new ArrayList<>();
        String eventUrl = "https://tonystreet.com/event/event_event_list.do";
        Crawler.setSSL();
        Document doc = Jsoup.parse(crawl(eventUrl));

        Elements eventLists = doc.select("body > div.wrapper > div.container > div > section > div > div > ul > li");

        for (Element e : eventLists) {
            Sale curSale = new Sale();
            curSale.setSaleTitle(e.select("a > div.text > h3").text());
            curSale.setSaleDsc(e.select("a > div.text > h3").text());
            curSale.setSaleThumbnail(e.select("a > div.img > img").attr("src"));
            curSale.setSaleLink("https://tonystreet.com/event/event_event_view.do?i_sEventcd=" + e.attr("id"));

            Elements eventDate = e.select("a > div.text > div > p");
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd");

            int index = eventDate.text().indexOf("~");
            String eventStart = eventDate.text().substring(5, index - 1);
            String eventEnd = eventDate.text().substring(index + 2, index + 12);

            Date eventStartDate = inputFormat.parse(eventStart);
            Date eventEndDate = inputFormat.parse(eventEnd);

            curSale.setSaleStartDate(eventStartDate);
            curSale.setSaleEndDate(eventEndDate);

            if (saleFilter(curSale)) {
                curSale.setSaleBigImg(Jsoup.parse(crawl(curSale.getSaleLink()))
                        .select("#frm > div.content.product-content.Ex-con.notSurvey > section > div:nth-child(2) > p > img:nth-child(1)").attr("src"));
                result.add(curSale);
            }
        }
        return result;
    }

    @SneakyThrows
    public static List<Sale> crawlInnisfree() {
        List<Sale> result = new ArrayList<>();
        String eventUrl = "https://www.innisfree.com/kr/ko/Event.do";
        System.out.println(1);
        Crawler.setSSL();
        System.out.println(2);
        Document doc = Jsoup.parse(crawl(eventUrl));
        System.out.println(3);
        System.out.println(doc.text());
        Elements eventLists = doc.select("#eventList > li");

        for (Element e : eventLists) {
            Sale curSale = new Sale();
            curSale.setSaleTitle(e.select("a > span.descWrap > strong").text());
            curSale.setSaleDsc(e.select("a > span.descWrap > strong").text());
            curSale.setSaleThumbnail(e.select("a > span.img > img").attr("src"));
            String tempLink = e.select("a").attr("href");
            if (!tempLink.startsWith("http")) {
                tempLink = "https://www.innisfree.com/" + tempLink;
            }
            curSale.setSaleLink(tempLink);

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

            Elements eventDate = e.select("a > span.descWrap > span");
            int index = eventDate.text().indexOf("~");
            String eventStart = eventDate.text().substring(0, index - 1);
            if ((int) eventDate.text().substring(index + 2, index + 3).charAt(0) == 54620 || (int) eventDate.text().substring(index + 2, index + 3).charAt(0) == 51652) {
                continue;
            }
            String eventEnd = eventDate.text().substring(index + 2, index + 12);
            Date eventStartDate = inputFormat.parse(eventStart);
            Date eventEndDate = inputFormat.parse(eventEnd);

            curSale.setSaleStartDate(eventStartDate);
            curSale.setSaleEndDate(eventEndDate);
            if (saleFilter(curSale)) {
                curSale.setSaleBigImg(Jsoup.parse(crawl(curSale.getSaleLink()))
                        .select("#contents > div.pdtViewTop > div.pdtSlider_area > div.swiper-container.pdtViewSlider.swiper-container-initialized.swiper-container-horizontal > ul > li.swiper-slide.swiper-slide-visible.swiper-slide-active > img").attr("src"));
                result.add(curSale);
            }
        }
        return result;
    }


    @SneakyThrows
    public static Boolean saleFilter(Sale sale) {
//      기간 필터링: 기간이 10일 이하
        if (sale.getSaleEndDate() != null) {
            long eventPeriod = sale.getSaleEndDate().getTime() - sale.getSaleStartDate().getTime();
            long eventPeriodInDays = eventPeriod / (24 * 60 * 60 * 1000);

            if (eventPeriodInDays > 15) {
                return false;
            }
        }

//      불필요 단어 + 과도한 % 필터링
        List<String> donIncludeWords = Arrays.asList("카드", "출석", "출첵", "LIVE", "쿠폰", "사은품", "증정", "배송", "제휴", "멤버쉽", "포장", "적립", "체험");
        List<Pattern> patterns = new ArrayList<>();
        for (String word : donIncludeWords) {
            patterns.add(Pattern.compile("(?m)" + word));
        }
        for (Pattern pattern : patterns) {
            if (pattern.matcher(sale.getSaleTitle()).find()
                    || pattern.matcher(sale.getSaleDsc()).find()) {
                return false;
            }
        }

        List<Pattern> patterns2 = Arrays.asList(Pattern.compile("(?m)\\d*%"), Pattern.compile("(?m)\\d*.\\d*%"));
        for (Pattern pattern : patterns2) {
            double per = (double) 0;
            Matcher matcher = pattern.matcher(sale.getSaleTitle());
            while (matcher.find()) {
                String temp = matcher.group();
                if (Double.parseDouble(temp.substring(0, 2)) > per) {
                    per = Double.parseDouble(temp.substring(0, 2));
                }
            }
            Matcher matcher2 = pattern.matcher(sale.getSaleDsc());
            while (matcher2.find()) {
                String temp = matcher2.group();
                if (Double.parseDouble(temp.substring(0, 2)) > per) {
                    per = Double.parseDouble(temp.substring(0, 2));
                }
            }
            if (per > 90) {
                return false;
            }
        }
        return true;
    }

    // SSL 우회 등록
    public static void setSSL() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    }

}


