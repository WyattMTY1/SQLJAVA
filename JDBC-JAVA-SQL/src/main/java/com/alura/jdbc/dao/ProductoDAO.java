package com.alura.jdbc.dao;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoDAO {
    final private Connection con;

    public ProductoDAO(Connection con) {
        this.con = con;
    }

    public void guardar(Producto producto){


        try(con) {
            final PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTO"
                    + "(nombre,descripcion,cantidad,categoria_id)"
                    + "VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            try(statement){
                ejecutaRegistro(producto, statement);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void ejecutaRegistro(Producto producto, PreparedStatement statement) throws SQLException {


        statement.setString(1, producto.getNombre());
        statement.setString(2, producto.getDescripcion());
        statement.setInt(3, producto.getCantidad());
        statement.setInt(4, producto.getCategoriaId());

        statement.execute();
        final ResultSet resultSet = statement.getGeneratedKeys();
        try(resultSet){
            while (resultSet.next()){
                producto.setId(resultSet.getInt(1));
                System.out.println(String.format("Fue insertado el producto %s", producto));

            }
        }



    }

    public List<Producto> listar() {
        ConnectionFactory factory = new ConnectionFactory();
        final Connection con = factory.recuperaConexion();
//		Statement statement = con.createStatement();
        List<Producto> result = new ArrayList<>();
        try(con) {
            final PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE,DESCRIPCION,CANTIDAD FROM PRODUCTO");
            try(statement) {
                statement.execute();

                ResultSet resultSet = statement.getResultSet();

                try(resultSet) {
                    while (resultSet.next()) {
                        Producto fila = new Producto(
                                resultSet.getInt("ID"),
                                resultSet.getString("NOMBRE"),
                                resultSet.getString("DESCRIPCION"),
                                resultSet.getInt("CANTIDAD"));


                        result.add(fila);
                    }
                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int modificar(Integer id, String nombre, String descripcion, Integer cantidad) {
        ConnectionFactory factory = new ConnectionFactory();
        final Connection con = factory.recuperaConexion();
        try(con) {
            final PreparedStatement statement = con.prepareStatement("UPDATE PRODUCTO SET"
                    +"NOMBRE = ?"
                    +"DESCRIPCION = ?"
                    +"CANTIDAD = ? "
                    +"WHERE ID = ?");

            try(statement){
                statement.setString(1,nombre);
                statement.setString(2,descripcion);
                statement.setInt(3,cantidad);
                statement.setInt(4,id);

                statement.execute();

                int updateCount = statement.getUpdateCount();
                con.close();
                return updateCount;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int eliminar(Integer id) {
        final Connection con = new ConnectionFactory().recuperaConexion();
        try(con) {
            final PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ");
            try(statement) {
                statement.setInt(1, id);

                return statement.getUpdateCount();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Producto> listar(Integer categoriaId) {
        ConnectionFactory factory = new ConnectionFactory();
        final Connection con = factory.recuperaConexion();
//		Statement statement = con.createStatement();
        List<Producto> result = new ArrayList<>();
        try(con) {
            var queryselect = "SELECT ID, NOMBRE,DESCRIPCION,CANTIDAD FROM PRODUCTO WHERE CATEGORIA_ID = ?";
            final PreparedStatement statement = con.prepareStatement(queryselect);
            try(statement) {
                statement.setInt(1,categoriaId);
                statement.execute();

                ResultSet resultSet = statement.getResultSet();

                try(resultSet) {
                    while (resultSet.next()) {
                        Producto fila = new Producto(
                                resultSet.getInt("ID"),
                                resultSet.getString("NOMBRE"),
                                resultSet.getString("DESCRIPCION"),
                                resultSet.getInt("CANTIDAD"));


                        result.add(fila);
                    }
                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
