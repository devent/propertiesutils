package com.anrisoftware.propertiesutils;

import java.text.ParseException;

public interface StringToType<T> {

    T stringToType(String value) throws ParseException;

}
