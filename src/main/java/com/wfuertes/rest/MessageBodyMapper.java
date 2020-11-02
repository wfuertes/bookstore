package com.wfuertes.rest;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class MessageBodyMapper implements MessageBodyWriter<Object>, MessageBodyReader<Object> {

    private final Gson gson;

    @Inject
    public MessageBodyMapper(Gson gson) {
        this.gson = gson;
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return mediaType.equals(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public void writeTo(Object object,
                        Class<?> clazz,
                        Type type,
                        Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap<String, Object> multivaluedMap,
                        OutputStream outputStream) throws WebApplicationException, IOException {

        final String json = gson.toJson(object, type);
        outputStream.write(json.getBytes());
    }

    @Override
    public boolean isReadable(Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType) {
        return isWriteable(clazz, type, annotations, mediaType);
    }

    @Override
    public Object readFrom(Class<Object> clazz,
                           Type type,
                           Annotation[] annotations,
                           MediaType mediaType,
                           MultivaluedMap<String, String> multivaluedMap,
                           InputStream inputStream) throws WebApplicationException {
        return gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), type);
    }
}
