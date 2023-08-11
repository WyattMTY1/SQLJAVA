package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.dao.ProductoDAO;

import java.sql.*;
import java.util.List;

public class ProductoController {

	private ProductoDAO productoDAO;

	public ProductoController(){
		this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
	}
	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException{
		// TODO
		return productoDAO.modificar(id,nombre,descripcion,cantidad);
	}

	public int eliminar(Integer id) throws SQLException{
		// TODO
		return productoDAO.eliminar(id);
	}

	public List<Producto> listar(){
		// TODO
		return productoDAO.listar();
	}
    public void guardar(Producto producto,Integer categoriaId){
		// TODO
		producto.setCategoriaId(categoriaId);
		productoDAO.guardar(producto);
	}
	public  List<Producto> listar(Categoria categoria){
		return  productoDAO.listar(categoria.getId());
	}

}
