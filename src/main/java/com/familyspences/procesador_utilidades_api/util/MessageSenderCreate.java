package com.familyspences.procesador_utilidades_api.util;

public interface MessageSenderCreate <T> {
    String execute(T message, Long idMessage);
}