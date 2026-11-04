package org.wicketstuff.datatables;

import java.io.Serializable;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

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

    public static class SortSerializer extends ValueSerializer<Sort> {

        @Override
        public void serialize(Sort sort, JsonGenerator jsonGenerator, SerializationContext ctx) throws JacksonException {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeNumber(sort.column);
            jsonGenerator.writeString(sort.direction.name().toLowerCase());
            jsonGenerator.writeEndArray();
        }
    }
}
