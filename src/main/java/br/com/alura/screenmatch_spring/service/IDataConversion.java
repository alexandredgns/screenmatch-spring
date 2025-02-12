package br.com.alura.screenmatch_spring.service;

public interface IDataConversion {

    <T> T getData(String json, Class<T> tClass );

}
