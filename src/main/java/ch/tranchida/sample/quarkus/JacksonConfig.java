package ch.tranchida.sample.quarkus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

@Singleton
public class JacksonConfig implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper mapper) {
        // Enable pretty-print (indentation) for JSON responses
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
}

