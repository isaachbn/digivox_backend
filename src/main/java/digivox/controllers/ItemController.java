package digivox.controllers;

import digivox.dtos.ItemDTO;
import digivox.models.Item;
import digivox.models.Type;
import digivox.services.ItemService;
import digivox.services.TypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api/items"})
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final TypeService typeService;

    @GetMapping
    public List findAll() {
        return itemService.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id) {
        Optional<Item> itemMode = itemService.findById(id);

        return itemMode
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody @Valid ItemDTO itemDTO) {
        Optional<Type> typeModel = typeService.findById(itemDTO.getType());

        return typeModel
                .map(type -> {
                    Item item = itemDTO.convertModel();
                    item.setType(type);
                    itemService.save(item);
                    return new ResponseEntity<>(item, HttpStatus.CREATED);
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping(value="/{id}")
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody @Valid ItemDTO itemDTO) {
        Optional<Type> typeModel = typeService.findById(itemDTO.getType());

        return typeModel
                .map(type -> itemService.findById(id)
                        .map(item -> {
                            item.setName(itemDTO.getName());
                            item.setPrice(itemDTO.getPrice());
                            item.setType(type);
                            Item updated = itemService.save(item);
                            return ResponseEntity.ok().body(updated);
                        }).orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<Item> itemModel = itemService.findById(id);

        return itemModel
                .map(record -> {
                    itemService.remove(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
