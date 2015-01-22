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

import br.com.criativasoft.cpluslibparser.utils.ExtFileFilter;

public class HeaderLibraryScanner extends LibraryScanner {

    private File folder;

    public HeaderLibraryScanner(File folder) {
        super();
        this.folder = folder;
    }

    @Override
    protected File[] getFilesToParse( File folder ) {
        // check /src directory
        File src = new File(folder, "src");
        String name = folder.getName();
        if (src.exists()) {
            folder = src;
        }

        return folder.listFiles(new ExtFileFilter(".h"));
    }

    @Override
    protected void configParser( SourceParser parser , File currentFile ) {
        // TODO Auto-generated method stub

    }

    @Override
    protected String getLibraryName() {
        return folder.getName();
    }

}
