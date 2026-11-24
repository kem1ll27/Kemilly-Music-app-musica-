package br.edu.ifg.luziana.pw.model.dao;

import br.edu.ifg.luziana.pw.model.dto.PerfilUsuarioDTO;
import br.edu.ifg.luziana.pw.model.entity.PerfilUsuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

@RequestScoped
public class PerfilUsuarioDAO {

    @Inject
    EntityManager entityManager;

    public void insert(PerfilUsuario entity) { entityManager.persist(entity); }
    public void update(PerfilUsuario entity) { entityManager.merge(entity); }
    public void delete(PerfilUsuario entity) { entityManager.remove(entity); }

    public List<PerfilUsuarioDTO> listarTodos() {
        //language=jpql
        String jpql = "select new br.edu.ifg.luziana.pw.model.dto.PerfilUsuarioDTO(pu.id, pu.nome) from PerfilUsuario pu";
        return entityManager.createQuery(jpql, PerfilUsuarioDTO.class).getResultList();
    }

    public PerfilUsuario buscarPorNome(String nome) {
        //language=jpql
        String jpql = "select p from PerfilUsuario p where p.nome = :nome";
        try {
            return entityManager
                    .createQuery(jpql, PerfilUsuario.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
