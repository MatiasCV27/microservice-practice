package motoservice.service;

import motoservice.entities.Moto;
import motoservice.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    public List<Moto> getAll() {
        return motoRepository.findAll();
    }

    public Moto getMotoById(int id) {
        return  motoRepository.findById(id).orElse(null);
    }

    public Moto saveMoto(Moto moto) {
        Moto nuevaMoto = motoRepository.save(moto);
        return nuevaMoto;
    }

    public List<Moto> findByUsuarioId(int usuarioId) {
        return motoRepository.findByUsuarioId(usuarioId);
    }

}
