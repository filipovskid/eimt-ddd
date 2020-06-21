package com.filipovski.parkingguidance.sharedkernel.domain.base;

import java.util.Objects;
import java.util.UUID;

import lombok.Getter;
import org.springframework.lang.NonNull;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public class DomainObjectId implements ValueObject {
    private String id;

    public DomainObjectId(String id) {
        this.id = id;
    }

    @NonNull
    public static <ID extends DomainObjectId> ID randomId(@NonNull Class<ID> idClass) {
        Objects.requireNonNull(idClass, "idClass must not be null!");

        try {
            return idClass.getConstructor(String.class).newInstance(UUID.randomUUID().toString());
        } catch (Exception e) {
            throw new RuntimeException("Could not create a new instance of " + idClass, e);
        }
    }
}
