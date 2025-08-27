package med.voll.api.repository;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.consultas.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    Boolean existsByMedicoIdAndData(Long idMedico,  LocalDateTime data);


    Boolean existsByPacienteIdAndDataBetween( Long aLong, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);



}
