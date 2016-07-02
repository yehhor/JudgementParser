import model.Judge;
import repository.DataAccesObject;
import utils.DocumentUtils;

import java.util.ResourceBundle;

import static utils.DocumentUtils.*;

/**
 * Created by yehor on 01.07.2016.
 */
public class Parser {

    private static DataAccesObject repository = DataAccesObject.getInstance();

    private static final ResourceBundle bundle = ResourceBundle.getBundle("db/dbProp");

    public static void main(String[] args) {
        int startIndex = Integer.parseInt(bundle.getString("startIndex"));
        int endIndex = Integer.parseInt(bundle.getString("endIndex"));


        for (int i = startIndex; i < endIndex; i++) {
            try {
                System.out.print("processing http://court.gov.ua/sud" + DocumentUtils.getCorrectNumber(i) + "/");
                if (i % 500 == 0)
                    System.out.print("doing " + i + " iteration");
                Judge judge = getJudge(i);
                System.out.print(judge == null ? "   noInfo" : "   saving");
                System.out.println();
                if (judge == null)
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


}
