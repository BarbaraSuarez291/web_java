package services;

public class Service {
	
	/** Recibe un String y retorna true si se pueda pasar a tipo Integer de lo contrario retorna false
	 * @param String cadena
	 * @return boolean **/
	public  boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	/** Recibe un String y retorna true si se pueda pasar a tipo Double de lo contrario retorna false
	 * @param String cadena
	 * @return boolean **/
	public  boolean isDouble(String cadena){
		try {
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
}
