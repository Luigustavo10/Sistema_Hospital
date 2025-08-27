package med.voll.api.domain.consultas;

public record DadosDetalhamentoConsultas(
        Long id,
        Long idPaciente,
        Long idMedico,
        String data
) {
    public DadosDetalhamentoConsultas(Consulta consulta) {
        this(consulta.getId(), consulta.getPaciente().getId(), consulta.getMedico().getId(), consulta.getData().toString());
    }
}
