package med.voll.api.domain.consultas.validacoes;

import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;
import java.time.DayOfWeek;

@Component
public class ValidarHorarioDeFuncionamentoClinica implements ValidadorAgendamentoConsulta {

    public void validar(DadosAgendamentoConsulta dados) {

        var dataConsulta = dados.data();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var hora = dataConsulta.getHour();
        var anterDaAbertura = dataConsulta .getHour() < 7;
        var depoisDoFechamento = dataConsulta.getHour() > 18;

        if(domingo || anterDaAbertura || depoisDoFechamento) {
            throw new RuntimeException("Consulta fora do horário de funcionamento da clínica");
        }

    }
}
