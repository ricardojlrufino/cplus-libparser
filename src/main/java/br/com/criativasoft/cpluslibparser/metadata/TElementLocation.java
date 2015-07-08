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

import java.io.File;
import java.io.Serializable;

public class TElementLocation implements Serializable {
    
    private static final long serialVersionUID = -5924664891658306100L;

    private String path;
    
    private int offset;
    
    private int length;
    
    private TElement element;
    
    public TElementLocation() {
    }
    
    public TElementLocation(int offset, int length) {
        this(null, offset, length);
    }
    
    public TElementLocation(String path, int offset, int length) {
        super();
        this.path = path;
        this.offset = offset;
        this.length = length;
    }

    public void setElement( TElement element ) {
        this.element = element;
    }
    
    public TElement getElement() {
        return element;
    }

    public String getPath() {
        return path;
    }
    
    public String getFileName() {
    	
    	if(path == null) return null;
    	
    	int lastIndexOf = path.lastIndexOf(File.separator);
    	
        return path.substring(lastIndexOf + 1);
    }

    public void setPath( String path ) {
        this.path = path;
    }

    
    /**
     * Same as {@link #getOffset()}
     * @return
     */
    public int getStartOffset() {
        return offset;
    }
    
    public int getEndOffset() {
        return offset + length;
    }

    public void setOffset( int offset ) {
        this.offset = offset;
    }
    
    public void setLength(int length) {
		this.length = length;
	}
    
    public int getLength() {
		return length;
	}
    
    public boolean containsOffset(int offs) {
        return (offs >= getStartOffset() && offs <= getEndOffset());
    }
    
    @Override
    public String toString() {
    	return "["+getStartOffset()+", "+getEndOffset()+"]";
    }
    

}
