package mx.com.qtx.ejmSpSec.seguridad.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.qtx.ejmSpSec.seguridad.entidades.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario,String>{

}
