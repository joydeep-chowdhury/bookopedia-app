package com.joydeep.poc.services;

import org.springframework.ui.Model;

public interface EntityService<E> {
    String getEntityPageById(String id, Model model);
}
