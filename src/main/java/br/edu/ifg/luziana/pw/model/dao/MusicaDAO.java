package br.edu.ifg.luziana.pw.model.dao;

import br.edu.ifg.luziana.pw.model.dto.MusicaDTO;
import br.edu.ifg.luziana.pw.model.entity.Musica;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@RequestScoped
public class MusicaDAO {

    @Inject
    EntityManager entityManager;

    public void insert(Musica entity) { entityManager.persist(entity); }
    public void delete(Musica entity) { entityManager.remove(entity); }

    public Musica buscarPorId(Integer id) {
        return entityManager.find(Musica.class, id);
    }

    public List<MusicaDTO> listarPorCategoria(String categoria) {
        //language=jpql
        String jpql = "select new br.edu.ifg.luziana.pw.model.dto.MusicaDTO(m.id, m.titulo, m.artista, m.imagemUrl, m.linkUrl, m.categoria) " +
                "from Musica m where m.categoria = :categoria order by m.id desc";
        return entityManager
                .createQuery(jpql, MusicaDTO.class)
                .setParameter("categoria", categoria)
                .getResultList();
    }

    public List<MusicaDTO> listarTodas() {
        //language=jpql
        String jpql = "select new br.edu.ifg.luziana.pw.model.dto.MusicaDTO(m.id, m.titulo, m.artista, m.imagemUrl, m.linkUrl, m.categoria) " +
                "from Musica m order by m.id desc";
        return entityManager.createQuery(jpql, MusicaDTO.class).getResultList();
    }
}
