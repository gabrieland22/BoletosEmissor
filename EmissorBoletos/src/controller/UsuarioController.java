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
	
	public void remover(int id) {
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append(" DELETE obj from tb_usuario obj ");
		hql.append(" where obj.id = :id ");
		Query q = em.createNativeQuery(hql.toString());
		q.setParameter("id", id);
		
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
	
	@SuppressWarnings("unchecked")
	public List<Usuario> buscaPadrao(UsuarioFiltro filtro){
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append(" SELECT obj from Usuario obj ");
		hql.append(" where lower(obj.nome) like lower (:nome) ");
		
		Query q = em.createQuery(hql.toString());
		
		q.setParameter("nome", "%"+ filtro.getNome() +"%");
		
		List<Usuario> listaUsuario = q.getResultList();
		em.getTransaction().commit();
		emf.close();
		return listaUsuario;
	}
	
	public Usuario recuperaUsuarioPorID(int id){
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append(" SELECT obj from Usuario obj ");
		hql.append(" where obj.id = :id ");
		
		Query q = em.createQuery(hql.toString());
		
		q.setParameter("id", id);
		
		Usuario result = (Usuario) q.getSingleResult();
		em.getTransaction().commit();
		emf.close();
		return result;
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
