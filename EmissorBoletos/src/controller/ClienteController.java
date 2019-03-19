package controller;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Cliente;
import vo.ClienteEnvioVO;

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
	
	public void removerClienteSelecionado(int id) {
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append(" UPDATE tb_cooperado_envio obj ");
		hql.append(" set enviar_email = 0 ");
		hql.append(" where obj.id = :id ");
		Query q = em.createNativeQuery(hql.toString());
		q.setParameter("id", id);
		
		q.executeUpdate();
		em.getTransaction().commit();
		emf.close();
	}
	
	public void limparTabelaClientes() {
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append(" DELETE Cliente ");
		Query q = em.createQuery(hql.toString());
		q.executeUpdate();
		em.getTransaction().commit();
		emf.close();
	}
	
	public void salvarClienteSelecionado(int id) {
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append(" UPDATE tb_cooperado_envio obj ");
		hql.append(" set enviar_email = 1 ");
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
	
	public void atualizaClientesReceberEmailNao() {
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append(" UPDATE tb_cooperado_envio ");
		hql.append(" set enviar_email = 0 ");
		Query q = em.createNativeQuery(hql.toString());
		
		q.executeUpdate();
		em.getTransaction().commit();
		emf.close();
	}
	
	public void atualizaClientesReceberEmailSim() {
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append(" UPDATE tb_cooperado_envio ");
		hql.append(" set enviar_email = 1 ");
		Query q = em.createNativeQuery(hql.toString());
		
		q.executeUpdate();
		em.getTransaction().commit();
		emf.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> buscaPadrao(ClienteFiltro filtro){
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		boolean setouParametro = false;
		
		hql.append(" SELECT obj from Cliente obj ");
		
		if(filtro.getNome() != null && !filtro.getNome().equals("")) {
			if(!setouParametro) {
				hql.append(" where ").append("lower(obj.nome) like lower (:nome) ");
				setouParametro = true;
			}else {
				hql.append(" and ").append("lower(obj.nome) like lower (:nome) ");
			}
		}
		
		if(filtro.getCpf() != null && !filtro.getCpf().equals("")) {
			if(!setouParametro) {
				hql.append(" where ").append("lower(obj.cpf) like lower (:cpf) ");
				setouParametro = true;
			}else {
				hql.append(" and ").append("lower(obj.cpf) like lower (:cpf) ");
			}
		}
		
		if(filtro.getCodigo() != null && !filtro.getCodigo().equals("")) {
			if(!setouParametro) {
				hql.append(" where ").append("lower(obj.codigo) like lower (:codigo) ");
				setouParametro = true;
			}else {
				hql.append(" and ").append("lower(obj.codigo) like lower (:codigo) ");
			}
		}
		
		Query q = em.createQuery(hql.toString());
		
		if(filtro.getNome() != null && !filtro.getNome().equals("")) {
		q.setParameter("nome", "%"+ filtro.getNome().trim() +"%");
		}
		
		if(filtro.getCpf() != null && !filtro.getCpf().equals("")) {
		q.setParameter("cpf", filtro.getCpf().trim());
		}
		
		if(filtro.getCodigo() != null && !filtro.getCodigo().equals("")) {
			q.setParameter("codigo", filtro.getCodigo().trim());
		}
		
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
	
	public boolean existeCliente(Cliente cli) {
		
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		
		hql.append(" SELECT count(obj.id) from Cliente obj ");
		hql.append(" where obj.codigo = :codigo or obj.cpf = :cpf ");
//		if(cli.getId() != 0) {
//			hql.append(" and obj.id != :id ");
//		}
		
		Query q = em.createQuery(hql.toString());
		
//		if(cli.getId() != 0) {
//			q.setParameter("id", cli.getId());
//		}
		q.setParameter("codigo", cli.getCodigo());
		q.setParameter("cpf", cli.getCpf());
		
		em.getTransaction().commit();
		emf.close();
		
		if ((Long) q.getSingleResult() > 0l) {
			return true;
		}else {
			return false;
		}
	}
	
	public List<ClienteEnvioVO> recuperaClientesParaEnvio(){
		List<ClienteEnvioVO> listaClientesParaEnvio = new ArrayList<>();
		String  cpf, email, nome;
		StringBuilder hql = new StringBuilder();
		em.getTransaction().begin();
		hql.append(" SELECT obj.cpf, obj.numero_controle, obj.email, obj.nome ");
		hql.append(" FROM tb_cooperado_envio obj ");
		hql.append(" WHERE obj.enviar_email = 1 and obj.email IS NOT NULL ");
		
		Query q = em.createNativeQuery(hql.toString());
		
		em.getTransaction().commit();
		emf.close();
		
		ClienteEnvioVO clienteEnvioVO;
		List lista = q.getResultList();
		if (lista.size() > 0) {
			for (Object item : lista) {
				Object[] array;
		        array = (Object[]) item;
		        clienteEnvioVO = new ClienteEnvioVO();
		        
		        
		        if (array[0] != null) {
		        	cpf = array[0].toString();
		        	cpf.replaceAll(".", "");
		        	cpf.replaceAll("-", "");
		        	clienteEnvioVO.setCpf(cpf.trim());
		          }

		          if (array[1] != null) {
		        	  clienteEnvioVO.setNumeroControle((String) array[1]);
		          }else{
		        	  clienteEnvioVO.setNumeroControle(null);
		          }

		          if (array[2] != null) {
		        	  email = (String) array[2];
		        	  email.toLowerCase();
		        	  clienteEnvioVO.setEmail(email.trim());
		          }
		          
		          if (array[3] != null) {
		        	  nome = (String) array[3];
		        	  clienteEnvioVO.setEmail(nome.trim());
		          }
		          
		          listaClientesParaEnvio.add(clienteEnvioVO);
			}
		}
		
		return listaClientesParaEnvio;
	}
	
}
