package com.hs.hscontrolinformation.util;

import java.util.List;

public interface ServiceTemplate<T> {

    public List<T> listar();
    public void guardar(T data);
    public void eliminar(T data);
    public T encontrar(T data);

}
