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
package br.com.criativasoft.cpluslibparser.utils;

import java.io.File;
import java.io.FileFilter;

public class ExtFileFilter implements FileFilter{
    
    private String ext;

    public ExtFileFilter(String ext) {
        super();
        this.ext = ext;
    }


    @Override
    public boolean accept( File pathname ) {
        return pathname.getName().endsWith(ext);
    }

}
