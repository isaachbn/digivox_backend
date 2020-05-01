package digivox.services;

import digivox.dtos.TypeDTO;
import digivox.models.Type;
import digivox.repositories.TypeRepository;
import digivox.validators.FieldValueExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeService implements FieldValueExists {
    private final TypeRepository typeRepository;

    @Autowired
    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    public ResponseEntity findById(Long id) {
        return typeRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Type> create(TypeDTO typeDTO) {
        Type typeModel = typeRepository.save(typeDTO.convertModel());
        return new ResponseEntity<>(typeModel, HttpStatus.CREATED);
    }

    public ResponseEntity<?> remove(Long id) {
        return typeRepository.findById(id)
                .map(record -> {
                    typeRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity update(Long id, TypeDTO typeDTO) {
        return typeRepository.findById(id)
                .map(record -> {
                    record.setName(typeDTO.getName());
                    Type updated = typeRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
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
