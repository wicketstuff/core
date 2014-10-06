package org.wicketstuff.datatables;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *
 */
@JsonSerialize(using = Sort.SortSerializer.class)
public class Sort implements Serializable {

    public static enum Direction {
        ASC,
        DESC
    }

    private final int column;
    private final Direction direction;

    public Sort(int column, Direction direction) {
        this.column = column;
        this.direction = direction;
    }

    public static class SortSerializer extends JsonSerializer<Sort> {

        @Override
        public void serialize(Sort sort, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {

            jsonGenerator.writeStartArray();
            jsonGenerator.writeNumber(sort.column);
            jsonGenerator.writeString(sort.direction.name().toLowerCase());
            jsonGenerator.writeEndArray();
        }
    }
}
