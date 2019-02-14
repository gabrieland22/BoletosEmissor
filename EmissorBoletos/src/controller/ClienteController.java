package controller;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Cliente;

public class ClienteController {
	
	EntityManagerFactory emf;
	EntityManager em;
	
	public ClienteController() {
		emf = Persistence.createEntityManagerFactory("pu");
		em = emf.createEntityManager();
	}
	
	public void salvar(Cliente cooperado) {
		em.getTransaction().begin();
		em.merge(cooperado);
		em.getTransaction().commit();
		emf.close();
	}
	
	public void remover(Cliente cooperado) {
		em.getTransaction().begin();
		Query q = em.createQuery("DELETE Cooperado WHERE codigo = " + cooperado.getCodigo());
		q.executeUpdate();
		em.getTransaction().commit();
		emf.close();
	}
	
	public List<Cliente> listarCooperado(){
		em.getTransaction().begin();
		Query consulta = em.createQuery("SELECT OBJ FROM Cooperado OBJ");
		List<Cliente> listaCooperado = new ArrayList<Cliente>();
		listaCooperado = consulta.getResultList();
		em.getTransaction().commit();
		emf.close();
		return listaCooperado;
	}
	
}
