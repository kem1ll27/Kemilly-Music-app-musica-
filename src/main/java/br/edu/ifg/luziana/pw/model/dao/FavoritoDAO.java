package br.edu.ifg.luziana.pw.model.dao;

import br.edu.ifg.luziana.pw.model.entity.Favorito;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

@RequestScoped
public class FavoritoDAO {

    @Inject
    EntityManager entityManager;

    public void insert(Favorito entity) { entityManager.persist(entity); }
    public void delete(Favorito entity) { entityManager.remove(entity); }

    public Favorito buscar(Integer usuarioId, String musica) {
        //language=jpql
        String jpql = "select f from Favorito f where f.usuario.id = :usuarioId and f.musica = :musica";
        try {
            return entityManager
                    .createQuery(jpql, Favorito.class)
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("musica", musica)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<String> listarMusicasPorUsuario(Integer usuarioId) {
        //language=jpql
        String jpql = "select f.musica from Favorito f where f.usuario.id = :usuarioId order by f.id desc";
        return entityManager
                .createQuery(jpql, String.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }

    public void removerPorUsuario(Integer usuarioId) {
        //language=jpql
        String jpql = "delete from Favorito f where f.usuario.id = :usuarioId";
        entityManager.createQuery(jpql)
                .setParameter("usuarioId", usuarioId)
                .executeUpdate();
    }
}
