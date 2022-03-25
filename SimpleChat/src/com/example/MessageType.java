package com.example;

/**
 * Класс отвечает за тип сообщений пересылаемых между клиентом и сервером
 *
 * Основные моменты протокола:
 * - Когда новый клиент хочет подсоединиться к серверу, сервер должен запросить имя клиента.
 * - Когда клиент получает запрос имени от сервера он должен отправить свое имя серверу.
 * - Когда сервер получает имя клиента он должен принять это имя или запросить новое.
 * - Когда новый клиент добавился к чату, сервер должен сообщить остальным участникам о новом клиенте.
 * - Когда клиент покидает чат, сервер должен сообщить остальным участникам об этом.
 * - Когда сервер получает текстовое сообщение от клиента, он должен переслать его всем остальным участникам чата.
 */
public enum MessageType {
    NAME_REQUEST,
    USER_NAME,
    NAME_ACCEPTED,
    TEXT,
    USER_ADDED,
    USER_REMOVED
}
