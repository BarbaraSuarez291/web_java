package models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Factura {
	private int id;
	private int idUser;
	private Date fecha;
	private Double total;
	
	
	public Factura(int idUser, Date fecha, Double total) {
		super();
		this.idUser = idUser;
		this.fecha = fecha;
		this.total = total;
	}
	public Factura(int id, int idUser, Date fecha, Double total) {
		super();
		this.id = id;
		this.idUser = idUser;
		this.fecha = fecha;
		this.total = total;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "Factura [id=" + id + ", idUser=" + idUser + ", fecha=" + fecha + "]";
	}
	public Factura() {
		super();
	}
	
	public Factura(int idUser, Double total) {
		this.idUser = idUser;
		this.total = total;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	

	
}
