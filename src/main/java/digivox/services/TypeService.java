package digivox.services;

import digivox.models.Type;
import digivox.repositories.TypeRepository;
import digivox.validators.FieldValueExists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TypeService implements FieldValueExists {
    private final TypeRepository typeRepository;

    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    public Optional<Type> findById(Long id) {
        return typeRepository.findById(id);
    }

    public Type save(Type type) {
        return typeRepository.save(type);
    }

    public void remove(Type type) {
        typeRepository.delete(type);
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
        if (!fieldName.equals("type")) {
            return true;
        }

        if (value == null || value instanceof String) {
            return true;
        }

        Optional<Type> optionalType = this.typeRepository.findById(Long.parseLong(value.toString()));

        return !optionalType.isPresent();
    }
}
