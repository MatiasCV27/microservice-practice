package comuserservices.service;

import comuserservices.entities.Usuario;
import comuserservices.feignclients.CarroFeignClient;
import comuserservices.feignclients.MotoFeignClient;
import comuserservices.models.Carro;
import comuserservices.models.Moto;
import comuserservices.repository.UsuarioRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UsuarioService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UsuarioRepostory usuarioRepostory;

    public List<Usuario> getAll() {
        return usuarioRepostory.findAll();
    }

    public Usuario getUsuarioById(int id) {
        return  usuarioRepostory.findById(id).orElse(null);
    }

    public Usuario saveUsuario(Usuario usuario) {
        Usuario nuevoUsuario = usuarioRepostory.save(usuario);
        return nuevoUsuario;
    }

    //Todo: Implementando el obtener con RestTemplate
    public List<Carro> getCarros(int usuarioId) {
        List carros = restTemplate.getForObject("http://carro-service/carro/usuario/" + usuarioId, List.class);
        return carros;
    }

    public List<Moto> getMotos(int usuarioId) {
        List motos = restTemplate.getForObject("http://moto-service/moto/usuario/" + usuarioId, List.class);
        return motos;
    }
    //Todo: =======================================================================================================

    //Todo: Implementando el guardar con FeignClients
    @Autowired
    private CarroFeignClient carroFeignClient;

    @Autowired
    private MotoFeignClient motoFeignClient;

    public Carro saveCarro(int usuarioId, Carro carro) {
        carro.setUsuarioId(usuarioId);
        Carro nuevoCarro = carroFeignClient.save(carro);
        return nuevoCarro;
    }

    public Moto saveMoto(int usuarioId, Moto moto) {
        moto.setUsuarioId(usuarioId);
        Moto nuevaMoto = motoFeignClient.save(moto);
        return nuevaMoto;
    }

    public Map<String, Object> getUsuariosAndVehiculos(int usuarioId) {
        Map<String, Object> resultado = new HashMap<>();
        Usuario usuario = usuarioRepostory.findById(usuarioId).orElse(null);

        if (usuario == null) {
            resultado.put("Mensaje", "El usuario no existe");
            return resultado;
        }
        resultado.put("Usuario", usuario);

        List<Carro> carros = carroFeignClient.getCarros(usuarioId);
        if (carros.isEmpty()) resultado.put("Carros", "El usuario no tiene carros");
        else resultado.put("Carros", carros);

        List<Moto> motos = motoFeignClient.getMotos(usuarioId);
        if (motos.isEmpty()) resultado.put("Motos", "El usuario no tiene motos");
        else resultado.put("Motos", motos);

        return resultado;
    }
    //Todo: =======================================================================================================
}

