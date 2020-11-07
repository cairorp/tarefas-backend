package br.com.pedro.tarefas.rest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaListarDto {
    private Integer id;
    private String nome;
    private Integer qtdTarefas;
}
