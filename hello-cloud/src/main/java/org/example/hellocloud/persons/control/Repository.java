package org.example.hellocloud.persons.control;

import java.util.List;

public interface Repository<T> {
    
    public T insert(T e) throws Exception;
    public T findById(Long id);
    public T update(T e) throws Exception;
    public T delete(T e) throws Exception;
    public T delete(Long id) throws Exception;
    public List<T> listAll();
    
}
