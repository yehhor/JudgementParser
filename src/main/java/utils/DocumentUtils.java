package utils;

import model.Judge;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by yehor on 02.07.2016.
 */
public class DocumentUtils {

    private static final String URL_FORMAT = "http://court.gov.ua/sud%s/";
    private static final String CLIENT = "Chrome/51.0.2704.103";
    private static final String REFERRER = "google.com.ua";

    public static Judge getJudge(Integer number) {
        Document doc;
        Judge judge = new Judge();
        try {
            doc = getDocument(getCorrectNumber(number));
            judge.setName(getName(doc));
            judge.setAdress(getAdress(doc));
            judge.setEmail(getEmail(doc));
            judge.setPhone(getPhone(doc));
            judge.setUrl(getUrl(doc));
            String[] sched = getSchedule(doc);
            judge.setSchedule_BREAK(sched[2]);
            judge.setSchedule_FR(sched[1]);
            judge.setSchedule_MON_TH(sched[0]);
        } catch (Exception e) {
            return null;
        }
        if (isEmpty(judge))
            return null;
        return judge;
    }

    private static boolean isEmpty(Judge judge) {

        return (judge.getName().equals("noName") && judge.getAdress().isEmpty()
                && judge.getEmail().isEmpty() && judge.getUrl().isEmpty());
    }

    private static String getPhone(Document doc) {
        Elements elements = doc.getElementsByClass("menur1");
        elements = elements.select("tbody").select("tr");
        if (elements.isEmpty())
            return "";
        Element element = elements.select("td.b3").first();
        if (element == null)
            return "";
        return element.text();
    }

    private static String getEmail(Document doc) {
        Elements elements = doc.getElementsByClass("menur1");
        elements = elements.select("tbody").select("tr");
        if (elements.isEmpty())
            return "";
        Element element = elements.select("td.b2").get(4);
        if (element == null)
            return "";
        return element.text();
    }

    private static String getUrl(Document doc) {
        Elements elements = doc.getElementsByClass("menur1");
        elements = elements.select("tbody").select("tr");
        if (elements.isEmpty())
            return "";
        Element element = elements.select("td.b2").get(2);
        if (element == null)
            return "";
        return element.text();
    }

    private static String getName(Document doc) {
        Elements elements = doc.getElementsByClass("mlh");
        if (elements.isEmpty() || !elements.first().text().contains("суд")) {
            elements = doc.select("div:contains( суд )");
        }
        if (elements.isEmpty()) {
            elements = doc.getElementsByTag("td").select("td#selected").select("td.b1");
        }
        return elements.isEmpty() || !elements.first().text().contains("суд") ? "noName" : elements.first().text();
    }

    private static String getAdress(Document doc) {
        Elements elements = doc.getElementsByClass("menur1");
        elements = elements.select("tbody").select("tr");
        Element element = elements.select("td.b2").first();
        if (element == null)
            return "";
        return element.text();
    }

    public static String getCorrectNumber(Integer number) {
        String textNumber = String.valueOf(number);
        while (textNumber.length() < 4)
            textNumber = 0 + textNumber;
        return textNumber;
    }

    private static String[] getSchedule(Document doc) {
        String[] schedule = new String[3];
        Elements elements_MO_THU = doc.select("table.menur2").select("tr").select("td.b2");
        schedule[0] = elements_MO_THU.get(0).text();
        schedule[1] = elements_MO_THU.get(2).text();
        schedule[2] = doc.select("table.menur2").select("tbody").select("tr").select("td.b3").first().text();
        return schedule;
    }


    private static Document getDocument(String page) throws IOException {
        String url = String.format(URL_FORMAT, page);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent(CLIENT).referrer(REFERRER).get();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return doc == null ? Document.createShell("") : doc;
    }
}
