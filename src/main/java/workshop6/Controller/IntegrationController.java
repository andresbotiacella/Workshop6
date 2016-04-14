package workshop6.Controller;

import java.util.ArrayList;
import workshop6.Model.IntegrationModel;
import workshop6.Model.IntegrationDistribution;

/**
 * Controller class containing the logic displayed in the program
 * @author Andy
 */
public class IntegrationController {
    
    /**
     * Method that loads data from a file
     *
     * @param fileName name of the file
     * @return List containing all the data by row
     */
    public ArrayList<String[]> loadIntegrationData(String fileName){
        return IntegrationModel.getData(fileName);
    }
    
    
    /**
     * Method that performs the calculations needed to find the x value from the integration
     *
     * @param dof double dof
     * @param numSeg int number of segments
     * @param initialX double initialX
     * @param expectedValue double expected integral value
     * @return Double finalX
     */
    public double performCalculations(double dof, int numSeg, double initialX, double expectedValue){
        IntegrationModel model = new IntegrationModel();
        Double result = model.calculateIntegrationFunctionFindX(dof, numSeg, initialX, expectedValue);
        return result;
    }
    
     
}
