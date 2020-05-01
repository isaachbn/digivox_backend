package digivox.controllers;

import digivox.dtos.ItemDTO;
import digivox.models.Item;
import digivox.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping({"/api/items"})
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List findAll() {
        return itemService.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id){
        return itemService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody @Valid ItemDTO itemDTO) {
        return itemService.create(itemDTO);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody @Valid ItemDTO itemDTO) {
        return itemService.update(id, itemDTO);
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        return itemService.remove(id);
    }
}
