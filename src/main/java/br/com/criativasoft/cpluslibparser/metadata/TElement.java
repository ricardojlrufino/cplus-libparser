/*******************************************************************************
 * Copyright (c) 2014, CriativaSoft, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 *******************************************************************************/
package br.com.criativasoft.cpluslibparser.metadata;

import java.io.Serializable;
import java.util.Collection;

public class TElement implements Serializable {
    
    private static final long serialVersionUID = -631746841951042333L;
    
    protected static final String STR_EMPTY = "";
    protected static final String STR_WHITESPACE = " ";
    protected static final String STR_STATIC_TOKEN = "::";
    protected static final String STR_DOT_TOKEN = ".";
    
    protected static final String C_GRAY = "#A0A0A0";
    protected static final String C_LIGHT_BLUE = "#008080";
    
    public TElement() {
    }
    
    public TElement(String name) {
        super();
        this.name = name;
    }

    protected String name;
    protected String htmlRepresentation;
    
    private TElementLocation location;
    
    public void setName( String name ) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public String name() {
        return name;
    }
    
    public void setLocation( TElementLocation location ) {
        this.location = location;
        location.setElement(this);
    }
    
    public TElementLocation getLocation() {
        return location;
    }
    
    public void setHtmlRepresentation(String htmlRepresentation) {
		this.htmlRepresentation = htmlRepresentation;
	}
 
    public String getHtmlRepresentation() {
    	if(htmlRepresentation == null) return name;
		return htmlRepresentation;
	}

    protected String htmlfont(String text, String color){
    	if(color == null) return text;
    	return "<font color='"+color+"'>"+text+"</font>";
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ":" + this.getName();
    }
    
    public static boolean isPrimitive(String type){
        
        if(type.equals("int") || type.equals("long") || type.equals("float") || type.equals("double") 
        || type.equals("char") || type.equals("bool") || type.equals("boolean") || type.equals("size_t")){
            return true;
        }
            
        if(type.startsWith("unsigned") || type.startsWith("uint") || type.startsWith("ulong")){
            return true;
        }
        
        return false;
    }
    
    public static boolean contains(int offset, Collection<? extends TElement> list){
        for (TElement el : list) {
            if(el.getLocation() != null && el.getLocation().containsOffset(offset)){
                return true;
            }
        }
        
        return false;
    }

}
