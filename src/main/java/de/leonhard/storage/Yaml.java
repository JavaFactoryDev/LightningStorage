package de.leonhard.storage;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import de.leonhard.storage.internal.base.FileData;
import de.leonhard.storage.internal.base.FlatFile;
import de.leonhard.storage.internal.base.StorageBase;
import de.leonhard.storage.internal.editor.YamlEditor;
import de.leonhard.storage.internal.editor.YamlParser;
import de.leonhard.storage.internal.enums.ConfigSettings;
import de.leonhard.storage.internal.enums.FileType;
import de.leonhard.storage.internal.enums.ReloadSettings;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.*;

@SuppressWarnings({"unused", "WeakerAccess", "unchecked"})
@Getter
public class Yaml extends FlatFile implements StorageBase {
    @Setter
    private String pathPrefix;
    @Setter
    private ConfigSettings configSettings = ConfigSettings.skipComments;
    private final YamlEditor yamlEditor;
    private final YamlParser parser;
    private FileData fileData;
    private final int BUFFER_SIZE = 8192;


    public Yaml(final String name, final String path) {
        create(name, path, FileType.YAML);
        yamlEditor = new YamlEditor(getFile());
        parser = new YamlParser(yamlEditor);

        update();
    }

    public Yaml(final String name, final String path, final ReloadSettings reloadSettings) {
        create(name, path, FileType.YAML);
        setReloadSettings(reloadSettings);
        yamlEditor = new YamlEditor(getFile());
        parser = new YamlParser(yamlEditor);

        update();
    }

    public Yaml(final File file) {
        if (isYaml(file)) {
            create(file);

            update();

            yamlEditor = new YamlEditor(getFile());
            parser = new YamlParser(yamlEditor);
        } else {
            yamlEditor = null;
            parser = null;
        }
    }

    public Yaml(final String name, final String path, final String resourcefile) {
        if (create(name, path, FileType.YAML)) {
            try (BufferedInputStream configInputStream = new BufferedInputStream(
                    Objects.requireNonNull(Config.class.getClassLoader().getResourceAsStream(resourcefile + ".yml"))); FileOutputStream outputStream = new FileOutputStream(this.getFile())) {
                final byte[] data = new byte[BUFFER_SIZE];
                int count;
                while ((count = configInputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                    outputStream.write(data, 0, count);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        yamlEditor = new YamlEditor(getFile());
        parser = new YamlParser(yamlEditor);

        update();
    }

    public Yaml(final String name, final String path, final ReloadSettings reloadSettings, final String resourcefile) {
        if (create(name, path, FileType.YAML)) {
            try (BufferedInputStream configInputStream = new BufferedInputStream(
                    Objects.requireNonNull(Config.class.getClassLoader().getResourceAsStream(resourcefile + ".yml"))); FileOutputStream outputStream = new FileOutputStream(getFile())) {
                final byte[] data = new byte[BUFFER_SIZE];
                int count;
                while ((count = configInputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                    outputStream.write(data, 0, count);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setReloadSettings(reloadSettings);
        yamlEditor = new YamlEditor(getFile());
        parser = new YamlParser(yamlEditor);

        update();
    }

    public Yaml(final File file, final String resourcefile) {
        if (isYaml(file)) {
            if (create(file)) {
                try (BufferedInputStream configInputStream = new BufferedInputStream(
                        Objects.requireNonNull(Config.class.getClassLoader().getResourceAsStream(resourcefile + ".yml"))); FileOutputStream outputStream = new FileOutputStream(this.getFile())) {
                    final byte[] data = new byte[BUFFER_SIZE];
                    int count;
                    while ((count = configInputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                        outputStream.write(data, 0, count);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            yamlEditor = new YamlEditor(getFile());
            parser = new YamlParser(yamlEditor);

            update();
        } else {
            yamlEditor = null;
            parser = null;
        }
    }

    public Yaml(final String name, final String path, final File resourcefile) {
        if (create(name, path, FileType.YAML)) {
            try (BufferedInputStream configInputStream = new BufferedInputStream(
                    Objects.requireNonNull(Config.class.getClassLoader().getResourceAsStream(resourcefile.getName()))); FileOutputStream outputStream = new FileOutputStream(this.getFile())) {
                final byte[] data = new byte[BUFFER_SIZE];
                int count;
                while ((count = configInputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                    outputStream.write(data, 0, count);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        yamlEditor = new YamlEditor(getFile());
        parser = new YamlParser(yamlEditor);

        update();
    }

    public Yaml(final String name, final String path, final ReloadSettings reloadSettings, final File resourcefile) {
        if (create(name, path, FileType.YAML)) {
            try (BufferedInputStream configInputStream = new BufferedInputStream(
                    Objects.requireNonNull(Config.class.getClassLoader().getResourceAsStream(resourcefile.getName()))); FileOutputStream outputStream = new FileOutputStream(this.getFile())) {
                final byte[] data = new byte[BUFFER_SIZE];
                int count;
                while ((count = configInputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                    outputStream.write(data, 0, count);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setReloadSettings(reloadSettings);
        yamlEditor = new YamlEditor(getFile());
        parser = new YamlParser(yamlEditor);

        update();
    }

    public Yaml(final File file, final File resourcefile) {
        if (isYaml(file)) {
            if (create(file)) {
                try (BufferedInputStream configInputStream = new BufferedInputStream(
                        Objects.requireNonNull(Config.class.getClassLoader().getResourceAsStream(resourcefile.getName()))); FileOutputStream outputStream = new FileOutputStream(this.getFile())) {
                    final byte[] data = new byte[BUFFER_SIZE];
                    int count;
                    while ((count = configInputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                        outputStream.write(data, 0, count);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            yamlEditor = new YamlEditor(getFile());
            parser = new YamlParser(yamlEditor);

            update();
        } else {
            yamlEditor = null;
            parser = null;
        }
    }

    public Yaml(final String name, final String path, final BufferedInputStream resourceStream) {
        if (create(name, path, FileType.YAML)) {
            try (BufferedInputStream configInputStream = resourceStream; FileOutputStream outputStream = new FileOutputStream(this.getFile())) {
                final byte[] data = new byte[BUFFER_SIZE];
                int count;
                while ((count = configInputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                    outputStream.write(data, 0, count);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        yamlEditor = new YamlEditor(getFile());
        parser = new YamlParser(yamlEditor);

        update();
    }

    public Yaml(final String name, final String path, final ReloadSettings reloadSettings, final BufferedInputStream resourceStream) {
        if (create(name, path, FileType.YAML)) {
            try (BufferedInputStream configInputStream = resourceStream; FileOutputStream outputStream = new FileOutputStream(this.getFile())) {
                final byte[] data = new byte[BUFFER_SIZE];
                int count;
                while ((count = configInputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                    outputStream.write(data, 0, count);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setReloadSettings(reloadSettings);
        yamlEditor = new YamlEditor(getFile());
        parser = new YamlParser(yamlEditor);

        update();
    }

    public Yaml(final File file, final BufferedInputStream resourceStream) {
        if (isYaml(file)) {
            if (create(file)) {
                try (BufferedInputStream configInputStream = resourceStream; FileOutputStream outputStream = new FileOutputStream(this.getFile())) {
                    final byte[] data = new byte[BUFFER_SIZE];
                    int count;
                    while ((count = configInputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                        outputStream.write(data, 0, count);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            yamlEditor = new YamlEditor(getFile());
            parser = new YamlParser(yamlEditor);

            update();
        } else {
            yamlEditor = null;
            parser = null;
        }
    }

    protected final boolean isYaml(final String fileName) {
        return (fileName.lastIndexOf(".") > 0 ? fileName.substring(fileName.lastIndexOf(".") + 1) : "").equals("yml");
    }

    protected final boolean isYaml(final File file) {
        return isYaml(file.getName());
    }

    @Override
    public void set(final String key, final Object value) {
        set(key, value, this.configSettings);
    }

    public void set(final String key, final Object value, final ConfigSettings configSettings) {
        reload();

        final String finalKey = (pathPrefix == null) ? key : pathPrefix + "." + key;

        synchronized (this) {

            String old = fileData.toString();
            fileData.insert(finalKey, value);

            if (fileData != null && old.equals(fileData.toString())) {
                return;
            }

            try {
                if (configSettings.equals(ConfigSettings.preserveComments)) {

                    final List<String> unEdited = yamlEditor.read();
                    final List<String> header = yamlEditor.readHeader();
                    final List<String> footer = yamlEditor.readFooter();
                    write(fileData.toMap());
                    header.addAll(yamlEditor.read());
                    if (!header.containsAll(footer))
                        header.addAll(footer);
                    yamlEditor.write(parser.parseComments(unEdited, header));
                    return;
                }
                write(Objects.requireNonNull(fileData).toMap());
            } catch (final IOException e) {
                System.err.println("Error while writing '" + getName() + "'");
            }
        }
    }

    public void write(final Map data) throws IOException {
        YamlWriter writer = new YamlWriter(new FileWriter(getFile()));
        writer.write(data);
        writer.close();
    }

    @Override
    public Object get(final String key) {
        reload();
        String finalKey = (this.pathPrefix == null) ? key : this.pathPrefix + "." + key;
        return fileData.get(key);
    }

    @Override
    public boolean contains(final String key) {
        String tempKey = (pathPrefix == null) ? key : pathPrefix + "." + key;
        reload();
        return fileData.containsKey(tempKey);
    }

    protected void reload() {
        if (shouldReload()) {
            update();
        }
    }

    @Override
    public void update() {
        YamlReader reader = null;
        try {
            reader = new YamlReader(new FileReader(getFile()));// Needed?
            fileData = new FileData((Map<String, Object>) reader.read());
        } catch (IOException e) {
            System.err.println("Exception while reloading yaml");
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Exception while closing file");
                e.printStackTrace();
            }
        }
    }

    public List<String> getHeader() {
        try {
            return yamlEditor.readHeader();
        } catch (IOException e) {
            System.err.println("Error while getting header of '" + getName() + "'");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void remove(final String key) {
        final String finalKey = (pathPrefix == null) ? key : pathPrefix + "." + key;

        fileData.remove(finalKey);

        try {
            write(fileData.toMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> singleLayerKeySet() {
        reload();
        return fileData.singleLayerKeySet();
    }

    @Override
    public Set<String> singleLayerKeySet(final String key) {
        reload();
        return fileData.singleLayerKeySet(key);
    }

    @Override
    public Set<String> keySet() {
        reload();
        return fileData.keySet();
    }

    @Override
    public Set<String> keySet(final String key) {
        reload();
        return fileData.keySet(key);
    }

    protected final Yaml getYamlInstance() {
        return this;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        } else {
            Yaml yaml = (Yaml) obj;
            return this.fileData.equals(yaml.fileData)
                    && this.pathPrefix.equals(yaml.pathPrefix)
                    && this.configSettings.equals(yaml.configSettings)
                    && super.equals(yaml.getFlatFileInstance());
        }
    }
}