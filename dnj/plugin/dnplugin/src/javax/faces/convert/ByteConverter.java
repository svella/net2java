
 /*
 * $Id: ByteConverter.java,v 1.1 2007/01/05 01:23:06 dannyc Exp $
 */

/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.faces.convert;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;


/**
 * <p>{@link Converter} implementation for <code>java.lang.Byte</code>
 * (and byte primitive) values.</p>
 */

public class ByteConverter implements Converter {


    // ------------------------------------------------------ Manifest Constants


    /**
     * <p>The standard converter id for this converter.</p>
     */
    public static final String CONVERTER_ID = "javax.faces.Byte";


    // ------------------------------------------------------- Converter Methods

    /**
     * @exception ConverterException {@inheritDoc}
     * @exception NullPointerException {@inheritDoc}
     */ 
    public Object getAsObject(FacesContext context, UIComponent component,
                              String value) {

        if (context == null || component == null) {
            throw new NullPointerException();
        }
        
        // If the specified value is null or zero-length, return null
        if (value == null) {
            return (null);
        }
        value = value.trim();
        if (value.length() < 1) {
            return (null);
        }
        
        try {
            return (Byte.valueOf(value));
        } catch (Exception e) {
            throw new ConverterException(e);
        }


    }

    /**
     * @exception ConverterException {@inheritDoc}
     * @exception NullPointerException {@inheritDoc}
     */ 
    public String getAsString(FacesContext context, UIComponent component,
                              Object value) {

        if (context == null || component == null) {
            throw new NullPointerException();
        }
        
        // If the specified value is null, return a zero-length String
        if (value == null) {
            return "";
        }

        // If the incoming value is still a string, play nice
        // and return the value unmodified
        if (value instanceof String) {
            return (String) value;
        }
        try {
            return (Byte.toString(((Byte) value).byteValue()));
        } catch (Exception e) {
            throw new ConverterException(e);
        }

    }


}

 