package digivox.services;

import digivox.models.Item;
import digivox.repositories.ItemRepository;
import digivox.validators.FieldValueExists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemService implements FieldValueExists {
    private final ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public void remove(Long id) {
        itemRepository.deleteById(id);
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
