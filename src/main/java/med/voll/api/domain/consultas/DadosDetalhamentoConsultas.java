package med.voll.api.domain.consultas;

public record DadosDetalhamentoConsultas(
        Long id,
        Long idPaciente,
        Long idMedico,
        String data
) {
}
