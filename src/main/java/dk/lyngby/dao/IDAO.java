package dk.lyngby.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface IDAO<T> {
    T create(T t);
    T update(T t);
    void delete(T t);
    T getById(Long id);
    List<T> getAll();

}