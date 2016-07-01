import model.Judge;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import repository.DataAccesObject;

import java.io.IOException;

/**
 * Created by yehor on 01.07.2016.
 */
public class Parser {

    private static DataAccesObject repository = DataAccesObject.getInstance();

    private static final String URL_FORMAT = "http://court.gov.ua/sud%s/";
    private static final String CLIENT = "Chrome/48.0.2564.116";
    private static final String REFERRER = "google.ru";
    private static final String NUMBER = "0209";

    public static void main(String[] args) {

        int count;
        for (int i = 209; i < 5000; i++) {
            try {
                if(i%500 == 0)
                    System.out.println("doing + " + i + " iteration");
                Document doc = getDocument(getCorrectNumber(i));
                Judge judge = new Judge();
                judge.setName(getName(doc));
                judge.setAdress(getAdress(doc));
                judge.setEmail(getEmail(doc));
                judge.setPhone(getPhone(doc));
                judge.setUrl(getUrl(doc));
                if(judge.getAdress() == null && judge.getEmail() == null && judge.getUrl() == null)
                    continue;
                repository.save(judge);
            } catch (IllegalArgumentException e) {
                /* NOPE*/
            } catch (Exception e) {
                System.out.println("EXCEPTION in MAIN");
                e.printStackTrace();
            }
        }
    }


    protected static String getPhone(Document doc) {
        Elements elements = doc.getElementsByClass("menur1");
        elements = elements.select("tbody").select("tr");
        if (elements.isEmpty())
            return "";
        Element element = elements.select("td.b3").first();
        if (element == null)
            return "";
        return element.text();
    }

    protected static String getEmail(Document doc) {
        Elements elements = doc.getElementsByClass("menur1");
        elements = elements.select("tbody").select("tr");
        if (elements.isEmpty())
            return "";
        Element element = elements.select("td.b2").get(4);
        if (element == null)
            return "";
        return element.text();
    }

    protected static String getUrl(Document doc) {
        Elements elements = doc.getElementsByClass("menur1");
        elements = elements.select("tbody").select("tr");
        if (elements.isEmpty())
            return "";
        Element element = elements.select("td.b2").get(2);
        if (element == null)
            return "";
        return element.text();
    }

    protected static String getName(Document doc) {
        Elements elements = doc.getElementsByClass("mlh").attr("id", "main");

        return elements.get(0).text();

    }

    protected static String getAdress(Document doc) {
        Elements elements = doc.getElementsByClass("menur1");
        elements = elements.select("tbody").select("tr");
        if (elements.isEmpty())
            return "";
        Element element = elements.select("td.b2").first();
        if (element == null)
            return "";
        return element.text();
    }

    protected static String getCorrectNumber(Integer number) {
        String textNumber = String.valueOf(number);
        while (textNumber.length() < 4)
            textNumber = 0 + textNumber;
        return textNumber;
    }

    protected static Document getDocument(String page) throws IOException {
        String url = String.format(URL_FORMAT, page);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent(CLIENT).referrer(REFERRER).get();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return doc;
    }

}
