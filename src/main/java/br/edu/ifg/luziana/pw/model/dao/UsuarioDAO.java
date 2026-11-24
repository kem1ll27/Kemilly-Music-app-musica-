package br.edu.ifg.luziana.pw.model.dao;

import br.edu.ifg.luziana.pw.model.entity.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

@RequestScoped
public class UsuarioDAO {

    @Inject
    EntityManager entityManager;

    public void insert(Usuario entity) { entityManager.persist(entity); }
    public void update(Usuario entity) { entityManager.merge(entity); }
    public void delete(Usuario entity) { entityManager.remove(entity); }

    public Usuario buscarPorId(Integer id) {
        return entityManager.find(Usuario.class, id);
    }

    public Usuario buscarPorEmail(String email) {
        //language=jpql
        String jpql = "select u from Usuario u where u.email = :email";
        try {
            return entityManager
                    .createQuery(jpql, Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Usuario> listarTodos() {
        //language=jpql
        String jpql = "select u from Usuario u";
        return entityManager.createQuery(jpql, Usuario.class).getResultList();
    }
}
