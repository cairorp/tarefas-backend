package br.com.pedro.tarefas.rest.model.dto;

import br.com.pedro.tarefas.rest.model.entity.Pessoa;
import lombok.Data;

@Data
public class PessoaDto {
    private String nome;

    public Pessoa toEntity() {

        return Pessoa
                .builder()
                .nome(this.nome)
                .build();
    }
}
