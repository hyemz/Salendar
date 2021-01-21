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
        // Jsoup를 이용해서 https://www.lotteon.com/display/plan/planDetail/12710?mall_no=7 크롤링
        String eventUrl = "https://www.lotteon.com/display/plan/planDetail/12710?mall_no=7"; //    크롤링할 url지정
        Document doc = null;

        try {
            doc = Jsoup.connect(eventUrl).get();
            //System.out.println(doc);    //  Document에는 페이지의 전체 소스가 저장된다
            //select를 이용하여 원하는 태그를 선택한다. select는 원하는 값을 가져오기 위한 중요한 기능이다.
            Elements element = doc.select("div.imgBox > img");
            for(int i=0; i<element.size(); i++){
                System.out.println(element.get(i).attr("src"));
                String src = element.get(i).attr("src");

                URL url = new URL(src);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                conn.setRequestProperty("Referer", src);
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




//    public static void main(String[] args) throws IOException{
//        String saveDir = "/Users/jeeyoungkim/Desktop/Ssafy/2학기 프로젝트 1/crawling/";
//        String url = "https://www.lotteon.com/m/display/plan/planDetail/12710?mall_no=7";    //  Crawling 할 url
//        String typeRgx = "(jpg|png)";
//
//        getTypedFileDown(url, saveDir, typeRgx);
//    }
//
//
//    public static String getSource(String url) throws MalformedURLException, IOException {
//        String output = "";
//        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
//        String line;
//        while ((line = br.readLine()) != null) {
//            output += line;
//        }
//        return output;
//    }
//
//    public static ArrayList<String> getTypedFile(String text, String typeRegex) {
//        String regex = "\"(http|https)[:][/][/][^<>\"]+[.]" + typeRegex + "\"";
//        Matcher m = Pattern.compile(regex).matcher(text);
//        ArrayList<String> output = new ArrayList<>();
//        while (m.find()) {
//            output.add(m.group().replace("\"", ""));
//        }
//        return output;
//    }
//
//    public static void FileDownload(String address, String saveDir) {
//        try {
//            URL u = new URL(address);
//            FileOutputStream fos = new FileOutputStream(saveDir);
//            InputStream is = u.openStream();
//            byte[] buf = new byte[1024];
//            int len = 0;
//            while ((len = is.read(buf)) > 0) {
//                fos.write(buf, 0, len);
//            }
//            fos.close();
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String getWebFileName(String filePath) {
//        String[] parts = filePath.split("[/]");
//        return parts[parts.length - 1];
//    }
//
//    public static void getTypedFileDown(String url, String saveDir, String typeRegex) throws IOException {
//        String source = getSource(url);
//        ArrayList<String> urls = getTypedFile(source, typeRegex);
//        System.out.println("다운로드를 시작합니다.");
//        int size = urls.size();
//        int k = 1;
//        for (String i : urls) {
//            System.out.println();
//            System.out.println("//--------------------------------------");
//            System.out.println("//   다운로드 중... : " + i);
//            String save = saveDir + "\\" + getWebFileName(i);
//            System.out.println("//    다운로드 위치 : " + save);
//            FileDownload(i, save);
//            System.out.println("//   다운로드 진행률 : " + (k * 100 / size) + "%");
//            System.out.println("//--------------------------------------");
//            System.out.println();
//            k++;
//        }
//        System.out.println("총 " + urls.size() + "개의 파일을 다운로드하였습니다.");
//    }
