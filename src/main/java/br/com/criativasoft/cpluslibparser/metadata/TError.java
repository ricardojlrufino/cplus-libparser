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

import java.util.Collection;

public class TError extends TElement {
    
    /**
     * IProblem relates to a valid syntax error in the parser
     */
    protected final static int SYNTAX_RELATED = 0x04000000;
    
    /**
     * Syntax error, detected by the parser.
     */
    public final static int SYNTAX_ERROR = SYNTAX_RELATED | 0x001;
    
    /**
     * Missing semicolon.
     */
    public final static int MISSING_SEMICOLON = SYNTAX_RELATED | 0x002;
 

    public final static int COMPILER_ERROR = SYNTAX_RELATED | 0x002;
    
    private static final long serialVersionUID = 6976836078408066351L;
    
    private int code;

    public TError(String name, int code) {
        super(name);
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
    /**
     * @see {@link TElementLocation#getStartOffset()}
     * @return
     */
    public int getStartOffset() {
        TElementLocation location = getLocation();
        if(location != null) return location.getStartOffset();
        return -1;
    }
    
    /**
     * @see {@link TElementLocation#getEndOffset()}
     * @return
     */
    public int getEndOffset() {
        TElementLocation location = getLocation();
        if(location != null) return location.getEndOffset();
        return -1;
    }
    
    /**
     * @see {@link TElementLocation#getLength()}
     * @return
     */
    public int getLength() {
        TElementLocation location = getLocation();
        if(location != null) return location.getLength();
        return -1;
    }
    
    /**
     * @see {@link TElementLocation#getPath()}
     * @return
     */
    public String getPath() {
        TElementLocation location = getLocation();
        if(location != null) return location.getPath();
        return null;
    }
    
    public static boolean containsError(int offset, Collection<TError> errors){
        for (TError error : errors) {
            if(error.getLocation() != null && error.getLocation().containsOffset(offset)){
                return true;
            }
        }
        
        return false;
    }
    

}
