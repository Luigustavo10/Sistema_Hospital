package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.endereco.Endereco;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id") // Generates equals and hashCode based on the id field
@Table(name = "medicos")
@Entity(name = "Medico")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean ativo;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING) // Ensures that the enum is stored as a string in the database
    private Especialidade especialidade;

    @Embedded // Indicates that the Endereco class is embedded in this entity
    private Endereco endereco;

    public Medico(DadosCadastroMedico dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.crm = dados.crm();
        this.telefone = dados.telefone();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DadosAtualizacaoMedico dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }
    public void excluir() {
        this.ativo = false; // Marks the doctor as inactive instead of deleting from the database
    }
}

