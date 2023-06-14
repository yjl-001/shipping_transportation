package com.example.config_service.utils;

public class Message<T> {
    private Long key;
    private T data;

    public Message(Long key, T data){
        this.key = key;
        this.data = data;
    }

    public Long getKey(){
        return this.key;
    }

    public void setKey(Long key){
        this.key = key;
    }

    public T getData(){
        return this.data;
    }

    public void setData(T data){
        this.data = data;
    }


}
