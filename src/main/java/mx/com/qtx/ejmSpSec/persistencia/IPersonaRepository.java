package mx.com.qtx.ejmSpSec.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.qtx.ejmSpSec.entidades.Persona;

public interface IPersonaRepository extends JpaRepository<Persona, Long> {

}
