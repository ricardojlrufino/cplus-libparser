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
package br.com.criativasoft.cpluslibparser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.com.criativasoft.cpluslibparser.metadata.TAttribute;
import br.com.criativasoft.cpluslibparser.metadata.TClass;
import br.com.criativasoft.cpluslibparser.metadata.TFunction;
import br.com.criativasoft.cpluslibparser.metadata.TLibrary;

/**
 * Metadata cache for one or more libraries, this class can also represent an execution
 * context, for example when working with two projects in the workspace.
 * @author Ricardo JL Rufino (ricardo@criativasoft.com.br)
 * @date 28/11/2014
 */
public class LibraryCache {

    private String name;

    private Set<LibraryIndexListener> listeners = new HashSet<LibraryIndexListener>();

    private Map<String, TLibrary> libraries = new HashMap<String, TLibrary>();

    /** this map store the classes from all libraries */
    private Map<String, TClass> allClasses = new HashMap<String, TClass>();
    // TODO: problems with classes of same name... key can be: Libraty_ClassNAME?

    private Set<TAttribute> staticAttrs = new HashSet<TAttribute>();

    private Set<TFunction> globalFunctions = new HashSet<TFunction>();

    public Set<LibraryIndexListener> getListeners() {
        return listeners;
    }

    public void setListeners( Set<LibraryIndexListener> listeners ) {
        this.listeners = listeners;
    }

    public Map<String, TLibrary> getLibraries() {
        return libraries;
    }

    public void setLibraries( Map<String, TLibrary> libraries ) {
        this.libraries = libraries;
    }

    public Map<String, TClass> getAllClasses() {
        return allClasses;
    }

    public void setAllClasses( Map<String, TClass> allClasses ) {
        this.allClasses = allClasses;
    }

    public Set<TAttribute> getStaticAttrs() {
        return staticAttrs;
    }

    public void setStaticAttrs( Set<TAttribute> staticAttrs ) {
        this.staticAttrs = staticAttrs;
    }

    public Set<TFunction> getGlobalFunctions() {
        return globalFunctions;
    }

    public Set<TFunction> getGlobalFunctions( String name ) {

        Set<TFunction> functions = getGlobalFunctions();
        Set<TFunction> founds = new HashSet<TFunction>();

        for (TFunction tFunction : functions) {
            if (tFunction.name().equals(name)) {
                founds.add(tFunction);
            }
        }

        return founds;
    }

    public void setStaticFunctions( Set<TFunction> globalFunctions ) {
        this.globalFunctions = globalFunctions;
    }

    public boolean addListener( LibraryIndexListener e ) {
        return listeners.add(e);
    }

    public boolean removeListener( LibraryIndexListener e ) {
        return listeners.remove(e);
    }

    public TLibrary getLibrary( String name ) {
        return libraries.get(name);
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
