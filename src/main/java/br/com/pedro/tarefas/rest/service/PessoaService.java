package br.com.pedro.tarefas.rest.service;

import br.com.pedro.tarefas.rest.model.dto.PessoaDto;
import br.com.pedro.tarefas.rest.model.dto.PessoaListarDto;
import br.com.pedro.tarefas.rest.model.entity.Pessoa;
import br.com.pedro.tarefas.rest.model.entity.Tarefa;

import java.util.List;

public interface PessoaService {
    PessoaListarDto cadastrar(PessoaDto pessoa);

    PessoaListarDto alterar(Integer id, PessoaDto pessoa);

    List<PessoaListarDto> listarTodas();

    void deletar(Integer id);

    Pessoa buscarPorId(Integer id);

    Tarefa cadastrarTarefa(Integer idPessoa, Tarefa tarefa);

    Tarefa alterarTarefa(Integer idPessoa, Tarefa tarefa);

    void deletarTarefa(Integer idPessoa, Integer idTarefa);
}
