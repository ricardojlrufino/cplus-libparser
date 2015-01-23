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

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import br.com.criativasoft.cpluslibparser.metadata.TClass;
import br.com.criativasoft.cpluslibparser.metadata.TFunction;
import br.com.criativasoft.cpluslibparser.metadata.TLibrary;

// TODO: Documentar
// NOTA: Dizer que é assincromo, e da exemplos de registraro listener.
// 
public class LibraryIndex {
    
    private final static Logger LOG = Logger.getLogger(LibraryIndex.class.getName()); 
	
	/** Number of folders/files scan at same time. */
	public static int THREAD_POOL_SIZE = 10;
	
	private static ExecutorService executor;
	
	private static LibraryCache globalCache = new LibraryCache();
	
	private static LibraryCache contextCache;
	
	/** TODO: add DOCS */
	private static boolean useContext = false;
	
	
	private static LibraryCache getCache() {
	    if(useContext) return contextCache;
	    else return globalCache;
    }
	
    public static Map<String, TLibrary> getLibraries() {
        return getCache().getLibraries();
    }
	
    public static void addLibrary(TLibrary library){
        LibraryCache cache = getCache();
        
        Map<String, TLibrary> libraries = cache.getLibraries();
        Map<String, TClass> allClasses = cache.getAllClasses();
        Set<LibraryIndexListener> listeners = cache.getListeners();
        Set<TFunction> globalFunctions = cache.getGlobalFunctions();
        
		TLibrary found = libraries.get(library.name());
		
		if(found != null && ! found.isReloadable()) return;

		// Merge with existing library (ex: parsing a new file..)
		if (found != null && found.isReloadable()) {
			found.merge(library);
			found.setLastUpdate(System.currentTimeMillis());
		}

		// Add all class to cache.
		Set<TClass> classes = library.getClasses();
		for (TClass tClass : classes) {
			String name = tClass.name().toLowerCase();
			if (!allClasses.containsKey(name)) {
			    allClasses.put(name, tClass);
			}
		}
		
		// Add Function's
		Set<TFunction> libFunctions = library.getGlobalFunctions();
		globalFunctions.addAll(libFunctions);
		
        // Find/resolve inheritance
		for (TClass tClass : classes) {
            if(tClass.getParent() != null && ! tClass.isParentFound()){
                TClass parent = allClasses.get(tClass.getParent().getName());
                if(parent == null) parent = getClass(tClass.getParent().getName());
                if(parent != null){
                    tClass.setParent(parent);
                    tClass.setParentFound(true);
                }
            }
		}

		boolean contains = libraries.containsKey(library.name());
		if(!contains){
		    library.setLastUpdate(System.currentTimeMillis());
			libraries.put(library.name(), library);
			LOG.info("load: " + library);
		}else{
		    LOG.info("reload: " + library);
		}

		for (LibraryIndexListener listener : listeners) {
			if (contains) {
				listener.onReloadLibrary(found);
			} else {
				listener.onLoadLibrary(library);
			}
		}
           
    }
    
    public static void removeLibrary(TLibrary library){
        
        LibraryCache cache = getCache();
        Map<String, TLibrary> libraries = cache.getLibraries();
        Map<String, TClass> allClasses = cache.getAllClasses();
        Set<LibraryIndexListener> listeners = cache.getListeners();
        
    	if(library != null){
    		libraries.remove(library);
    		
    		Set<TClass> classes = library.getClasses();
    		
    		allClasses.values().removeAll(classes);

//    		Set<String> keySet = allClasses.keySet();
//    		for (String key : keySet) {
//    			TClass current = allClasses.get(key);
//    			if(classes.contains(current)){
//    				allClasses.remove(key);
//    			}
//			}
    		
    		for (LibraryIndexListener listener : listeners) {
    			listener.onUnloadLibrary(library);
    		}
    	}
    	
    }
    
   public static void removeLibrary(String library){
       LibraryCache cache = getCache();
       Map<String, TLibrary> libraries = cache.getLibraries();
	   removeLibrary(libraries.get(library));
   }
   
   
    /**
     * Remove all cached instances..
     */
    public void clear(){
    	
        LibraryCache cache = getCache();
        Map<String, TLibrary> libraries = cache.getLibraries();
        Set<LibraryIndexListener> listeners = cache.getListeners();
        
    	Collection<TLibrary> libraryList = libraries.values();
    	for (TLibrary library : libraryList) {
    	    for (LibraryIndexListener listener : listeners) {
                listener.onUnloadLibrary(library);
            }
		}
    	
    	libraries.clear();
    	cache.getAllClasses().clear();
    	cache.getStaticAttrs().clear();
    	cache.getGlobalFunctions().clear();
    }
    
    
    public static void scanFolder(File folder, LibraryScanner scanner){
        
    	getExecutor().execute(new ScanTask(folder, scanner));
        
    }
    
    public static void scanSource(String source, LibraryScanner scanner){
        
    	getExecutor().execute(new ScanTask(source, scanner));
        
    }
    
    static ExecutorService getExecutor() {
    	if(executor == null) executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		return executor;
	}
    
    public static Map<String, TClass> getAllClasses() {
        return getCache().getAllClasses();
    }
    
    public static TClass getClass(String name){
        return getCache().getAllClasses().get(name.toLowerCase());
    }
    
    public static Set<TFunction> getGlobalFunctions() {
        return getCache().getGlobalFunctions();
    }
    
    public static Set<TFunction> getGlobalFunctions(String name) {
        return getCache().getGlobalFunctions(name);
    }
    
    public static TLibrary getLibrary(String name){
        return getCache().getLibrary(name);
    }
    
    
    
    public static boolean addListener( LibraryIndexListener e ) {
        return getCache().addListener(e);
    }
    
    public static boolean removeListener( LibraryIndexListener e ) {
        return getCache().removeListener(e);
    }
    
    public static void setUseContext( boolean useContext ) {
        LibraryIndex.useContext = useContext;
    }
    
    public static void setContextCache( LibraryCache contextCache ) {
        setUseContext(true);
        LibraryIndex.contextCache = contextCache;
    }
    
    public static class ScanTask implements Runnable{
    	
    	private String sourceCode;
    	private File folder;
    	private LibraryScanner scanner;

		public ScanTask(File folder, LibraryScanner scanner) {
			super();
			this.folder = folder;
			this.scanner = scanner;
		}
		
		
		public ScanTask(String sourceCode, LibraryScanner scanner) {
			super();
			this.sourceCode = sourceCode;
			this.scanner = scanner;
		}

		@Override
		public void run() {
			TLibrary library = null;
			if(folder != null) library = scanner.scan(folder);
			if(sourceCode != null) library = scanner.scan(sourceCode);
			
	        if(library != null && library.name() != null && library.name().length() > 1){ 
	            addLibrary(library);
	        }    
		}
    	
    }

}
