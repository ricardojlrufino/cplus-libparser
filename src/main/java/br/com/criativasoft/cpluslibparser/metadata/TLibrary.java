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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TLibrary extends TElement {
	
    private static final long serialVersionUID = -5028212828784647520L;

    private Set<TClass> classes = new HashSet<TClass>();
    
    private Set<TFunction> globalFunctions = new HashSet<TFunction>();
    
    private Set<TAttribute> globalVariables = new HashSet<TAttribute>();
    
    private Set<TError> errors = new HashSet<TError>();
    
    private boolean reloadable;
    
    private long lastUpdate;
    
    
    public TLibrary() {
        super();
    }

    public TLibrary(String name) {
        super(name);
    }
    
    public Set<TClass> getClasses() {
        return classes;
    }

    public boolean addClass( TClass klass ) {
        klass.setLibrary(this);
        return classes.add(klass);
    }

    public void addAllClass( Collection<? extends TClass> c ) {
        for (TClass tClass : c) {
            addClass(tClass);
        }
    }
    
    public void setGlobalFunctions(Set<TFunction> globalFunctions) {
		this.globalFunctions = globalFunctions;
	}
    
    public Set<TFunction> getGlobalFunctions() {
		return globalFunctions;
	}
    
    public void setGlobalVariables(Set<TAttribute> globalVariables) {
		this.globalVariables = globalVariables;
	}
    
    public Set<TAttribute> getGlobalVariables() {
		return globalVariables;
	}
    
    /**
     * Sets the library can be updated after being loaded. <br/>
     * This is usually used for the libraries that are being constantly edited and must be reloaded.
     */
    public void setReloadable( boolean reloadable ) {
        this.reloadable = reloadable;
    }
    
    public boolean isReloadable() {
        return reloadable;
    }
    

    public Set<TElement> getAllMembers(){
    	Set<TElement> elements = new HashSet<TElement>();
    	elements.addAll(classes);
    	if(globalFunctions != null) elements.addAll(globalFunctions);
    	if(globalVariables != null) elements.addAll(globalVariables);
    	return elements;
    }
    
    public TElement findMember(String name){
    	Set<TElement> elements = getAllMembers();
    	
    	for (TElement element : elements) {
			if(element.name().equals(name)){
				return element;
			}
		}
    	
    	return null;
    }
    
    public void merge(TLibrary library){
    	addAllClass(library.getClasses());
    	globalFunctions.addAll(library.getGlobalFunctions());
    	globalVariables.addAll(library.getGlobalVariables());
    	setErrors(library.getErrors()); // replace errors to clear 
    }

	
	public void addAllGlobalVariables(Set<TAttribute> globalVariables) {
		this.globalVariables.addAll(globalVariables);
	}

	public void addAllGlobalFunctions(Set<TFunction> globalFunctions) {
		this.globalFunctions.addAll(globalFunctions);
	}
	
	public void addError(TError error){
		this.errors.add(error);
	}
	
	public void setErrors( Set<TError> errors ) {
        this.errors = errors;
    }
	
	/** Get parser error's */
	public Set<TError> getErrors() {
        return errors;
    }

	public void clear() {
		classes.clear();
		errors.clear();
		globalFunctions.clear();
		globalVariables.clear();
	}
	
	/**
	 * Clear metadata associated with this file
	 * @param path - file path
	 */
	public void clear(String path) {
		List<TElement> list = new ArrayList<TElement>();
		list.addAll(classes);
		list.addAll(errors);
		list.addAll(globalVariables);
		list.addAll(globalFunctions);
		
		for (TElement tElement : list) {
			if(tElement.getLocation() != null){
				TElementLocation location = tElement.getLocation();
				if (location != null && location.getPath() != null 
					&& (path.equals(location.getPath()) ||  path.endsWith(location.getPath()))) {
					classes.remove(tElement);
					errors.remove(tElement);
					globalVariables.remove(tElement);
					globalFunctions.remove(tElement);
				}
			}
		}
	}
	
	public void setLastUpdate( long lastUpdate ) {
        this.lastUpdate = lastUpdate;
    }
	
	public long getLastUpdate() {
        return lastUpdate;
    }



}
