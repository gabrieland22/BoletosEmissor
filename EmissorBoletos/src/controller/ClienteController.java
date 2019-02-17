package controller;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Cliente;
import model.Usuario;

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
	
	public void remover(int id) {
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append(" DELETE obj from tb_cooperado_envio obj ");
		hql.append(" where obj.id = :id ");
		Query q = em.createNativeQuery(hql.toString());
		q.setParameter("id", id);
		
		q.executeUpdate();
		em.getTransaction().commit();
		emf.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> listarUsuarios(){
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append("SELECT obj from Cliente obj ");
		Query q = em.createQuery(hql.toString());
		
		List<Cliente> listaCliente = q.getResultList();
		em.getTransaction().commit();
		emf.close();
		return listaCliente;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> buscaPadrao(ClienteFiltro filtro){
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append(" SELECT obj from Cliente obj ");
		hql.append(" where lower(obj.nome) like lower (:nome) ");
		
		Query q = em.createQuery(hql.toString());
		
		q.setParameter("nome", "%"+ filtro.getNome() +"%");
		
		
		List<Cliente> listaCliente = q.getResultList();
		em.getTransaction().commit();
		emf.close();
		return listaCliente;
	}
	
	public Cliente recuperaClientePorID(int id){
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append(" SELECT obj from Cliente obj ");
		hql.append(" where obj.id = :id ");
		
		Query q = em.createQuery(hql.toString());
		
		q.setParameter("id", id);
		
		Cliente result = new Cliente();
		try {
			result = (Cliente) q.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		em.getTransaction().commit();
		emf.close();
		return result;
	}
	
}
