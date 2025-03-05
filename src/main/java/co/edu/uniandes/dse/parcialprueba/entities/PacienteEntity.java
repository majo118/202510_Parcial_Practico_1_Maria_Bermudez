package co.edu.uniandes.dse.parcialprueba.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class PacienteEntity extends BaseEntity {

    private String nombre;
    private Integer edad;
    private Integer celular;
    private String correo;

    @PodamExclude
	@OneToMany //(mappedBy = "ConsultaMedica")
	private List<ConsultaMedicaEntity> consultas;

}
