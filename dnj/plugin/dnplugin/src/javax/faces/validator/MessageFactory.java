
 /*
 * $Id: MessageFactory.java,v 1.1 2007/01/05 01:23:17 dannyc Exp $
 */

/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.faces.validator;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.util.Locale;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.MissingResourceException;
import javax.faces.component.UIComponent;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.FacesException;
import java.text.MessageFormat;
import java.io.IOException;

/**
 * 
 * <p>supported filters: <code>package</code> and
 * <code>protection</code>.</p>
 */

 class MessageFactory extends Object
{
    //
    // Protected Constants
    //

    //
    // Class Variables
    //

    //
    // Instance Variables
    //

    // Attribute Instance Variables

    // Relationship Instance Variables

    //
    // Constructors and Initializers    
    //

    private MessageFactory() {
    }

    //
    // Class methods
    //

    //
    // General Methods
    //
    
     static String substituteParams(Locale locale, String msgtext, Object params[]) {
        String localizedStr = null;
        
        if (params == null || msgtext == null ) {
            return msgtext;
        }    
        StringBuffer b = new StringBuffer(100);
        MessageFormat mf = new MessageFormat(msgtext);
        if (locale != null) {
            mf.setLocale(locale);
            b.append(mf.format(params));
            localizedStr = b.toString();
        }    
        return localizedStr;
    }

    /**

    * This version of getMessage() is used in the RI for localizing RI
    * specific messages.

    */

     static FacesMessage getMessage(String messageId, Object params[]) {
        Locale locale = null;
        FacesContext context = FacesContext.getCurrentInstance();
        // context.getViewRoot() may not have been initialized at this point.
        if (context != null && context.getViewRoot() != null) {
            locale = context.getViewRoot().getLocale();
            if (locale == null) {
                locale = Locale.getDefault();
            }
        } else {
            locale = Locale.getDefault();
        }
        
	return getMessage(locale, messageId, params);
    }

     static FacesMessage getMessage(Locale locale, String messageId, 
					   Object params[]) {
	FacesMessage result = null;
	String 
	    summary = null,
	    detail = null,
	    bundleName = null;
	ResourceBundle bundle = null;

	// see if we have a user-provided bundle
	if (null != (bundleName = getApplication().getMessageBundle())) {
	    if (null != 
		(bundle = 
		 ResourceBundle.getBundle(bundleName, locale,
					  getCurrentLoader(bundleName)))) {
		// see if we have a hit
		try {
		    summary = bundle.getString(messageId);
		}
		catch (MissingResourceException e) {
		}
	    }
	}
	
	// we couldn't find a summary in the user-provided bundle
	if (null == summary) {
	    // see if we have a summary in the app provided bundle
	    bundle = ResourceBundle.getBundle(FacesMessage.FACES_MESSAGES, 
					      locale,
					      getCurrentLoader(bundleName));
	    if (null == bundle) {
		throw new NullPointerException();
	    }
	    // see if we have a hit
	    try {
		summary = bundle.getString(messageId);
	    }
	    catch (MissingResourceException e) {
	    }
	}
	
	// we couldn't find a summary anywhere!  Return null
	if (null == summary) {
	    return null;
	}

	// At this point, we have a summary and a bundle.
	if (null == summary || null == bundle) {
            throw new NullPointerException(" summary " + summary + " bundle " + 
                bundle);
	}
	summary = substituteParams(locale, summary, params);

	try {
	    detail = substituteParams(locale,
				      bundle.getString(messageId + "_detail"), 
				      params);
	}
	catch (MissingResourceException e) {
	}

        return (new FacesMessage(summary, detail));
    }


    //
    // Methods from MessageFactory
    // 
     static FacesMessage getMessage(FacesContext context, String messageId) {
        return getMessage(context, messageId, null);
    }    
    
     static FacesMessage getMessage(FacesContext context, String messageId,
					   Object params[]) {
        if (context == null || messageId == null ) {
            throw new NullPointerException(" context " + context + " messageId " + 
                messageId);
        }
        Locale locale = null;
        // viewRoot may not have been initialized at this point.
        if (context != null && context.getViewRoot() != null) {
            locale = context.getViewRoot().getLocale();
        } else {
            locale = Locale.getDefault();
        }
	if (null == locale) {
	    throw new NullPointerException(" locale " + locale);
	}
        FacesMessage message = getMessage(locale, messageId, params);
        if (message != null) {
            return message;
        }
        locale = Locale.getDefault();
        return (getMessage(locale, messageId, params));
    }  
    
     static FacesMessage getMessage(FacesContext context, 
        UIComponent component, String messageId) {
        return getMessage(context, component, messageId, null);
         
    }
    
     static FacesMessage getMessage(FacesContext context, 
        UIComponent component, String messageId, Object params[]) {
        // if component id is specified, insert the "id" at
        // the end of the parameter list
        String id = "";
        if (component != null && component.getId() != null) {
            id = (" \"" + component.getId() + "\"" + ": ");
        }
        int length = 1;
        if (params != null) {
            length = (params.length) + 1;
        }
        Object[] newParams = new Object[length];
        if ( params != null) {
            for (int i = 0; i < params.length; ++i ) {
                newParams[i] = params[i];
            }
        }
        newParams[length-1] = id;
        return getMessage(context, messageId, newParams);
    }
    
     static FacesMessage getMessage(FacesContext context, String messageId,
                                       Object param0) {
        return getMessage(context, messageId, new Object[]{param0});                                       
    }                                       
    
     static FacesMessage getMessage(FacesContext context, String messageId,
                                       Object param0, Object param1) {
         return getMessage(context, messageId, new Object[]{param0, param1});                                        
    }                                       

     static FacesMessage getMessage(FacesContext context, String messageId,
                                       Object param0, Object param1,
                                       Object param2) {
         return getMessage(context, messageId, 
             new Object[]{param0, param1, param2});                                        
    }                                       

     static FacesMessage getMessage(FacesContext context, String messageId,
                                       Object param0, Object param1,
                                       Object param2, Object param3) {
         return getMessage(context, messageId, 
                 new Object[]{param0, param1, param2, param3});                                        
    }                                       

    protected static Application getApplication() {
	FacesContext context = FacesContext.getCurrentInstance();
	if (context != null) {
	    return (FacesContext.getCurrentInstance().getApplication());
	}
	ApplicationFactory afactory = (ApplicationFactory)
	    FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
	return (afactory.getApplication());
    }

    protected static ClassLoader getCurrentLoader(Object fallbackClass) {
        ClassLoader loader =
	    Thread.currentThread().getContextClassLoader();
	if (loader == null) {
	    loader = fallbackClass.getClass().getClassLoader();
	}
	return loader;
    }


} // end of class MessageFactory

 