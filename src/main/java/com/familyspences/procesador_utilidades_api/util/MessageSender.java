package com.familyspences.procesador_utilidades_api.util;

public interface MessageSender<T> {
    void execute(T message, Long idMessage);

}
