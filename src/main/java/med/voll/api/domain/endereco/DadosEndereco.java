package med.voll.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
        @NotBlank
        String logradouro,

        @NotBlank
        String bairro,

        @NotBlank
        @Pattern(regexp = "\\d{5}-\\d{3}|\\d{8}", message = "CEP deve estar no formato XXXXX-XXX ou XXXXXXXX")
        String cep,

        @NotBlank
        String cidade,

        @NotBlank
        String uf,

        String complemento,
        String numero) {





}