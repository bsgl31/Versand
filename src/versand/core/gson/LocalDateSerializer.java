package versand.core.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * De/Serialisiert ein {@link LocalDate} im YYYY-MM-DD Format, um es mit {@link Gson} zu speichern.
 */
public class LocalDateSerializer implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    @Override
    public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(localDate.getYear() + "-" + localDate.getMonthValue() + "-" + localDate.getDayOfMonth());
    }

    @Override
    public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String[] split = jsonElement.getAsString().split("-");
        return LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

}
