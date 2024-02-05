package org.example.Interpolations;

import org.apache.http.MethodNotSupportedException;

public abstract interface Interpolation {
//    Object[] values = new Object[0];
    Object[] interpolate() throws MethodNotSupportedException;
}
