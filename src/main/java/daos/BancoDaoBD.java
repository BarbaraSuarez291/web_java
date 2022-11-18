package daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexion.ConexionFactory;
import models.Usuario;
import daos.BancoDao;
/**
 * Este dao cuenta con la funcionalidades para realizar las operaciones bancarias.<br>
 * {@link BancoDaoBD listar_usuarios(int id)}<br>
 * {@link BancoDaoBD agregar(int id, double cantidad)}<br>
 * {@link BancoDaoBD Traer_Saldo(int id,double saldo)}<br>
 * {@link BancoDaoBD Tranferencia(String nombre, double cantidad)}<br>
 * {@link BancoDaoBD traer_saldo_pornombre(String nombre, double saldo)}<br>
 * @see {@link BancoDaoBD}
 * */
public class BancoDaoBD implements BancoDao{
    
	/**
	 * Recibe una ID y retorna la informacion del usuario en un objeto.
	 * @return  usuario
	 * @see {@link BancoDaoBD listar_usuarios(int id)}
	 */
	@Override
	public Usuario lista_usuario(int id) {
		Usuario usu = null;
		try {
			var con = ConexionFactory.getConexion();
            var sql = "select id, nombre, clave, rol, saldo from usuario where id = ?";
			var ps = con.prepareStatement(sql);

			ps.setInt(1, id);

			var rs = ps.executeQuery();

			if (rs.next()) {
				var id_usu = rs.getInt("id");
				var nombre = rs.getString("nombre");
				var clave = rs.getString("clave");
				var rol = rs.getString("rol");
				var saldo = rs.getDouble("saldo");

				usu = new Usuario();
				usu.setId(id_usu);
				usu.setNombre(nombre);
				usu.setClave(clave);
				usu.setRol(rol);
				usu.setSaldo(saldo);
                
			}

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usu;
         
	}
	/**
	 * Recibe una ID del usuario, la cantidad y no retorna nada. 
	 * Solo actualiza el saldo del usuario.
	 * @see {@link BancoDaoBD agregar(int id, double cantidad)}
	 */
	@Override
	public void agregar(int id, double cantidad) {
        try {
        	var con = ConexionFactory.getConexion();
			var query = "UPDATE usuario SET saldo=? WHERE id=?";
			
			var ps1 = con.prepareStatement(query);

			ps1.setDouble(1, cantidad);
			ps1.setInt(2, id);
		    
			ps1.executeUpdate();
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Recibe una ID del usuario y retorna el saldo del usuario que ingresamos por parametro 
	 * a través de la ID.
	 * @return  double saldo
	 * @see {@link BancoDaoBD Traer_Saldo(int id, double saldo)}
	 */
	@Override
	public double Traer_Saldo(int id,double saldo) {
		Usuario usu1 = null;
		try {
			
			var con = ConexionFactory.getConexion();
            var sql = "select saldo from usuario where id = ?";
			var ps2 = con.prepareStatement(sql);
			
			ps2.setInt(1, id);
			
			var rs2 = ps2.executeQuery();
			if(rs2.next()) {
		    var saldo1 = rs2.getDouble("saldo");
		    usu1 = new Usuario();
		    usu1.setSaldo(saldo1);
			}

      } catch (SQLException e) {
			e.printStackTrace();
		}
		return saldo = usu1.getSaldo();
    }
	/**
	 * Recibe el nombre del destino del saldo y la cantidad, actualiza el saldo del usuario y no retorna nada. 
	 * @see {@link BancoDaoBD Tranferencia(String nombre, double cantidad)}
	 */
	@Override
	public void Tranferencia(String nombre, double cantidad) {
		
		 try {
	        	var con = ConexionFactory.getConexion();
				var query = "UPDATE usuario SET saldo=? WHERE nombre = ?";
				
				var ps1 = con.prepareStatement(query);

				ps1.setDouble(1, cantidad);
				ps1.setString(2, nombre);
			    
				ps1.executeUpdate();
				
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		
	}
	/**
	 * Recibe el nombre del usuario y retorna un double con el saldo de este.
	 * @return double saldo
	 * @see {@link BancoDaoBD traer_saldo_pornombre(String nombre, double saldo)}
	 */
	@Override
	public double traer_saldo_pornombre(String nombre, double saldo) {
		Usuario usu3 = null;
		try {
			
			var con = ConexionFactory.getConexion();
            var sql = "select saldo from usuario where nombre = ?";
			var ps3 = con.prepareStatement(sql);
			
			ps3.setString(1, nombre);
			
			var rs3 = ps3.executeQuery();
			if(rs3.next()) {
		    var saldo3 = rs3.getDouble("saldo");
		    usu3 = new Usuario();
		    usu3.setSaldo(saldo3);
			}

      } catch (SQLException e) {
			e.printStackTrace();
		}
		return saldo = usu3.getSaldo();
	}

}
