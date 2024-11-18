package org.iplacex.proyectos.discografia.discos;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/discografia")
public class DiscoController {

    private final IDiscoRepository discoRepository;
    private final IArtistaRepository artistaRepository;

    @Autowired
    public DiscoController(IDiscoRepository discoRepository, IArtistaRepository artistaRepository) {
        this.discoRepository = discoRepository;
        this.artistaRepository = artistaRepository;
    }

    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {
        if (!artistaRepository.existsById(disco.getIdArtista())) {
            return new ResponseEntity<>("El artista no existe", HttpStatus.NOT_FOUND);
        }
        Disco nuevoDisco = discoRepository.save(disco);
        return new ResponseEntity<>(nuevoDisco, HttpStatus.CREATED);
    }

    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        List<Disco> discos = discoRepository.findAll();
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }

    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable String id) {
        Optional<Disco> disco = discoRepository.findById(id);
        if (disco.isPresent()) {
            return new ResponseEntity<>(disco.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Disco no encontrado", HttpStatus.NOT_FOUND);
        }
    }
 
    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) {
        List<Disco> discos = discoRepository.findDiscosByIdArtista(id);
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }
}






  
