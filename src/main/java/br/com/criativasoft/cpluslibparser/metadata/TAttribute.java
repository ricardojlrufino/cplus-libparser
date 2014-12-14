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


public class TAttribute extends TElement {
    
    private static final long serialVersionUID = -6969982195267395502L;

    String type;
    
    private TElement parent;
    
    boolean isStatic;
    boolean isPublic;
    boolean isLocal; // Variable of a function.
    boolean isArray;
    boolean isExtern;
    boolean isEnum;
    
    public TAttribute(String type, String name) {
        super(name);
        this.type = type;
    }
    
    public TAttribute(String name) {
        super(name);
    }

    public String getType() {
        return type;
    }
    public void setType( String type ) {
        this.type = type;
    }
    
    public boolean isPublic() {
        return isPublic;
    }
    
    public boolean isStatic() {
        return isStatic;
    }
    
    public void setPublic( boolean isPublic ) {
        this.isPublic = isPublic;
    }
    
    public void setStatic( boolean isStatic ) {
        this.isStatic = isStatic;
    }
    
    public void setArray( boolean isArray ) {
        this.isArray = isArray;
    }
    
    public boolean isArray() {
        return isArray;
    }
    
    public void setExtern(boolean isExtern) {
		this.isExtern = isExtern;
	}
    
    public boolean isExtern() {
		return isExtern;
	}
    
    public void setEnum( boolean isEnum ) {
        this.isEnum = isEnum;
    }
    
    public boolean isEnum() {
        return isEnum;
    }
    
    /**
     * Check if is a local variable of a function
     * @return
     */
    public boolean isLocal() {
		return isLocal;
	}
    
    public void setLocal(boolean isLocal) {
		this.isLocal = isLocal;
	}
    
    public void setParent(TElement parent) {
		this.parent = parent;
	}
    
    public TElement getParent() {
		return parent;
	}
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name() == null) ? 0 : name().hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TAttribute other = (TAttribute) obj;
		if (name() == null) {
			if (other.name() != null)
				return false;
		} else if (!name().equals(other.name()))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	 public String getHtmlRepresentation() {
	    	
	    	if(htmlRepresentation == null){
		    	
		    	StringBuilder sb = new StringBuilder();
		    	sb.append(name());
		
		        
		        if(type != null){
		        	sb.append(" : ").append(htmlfont(type, C_LIGHT_BLUE));
		        }
		        
		        if(isStatic && parent == null && getLocation() != null ){
		        	sb.append(" - (").append(htmlfont(getLocation().getFileName(), C_GRAY)).append(")");
		        }
		        
		        htmlRepresentation = sb.toString();
	        
	    	}
	    	
			return htmlRepresentation;
		}
	 

	@Override
    public String toString() {
        return (isStatic ? "::" : "") + (isPublic ? "+" : "-") + type + " " +  name() + (isArray() ? "[]" : "");
    }
    

}
