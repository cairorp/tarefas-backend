package br.com.pedro.tarefas.rest.service.impl;

import br.com.pedro.tarefas.rest.exception.PessoaException;
import br.com.pedro.tarefas.rest.model.dto.PessoaDto;
import br.com.pedro.tarefas.rest.model.dto.PessoaListarDto;
import br.com.pedro.tarefas.rest.model.entity.Pessoa;
import br.com.pedro.tarefas.rest.model.entity.Tarefa;
import br.com.pedro.tarefas.rest.respository.PessoaRepository;
import br.com.pedro.tarefas.rest.respository.TarefaRepository;
import br.com.pedro.tarefas.rest.service.PessoaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;
    private final TarefaRepository tarefaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository,
                             TarefaRepository tarefaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.tarefaRepository = tarefaRepository;
    }

    @Override
    public PessoaListarDto cadastrar(PessoaDto pessoa) {

        try {
            Pessoa pessoaBase = this.pessoaRepository
                    .save(pessoa.toEntity());

            return new PessoaListarDto(pessoaBase.getId(), pessoaBase.getNome(), 0);

        } catch (Exception ex) {

            throw new PessoaException("Ocorreu um erro ao tentar cadastrar a pessoa.");

        }
    }

    @Override
    public PessoaListarDto alterar(Integer id, PessoaDto pessoa) {
        Pessoa pessoaBase = buscarPorId(id);

        try {
            pessoaBase.setNome(pessoa.getNome());

            this.pessoaRepository.save(pessoaBase);

            return new PessoaListarDto(pessoaBase.getId(),
                    pessoaBase.getNome(),
                    pessoaBase.getTarefas() == null ? 0 : pessoaBase.getTarefas().size());

        } catch (Exception ex) {
            throw new PessoaException("Ocorreu um erro ao tentar alterar a pessoa referente ao ID: " + id);
        }


    }

    @Override
    public List<PessoaListarDto> listarTodas() {
        return this.pessoaRepository.listarTodos();
    }

    @Override
    public void deletar(Integer id) {
        Pessoa pessoa = buscarPorId(id);

        try {
            this.pessoaRepository.delete(pessoa);
        } catch (Exception ex) {
            throw new PessoaException("Ocorreu um erro ao tentar deletar a pessoa do ID: " + id);
        }
    }

    @Override
    public Pessoa buscarPorId(Integer id) {

        return pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaException("Não foi encontrado uma pessoa com o ID: " + id));
    }

    @Override
    public Tarefa cadastrarTarefa(Integer idPessoa, Tarefa tarefa) {
        Pessoa pessoa = buscarPorId(idPessoa);

        try {
            pessoa.getTarefas().add(tarefa);

            this.pessoaRepository.save(pessoa);
            List<Tarefa> tarefas = pessoa.getTarefas();

            tarefas.sort((o1, o2) ->
                    comparador(o1.getId(), o2.getId())
            );

            return tarefas.stream()
                    .findFirst()
                    .get();

        } catch (Exception ex) {
            throw new PessoaException("Ocorreu um erro ao cadastrar a tarefa.");
        }
    }

    @Override
    public Tarefa alterarTarefa(Integer idPessoa, Tarefa tarefa) {
        Pessoa pessoa = buscarPorId(idPessoa);

        try{
            pessoa.setTarefas(pessoa.getTarefas()
                    .stream().filter(t -> !t.getId().equals(tarefa.getId()))
                    .collect(Collectors.toList())
            );

            pessoa.getTarefas().add(tarefa);

            this.pessoaRepository.save(pessoa);

            List<Tarefa> tarefas = pessoa.getTarefas();

            return tarefas.stream()
                    .filter(t -> t.getId().equals(tarefa.getId()))
                    .findFirst()
                    .get();

        }catch (Exception ex){
            throw new PessoaException("Ocorreu um erro ao tentar alterar a tarefa.");
        }

    }

    @Override
    public void deletarTarefa(Integer idPessoa, Integer idTarefa) {
        Pessoa pessoa = buscarPorId(idPessoa);

        try{
            pessoa.setTarefas(pessoa.getTarefas()
                    .stream().filter(t -> !t.getId()
                            .equals(idTarefa))
                    .collect(Collectors.toList()));

            this.pessoaRepository.save(pessoa);
            
            this.tarefaRepository.deletarNaoUsados();
        }catch (Exception ex){
            throw new PessoaException("Ocorreu um erro ao tentar deletar a tarefa.");
        }
    }

    private Integer comparador(Integer o1, Integer o2) {
        return o2 - o1;
    }
}
