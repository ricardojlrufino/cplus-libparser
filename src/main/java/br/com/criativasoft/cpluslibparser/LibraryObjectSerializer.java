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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import br.com.criativasoft.cpluslibparser.metadata.TLibrary;

public class LibraryObjectSerializer implements LibrarySerializer {

    @Override
    public void serialize( TLibrary library , File file ) throws IOException {
        OutputStream stream = new FileOutputStream(file);
        OutputStream buffer = new BufferedOutputStream(stream);
        ObjectOutput output = new ObjectOutputStream(buffer);

        try {
            output.writeObject(library);
        } finally {
            output.close();
        }

    }

    @Override
    public TLibrary deserialize( File file ) throws IOException {
        InputStream stream = new FileInputStream(file);
        InputStream buffer = new BufferedInputStream(stream);
        ObjectInput input = new ObjectInputStream(buffer);
        TLibrary library = null;
        try {
            library = (TLibrary) input.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            input.close();
        }
        return library;
    }

}
