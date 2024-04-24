package comuserservices.repository;

import comuserservices.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepostory extends JpaRepository<Usuario, Integer> {

}
