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

import br.com.criativasoft.cpluslibparser.metadata.TLibrary;

/**
 * Listener for watch library index changes.
 * @author Ricardo JL Rufino - (ricardo.jl.rufino@gmail.com)
 * @date 22/11/2014
 */
public interface LibraryIndexListener {

    void onLoadLibrary( TLibrary library );

    void onUnloadLibrary( TLibrary library );

    void onReloadLibrary( TLibrary library );

}
