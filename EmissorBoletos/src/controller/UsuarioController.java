package controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Usuario;

public class UsuarioController {
	EntityManagerFactory emf;
	EntityManager em;
	
	public UsuarioController() {
		emf = Persistence.createEntityManagerFactory("pu");
		em = emf.createEntityManager();
	}
	
	public void salvar(Usuario usuario) {
		em.getTransaction().begin();
		em.merge(usuario);
		em.getTransaction().commit();
		emf.close();
	}
	
	public void remover(Usuario usuario) {
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append(" DELETE obj from Usuario obj ");
		hql.append(" where nome = :nome ");
		Query q = em.createQuery(hql.toString());
		q.setParameter("nome", usuario.getNome());
		
		q.executeUpdate();
		em.getTransaction().commit();
		emf.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuarios(){
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append("SELECT obj from Usuario obj ");
		Query q = em.createQuery(hql.toString());
		
		List<Usuario> listaUsuario = q.getResultList();
		em.getTransaction().commit();
		emf.close();
		return listaUsuario;
	}
	
	public boolean verificaLogin(String nome, String senha) {
		
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append(" SELECT count(obj.id) from Usuario obj ");
		hql.append(" where obj.nome = :nome and obj.senha = :senha ");
		Query q = em.createQuery(hql.toString());
		q.setParameter("nome", nome);
		q.setParameter("senha", senha);
		
		em.getTransaction().commit();
		emf.close();
		
		if ((Long) q.getSingleResult() > 0l) {
			return true;
		}else {
			return false;
		}
	}
}
