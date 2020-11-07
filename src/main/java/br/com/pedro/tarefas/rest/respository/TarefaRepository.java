package br.com.pedro.tarefas.rest.respository;

import br.com.pedro.tarefas.rest.model.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TASK.TAREFA T" +
            " WHERE T.ID NOT IN (SELECT PT.TAREFA_ID FROM TASK.PESSOA_TAREFA PT WHERE PT.TAREFA_ID = T.ID)", nativeQuery = true)
    void deletarNaoUsados();
}
