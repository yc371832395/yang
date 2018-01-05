package com.mdm.common;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public abstract class AbstractBean implements Serializable {
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
