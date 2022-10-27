package models;

public class ProdCarrito {
	//private Producto prod;
	private int id;
	private int cant;
	private Double precio;
	private String nombre;
	
	public ProdCarrito(int prod, int cant) {
		super();
		this.id = prod;
		this.cant = cant;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ProdCarrito(int id, int cant, Double precio, String nombre) {
		super();
		this.id = id;
		this.cant = cant;
		this.precio = precio;
		this.nombre = nombre;
	}
	public ProdCarrito() {
		super();

	}
	@Override
	public String toString() {
		return "ProdCarrito [prod=" + id + ", cant=" + cant + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCant() {
		return cant;
	}
	public void setCant(int cant) {
		this.cant = cant;
	}
	
	
	
	
	
}
