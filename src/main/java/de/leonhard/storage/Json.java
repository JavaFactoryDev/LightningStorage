package de.leonhard.storage;

import de.leonhard.util.JsonUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.*;

public class Json extends StorageCreator implements StorageBase {
    private JSONObject object;
    private File file;


    /**
     * Creates a .json file where you can put your data in.+
     *
     * @param name Name of the .json file
     * @param path Absolute path, where the .json file should be created.
     */

    public Json(final String name, final String path) {
        File newFile = new File(path + File.separator + name + ".json");

        if (!newFile.exists()) {
            try {
                create(path, name, FileType.JSON);
                this.file = super.file;
                object = new JSONObject();
                Writer writer = new PrintWriter(new FileWriter(file.getAbsolutePath()));
                writer.write(object.toString(2));
                writer.close();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.file = newFile;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
        JSONTokener tokener = new JSONTokener(fis);
        object = new JSONObject(tokener);
    }

    //TODO IMMER auf TODOS überprüfen.


    /**
     * Sets a value to the json if the file doesn't already contain the value (Not mix up with Bukkit addDefault)
     * Uses {@link JSONObject}
     *
     * @param key   Key to set the value
     * @param value Value to set
     */

    @Override
    public void setDefault(String key, Object value) {
        if (contains(key)) {
            return;
        }
        set(key, value);
    }


    private void reload() {

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException | NullPointerException e) {
            System.err.println("Exception while reading Json");
            e.printStackTrace();
        }
        JSONTokener tokener = new JSONTokener(fis);
        object = new JSONObject(tokener);

    }


    /**
     * Gets a long from a JSON-File
     * Uses {@link JSONObject}
     *
     * @param key Path to long in JSON-FILE
     * @return long from JSON
     */

    @Override
    public long getLong(final String key) {
        reload();
        if (!contains(key))
            return 0;

        return (long) get(key);

    }

    /**
     * Get a double from a JSON-File
     * Uses {@link JSONObject}
     *
     * @param key Path to double in JSON-File
     * @return Double from JSON
     */

    @Override
    public double getDouble(final String key) {
        reload();

        if (!contains(key))
            return 0;

        return (get(key) instanceof Integer) ? (double) (int) get(key) : (double) get(key);//TrobleShooting: Integer not castable to Double
        // -> Wrapper class

    }

    /**
     * Get a float from a JSON-File
     * Uses {@link JSONObject}
     *
     * @param key Path to float in JSON-File
     * @return Float from JSON
     */


    @Override
    public float getFloat(final String key) {
        reload();

        if (!contains(key))
            return 0;

        if (key.contains(".")) {
            if (get(key) instanceof Double) {
                return (float) (double) get(key);
            }
            return (get(key) instanceof Integer) ? (float) (int) get(key) : (int) get(key);//TrobleShooting: Integer not castable to Double -> Wrapper class
            //
        }
        return (object.get(key) instanceof Integer) ? (float) (int) object.get(key) : (float) object.get(key);

    }

    //TODO getter Method -> With NestedObject

    /**
     * Gets a int from a JSON-File
     * <p>
     * Uses {@link JSONObject}
     *
     * @param key Path to int in JSON-File
     * @return Int from JSON
     */
    @Override
    public int getInt(final String key) {
        reload();

        if (!contains(key))
            return 0;

        return (int) get(key);

    }

    /**
     * Get a byte from a JSON-File
     * Uses {@link JSONObject}
     *
     * @param key Path to byte in JSON-File
     * @return Byte from JSON
     */

    @Override
    public byte getByte(final String key) {
        reload();

        if (!contains(key))
            return 0;


        return (byte) get(key);

    }

    /**
     * Get a boolean from a JSON-File
     * Uses {@link JSONObject}
     *
     * @param key Path to boolean in JSON-File
     * @return Boolean from JSON
     */

    @Override
    public boolean getBoolean(final String key) {
        reload();

        if (!contains(key))
            return false;


        return getBoolean(key);

    }


    /**
     * Get String List
     * Uses {@link JSONObject}
     *
     * @param key Path to String  in Json-File
     * @return String from Json
     */

    @Override
    public String getString(final String key) {
        reload();

        if (!contains(key))
            return null;


        return (String) get(key);

    }


    /**
     * Get a List from a JSON-File by key
     * Uses {@link YamlObject}
     *
     * @param key Path to StringList in JSON-File
     * @return String-List
     */

    @Override
    public List<?> getList(final String key) {
        reload();
        if (!contains(key))
            return new ArrayList<>();


        final Object object = get(key);
        final JSONArray ja = new JSONArray(object.toString());
        List<Object> list = new ArrayList<>();
        for (Object a : ja) {
            list.add(a);
        }
        return list;

    }

    /**
     * Get a String-List from a JSON-File by key
     * Uses {@link JSONObject}
     *
     * @param key Path to String List in YAML-File
     * @return String-List
     */

    @Override
    public List<String> getStringList(final String key) {
        reload();

        if (!contains(key))
            return new ArrayList<>();


        return (List<String>) getList(key);

    }

    /**
     * Get a Integer-List from a JSON-File by key
     * Uses {@link JSONObject}
     *
     * @param key Path to Integer List in JSON-File
     * @return Integer-List
     */

    @Override
    public List<Integer> getIntegerList(final String key) {
        reload();
        if (!contains(key))
            return new ArrayList<>();

        return (List<Integer>) getList(key);
    }

    /**
     * Get a Byte-List from a JSON-File by key
     * Uses {@link JSONObject}
     *
     * @param key Path to Byte List in JSON-File
     * @return Byte-List
     */

    @Override
    public List<Byte> getByteList(final String key) {
        reload();

        if (!contains(key))
            return new ArrayList<>();

        return (List<Byte>) getList(key);
    }


    /**
     * Get a Long-List from a JSON-File by key
     * Uses {@link JSONObject}
     *
     * @param key Path to Long List in JSON-File
     * @return Long-List
     */

    @Override
    public List<Long> getLongList(final String key) {
        reload();

        if (!contains(key))
            return new ArrayList<>();

        return (List<Long>) getList(key);
    }

    /**
     * Gets a Map by key
     * Although used to get nested objects {@link Json}
     *
     * @param key Path to Map-List in JSON
     * @return Map
     */

    @Override//GetMap
    public Map getMap(final String key) {
        reload();

        if (!contains(key))
            return new HashMap();

        Object map;
        try {
            map = get(key);
        } catch (JSONException e) {
            return new HashMap<>();
        }
        if (map instanceof Map) {
            return (Map<?, ?>) object.get(key);
        } else if (map instanceof JSONObject) {
            return JsonUtil.jsonToMap((JSONObject) map);
        }
        throw new IllegalArgumentException("Json does not contain: '" + key + "'.");
    }


    @Override
    public void set(final String key, final Object value) {
        synchronized (this) {
            if (key.contains(".")) {
                String[] parts = key.split("\\.");
                HashMap keyMap = new HashMap();

                int j = 0;
                for (int i = parts.length - 1; i > 0; i--) {

                    final String part = JsonUtil.getFirst(key, j + 1);

                    keyMap = (get(part) == null) ? new HashMap() : (HashMap) JsonUtil.jsonToMap((JSONObject) get(part)); //NICHT IN DER SCHLEIFE

                    if (i == parts.length - 1) {
                        keyMap.put(parts[parts.length - 1], value);
                    } else {
                        HashMap preResult = new HashMap();
                        preResult.put(parts[i], keyMap);
                        keyMap = preResult;
                    }
                    System.out.println(keyMap);

                    j++;
                }
                object.put(parts[0], keyMap);
                try {
                    Writer writer = new PrintWriter(new FileWriter(file.getAbsolutePath()));
                    writer.write(object.toString(2));
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            object.put(key, value);
            try {
                Writer writer = new PrintWriter(new FileWriter(file.getAbsolutePath()));
                writer.write(object.toString(2));
                writer.close();
            } catch (IOException e) {
                System.err.println("Couldn' t set " + key + " " + value);
                e.printStackTrace();
            }
        }
    }


    @Override
    public <T> T getOrSetDefault(final String path, T def) {
        if (!contains(path)) {
            set(path, def);
            return def;
        } else {
            return (T) object.get(path);
        }
    }

    public Object get(final String key) {

        if (key.contains(".")) {
            String[] parts = key.split("\\.");
            Map preResult = (get(parts[0]) == null) ? new HashMap() : (HashMap) JsonUtil.jsonToMap((JSONObject) get(parts[0]));
            for (int i = 1; i < parts.length; i++) {
                if (!(preResult.get(parts[i]) instanceof HashMap))
                    return preResult.get(parts[i]);
                preResult = (HashMap) preResult.get(parts[i]);
            }
        }
        return object.has(key) ? object.get(key) : null;
    }

    @Override
    public boolean contains(String key) {
        reload();
        if (key.contains(".")) {
            String[] parts = key.split("\\.");
            Map preResult = (get(parts[0]) == null) ? new HashMap() : (HashMap) JsonUtil.jsonToMap((JSONObject) get(parts[0]));
            for (int i = 1; i < parts.length - 1; i++) {
                if (!preResult.containsKey(parts[i]))
                    return false;
                if (!(get(parts[i]) instanceof HashMap))
                    return false;
                preResult = (HashMap) preResult.get(parts[i]);
            }
            return true;
        }
        return object.has(key);
    }

    public File getFile() {
        return file;
    }

    @Override
    public String getFilePath() {
        return file.getAbsolutePath();
    }
}
