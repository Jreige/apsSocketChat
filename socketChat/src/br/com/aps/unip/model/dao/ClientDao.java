package br.com.aps.unip.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import br.com.aps.unip.connection.ConnectionFactory;
import br.com.aps.unip.model.bean.Client;

public class ClientDao {

	public Client save(Client client) {
		EntityManager em = new ConnectionFactory().getConnection();
		try {
			em.getTransaction().begin();
			Query query = em.createQuery("select count(c) from Client c where c.name = :name");
			query.setParameter("name", client.getName());
			Long existClient =   (Long) query.getSingleResult();
			if(client.getId() == 0 && existClient==0) {
			em.persist(client);
			JOptionPane.showMessageDialog(null, "Novo usuário registrado com sucesso!");
			}else {
			JOptionPane.showMessageDialog(null, "Usuário já existe!");
			}
			em.getTransaction().commit();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Usuário não pode ser salvo! <br> [ERRO] -> " + e);
			em.getTransaction().rollback();
			e.printStackTrace();
		}finally {
			em.close();
		}
		return client;
	}
	
	public Client findByName(String name) {
		EntityManager em = new ConnectionFactory().getConnection();
		Client client = null;
		try {
			Query query = em.createQuery("select c from Client c where c.name = :name");
			query.setParameter("name", name);
			client = (Client) query.getSingleResult();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "prooblemas na validação: [ERRO] -> " + e);
			System.out.println(e);
		}finally{
			em.close();
		}
		return client;
	}
	
}
