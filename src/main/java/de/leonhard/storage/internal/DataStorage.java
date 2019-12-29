package de.leonhard.storage.internal;

import de.leonhard.storage.internal.serialize.LightningSerializer;
import de.leonhard.storage.util.ClassWrapper;
import de.leonhard.storage.util.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DataStorage {

	Set<String> singleLayerKeySet();

	Set<String> singleLayerKeySet(final String key);

	Set<String> keySet();

	Set<String> keySet(final String key);

	void remove(final String key);

	/**
	 * Set an object to your data-structure
	 *
	 * @param key   The key your value should be associated with
	 * @param value The value you want to set in your data-structure.
	 */
	void set(final String key, final Object value);

	/**
	 * Method to deserialize a class using the {@link LightningSerializer}.
	 * You will need to register your serializable in the {@link LightningSerializer} before.
	 *
	 * @param key   The key your value should be associated with.
	 * @param value The value you want to set in your data-structure.
	 */
	default void setSerializable(final String key, final Object value) {
		Object data = LightningSerializer.deserialize(value);
		set(key, data);
	}

	/**
	 * Checks whether a key exists in the data-structure
	 *
	 * @param key Key to check
	 * @return Returned value.
	 */
	boolean contains(final String key);

	Object get(final String key);

	// ----------------------------------------------------------------------------------------------------
	// Getting primitive types from data-structure
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Get a value or a default one
	 *
	 * @param key Path to value in data-structure
	 * @param def Default value & type of it
	 */
	default <T> T get(final String key, final T def) {
		if (!contains(key)) {
			return def;
		}
		return ClassWrapper.getFromDef(get(key), def);
	}

	/**
	 * Get a String from a data-structure
	 *
	 * @param key Path to String in data-structure
	 * @return Returns the value
	 */
	default String getString(final String key) {
		return getOrDefault(key, "");
	}

	/**
	 * Gets a long from a data-structure by key
	 *
	 * @param key Path to long in data-structure
	 * @return String from data-structure
	 */
	default long getLong(final String key) {
		return getOrDefault(key, 0L);
	}

	/**
	 * Gets an int from a data-structure
	 *
	 * @param key Path to int in data-structure
	 * @return Int from data-structure
	 */
	default int getInt(final String key) {
		return getOrDefault(key, 0);
	}

	/**
	 * Get a byte from a data-structure
	 *
	 * @param key Path to byte in data-structure
	 * @return Byte from data-structure
	 */
	default byte getByte(final String key) {
		return getOrDefault(key, (byte) 0);
	}

	/**
	 * Get a boolean from a data-structure
	 *
	 * @param key Path to boolean in data-structure
	 * @return Boolean from data-structure
	 */
	default boolean getBoolean(final String key) {
		return getOrDefault(key, false);
	}

	/**
	 * Get a float from a data-structure
	 *
	 * @param key Path to float in data-structure
	 * @return Float from data-structure
	 */
	default float getFloat(final String key) {
		return getOrDefault(key, 0F);
	}


	/**
	 * Get a double from a data-structure
	 *
	 * @param key Path to double in the data-structure
	 * @return Double from data-structure
	 */
	default double getDouble(final String key) {
		return getOrDefault(key, 0D);
	}

	// ----------------------------------------------------------------------------------------------------
	// Getting Lists and non-ClassWrapper types from data-structure
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Get a List from a data-structure
	 *
	 * @param key Path to StringList in data-structure.
	 * @return List
	 */
	default List<?> getList(final String key) {
		return getOrDefault(key, new ArrayList<>());
	}

	default List<String> getStringList(final String key) {
		return getOrDefault(key, new ArrayList<>());
	}

	default List<Integer> getIntegerList(final String key) {
		return getOrDefault(key, new ArrayList<>());
	}

	default List<Byte> getByteList(final String key) {
		return getOrDefault(key, new ArrayList<>());
	}

	default List<Long> getLongList(final String key) {
		return getOrDefault(key, new ArrayList<>());
	}

	default Map getMap(final String key) {
		return (Map) get(key);
	}

	/**
	 * Serialize an Enum from entry in the data-structure
	 *
	 * @param key      Path to Enum
	 * @param enumType Class of the Enum
	 * @param <E>      EnumType
	 * @return Serialized Enum
	 */
	default <E extends Enum<E>> E getEnum(final String key, final Class<E> enumType) {
		Object object = get(key);
		Valid.checkBoolean(object instanceof String, "No usable Enum-Value found for '" + key + "'.");
		return Enum.valueOf(enumType, (String) object);
	}

	/**
	 * Method to serialize a Class using the {@link LightningSerializer}.
	 * You will need to register your serializable in the {@link LightningSerializer} before.
	 *
	 * @return Serialized instance of class.
	 */
	default <T> T getSerializable(final String key, final Class<T> clazz) {
		if (!contains(key)) {
			return null;

		}
		return LightningSerializer.serialize(get(key), clazz);
	}

	// ----------------------------------------------------------------------------------------------------
	// Advanced methods to save time.
	// ----------------------------------------------------------------------------------------------------

	/**
	 * @param key Key to data in our data-structure.
	 * @param def Default value, if data-structure doesn't contain key.
	 * @param <T> Type of default-value.
	 */
	default <T> T getOrDefault(final String key, final T def) {
		if (!contains(key)) {
			return def;
		}
		return ClassWrapper.getFromDef(get(key), def);
	}

	/**
	 * Sets a value to the data-structure if the data-structure doesn't already contain the value
	 * Has nothing to do with Bukkit't 'addDefault'
	 *
	 * @param key   Key to set the value
	 * @param value Value to set.
	 */
	default void setDefault(final String key, final Object value) {
		if (!contains(key)) {
			set(key, value);
		}
	}

	/**
	 * Mix of setDefault & getDefault.
	 * <p>
	 * Sets a value to the data-structure if the data-structure doesn't already contain the value
	 * Returns a default value if the data-structure doesn't already contain the key.
	 * <p>
	 * If the key is already contained by the data-structure the value of assigned to
	 * the key will be returned and casted to the type of your def.
	 *
	 * @param key Key to set the value
	 * @param def Value to set or return.
	 */
	default <T> T getOrSetDefault(final String key, final T def) {
		if (!contains(key)) {
			set(key, def);
			return def;
		} else {
			return ClassWrapper.getFromDef(get(key), def);
		}
	}
}