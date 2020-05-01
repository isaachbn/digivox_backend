package digivox.controllers;

import digivox.dtos.TypeDTO;
import digivox.models.Type;
import digivox.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping({"/api/types"})
public class TypeController {
    private final TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public List findAll() {
        return typeService.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id){
        return typeService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Type> create(@RequestBody @Valid TypeDTO typeDTO) {
        return typeService.create(typeDTO);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody @Valid TypeDTO typeDTO) {
        return typeService.update(id, typeDTO);
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        return typeService.remove(id);
    }
}
