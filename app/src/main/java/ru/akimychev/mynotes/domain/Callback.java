package ru.akimychev.mynotes.domain;

public interface Callback <T> {

    void onSuccess (T data);

    void onError (Throwable exception);
}
