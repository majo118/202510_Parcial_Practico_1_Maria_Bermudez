package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialprueba.entities.ConsultaMedicaEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.ConsultaMedicaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConsultaMedicaService {
    @Autowired
	ConsultaMedicaRepository consultaRepository;

	@Transactional
	public ConsultaMedicaEntity createConsulta(ConsultaMedicaEntity consultaEntity) throws IllegalOperationException {
		log.info("Inicia proceso de creación de la consulta medica");
		
        Calendar calendar = Calendar.getInstance();
		if (consultaEntity.getFecha().compareTo(calendar.getTime()) > 0)
			throw new IllegalOperationException("Fecha no valida");
	
		log.info("Termina proceso de creación de la consulta medica");
		return consultaRepository.save(consultaEntity);
	}

}
