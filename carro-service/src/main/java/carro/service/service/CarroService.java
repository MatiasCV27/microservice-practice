package carro.service.service;

import carro.service.entities.Carro;
import carro.service.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    public List<Carro> getAll() {
        return carroRepository.findAll();
    }

    public Carro getCarroById(int id) {
        return  carroRepository.findById(id).orElse(null);
    }

    public Carro saveCarro(Carro carro) {
        Carro nuevoCarro = carroRepository.save(carro);
        return nuevoCarro;
    }

    public List<Carro> findByUsuarioId(int usuarioId) {
        return carroRepository.findByUsuarioId(usuarioId);
    }
}
