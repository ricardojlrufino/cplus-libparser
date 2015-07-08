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
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

import br.com.criativasoft.cpluslibparser.metadata.TElementLocation;
import br.com.criativasoft.cpluslibparser.metadata.TLibrary;

// TODO: DOCS
// NOTA: Informar que ele utiliza :  LibraryIndex.getExecutor() eque usa thread's 
public abstract class LibraryScanner {

    private final static Logger LOG = Logger.getLogger(LibraryScanner.class.getName());

    protected abstract File[] getFilesToParse( File folder );

    protected boolean serialize = true;
    protected boolean deserialize = true;
    protected String useCacheFile = "lib.index";
    protected LibrarySerializer serializer = new LibraryObjectSerializer();

    /**
     * Configure parser for this file.
     * @param parser
     */
    protected abstract void configParser( SourceParser parser , File currentFile );

    protected abstract String getLibraryName();

    public TLibrary scan( String sourceCode ) {

        SourceParser parser = new SourceParser();
        configParser(parser, null);
        parser.parse(sourceCode);

        TLibrary library = new TLibrary(getLibraryName());
        collectMetadata(parser, library);

        return library;
    }

    /**
     * 
     * @param folder
     * @return
     */
    public TLibrary scan( File folder ) {

        if (!folder.exists() || !folder.isDirectory()) throw new IllegalArgumentException("path is not a directory !");

        if (deserialize) {
            File cache = new File(folder, useCacheFile);
            if (cache.exists()) {
                LOG.info("deserializing : " + cache.getPath());
                try {
                    return serializer.deserialize(cache);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        File[] files = getFilesToParse(folder);

        Semaphore semaphore = new Semaphore(0); // to Wait all parser
        List<SourceParser> runningParser = new LinkedList<SourceParser>();

        for (File file : files) {
            File sourcefile = file;

            LOG.fine("LibraryScanner :: scanFolder = " + folder + ", file: " + sourcefile.getName());

            if (sourcefile.exists()) {
                SourceParser parser = new SourceParser();
                configParser(parser, sourcefile);

                LibraryIndex.getExecutor().execute(new FileScanTask(parser, semaphore, sourcefile));

                runningParser.add(parser);

            }
        }

        // Wait all parsers/threads to finish
        try {
            semaphore.acquire(runningParser.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TLibrary library = collectMetadata(runningParser);
        TElementLocation location = new TElementLocation(folder.getPath(), 0, 0);
        library.setLocation(location);

        if (library != null && serialize) {
            File cache = new File(folder, useCacheFile);
            if (cache.exists()) cache.delete();
            try {
                serializer.serialize(library, cache);
                LOG.fine("LibraryScanner :: serializing...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return library;

    }

    /**
     * Extracts all metadata collected by the parser and add the library
     * @param parsers - each parser is a file.
     * @param folder
     * @return returns a new instance of TLibrary
     */
    protected TLibrary collectMetadata( List<SourceParser> parsers ) {

        TLibrary library = new TLibrary(getLibraryName());

        for (SourceParser sourceParser : parsers) {
            collectMetadata(sourceParser, library);
        }

        return library;

    }

    protected TLibrary collectMetadata( SourceParser sourceParser , TLibrary library ) {
        library.addAllClass(sourceParser.getClasses());
        library.addAllGlobalFunctions(sourceParser.getGlobalFunctions());
        library.addAllGlobalVariables(sourceParser.getGlobalVariables());
        library.setErrors(sourceParser.getErrors());
        return library;
    }

    /**
     * Sets the serializer. By default is: {@link LibraryObjectSerializer}
     * @param serializer
     */
    public void setSerializer( LibrarySerializer serializer ) {
        this.serializer = serializer;
    }

    /**
     * @see #setSerialize(boolean)
     */
    public void setSerialize( boolean serialize ) {
        this.serialize = serialize;
    }

    /**
     * @see #setSerialize(boolean)
     */
    public void setDeserialize( boolean deserialize ) {
        this.deserialize = deserialize;
    }

    private static final class FileScanTask implements Runnable {

        private Semaphore semaphore;
        private SourceParser parser;
        private File file;

        public FileScanTask(SourceParser parser, Semaphore semaphore, File file) {
            super();
            this.parser = parser;
            this.semaphore = semaphore;
            this.file = file;
        }

        @Override
        public void run() {
            parser.parse(file);
            semaphore.release();
        }

    }

}
