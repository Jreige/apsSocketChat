package br.unip.aps.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import br.unip.aps.connection.ConnectionFactory;
import br.unip.aps.model.Cliente;

public class ClientDao extends DaoGenerico<Cliente>{

	public Cliente save(Cliente client) {
		EntityManager em = new ConnectionFactory().getConnection();
		try {
			em.getTransaction().begin();
			Query query = em.createQuery("select count(c) from Client c where c.name = :name");
			query.setParameter("name", client.getName());
			Long existClient =   (Long) query.getSingleResult();
			if(client.getId() == 0 && existClient==0) {
			em.persist(client);
			JOptionPane.showMessageDialog(null, "Novo usu�rio registrado com sucesso!");
			}else {
			JOptionPane.showMessageDialog(null, "Usu�rio j� existe!");
			}
			em.getTransaction().commit();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Usu�rio n�o pode ser salvo! <br> [ERRO] -> " + e);
			em.getTransaction().rollback();
			e.printStackTrace();
		}finally {
			em.close();
		}
		return client;
	}
	
	public Cliente findByName(String name) {
		EntityManager em = new ConnectionFactory().getConnection();
		Cliente client = null;
		try {
			Query query = em.createQuery("select c from Client c where c.name = :name");
			query.setParameter("name", name);
			client = (Cliente) query.getSingleResult();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "prooblemas na valida��o: [ERRO] -> " + e);
			System.out.println(e);
		}finally{
			em.close();
		}
		return client;
	}
	
}
