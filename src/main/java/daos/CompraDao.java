package daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexion.ConexionFactory;
import models.DetalleProducto;
import models.Factura;

public class CompraDao {

	
	
	public void insertF(Factura factura) throws ClassNotFoundException {
		try {
			var con = ConexionFactory.getConexion();

			var queryF = "INSERT INTO facturas";
			queryF += " ( idUser, total)";
			queryF += " values ( ?, ?)";

			var ps = con.prepareStatement(queryF);

			ps.setInt(1, factura.getIdUser());
			//ps.setDate(2, (Date) factura.getFecha());
			ps.setDouble(2, factura.getTotal());

			ps.executeUpdate();

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public int obtenerIDFactura() {
		//MODIFICAR
		//TRAER LA ULTIMA FACTURA DEL USUARIO
int id= 89;
		try {
			var con = ConexionFactory.getConexion();

			var ps = con.prepareStatement(
					"select id" + " from facturas " + " order by id desc limit 1");
			var rs = ps.executeQuery();

			if (rs.next()) {
			 id = rs.getInt("id");
			}

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
		
		
		
	}

	public void insertD(DetalleProducto detalle) {
		try {
			var con = ConexionFactory.getConexion();
			
			var queryD = "INSERT INTO detalle_prod";
			queryD += " ( id_factura, id_prod, cantidad, precio)";
			queryD += " values ( ?, ?, ?, ?)";

			var ps = con.prepareStatement(queryD);

			ps.setInt(1, detalle.getId_factura());
			ps.setInt(2, detalle.getId_prod());
			ps.setInt(3, detalle.getCant());
			ps.setDouble(4, detalle.getPrecio());


			ps.executeUpdate();

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		
	}

	public ArrayList<DetalleProducto> getDetalleById(int id_factura) {
		ArrayList<DetalleProducto> listaProd = new ArrayList<DetalleProducto>();
		try {
			var con = ConexionFactory.getConexion();

			var ps = con.prepareStatement(
					"select id , id_factura, id_prod, cantidad, precio " + " from detalle_prod " + " where id_factura = ?");

			ps.setInt(1, id_factura);

			var rs = ps.executeQuery();
			
			while(rs.next()) {
				var _id = rs.getInt("id");
				var _id_factura = rs.getInt("id_factura");
				var _id_prod = rs.getInt("id_prod");
				var _cant = rs.getInt("cantidad");
				var _precio = rs.getDouble("precio");
				DetalleProducto detalle = new DetalleProducto();
				detalle.setId(_id);
				detalle.setId_factura(_id_factura);
				detalle.setId_prod(_id_prod);;
				detalle.setCant(_cant);
				detalle.setPrecio(_precio);
				listaProd.add(detalle);
			}

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaProd;
	}

	public Factura getFacturabyId(int id_factura) {
		Factura fac = null;
		try {
			var con = ConexionFactory.getConexion();

			var ps = con.prepareStatement(
					"select id, idUser, total" + " from facturas " + " where id = ?");

			ps.setInt(1, id_factura);

			var rs = ps.executeQuery();

			if(rs.next()) {
				var _id = rs.getInt("id");
				var _idUser = rs.getInt("idUser");
				var _total = rs.getDouble("total");
				

				fac = new Factura();
				fac.setId(_id);
				fac.setIdUser(_idUser);
				fac.setTotal(_total);
				

			}

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return fac;
	}

	public ArrayList<Factura> comprasPorUsuario(int id) {
		ArrayList<Factura> facturas = new ArrayList<Factura>();
		try {
			var con = ConexionFactory.getConexion();

			var ps = con.prepareStatement(
					"select * " + " from facturas " + " where idUser = ? order by id desc");

			ps.setInt(1, id);

			var rs = ps.executeQuery();

			while(rs.next()) {
				var _id = rs.getInt("id");
				var _idUser = rs.getInt("idUser");
				var _total = rs.getDouble("total");
				

				Factura fac = new Factura();
				fac.setId(_id);
				fac.setIdUser(_idUser);
				fac.setTotal(_total);
				facturas.add(fac);

			}

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return facturas;
		
	}
	
}
