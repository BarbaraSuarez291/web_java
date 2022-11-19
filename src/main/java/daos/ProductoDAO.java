package daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import Conexion.ConexionFactory;
import models.DetalleProducto;
import models.ProdCarrito;
import models.Producto;

public class ProductoDAO {

	/** Inserta productos en la base de datos.
	 * @param Producto **/
	public void insert(Producto prod) throws ClassNotFoundException {
		try {
			
			var con = ConexionFactory.getConexion();

			var query = "INSERT INTO productos";
			query += " ( nombre, precio, cant)";
			query += " values ( ?, ?, ?)";
			var ps = con.prepareStatement(query);
			ps.setString(1, prod.getNombre());
			ps.setDouble(2, prod.getPrecio());
			ps.setInt(3, prod.getCant());

			ps.executeUpdate();

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/** Lista todos los productos retornandolos dentro de una lista.
	 * @return List< Producto > **/ 
	public List<Producto> all() {
		var list = new ArrayList<Producto>();

		try {
			var con = ConexionFactory.getConexion();

			var ps = con.prepareStatement("SELECT * FROM productos");

			var rs = ps.executeQuery();

			while (rs.next()) {
				var _id = rs.getInt("id");
				var _nombre = rs.getString("nombre");
				var _precio = rs.getDouble("precio");				
				var _cant = rs.getInt("cant");
			

				var prod = new Producto();
				prod.setId(_id);
				prod.setNombre(_nombre);
				prod.setPrecio(_precio);
				prod.setCant(_cant);
				
				
				list.add(prod);

			}

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	/** Se le pasa por parametro el id del producto y retorna el stock.
	 * @param  (int idProd)
	 * @return int (Retorna el stock)**/
	public int obtenerStockDeProd(int idProd) {
		int stock = 0;
		
		try {
			var con = ConexionFactory.getConexion();

			var ps = con.prepareStatement(
					"select cant from productos where id = ?");

			ps.setInt(1, idProd);

			var rs = ps.executeQuery();

			if (rs.next()) {
				 stock = rs.getInt("cant");
			}

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return stock;
	}
	/** Se obtiene el Producto segun su id
	 * @param  (int id)(id del producto)
	 * @return Producto**/
	public Producto getById(int id) {

		Producto prod = null;
		try {
			var con = ConexionFactory.getConexion();

			var ps = con.prepareStatement(
					"select id, nombre, precio, cant" + " from productos " + " where id = ?");

			ps.setInt(1, id);

			var rs = ps.executeQuery();

			if (rs.next()) {
				var _id = rs.getInt("id");
				var _nombre = rs.getString("nombre");
				var _precio = rs.getDouble("precio");
				var _cant = rs.getInt("cant");

				prod = new Producto();
				prod.setId(_id);
				prod.setNombre(_nombre);
				prod.setPrecio(_precio);
				prod.setCant(_cant);

			}

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return prod;
	}
	
	
	
	/** Actualiza los datos del producto en la base de datos.
	 * Recibe por parametro un producto.
	 * @param Producto **/
	public void update(Producto prod) {
		try {
			var con = ConexionFactory.getConexion();

			var query = "UPDATE productos";
			query += " SET nombre = ?,";
			query += " precio = ?,";
			query += " cant = ?";
			query += " WHERE id = ?";

			var ps = con.prepareStatement(query);

			ps.setString(1, prod.getNombre());
			ps.setDouble(2, prod.getPrecio());
			ps.setInt(3, prod.getCant());
			ps.setInt(4, prod.getId());;
			ps.executeUpdate();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/** Elimina el producto que coicida con el id pasado por parametro.
	 * @param (int id) **/
	public void delete(int id) {
		try {
			var con = ConexionFactory.getConexion();

			var query = "DELETE FROM productos";
			query += " WHERE id = ?";

			var ps = con.prepareStatement(query);

			ps.setInt(1, id);

			ps.executeUpdate();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Toma el arraylist productosCarrito y va actualizando el stock de los productos
	 * @param ArrayList< ProdCarrito > productosCarrito
	 *  **/
	public void actualizarStock(ArrayList<ProdCarrito> productosCarrito) {
		for(ProdCarrito p : productosCarrito) {
			int stock = obtenerStockDeProd(p.getId());
			int stockActualizado= stock - p.getCant();
			Producto prod = new Producto(p.getId(), p.getNombre(), p.getPrecio(), stockActualizado);
			update(prod);
			
			
		}
			
	}



	
	
	
}
