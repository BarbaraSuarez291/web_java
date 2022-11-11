package models;

public class Usuario {
	private int id;
	private String nombre;
	private String clave;
	private String rol;
	private double saldo;
		
	public Usuario(int id, String nombre, String clave, String rol) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.clave = clave;
		this.rol = rol;
	}
	public Usuario() {
		super();
		this.id = id;
		this.nombre = nombre;
		this.clave = clave;
		this.rol = rol;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", clave=" + clave + ", rol=" + rol + ", saldo=" + saldo
				+ "]";
	}
	
}
