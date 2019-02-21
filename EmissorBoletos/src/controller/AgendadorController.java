package controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Agendador;
import model.Cliente;
import model.Usuario;

public class AgendadorController {
	EntityManagerFactory emf;
	EntityManager em;
	
	public AgendadorController() {
		emf = Persistence.createEntityManagerFactory("pu");
		em = emf.createEntityManager();
	}
	
	public void salvar(Agendador agendador) {
		em.getTransaction().begin();
		em.merge(agendador);
		em.getTransaction().commit();
		emf.close();
	}
	
	public void remover() {
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append(" UPDATE tb_agendador ");
		hql.append(" set enviar_boleto = 0 ");
		Query q = em.createNativeQuery(hql.toString());
		
		q.executeUpdate();
		em.getTransaction().commit();
		emf.close();
	}
	
	@SuppressWarnings("unchecked")
	public Agendador recuperaAgendamento(){
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append("SELECT obj from Agendador obj ");
		Query q = em.createQuery(hql.toString());
		
		Agendador result = new Agendador();
		try {
			result = (Agendador) q.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		em.getTransaction().commit();
		emf.close();
		return result;
	}

}
