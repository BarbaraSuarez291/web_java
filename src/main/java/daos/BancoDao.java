package daos;

import models.Usuario;
/** 
 * Esta interfaz gestiona las herramientas que utiliza el DAO.
 * @see BancoDao
 * */
public interface BancoDao {
  
	public Usuario lista_usuario(int id);

	public void agregar(int id, double cantidad);

	public double Traer_Saldo(int id, double saldo);
	
	public double traer_saldo_pornombre(String nombre, double saldo);

	public void Tranferencia(String nombre, double cantidad);

}
