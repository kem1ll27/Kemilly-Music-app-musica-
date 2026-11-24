package br.edu.ifg.luziana.pw.model.dao;

import br.edu.ifg.luziana.pw.model.dto.PlaylistDTO;
import br.edu.ifg.luziana.pw.model.entity.Playlist;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@RequestScoped
public class PlaylistDAO {

    @Inject
    EntityManager entityManager;

    public void insert(Playlist entity) { entityManager.persist(entity); }
    public void delete(Playlist entity) { entityManager.remove(entity); }

    public Playlist buscarPorId(Integer id) {
        return entityManager.find(Playlist.class, id);
    }

    public List<PlaylistDTO> listarTodas() {
        //language=jpql
        String jpql = "select new br.edu.ifg.luziana.pw.model.dto.PlaylistDTO(p.id, p.titulo, p.descricao, p.imagemUrl, p.linkUrl) " +
                "from Playlist p order by p.id desc";
        return entityManager.createQuery(jpql, PlaylistDTO.class).getResultList();
    }
}
