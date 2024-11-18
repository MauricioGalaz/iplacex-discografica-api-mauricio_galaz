package org.iplacex.proyectos.discografia.artistas;

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
public class ArtistaController {

    private final IArtistaRepository artistaRepository;

    @Autowired
    public ArtistaController(IArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista nuevoArtista = artistaRepository.save(artista);
        return new ResponseEntity<>(nuevoArtista, HttpStatus.CREATED);
    }

    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {
        List<Artista> artistas = artistaRepository.findAll();
        return new ResponseEntity<>(artistas, HttpStatus.OK);
    }

    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetArtistaRequest(@PathVariable String id) {
        Optional<Artista> artista = artistaRepository.findById(id);
        if (artista.isPresent()) {
            return new ResponseEntity<>(artista.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Artista no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artista artistaActualizado) {
        if (artistaRepository.existsById(id)) {
            artistaActualizado.setId(id); 
            Artista artista = artistaRepository.save(artistaActualizado);
            return new ResponseEntity<>(artista, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Artista no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable String id) {
        if (artistaRepository.existsById(id)) {
            artistaRepository.deleteById(id);
            return new ResponseEntity<>("Artista eliminado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Artista no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
