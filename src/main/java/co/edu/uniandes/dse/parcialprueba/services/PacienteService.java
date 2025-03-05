package co.edu.uniandes.dse.parcialprueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PacienteService {

    @Autowired
	PacienteRepository pacienteRepository;
	
	@Transactional
	public PacienteEntity createPaciente(PacienteEntity pacienteEntity){
		log.info("Inicia proceso de creación del paciente");

		log.info("Termina proceso de creación del paciente");
		return pacienteRepository.save(pacienteEntity);
	}

}
