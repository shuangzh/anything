package com.cmsz.hxj.web.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ThreadMap<K, V> implements Map<K, V> {
	private ThreadLocal<Map<K, V>> tMap;

	public ThreadMap() {
		tMap = new ThreadLocal<Map<K, V>>();
		tMap.set(new HashMap<K, V>());
	}

	public void reset() {
		if (tMap.get() == null) {
			tMap.set(new HashMap<K, V>());
		} else {
			tMap.get().clear();
		}
	}

	public Map<K, V> getMap() {
		if (tMap.get() == null) {
			tMap.set(new HashMap<K, V>());
		}
		return tMap.get();
	}
	
	@Override
	public int size() {
		return tMap.get().size();
	}

	@Override
	public boolean isEmpty() {
		return tMap.get().isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return tMap.get().containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return tMap.get().containsValue(value);
	}

	@Override
	public V get(Object key) {
		return tMap.get().get(key);
	}

	@Override
	public V put(K key, V value) {
		return tMap.get().put(key, value);
	}

	@Override
	public V remove(Object key) {
		return tMap.get().remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		tMap.get().putAll(m);
	}

	@Override
	public void clear() {
		tMap.get().clear();
	}

	@Override
	public Set<K> keySet() {
		return tMap.get().keySet();
	}

	@Override
	public Collection<V> values() {
		return tMap.get().values();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return tMap.get().entrySet();
	}

}
