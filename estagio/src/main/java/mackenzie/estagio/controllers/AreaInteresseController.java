package mackenzie.estagio.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

import mackenzie.estagio.entities.*;
import mackenzie.estagio.repositories.*;

@RestController
@RequestMapping("/api/areas-interesse")
@CrossOrigin(origins = "*")
public class AreaInteresseController {
    
    @Autowired
    private AreaInteresseRepository areaRepository;

    @GetMapping
    public ResponseEntity<List<AreaInteresse>> getAllAreas() {
        List<AreaInteresse> areas = new ArrayList<>();
        areaRepository.findAll().forEach(areas::add);
        return ResponseEntity.ok(areas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaInteresse> getAreaById(@PathVariable Long id) {
        AreaInteresse area = areaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Área de interesse não encontrada com id: " + id));
        return ResponseEntity.ok(area);
    }
    
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<AreaInteresse> getAreaByTitulo(@PathVariable String titulo) {
        AreaInteresse area = areaRepository.findByTitulo(titulo);
        if (area == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Área de interesse não encontrada com título: " + titulo);
        }
        return ResponseEntity.ok(area);
    }

    @PostMapping
    public ResponseEntity<AreaInteresse> createArea(@RequestBody AreaInteresse area) {
        // Validar título duplicado
        if (areaRepository.existsByTitulo(area.getTitulo())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Já existe uma área com este título");
        }
        
        AreaInteresse savedArea = areaRepository.save(area);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaInteresse> updateArea(
            @PathVariable Long id, 
            @RequestBody AreaInteresse areaAtualizada) {
        
        AreaInteresse area = areaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Área de interesse não encontrada com id: " + id));
        
        // Verificar se o novo título já existe (exceto para a própria área)
        AreaInteresse areaComMesmoTitulo = areaRepository.findByTitulo(areaAtualizada.getTitulo());
        if (areaComMesmoTitulo != null && !areaComMesmoTitulo.getId().equals(id)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Já existe uma área com este título");
        }
        
        area.setTitulo(areaAtualizada.getTitulo());
        area.setDescricao(areaAtualizada.getDescricao());
        
        AreaInteresse updated = areaRepository.save(area);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArea(@PathVariable Long id) {
        if (!areaRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Área de interesse não encontrada com id: " + id);
        }
        areaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}