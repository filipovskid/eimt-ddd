package com.filipovski.parkingguidance.sharedkernel.domain.base;

import com.sun.istack.Nullable;

import java.io.Serializable;

public interface IdentifiableDomainObject<ID extends Serializable> extends DomainObject {
    @Nullable
    ID id();
}
