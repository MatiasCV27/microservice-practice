package comuserservices.controller;

import comuserservices.entities.Usuario;
import comuserservices.models.Carro;
import comuserservices.models.Moto;
import comuserservices.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.getAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") int id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.saveUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    //Todo: Obtener datos de otros servicios con RestTemplate
    @GetMapping("/carros/{usuariosId}")
    public ResponseEntity<List<Carro>> getCarros(@PathVariable("usuariosId") int id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        List<Carro> carros = usuarioService.getCarros(id);
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/motos/{usuariosId}")
    public ResponseEntity<List<Moto>> getMotos(@PathVariable("usuariosId") int id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        List<Moto> motos = usuarioService.getMotos(id);
        return ResponseEntity.ok(motos);
    }
    //Todo ============================================================================

    //Todo: Guardar datos de otros servicios con FeignClient
    @PostMapping("/carro/{usuarioId}")
    public ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId") int id, @RequestBody Carro carro) {
        Carro nuevoCarro = usuarioService.saveCarro(id, carro);
        return ResponseEntity.ok(nuevoCarro);
    }

    @PostMapping("/moto/{usuarioId}")
    public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId") int id, @RequestBody Moto moto) {
        Moto nuevaMoto = usuarioService.saveMoto(id, moto);
        return ResponseEntity.ok(nuevaMoto);
    }

    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable("usuarioId") int id) {
        Map<String, Object> resultado = usuarioService.getUsuariosAndVehiculos(id);
        return ResponseEntity.ok(resultado);
    }
    //Todo ============================================================================
}

