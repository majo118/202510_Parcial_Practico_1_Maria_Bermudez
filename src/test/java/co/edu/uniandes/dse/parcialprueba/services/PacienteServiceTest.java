package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(PacienteService.class)
public class PacienteServiceTest {

	@Autowired
	private PacienteService pacienteService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<PacienteEntity> pacienteList = new ArrayList<>();

	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from PacienteEntity").executeUpdate();
	}

	private void insertData() {
		for (int i = 0; i < 3; i++) {
			PacienteEntity pacienteEntity = factory.manufacturePojo(PacienteEntity.class);
			entityManager.persist(pacienteEntity);
			pacienteList.add(pacienteEntity);
		}
	}

	@Test
	void testCreatePaciente(){
		PacienteEntity newEntity = factory.manufacturePojo(PacienteEntity.class);
	
		PacienteEntity result = pacienteService.createPaciente(newEntity);
		assertNotNull(result);

		PacienteEntity entity = entityManager.find(PacienteEntity.class, result.getId());

		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getNombre(), entity.getNombre());
		assertEquals(newEntity.getEdad(), entity.getEdad());
		assertEquals(newEntity.getCelular(), entity.getCelular());
		assertEquals(newEntity.getCorreo(), entity.getCorreo());
	}
}
