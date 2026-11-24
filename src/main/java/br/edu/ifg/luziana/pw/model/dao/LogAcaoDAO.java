package br.edu.ifg.luziana.pw.model.dao;

import br.edu.ifg.luziana.pw.model.dto.LogAcaoDTO;
import br.edu.ifg.luziana.pw.model.entity.LogAcao;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@RequestScoped
public class LogAcaoDAO {

    @Inject
    EntityManager entityManager;

    public void insert(LogAcao entity) { entityManager.persist(entity); }

    public List<LogAcaoDTO> listarTodos() {
        //language=jpql
        String jpql = "select new br.edu.ifg.luziana.pw.model.dto.LogAcaoDTO(l.acao, l.usuarioNome, l.usuarioEmail, l.dataHora) " +
                "from LogAcao l order by l.dataHora desc";
        return entityManager.createQuery(jpql, LogAcaoDTO.class).getResultList();
    }
}
