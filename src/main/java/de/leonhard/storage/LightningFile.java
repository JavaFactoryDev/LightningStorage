package de.leonhard.storage;

import de.leonhard.storage.internal.base.FileData;
import de.leonhard.storage.internal.base.FlatFile;
import de.leonhard.storage.internal.base.StorageBase;
import de.leonhard.storage.internal.enums.FileType;
import de.leonhard.storage.internal.enums.ReloadSettings;

import java.io.*;
import java.util.Base64;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class LightningFile extends FlatFile implements StorageBase {

    private FileData fileData;

    public LightningFile(final String name, final String path) {
        create(name, path, FileType.LS);
        setReloadSettings(ReloadSettings.INTELLIGENT);
    }

    public LightningFile(final String name, final String path, final ReloadSettings reloadSettings) {
        setReloadSettings(reloadSettings);
    }

    LightningFile(final File file) {
        create(file);
    }

    //added method for later implementation
    @Override
    public void update() {
        //TODO
    }

    //added method for later implementation
    @Override
    public Set<String> singleLayerKeySet() {
        return null;
    }

    //added method for later implementation
    @Override
    public Set<String> singleLayerKeySet(final String key) {
        return null;
    }

    //added method for later implementation
    @Override
    public Set<String> keySet() {
        return null;
    }

    //added method for later implementation
    @Override
    public Set<String> keySet(final String key) {
        return null;
    }

    //added method for later implementation
    @Override
    public void remove(final String key) {
        //TODO
    }

    //added method for later implementation
    @Override
    public void set(final String key, final Object value) {
        //TODO
    }

    @Override
    public boolean contains(final String key) {
        return false;
    }

    @Override
    public Object get(final String key) {
        return null;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        } else {
            LightningFile lightningFile = (LightningFile) obj;
            return this.fileData.equals(lightningFile.fileData)
                    && super.equals(lightningFile.getFlatFileInstance());
        }
    }

    public void write(PrintWriter writer) {
        for (String key : fileData.singleLayerKeySet()) {
            if (fileData.get(key) instanceof Map) {
                //noinspection unchecked
                write((Map<String, Object>) fileData.get(key), writer, "");
            } else {
                writer.println(key + " = " + fileData.get(key));
            }
        }
    }

    private void write(Map<String, Object> map, PrintWriter writer, String indentation) {
        for (String key : map.keySet()) {
            if (map.get(key) instanceof Map) {
                //noinspection unchecked
                write((Map<String, Object>) map.get(key), writer, indentation + "  ");
            } else {
                writer.println(indentation + key + " = " + map.get(key));
            }
        }
    }

    private String serialize(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    private Map<String, Object> deserialize(String s) throws IOException,
            ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return (Map<String, Object>) o;
    }
}