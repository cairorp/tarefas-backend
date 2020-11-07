package br.com.pedro.tarefas.rest.respository;

import br.com.pedro.tarefas.rest.model.dto.PessoaListarDto;
import br.com.pedro.tarefas.rest.model.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    @Query("  SELECT new br.com.pedro.tarefas.rest.model.dto.PessoaListarDto(" +
            "p.id, " +
            "p.nome, " +
            "size(p.tarefas)) " +
            "   FROM Pessoa p")
    List<PessoaListarDto> listarTodos();
}
