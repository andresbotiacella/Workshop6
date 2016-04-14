package workshop6.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity containing the data and results of the distribution
 * @author Andy
 */
public class IntegrationDistribution {

	private double widthSegment;
	private int numSeg;
	private double dof;
	private List<Double> valoresX;
	private List<Double> numerosSum;

	/**
	 * Construct Method
	 */
	public IntegrationDistribution(double dof, int numSeg, double valorX) {
            this.numSeg = numSeg;
            this.dof = dof;
            valoresX = new ArrayList<Double>();
            numerosSum = new ArrayList<Double>();
            widthSegment = valorX / numSeg;
            valoresX = calculateXValues(valorX);
	}

	/**
	 * Method to calculate an integration
	 * @return double result
	 */
	public double integrationCalculation() {

		double integral = 0;
		int i = 0;
		for (Double x : valoresX) {
                    if (i == 0) {
                        integral = calcularFx(x);
                        numerosSum.add(integral);
                    } else if (i < valoresX.size() - 1) {
                            integral = calculateMultiplier(i) * calcularFx(x);
                            numerosSum.add(integral);
                    } else if (i == valoresX.size() - 1) {
                            integral = calcularFx(x);
                            numerosSum.add(integral);
                    }
                    i++;
		}
		return sumCalculation(numerosSum) * (widthSegment / 3) ;
	}

    /**
     * Method to calculate the function
     *
     * @param x
     * @param dof
     * @return f(x) result
     */
    public double calculateMultiplier(int i) {
        double multiplier;
        if (i == 0) {
                multiplier = 1;
        } else if (i % 2 == 0) {
                multiplier = 2;
        } else {
                multiplier = 4;
        }
        return multiplier;
    }

    /**
     * Method to calculate the function
     *
     * @param x
     * @param dof
     * @return f(x) result
     */
    public double calcularFx(double x) {

            return calculationTDistribution(dof, x);
    }

	/**
	 * Method to calculate x Values
	 * @return List<Double> with x Values
	 */
	private List<Double> calculateXValues(double x) {

		double tempX = 0;
		valoresX.add(tempX);

		for (int i = 1; i <= numSeg; i++) {

			tempX += widthSegment;

			valoresX.add(tempX);
		}

		return valoresX;
	}
        
        /**
	 * Method to calculate the sum
	 * @param numbers 
	 * @return double 
	 */
	public double sumCalculation(List<Double> numbers){
            double sumatoria = 0;
            for (Double numero : numbers) {
                    sumatoria += numero;
            }
            return sumatoria;
	}
        
        /**
	 * Method for T Distribution
	 * @return double with result
	 */
	public double calculationTDistribution(double dof, double x) {

		double dividendo;
		double factor1;
		double factor2;
		double divisor;
		double multiplicador;
		double resultado;

		double dividendoGamma = (dof + 1) / 2f;
		double divisorGamma = dof / 2f;

		if (dividendoGamma % 1 == 0) {

			dividendo = gammaIntegers((int) dividendoGamma);

		} else {

			dividendo = gammaNoIntegers(dividendoGamma);
		}

		factor1 = Math.pow((dof * Math.PI), 0.5);

		if (divisorGamma % 1 == 0) {

			factor2 = gammaIntegers((int) divisorGamma);

		} else {

			factor2 = gammaNoIntegers(divisorGamma);
		}

		divisor = factor1 * factor2;

		multiplicador = Math
				.pow(1 + ((Math.pow(x, 2)) / dof), -((dof + 1) / 2));

		resultado = (dividendo / divisor) * multiplicador;

		return resultado;
	}

	/**
	 * Method for gamma
	 * @return long with result
	 */
	public long gammaIntegers(int x) {
		
		long factorial = 1;
		x -= 1;

		for (int i = 1; i <= x; i++) {

			factorial *= i;
		}
		
		return factorial;
	}

	/**
	 * Method for gamma
	 * @return double with result.
	 */
	public double gammaNoIntegers(double x) {

		double factorial = 1;

		x -= 1;
		
		while (x >= 0.5) {

			factorial *= x;
			x -= 1;
		}
		
		factorial *= Math.sqrt(Math.PI);

		return factorial;
	}

}
