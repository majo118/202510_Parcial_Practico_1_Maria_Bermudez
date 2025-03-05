package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.ConsultaMedicaEntity;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class PacienteConsultaServiceTest {

    @Autowired
	private PacienteConsultaService pacienteConsultaService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private PacienteEntity paciente = new PacienteEntity();
	private List<ConsultaMedicaEntity> consultaList = new ArrayList<>();

	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from PacienteEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from ConsultaMedicaEntity").executeUpdate();
	}

	private void insertData() {

		paciente = factory.manufacturePojo(PacienteEntity.class);
		entityManager.persist(paciente);

		for (int i = 0; i < 3; i++) {
			ConsultaMedicaEntity entity = factory.manufacturePojo(ConsultaMedicaEntity.class);
			entityManager.persist(entity);
			consultaList.add(entity);
			paciente.getConsultas().add(entity);
		}
	}

	@Test
	void testAddconsulta() throws EntityNotFoundException, IllegalOperationException {
		ConsultaMedicaEntity newConsulta = factory.manufacturePojo(ConsultaMedicaEntity.class);
		entityManager.persist(newConsulta);

		ConsultaMedicaEntity consultaEntity = pacienteConsultaService.addConsulta(paciente.getId(), newConsulta.getId());
		assertNotNull(consultaEntity);

		assertEquals(consultaEntity.getId(), newConsulta.getId());
		assertEquals(consultaEntity.getFecha(), newConsulta.getFecha());
	}
	
	@Test
	void testAddConsultaInvalidDate() {
		assertThrows(EntityNotFoundException.class, () -> {
			ConsultaMedicaEntity newConsulta = factory.manufacturePojo(ConsultaMedicaEntity.class);
			entityManager.persist(newConsulta);
            newConsulta.setFecha(new Date());
			pacienteConsultaService.addConsulta(paciente.getId(), newConsulta.getId());

            ConsultaMedicaEntity newConsulta2 = factory.manufacturePojo(ConsultaMedicaEntity.class);
			entityManager.persist(newConsulta2);
            newConsulta2.setFecha(new Date());
			pacienteConsultaService.addConsulta(paciente.getId(), newConsulta2.getId());
		});
	}

}
