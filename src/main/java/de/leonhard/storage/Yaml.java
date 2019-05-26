package de.leonhard.storage;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Yaml extends StorageCreator implements StorageBase {

    private File file;
    private YamlObject yamlObject;
    private String pathPrefix;
    private boolean autoReload = true;


    /*
    Structure:
    -Constructors:
    -Setters
    -Getters
    -private Methods (Reloaders etc.)
    -



     */

    public Yaml(String name, String path) {

        try {
            create(path, name, FileType.YAML);
            this.file = super.file;
        } catch (final IOException e) {
            e.printStackTrace();
        }
        update();
    }

    public Yaml(String name, String path, boolean autoReload) {

        try {
            create(path, name, FileType.YAML);
            this.file = super.file;
        } catch (
                final IOException e) {
            e.printStackTrace();
        }
        update();
        this.autoReload = autoReload;

    }


    @Override
    public void set(String key, Object value) {
        reload();

        if (!isAutoReload())
            update();

        key = (pathPrefix == null) ? key : pathPrefix + "." + key;

        final YamlReader reader;
        synchronized (this) {
            try {
                reader = new YamlReader(new FileReader(file));
                yamlObject = new YamlObject(reader.read());
                yamlObject.put(key, value);
                YamlWriter writer = new YamlWriter(new FileWriter(file));

                writer.write(yamlObject.toHashMap());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets a value to the yaml if the file doesn't already contain the value (Not mix up with Bukkit addDefault)
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


    @Override
    public <T> T getOrSetDefault(final String path, T def) {
        reload();
        if (!contains(path)) {
            set(path, def);
            return def;
        } else {
            return (T) get(path);
        }
    }


    /**
     * Get a String from a YAML-File
     * Uses {@link YamlObject}
     *
     * @param key Path to String in YAML-File
     * @return Returns the value
     */
    @Override
    public String getString(String key) {
        reload();

        final String finalKey = (pathPrefix == null) ? key : pathPrefix + "." + key;


        if (!contains(key))
            return "";

        return yamlObject.getString(finalKey);
    }

    /**
     * Gets a long from a YAML-File
     * Uses {@link YamlObject}
     *
     * @param key Path to long in YAML-FILE
     * @return long from YAML
     */
    @Override
    public long getLong(String key) {
        reload();

        final String finalKey = (pathPrefix == null) ? key : pathPrefix + "." + key;


        if (!contains(key))
            return 0;

        return yamlObject.getLong(finalKey);
    }


    /**
     * Gets a int from a YAML-File
     * Uses {@link YamlObject}
     *
     * @param key Path to int in YAML-File
     * @return Int from YAML
     */
    @Override
    public int getInt(String key) {
        reload();

        final String finalKey = (pathPrefix == null) ? key : pathPrefix + "." + key;


        if (!contains(key))
            return 0;

        return yamlObject.getInt(finalKey);
    }

    /**
     * Get a byte from a YAML-File
     * Uses {@link YamlObject}
     *
     * @param key Path to byte in YAML-File
     * @return Byte from YAML
     */
    @Override
    public byte getByte(String key) {
        reload();

        final String finalKey = (pathPrefix == null) ? key : pathPrefix + "." + key;


        if (!contains(key))
            return 0;

        return yamlObject.getByte(finalKey);
    }

    /**
     * Get a boolean from a YAML-File
     * Uses {@link YamlObject}
     *
     * @param key Path to boolean in YAML-File
     * @return Boolean from YAML
     */
    @Override
    public boolean getBoolean(String key) {
        reload();

        final String finalKey = (pathPrefix == null) ? key : pathPrefix + "." + key;


        if (!contains(key)) {
            System.out.println("CONTAINT NICHT");
            return false;

        }

        return yamlObject.getBoolean(finalKey);
    }


    @Override
    public boolean contains(String key) {

        key = (pathPrefix == null) ? key : pathPrefix + "." + key;

        return has(key);
    }

    private boolean has(String key) {
        reload();

        if (key.contains(".")) {
            String[] parts = key.split("\\.");
            Map map = (Map) get(parts[0]);

            return yamlObject.toHashMap().containsKey(parts[0]) && map.containsKey(parts[1]);
        }

        return yamlObject.toHashMap().containsKey(key);
    }


    /**
     * Get a float from a YAML-File
     * Uses {@link YamlObject}
     *
     * @param key Path to float in YAML-File
     * @return Float from YAML
     */
    @Override
    public float getFloat(String key) {
        reload();

        final String finalKey = (pathPrefix == null) ? key : pathPrefix + "." + key;


        if (!contains(key))
            return 0;

        return yamlObject.getFloat(finalKey);
    }

    /**
     * Get a double from a YAML-File
     * Uses {@link YamlObject}
     *
     * @param key Path to double in YAML-File
     * @return Double from YAML
     */
    @Override
    public double getDouble(String key) {
        reload();

        final String finalKey = (pathPrefix == null) ? key : pathPrefix + "." + key;


        if (!contains(key))
            return 0;

        return yamlObject.getDouble(finalKey);
    }

    /**
     * Get a List from a YAML-File
     * Uses {@link YamlObject}
     *
     * @param key Path to List in YAML-File
     * @return List
     */
    @Override
    public List<?> getList(String key) {
        reload();

        final String finalKey = (pathPrefix == null) ? key : pathPrefix + "." + key;

        if (!contains(key))
            return new ArrayList<>();

        if (yamlObject.get(finalKey) instanceof String)
            return new ArrayList<>(Arrays.asList(((String) yamlObject.get(finalKey)).split("-")));


        return (List) yamlObject.get(key);
    }

    /**
     * Get String List
     * Uses {@link YamlObject}
     *
     * @param key Path to String List in YAML-File
     * @return List
     */
    @Override
    public List<String> getStringList(String key) {
        reload();

        final String finalKey = (pathPrefix == null) ? key : pathPrefix + "." + key;


        if (!contains(key))
            return new ArrayList<>();

        return (List<String>) yamlObject.get(finalKey);

    }

    /**
     * Get a IntegerList from a YAML-File
     * Uses {@link YamlObject}
     *
     * @param key Path to Integer-List in YAML-File
     * @return Integer-List
     */
    @Override
    public List<Integer> getIntegerList(String key) {
        reload();

        final String finalKey = (pathPrefix == null) ? key : pathPrefix + "." + key;


        if (!contains(key))
            return new ArrayList<>();

        return (List<Integer>) yamlObject.get(finalKey);

    }

    /**
     * Get a Byte-List from a YAML-File
     * Uses {@link YamlObject}
     *
     * @param key Path to Byte-List from YAML-File
     * @return Byte-List
     */
    @Override
    public List<Byte> getByteList(String key) {
        reload();

        final String finalKey = (pathPrefix == null) ? key : pathPrefix + "." + key;


        if (!contains(key))
            return new ArrayList<>();

        return (List<Byte>) yamlObject.get(finalKey);
    }

    /**
     * Get a Long-List from a YAML-File
     * Uses {@link YamlObject}
     *
     * @param key Path to Long-List to YAML-File
     * @return Long-List
     */
    @Override
    public List<Long> getLongList(String key) {
        reload();

        final String finalKey = (pathPrefix == null) ? key : pathPrefix + "." + key;


        if (!contains(key))
            return new ArrayList<>();

        return (List<Long>) yamlObject.get(finalKey);
    }


    /**
     * Gets a Map by key
     * Although used to get nested objects {@link Yaml}
     *
     * @param key Path to Map-List in JSON
     * @return Map
     */


    @Override
    public Map getMap(String key) {
        reload();

        final String finalKey = (pathPrefix == null) ? key : pathPrefix + "." + key;


        if (!contains(key))
            return new HashMap();

        return (Map) yamlObject.get(finalKey);
    }


    private void reload() {

        if (!autoReload)
            return;

        update();
    }

    @Override
    public void update() {
        try {
            YamlReader reader = new YamlReader(new FileReader(file));
            yamlObject = new YamlObject(reader.read());
        } catch (IOException e) {
            System.err.println("Exception while reloading yaml");
            e.printStackTrace();
        }
    }

    public String getPathPrefix() {
        return pathPrefix;
    }


    public boolean isAutoReload() {
        return autoReload;
    }

    public void setAutoReload(boolean autoReload) {
        this.autoReload = autoReload;
    }

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
        reload();
    }

    private Object get(String key) {
        if (key.contains(".")) {
            String[] parts = key.split("\\.");
            HashMap result = (HashMap) get(parts[0]);
            return result.containsKey(parts[1]) ? result.get(parts[1]) : null;
        }
        return yamlObject.toHashMap().containsKey(key) ? yamlObject.toHashMap().get(key) : null;
    }
}

