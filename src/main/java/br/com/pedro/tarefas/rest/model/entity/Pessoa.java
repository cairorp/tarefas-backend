package br.com.pedro.tarefas.rest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "TASK", name = "PESSOA")
public class Pessoa {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOME")
    private String nome;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(schema = "TASK", name = "PESSOA_TAREFA",
            joinColumns = {
                    @JoinColumn(name = "PESSOA_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "TAREFA_ID")
            })
    private List<Tarefa> tarefas;
}
