package de.leonhard.storage;

import de.leonhard.storage.base.FileType;
import de.leonhard.storage.base.ReloadSettings;
import de.leonhard.storage.base.StorageBase;
import de.leonhard.storage.base.StorageCreator;
import de.leonhard.storage.comparator.Comparator;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
class LightningFile extends StorageCreator implements StorageBase, Comparator {


    private final ReloadSettings reloadSettings;
    private Map<String, Object> data;
    private File file;

    public LightningFile(final String name, final String path) {
        try {
            create(path, name, FileType.LS);
        } catch (IOException e) {
            System.err.println("Exception while creating '" + file.getName() + "'");
            e.printStackTrace();
        }
        this.reloadSettings = ReloadSettings.INTELLIGENT;
    }

    public LightningFile(final String name, final String path, final ReloadSettings reloadSettings) {
        this.reloadSettings = reloadSettings;
    }

    LightningFile(final File file) {
        this.file = file;
        this.reloadSettings = ReloadSettings.INTELLIGENT;
    }

    public String getName() {
        return this.file.getName();
    }

    @Override
    public void update() {

    }

    @Override
    public Set<String> getKeySet() {
        return null;
    }

    @Override
    public void removeKey(String key) {

    }

    @Override
    public void set(String key, Object value) {

    }

    @Override
    public boolean contains(String key) {
        return false;
    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            return this.file.equals(obj);
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(File pathname) {
        return this.file.compareTo(pathname);
    }

    @Override
    public int hashCode() {
        return this.file.hashCode();
    }

    @Override
    public String toString() {
        return this.file.getAbsolutePath();
    }
}
