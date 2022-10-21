package models;

import java.util.HashMap;
import java.util.Map;

public class Carrito {
	private int id;
	
	private int idUser;
	
		// listadoProd 
		//k = codigo del articulo(id), 
		//v = cantidad
	private Map<Integer, Integer> listadoProd = new HashMap<>();
	
	private Double total;
	
	private boolean compra; //si es false sigue siendo carrito,
							//si es true es una compra concretada

	
	
	


	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public Map<Integer, Integer> getListadoProd() {
		return listadoProd;
	}

	public void setListadoProd(Map<Integer, Integer> listadoProd) {
		this.listadoProd = listadoProd;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public int getId() {
		return id;
	}
	
	public void altaCarrito() {}
	public void actualizarCarrito() {}
	public void confirmarCompra() {}//cambia compra a true y genera factura
	
	
	
	
	
}
