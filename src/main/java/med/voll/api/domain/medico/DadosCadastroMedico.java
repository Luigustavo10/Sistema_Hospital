package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(

        Long id,

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email com formato invalido")
        String email,

        @NotBlank(message = "CRM é obrigatório")
        @Pattern(regexp = "\\d{4,6}", message = "CRM deve conter entre 4 e 6 dígitos")
        String crm,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone,

        @NotNull(message = "Especialidade é obrigatória")
        Especialidade especialidade,

        @NotNull(message = "Endereço é obrigatório")
        @Valid
        DadosEndereco endereco) {



}
