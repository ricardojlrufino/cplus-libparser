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

import java.util.HashSet;
import java.util.Set;

public class TClass extends TElement {
    
    private static final long serialVersionUID = 1941012827449860693L;

    public static enum TClassType{
        CLASS, ENUM, STRUCT, EXTERN
    }
    
    private TLibrary library;
    
    private TClass parent;
    
    private boolean parentFound;
    
    private Set<TAttribute> attributes = new HashSet<TAttribute>();
    
    private Set<TFunction> functions = new HashSet<TFunction>();
    
    private TClassType type = TClassType.CLASS;
    

    public TClass() {
        super();
    }

    public TClass(String name) {
        super(name);
    }

    public TClass getParent() {
        return parent;
    }
    
    public void setParent( TClass parent ) {
        this.parent = parent;
    }
    
    public void setType( TClassType type ) {
        this.type = type;
    }
    
    public TClassType getType() {
        return type;
    }
    
    public void setAttributes( Set<TAttribute> attributes ) {
        this.attributes = attributes;
    }
    
    public Set<TAttribute> getAttributes() {
        return attributes;
    }
    
    public void setFunctions( Set<TFunction> functions ) {
        this.functions = functions;
    }
    
    public Set<TFunction> getFunctions() {
        return getFunctions(false);
    }
    
    public Set<TFunction> getFunctions(boolean inherited) {
        
        if(inherited && parent != null){
            Set<TFunction> functionsStore =  new HashSet<TFunction>();
            return getFunctions(inherited, functionsStore);
        }
        
        return functions;
    }
    
    public Set<TFunction> getFunctions(boolean inherited, Set<TFunction> functionsStore) {
        
        functionsStore.addAll(this.functions);
        
        if(inherited && parent != null){
            return parent.getFunctions(true, functionsStore);
        }
        
        return functionsStore;
    }
    
    public TFunction getFunction( String function , boolean inherited) {
        Set<TFunction> functions = getFunctions(inherited);
        for (TFunction tFunction : functions) {
            if(tFunction.name().equals(function)){
                return tFunction;
            }
        }
        
        return null;
    }

    public boolean add( TAttribute e ) {
        return attributes.add(e);
    }

    public boolean add( TFunction e ) {
    	
    	if(getType() == TClassType.STRUCT) e.setPublic(true);
    	
        return functions.add(e);
    }
    
    public void setLibrary( TLibrary library ) {
        this.library = library;
    }
    public TLibrary getLibrary() {
        return library;
    }
    
    public void setParentFound( boolean parentFound ) {
        this.parentFound = parentFound;
    }
    
    public boolean isParentFound() {
        return parentFound;
    }


    @Override
    public String toString() {
        return "TClass:" +name + ", parent: " + getParent();
    }

}
