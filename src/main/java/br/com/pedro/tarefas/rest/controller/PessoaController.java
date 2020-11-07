package br.com.pedro.tarefas.rest.controller;

import br.com.pedro.tarefas.rest.model.dto.PessoaDto;
import br.com.pedro.tarefas.rest.model.entity.Tarefa;
import br.com.pedro.tarefas.rest.service.PessoaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.pessoaService.buscarPorId(id));
    }

    @DeleteMapping("/{idPessoa}/tarefa/{idTarefa}")
    public ResponseEntity<?> deletarTarefa(@PathVariable("idPessoa") Integer idPessoa,
                                           @PathVariable("idTarefa") Integer idTarefa){

        this.pessoaService.deletarTarefa(idPessoa, idTarefa);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/tarefa")
    public ResponseEntity<?> cadastrarTarefa(@PathVariable("id") Integer idPessoa, @RequestBody Tarefa tarefa) {

        return ResponseEntity
                .status(CREATED)
                .body(this.pessoaService.cadastrarTarefa(idPessoa, tarefa));
    }

    @PutMapping("/{id}/tarefa")
    public ResponseEntity<?> alterarTarefa(@PathVariable("id") Integer idPessoa, @RequestBody Tarefa tarefa) {

        return ResponseEntity
                .status(CREATED)
                .body(this.pessoaService.alterarTarefa(idPessoa, tarefa));
    }

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        return ResponseEntity.ok(this.pessoaService.listarTodas());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody PessoaDto pessoa) {

        return ResponseEntity.status(CREATED)
                .body(this.pessoaService.cadastrar(pessoa));
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> alterar(@PathVariable("id") Integer id,
                                      @RequestBody PessoaDto pessoaDto) {

        return ResponseEntity.ok(this.pessoaService.alterar(id, pessoaDto));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deletar(@PathVariable("id") Integer id) {

        this.pessoaService.deletar(id);

        return ResponseEntity.ok().build();
    }

}
