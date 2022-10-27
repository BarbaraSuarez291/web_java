package models;

public class DetalleProducto {
	
	private int id;
	private int id_factura;
	private int id_prod;
	private int cant;
	private Double precio;
	
	
	@Override
	public String toString() {
		return "DetalleProducto [id=" + id + ", id_factura=" + id_factura + ", id_prod=" + id_prod + ", cant=" + cant
				+ ", precio=" + precio + "]";
	}
	


	public DetalleProducto(int id_factura, int id_prod, int cant, Double precio) {
		super();
		this.id_factura = id_factura;
		this.id_prod = id_prod;
		this.cant = cant;
		this.precio = precio;
	}
	public DetalleProducto(int id, int id_factura, int id_prod, int cant, Double precio) {
		super();
		this.id = id;
		this.id_factura = id_factura;
		this.id_prod = id_prod;
		this.cant = cant;
		this.precio = precio;
	}
	public DetalleProducto() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_factura() {
		return id_factura;
	}
	public void setId_factura(int id_factura) {
		this.id_factura = id_factura;
	}
	public int getId_prod() {
		return id_prod;
	}
	public DetalleProducto(int id_prod, int cant, Double precio) {
		super();
		this.id_prod = id_prod;
		this.cant = cant;
		this.precio = precio;
	}
	public void setId_prod(int id_prod) {
		this.id_prod = id_prod;
	}
	public int getCant() {
		return cant;
	}
	public void setCant(int cant) {
		this.cant = cant;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	
	
	
}
