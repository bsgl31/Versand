package versand.core.gson;

import com.google.gson.*;
import versand.core.Utils;

import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * De/Serialisiert ein {@link LocalDate} im YYYY-MM-DD Format, um es mit {@link Gson} zu speichern.
 */
public class LocalDateSerializer implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    @Override
    public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(Utils.localDateToString(localDate));
    }

    @Override
    public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return Utils.localDateFromString(jsonElement.getAsString());
    }

}
