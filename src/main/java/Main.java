
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;
import com.heroku.sdk.jdbc.DatabaseUrl;
import workshop6.Controller.IntegrationController;
import workshop6.Model.IntegrationDistribution;
import static spark.Spark.get;
import static spark.Spark.get;
import static spark.Spark.get;


/**
 * 
 * @author Andy
 */
public class Main {

    public static void main(String[] args) {

        port(Integer.valueOf(System.getenv("PORT")));
        staticFileLocation("/public");

        /**
        *Calculates and Displays X Value from Integration
        */
        get("/IntegrationFindX", (req, res) -> {
            
            IntegrationController controller = new IntegrationController();
            
            ArrayList<String[]> dataFromFile = controller.loadIntegrationData("List1.txt");
            
            //Test Case 1
            String[] data1 = dataFromFile.get(0);
            int numberOfSegments1 = Integer.parseInt(data1[0]);
            int degreesOfFreedom1 = Integer.parseInt(data1[1]);
            double x1 = Double.parseDouble(data1[2]);
            double expectedValue1 = Double.parseDouble(data1[3]);
            double finalX1 = controller.performCalculations(degreesOfFreedom1, numberOfSegments1, x1, expectedValue1);
         
            
            String htmlData = "Test Case # 1<br><br>"; //Title
            htmlData += "<div style=\"display: inline-flex\">";
            htmlData += "<table style=\"border: 1px solid; border-collapse: collapse; text-align: center\">"; //Open Table
            htmlData += "<tr><th style=\"border: 1px solid; width: 100px;\">Number of Segments</th><th style=\"border: 1px solid; width: 100px;\">Degrees of Freedom</th><th style=\"border: 1px solid; width: 100px;\">x</th><th style=\"border: 1px solid; width: 150px;\">Expected Value</th><th style=\"border: 1px solid; width: 150px;\">Result X</th></tr>"; //Header
            htmlData += String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>", numberOfSegments1, degreesOfFreedom1, x1, expectedValue1, finalX1);
            htmlData += "</table><br>"; //Close Table
            htmlData += "</div><br><br><br>";
            
            //Test Case 2
            String[] data2 = dataFromFile.get(1);
            int numberOfSegments2 = Integer.parseInt(data2[0]);
            int degreesOfFreedom2 = Integer.parseInt(data2[1]);
            double x2 = Double.parseDouble(data2[2]);
            double expectedValue2 = Double.parseDouble(data2[3]);
            double finalX2 = controller.performCalculations(degreesOfFreedom2, numberOfSegments2, x2, expectedValue2);
            
            htmlData += "Test Case # 2<br><br>"; //Title
            htmlData += "<div style=\"display: inline-flex\">";
            htmlData += "<table style=\"border: 1px solid; border-collapse: collapse; text-align: center\">"; //Open Table
            htmlData += "<tr><th style=\"border: 1px solid; width: 100px;\">Number of Segments</th><th style=\"border: 1px solid; width: 100px;\">Degrees of Freedom</th><th style=\"border: 1px solid; width: 100px;\">x</th><th style=\"border: 1px solid; width: 150px;\">Expected Value</th><th style=\"border: 1px solid; width: 150px;\">Result X</th></tr>"; //Header
            htmlData += String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>", numberOfSegments2, degreesOfFreedom2, x2, expectedValue2, finalX2);
            htmlData += "</table><br>"; //Close Table
            htmlData += "</div><br><br><br>";
            
            
            //Test Case 3
            String[] data3 = dataFromFile.get(2);
            int numberOfSegments3 = Integer.parseInt(data3[0]);
            int degreesOfFreedom3 = Integer.parseInt(data3[1]);
            double x3 = Double.parseDouble(data3[2]);
            double expectedValue3 = Double.parseDouble(data3[3]);
            double finalX3 = controller.performCalculations(degreesOfFreedom3, numberOfSegments3, x3, expectedValue3);
            
            htmlData += "Test Case # 3<br><br>"; //Title
            htmlData += "<div style=\"display: inline-flex\">";
            htmlData += "<table style=\"border: 1px solid; border-collapse: collapse; text-align: center\">"; //Open Table
            htmlData += "<tr><th style=\"border: 1px solid; width: 100px;\">Number of Segments</th><th style=\"border: 1px solid; width: 100px;\">Degrees of Freedom</th><th style=\"border: 1px solid; width: 100px;\">x</th><th style=\"border: 1px solid; width: 150px;\">Expected Value</th><th style=\"border: 1px solid; width: 150px;\">Result X</th></tr>"; //Header
            htmlData += String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>", numberOfSegments3, degreesOfFreedom3, x3, expectedValue3, finalX3);
            htmlData += "</table><br>"; //Close Table
            htmlData += "</div><br><br><br>";
        
            return htmlData;
            
        });


        /**
        *Display Index
        */
        get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", "Hello World!");

            return new ModelAndView(attributes, "index.ftl");
        }, new FreeMarkerEngine());

        

    }

}