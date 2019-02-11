package controller;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Cooperado;

public class CooperadoController {
	
	EntityManagerFactory emf;
	EntityManager em;
	
	public CooperadoController() {
		emf = Persistence.createEntityManagerFactory("pu");
		em = emf.createEntityManager();
	}
	
	public void salvar(Cooperado cooperado) {
		em.getTransaction().begin();
		em.merge(cooperado);
		em.getTransaction().commit();
		emf.close();
	}
	
	public void remover(Cooperado cooperado) {
		em.getTransaction().begin();
		Query q = em.createQuery("DELETE Cooperado WHERE codigo = " + cooperado.getCodigo());
		q.executeUpdate();
		em.getTransaction().commit();
		emf.close();
	}
	
	public List<Cooperado> listarCooperado(){
		em.getTransaction().begin();
		Query consulta = em.createQuery("SELECT OBJ FROM Cooperado OBJ");
		List<Cooperado> listaCooperado = new ArrayList<Cooperado>();
		listaCooperado = consulta.getResultList();
		em.getTransaction().commit();
		emf.close();
		return listaCooperado;
	}
	
}
