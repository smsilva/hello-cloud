package org.example.hellocloud.infra;

import java.util.List;

public interface BaseRepository<T> {
    
    public T insert(T e) throws Exception;
    public T findById(Long id);
    public T update(T e) throws Exception;
    public T delete(T e) throws Exception;
    public T delete(Long id) throws Exception;
    public List<T> listAll();
    
}
