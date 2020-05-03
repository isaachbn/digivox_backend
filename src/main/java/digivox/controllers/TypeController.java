package digivox.controllers;

import digivox.dtos.TypeDTO;
import digivox.models.Type;
import digivox.services.TypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api/types"})
@AllArgsConstructor
public class TypeController {
    private final TypeService typeService;

    @GetMapping
    public List findAll() {
        return typeService.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id) {
        Optional<Type> typeModel = typeService.findById(id);

        return typeModel
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Type> create(@RequestBody @Valid TypeDTO typeDTO) {
        Type typeModel =  typeService.save(typeDTO.convertModel());

        return new ResponseEntity<>(typeModel, HttpStatus.CREATED);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody @Valid TypeDTO typeDTO) {
        Optional<Type> typeOptional = typeService.findById(id);

        return typeOptional
                .map(record -> {
                    record.setName(typeDTO.getName());
                    Type updated = typeService.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<Type> typeOptional = typeService.findById(id);
        return typeOptional
                .map(type -> {
                    typeService.remove(type);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
