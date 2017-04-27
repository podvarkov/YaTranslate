package com.example.maksimpodvarkov.yandextranslater;

/**
 * Created by maksim.podvarkov on 27.04.2017.
 */

    import android.util.Log;

    import com.google.gson.JsonArray;
    import com.google.gson.JsonDeserializationContext;
    import com.google.gson.JsonDeserializer;
    import com.google.gson.JsonElement;
    import com.google.gson.JsonObject;
    import com.google.gson.JsonParseException;

    import java.lang.reflect.Type;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

/**
     * Copyright (c) 2017 YandexTranslater
     *
     * @author iYaroslav
     */
    public class LanguagesContainer {

        private final HashMap<String, List<String>> directions;
        private final HashMap<String, String> languages; //TODO parse languages!

        private LanguagesContainer(HashMap<String, List<String>> directions,HashMap<String ,String> langs) {
            this.directions = directions;
            this.languages=langs;
        }

    public HashMap<String, String> getLanguages() {
        return languages;
    }

    String getLanguage(String key){
            return languages.get(key).toString();

        }
        @Override
        public String toString() {
            return "LanguagesContainer{" +
                    "directions=" + directions +
                    '}'+"\n" +
                    "langs="+languages+"}";
        }

        public static class UserDeserializer implements JsonDeserializer<LanguagesContainer> {

            @Override
            public LanguagesContainer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                final JsonObject object = json.getAsJsonObject();

                Log.i("JSON", json.toString());

                final HashMap<String, List<String>> directions = new HashMap<>();
                final HashMap<String,String> languages = new HashMap<>();
                for (JsonElement element : object.get("dirs").getAsJsonArray()) {
                    final String item = element.getAsString();
                    final int pos = item.indexOf('-');
                    final String key = item.substring(0, pos);
                    final String value = item.substring(pos + 1, item.length());

                    if (directions.containsKey(key)) {
                        directions.get(key).add(value);
                    } else {
                        directions.put(key, new ArrayList<String>() {{
                            add(value);
                        }});


                    }
                }
                for (Map.Entry<String, JsonElement> item:object.get("langs").getAsJsonObject().entrySet()
                     ) {
                    languages.put(item.getKey(),item.getValue().getAsString());
                    Log.d("Entry",item.getKey());
                    Log.d("Entry",item.getValue().getAsString());
                }

                return new LanguagesContainer(directions,languages);
            }
        }
    }


