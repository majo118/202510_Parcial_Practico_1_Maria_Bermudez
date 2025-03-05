package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialprueba.entities.ConsultaMedicaEntity;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.ConsultaMedicaRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PacienteConsultaService {

    @Autowired
	private ConsultaMedicaRepository consultaRepository;

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Transactional
	public ConsultaMedicaEntity addConsulta(Long pacienteId, Long consultaId) throws EntityNotFoundException, IllegalOperationException{
		log.info("Inicia proceso de asociarle una consulta al paciente con id = {0}", pacienteId);
		Optional<PacienteEntity> pacienteEntity = pacienteRepository.findById(pacienteId);
		Optional<ConsultaMedicaEntity> consultaEntity = consultaRepository.findById(consultaId);

		if (pacienteEntity.isEmpty())
			throw new EntityNotFoundException("Paciente no encontrado");

		if (consultaEntity.isEmpty())
			throw new EntityNotFoundException("Consulta medica no encontrada");

        for (ConsultaMedicaEntity consulta: pacienteEntity.get().getConsultas()) {
            if(consulta.getFecha().equals(consultaEntity.get().getFecha())) {throw new IllegalOperationException("Ya existe consulta para esa fecha");}
        }

		pacienteEntity.get().getConsultas().add(consultaEntity.get());
		log.info("Termina proceso de asociarle una consulta medica al paciente con id = {0}", pacienteId);
		return consultaEntity.get();
	}

	@Transactional
	public List<ConsultaMedicaEntity> getconsultas(Long pacienteId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todas las consultas medicas actuales del paciente con id = {0}", pacienteId);
		Optional<PacienteEntity> pacienteEntity = pacienteRepository.findById(pacienteId);

		if (pacienteEntity.isEmpty())
			throw new EntityNotFoundException("Paciente no encontrado");
        
        List<ConsultaMedicaEntity> consultasActuales = new ArrayList<>();
        for (ConsultaMedicaEntity consulta: pacienteEntity.get().getConsultas()) {
            if(consulta.getFecha().after(new Date())){consultasActuales.add(consulta);}
        }
        
		log.info("Termina proceso de consultar todas las consultas medicas actuales del paciente con id = {0}", pacienteId);
		return consultasActuales;
	}
}
