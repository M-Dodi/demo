package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libro")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping
    public List<Libro> findAllBooks() {
        return libroRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> findBookById(@PathVariable(value = "id") long id) {
        Optional<Libro> libro = libroRepository.findById(id);

        if(libro.isPresent()) {
            return ResponseEntity.ok().body(libro.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Libro saveBook(@Validated @RequestBody Libro libro) {
        return libroRepository.save(libro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> updateBook(@PathVariable(value = "id") long id,
                                           @Validated @RequestBody Libro libroDetails) {
        Optional<Libro> libro = libroRepository.findById(id);

        if(libro.isPresent()) {
            Libro existingLibro = libro.get();
            existingLibro.setNome(libroDetails.getNome());
            existingLibro.setAutore(libroDetails.getAutore());
            
            final Libro updatedLibro = libroRepository.save(existingLibro);
            return ResponseEntity.ok(updatedLibro);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "id") long id) {
        Optional<Libro> libro = libroRepository.findById(id);

        if(libro.isPresent()) {
            libroRepository.delete(libro.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}