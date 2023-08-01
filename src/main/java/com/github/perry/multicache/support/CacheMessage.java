package com.github.perry.multicache.support;

import java.io.Serializable;

/**
 * @Author fanpeijin
 * @Date 2023年06月25日 15点16分
 **/
public class CacheMessage implements Serializable {
    /** */
    private static final long serialVersionUID = 5987219310442078193L;

    private String cacheName;

    private Object key;

    public CacheMessage(String cacheName, Object key) {
        super();
        this.cacheName = cacheName;
        this.key = key;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }
}
