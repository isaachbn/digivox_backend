package digivox.services;

import digivox.dtos.ItemDTO;
import digivox.models.Item;
import digivox.repositories.ItemRepository;
import digivox.repositories.TypeRepository;
import digivox.validators.FieldValueExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService implements FieldValueExists {
    private final ItemRepository itemRepository;
    private final TypeRepository typeRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, TypeRepository typeRepository) {
        this.itemRepository = itemRepository;
        this.typeRepository = typeRepository;
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public ResponseEntity findById(Long id) {
        return itemRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Item> create(ItemDTO itemDTO) {
        return typeRepository.findById(itemDTO.getType())
                .map(type -> {
                    Item item = itemDTO.convertModel();
                    item.setType(type);
                    itemRepository.save(item);
                    return new ResponseEntity<>(item, HttpStatus.CREATED);
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    public ResponseEntity<?> remove(Long id) {
        return itemRepository.findById(id)
                .map(record -> {
                    itemRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity update(Long id, ItemDTO itemDTO) {
        return typeRepository.findById(itemDTO.getType())
                .map(type -> {
                    return itemRepository.findById(id)
                            .map(item -> {
                                item.setName(itemDTO.getName());
                                item.setPrice(itemDTO.getPrice());
                                item.setType(type);
                                Item updated = itemRepository.save(item);
                                return ResponseEntity.ok().body(updated);
                            }).orElse(ResponseEntity.notFound().build());
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
        if (!fieldName.equals("item")) {
            return true;
        }

        if (value == null || value instanceof String) {
            return true;
        }

        Optional<Item> optionalType = this.itemRepository.findById(Long.parseLong(value.toString()));

        return !optionalType.isPresent();
    }

    public Boolean rented(Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        if (!optionalItem.isPresent()) {
            return false;
        }

        Item item = optionalItem.get();

        if (item.getRented()) {
            return false;
        }

        item.setRented(true);
        itemRepository.save(item);

        return true;
    }

    public Boolean unRented(Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        if (!optionalItem.isPresent()) {
            return false;
        }

        Item item = optionalItem.get();
        item.setRented(false);
        itemRepository.save(item);

        return false;
    }
}
