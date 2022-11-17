package daos;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;

import javax.servlet.RequestDispatcher;

//import com.mysql.jdbc.PreparedStatement;
	import models.Usuario;
	import Conexion.ConexionFactory;

	public class UsuarioDao {
		   private PreparedStatement ps;
		   private ResultSet rs;
		   private ConexionFactory con = new ConexionFactory();
		    
		    
		    
	//************************VALIDAR USUARIO*****************************************	
		   /** Valida los datos del usuario(usuario y password) para ingresar al sistema.
		    * En caso de no encontrarlo retorna un error
		    * @param (String nombre, String clave)
		    * @return Usuario  **/
		    public Usuario validarUsuario(String nombre, String clave) throws SQLException  {
		        Usuario usuario = new Usuario();
		        String consulta = "SELECT * FROM usuario WHERE nombre = ? AND clave = ?";
		        var con = ConexionFactory.getConexion();
		        try {
		            ps = (PreparedStatement) con.prepareStatement(consulta);
		            ps.setString(1, nombre);
		            ps.setString(2, clave);
		            rs = ps.executeQuery();
		            rs.next();
		            do {
		                usuario.setId(rs.getInt("id"));	               
		                usuario.setNombre(rs.getString("nombre"));
		                usuario.setClave(rs.getString("clave"));
		                usuario.setRol(rs.getString("rol"));
		               
		            } while (rs.next());
		        } catch (SQLException ex) {
		            
		        	System.out.println(ex.getErrorCode()+"-Usuario No Encontrado!!!");
		        }
		        
		        return usuario;
		    }
	//*****************************************AGREGAR USUARIO*************************	    
		    public String agregarUsuario(Usuario usuario) {

		        String sentencia = "INSERT INTO usuario (nombre,clave,rol) VALUES (?,?,?)";
		        try {
		        	var con = ConexionFactory.getConexion();
		            ps = (PreparedStatement) con.prepareStatement(sentencia);
		           
		            ps.setString(1, usuario.getNombre());
		            ps.setString(2, usuario.getClave());
		            ps.setString(3, usuario.getRol());
		            
		            ps.executeUpdate();
		        } catch (SQLException ex) {
		            System.out.println(ex.getMessage()+" Error de SQL!!!");
		        }
		        return "Se agrego el usuario";
		       
		    }
	//***********************************LISTAR TODOS LOS USUARIOS*******************
		    /** Lista todos los usuarios, retorna una lista de usuarios
		     * @return  List<Usuario>**/ 
		    public List<Usuario> ListarUsuarios() {
		    	List<Usuario> lista = new ArrayList<Usuario>();
		    	String consulta = "SELECT * FROM usuario";
		        

		        try {
		        	var con = ConexionFactory.getConexion();
		            ps = (PreparedStatement) con.prepareStatement(consulta);
		            rs = ps.executeQuery();
		            while (rs.next()) {
		                Usuario usuario = new Usuario();
		                usuario.setId(rs.getInt("id"));
		                usuario.setNombre(rs.getString("nombre"));
		                usuario.setClave(rs.getString("clave"));
		                usuario.setRol(rs.getString("rol"));
		                lista.add(usuario);

		            }
		        } catch (SQLException ex) {
		            
		        }
		        
		        return lista;

		    }
	//****************************AGREGAR USUARIO TIPO CLIENTE****************************************************	    
		    /**
			    * Agrega un nuevo usuario de tipo cliente.
			    * @param  (Usuario usuario)
			    * @return String **/
		    public String agregarCliente(Usuario usuario)  {
		    String sentencia = "INSERT INTO usuario (nombre,clave,rol,Saldo) VALUES (?,?,?,?)";
		    usuario.setRol("cliente");
		    try {
		    	var con = ConexionFactory.getConexion();
	            ps = (PreparedStatement) con.prepareStatement(sentencia);
	           
	            ps.setString(1, usuario.getNombre());
	            ps.setString(2, usuario.getClave());
	            ps.setString(3, usuario.getRol());
	            ps.setDouble(4, 0);
	            ps.executeUpdate();
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage()+" Error de SQL!!!");
	        }
	      
		    return "Se agrego el usuario";
	       
	    }
		    
	//*************************BUSCAR USUARIO BY ID*************************************************************	    
		   /**
		    * Busca usuario por id
		    * @param  (int id) 
		    * @return Usuario usuario **/
		    public Usuario buscarUsuario(int id) throws SQLException{
		        Usuario usuario = new Usuario();
		        String consulta = "SELECT * FROM usuario WHERE Id = ?";
		        
		        var con = ConexionFactory.getConexion();
		        try {
		            ps = (PreparedStatement) con.prepareStatement(consulta);
		            ps.setInt(1, id);
		            rs = ps.executeQuery();
		            while(rs.next()){
		                usuario.setId(rs.getInt("id"));
		                usuario.setNombre(rs.getString("nombre"));
		                usuario.setClave(rs.getString("clave"));
		                usuario.setRol(rs.getString("rol"));
		                
		              }
		        } catch (Exception e) {
		        }
		        
		        return usuario;
		    }
		    
	//*************************BORRAR USUARIO BY ID**************************************************************	    
		    /**Elimina usuario por id
		     * @param (int id)
		     *  **/
		    public void eliminarUsuario(int id) throws SQLException {

		        String sql = "DELETE FROM usuario WHERE id=" + id;
		        var con = ConexionFactory.getConexion();
		        try {
		            ps = (PreparedStatement) con.prepareStatement(sql);
		            ps.executeUpdate();
		            System.out.println("Se Borrro el usuario con ID="+id);
		        } catch (SQLException ex) {
		            
		        }

		    }
		    
	//*********************************ACTUALIZAR USUARIO************
		    /** Se le pasa un usuario y lo actualiza en base de datos.
		     * @param (Usuario usuario) **/
		    public void actualizarUsuario(Usuario usuario) {
		    	
		        String sentencia = "UPDATE usuario set nombre=?,clave=?,rol=? WHERE id=?";
		        try {
		        	var con = ConexionFactory.getConexion();
		            ps = (PreparedStatement) con.prepareStatement(sentencia);
		            
		            ps.setString(1, usuario.getNombre());	            
		            ps.setString(2, usuario.getClave());
		            ps.setString(3, usuario.getRol());  
		            ps.setInt(4, usuario.getId());
		            ps.executeUpdate();

		        } catch (SQLException ex) {
		            
		        }
		        
		    }
		    
		    
		  //*************************BUSCAR USUARIO BY ID*************************************************************	    
			   /**
			    * Verifica si el usuario ya exista en base de datos.
			    * Si existe un usuario con ese nombre en base de datos devuelve true
			    * @param  (String nombre) 
			    * @return Boolean **/
			    public Boolean existeUsuario(String nombre) throws SQLException{
			        Boolean existeUsuario = false;
			        String consulta = "SELECT * FROM usuario WHERE nombre = ?";
			        
			        var con = ConexionFactory.getConexion();
			        try {
			            ps = (PreparedStatement) con.prepareStatement(consulta);
			            ps.setString(1, nombre);
			            rs = ps.executeQuery();
			            while(rs.next()){
			            	existeUsuario = true;
			                
			              }
			        } catch (Exception e) {
			        }
			        
			        return existeUsuario;
			    }
			/** Valida los datos al registrar, si retorna null se puede avanzar en
			 * el registro de usuario. De lo contrario retorna el mensaje de error.
			 * @param  (String nombre, String password)
			 * @return String **/
			public String validacionRegistro(String nombre, String password) {
				
				if(nombre.isEmpty() || password.isEmpty()) {
					return "Todos los campos son obligatorios";
				}if (nombre.length()<3) {
					return "El nombre de usuario debe ser mas largo";
				}if (password.length()<6) {
					return "La contraseña debe tener al menos 6 caracteres.";
				} else {
					return null;
				}
				
			}
		    
		    
	}
