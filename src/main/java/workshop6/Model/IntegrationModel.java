package workshop6.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import workshop6.Model.IntegrationDistribution;

/**
 * Model Class including all the functional methods
 *
 * @author Andy
 */
public class IntegrationModel {

    public final static double ZERO = 0;
    public final static double MIDDLE = 0.5;
    public final static double ERROR = 0.00001;
    
    public static ArrayList<String[]> getData(String fileName){
        
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String[]> values = new ArrayList<>();
        // We will be using a try-with-resource block
        try (BufferedReader reader = new BufferedReader(
                new FileReader(fileName))) {
            // Access the data from the file
            // Create a new StringBuilder
            StringBuilder string = new StringBuilder();
            
            // Read line-by-line
            String line = reader.readLine();
            // While there comes a new line, execute this
            while(line != null) {
                // Add these lines to the String builder
                data.add(line);
                // Read the next line
                line = reader.readLine();
            }
        } catch (Exception er) {
            // Since there was an error, you probably want to notify the user
            // For that error. So return the error.
            data = new ArrayList<String>();
        }
        // Return the string read from the file
        for(String l : data)
        {
            String[] valueRow = l.split("-");
            values.add(valueRow);
        }
        return values;
    }
    
    /**
     * Method to calculate the numerical integration
     *
     * @param dof Grados de libertad.
     * @param numSeg Numero de segmentos.
     * @param valorX DOF
     * 
     * @return double numerical integration
     */
	public double calculateIntegration(double dof, int numSeg, double valorX) {
		
		IntegrationDistribution in = new IntegrationDistribution(dof, numSeg, valorX);
		double initialIntegration = in.integrationCalculation();
		
		numSeg *= 2;
		IntegrationDistribution in2 = new IntegrationDistribution(dof, numSeg, valorX);
		double finalIntegration = in2.integrationCalculation();
		
		while((initialIntegration - finalIntegration) > ERROR){
			
			initialIntegration = finalIntegration;
			numSeg *= 2;
			
			in2 = new IntegrationDistribution(dof, numSeg, valorX);
			finalIntegration = in2.integrationCalculation();
		}
		
		return finalIntegration;
	}

	/**
	 * Metodo que permite encontrar el valor de x superior, con el cual se calcula la integral.
	 * @param dof Grados de libertad.
	 * @param numSeg Numero de segmentos.
	 * @param limiteSuperior Valor inicial de X.
	 * @param valorEsperado Valor resultado esperado de la integral.
	 * @return double con el valor de X
	 */
	public double calculateIntegrationFunctionFindX(double dof, int numSeg, double limiteSuperior, double valorEsperado) {
		
		boolean ajustarX = true;
		double d = 0.5;

		DecimalFormat formatoDecimal = new DecimalFormat("#.#####");
		DecimalFormatSymbols formatoDecimalSym = formatoDecimal.getDecimalFormatSymbols();
		formatoDecimalSym.setDecimalSeparator('.');
		formatoDecimal.setDecimalFormatSymbols(formatoDecimalSym);

		double initialIntegration = Double.valueOf(formatoDecimal.format(calculateIntegration(dof, numSeg, limiteSuperior)));

		double finalIntegration = 0;

		double diferenciaIntegrales = Math.abs(initialIntegration - valorEsperado);

		if (diferenciaIntegrales < ERROR) {

			return initialIntegration;

		} else {

			while (diferenciaIntegrales > ERROR) {

				diferenciaIntegrales = finalIntegration - valorEsperado;

				if (ajustarX && (diferenciaIntegrales < ERROR)) {

					d = adjustD(d, limiteSuperior);
					
				} else if (!ajustarX && (diferenciaIntegrales > ERROR)) {

					d = adjustD(d, limiteSuperior);
				}

				if (finalIntegration > valorEsperado) {

					ajustarX = true;

				} else {

					ajustarX = false;
				}
				
				limiteSuperior = adjustXValue(ajustarX, limiteSuperior, d);

				initialIntegration = finalIntegration;
				finalIntegration = Double.valueOf(formatoDecimal
						.format(calculateIntegration(dof, numSeg, limiteSuperior)));

				diferenciaIntegrales = Math.abs(finalIntegration - valorEsperado);
			}
		}

		limiteSuperior = Double.valueOf(formatoDecimal.format(limiteSuperior));
		return limiteSuperior;
	}
	
	/**
	 * Metodo que permite ajustar el valor de X si es necesario.
	 * @param ajustarX indica si el valor de X debe ser ajustado.
	 * @param limiteSuperior valor a ajustar.
	 * @param d valor d.
	 * @return double con el valor de X.
	 */
	private double adjustXValue(boolean ajustarX, double limiteSuperior, double d){
		
		if (ajustarX) {

			limiteSuperior -= d;

		} else {

			limiteSuperior += d;
		}

		return limiteSuperior;
	}
	
	/**
	 * Metodo que permite ajustar el valor de d, si es necesario.
	 * @param d Valor a ajustar.
	 * @param limiteSuperior Valor de X.
	 * @return double con el valor de d.
	 */
	private double adjustD(double d, double limiteSuperior){
		
		if (limiteSuperior != 1.0) {

			d /= 2;
		}

		return d;
	}


}
