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
import java.util.LinkedHashSet;
import java.util.Set;

public class TFunction extends TElement {
    
    private static final long serialVersionUID = 4220518946527465434L;

    protected static final String STR_EMPTY_PARAM = "()";
    
    private boolean isPublic = false; 
    
    private boolean isStatic = false; 
    
    private boolean isConstructor = false;
    
    private int paramCount = 0;
    
    private TClass parent;
    
    private Set<TParam> params = new LinkedHashSet<TParam>();
    
    private Set<TAttribute> localVariables = new HashSet<TAttribute>(); // variables defined in body of functions ()
    
    private String returnType; 
    
    public TFunction() {
        super();
    }
    
    public TFunction(String name) {
        this(name, null);
    }

    public TFunction(String name, TClass parent) {
        super(name);
        this.parent = parent;
        if(parent != null && name.equals(parent.name())){
            this.isConstructor = true;
        }
    }
    
    public boolean isPublic() {
        return isPublic;
    }
    
    public void setPublic( boolean isPublic ) {
        this.isPublic = isPublic;
    }
    
    public boolean isStatic() {
        return isStatic;
    }
    
    public void setStatic( boolean isStatic ) {
        this.isStatic = isStatic;
    }

    public void setParent( TClass parent ) {
        this.parent = parent;
    }
    
    public TClass getParent() {
        return this.parent;
    }
    
    public boolean isConstructor() {
        return isConstructor;
    }
    
    public boolean hasParams() {
        return paramCount > 0;
    }

    public void addParam(String type, String name){
        addParam(new TParam(type, name));
    }
    
    public void addParam(TParam param){
        this.paramCount++;
        this.params.add(param);
    }
    
    public Set<TParam> getParams() {
		return params;
	}
    
    public int getParamCount() {
		return paramCount;
	}
    
    public String toDeclarationString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name());
        sb.append(getParamsAsString());
        return sb.toString();
    }
    
    public String getHtmlRepresentation() {
    	
    	if(htmlRepresentation == null){
	    	
	    	StringBuilder sb = new StringBuilder();
	    	sb.append(name());
	
	    	if(!hasParams()){
	    		sb.append(STR_EMPTY_PARAM);
	    	}else{
	            sb.append('(');
	            for (TAttribute tAttribute : params) {
	                sb.append(htmlfont(tAttribute.getType(),C_LIGHT_BLUE));
	                sb.append(STR_WHITESPACE);
	                sb.append(tAttribute.name());
	                sb.append(',');
	            }
	
	            sb.deleteCharAt(sb.length() - 1);
	            sb.append(')');
	            
	    	}
	        
	        if(getReturnType() != null && getReturnType().length() > 0){
	            sb.append(" : ").append(htmlfont(getReturnType(), C_GRAY));
	        }
	        
	        if(isStatic && parent == null ){
	        	sb.append(" - (").append(htmlfont(getLocation().getFileName(), C_GRAY)).append(")");
	        }
	        
	        
	        htmlRepresentation = sb.toString();
        
    	}
    	
		return htmlRepresentation;
	}
    
 

    public void setReturnType( String freturn ) {
        this.returnType = freturn;
    }
    
    public String getReturnType() {
        return returnType;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TFunction other = (TFunction) obj;

		if(! name().equals(other.name())) return false;
		
		if (paramCount != other.getParamCount()) return false;
		
		// Same name and not have param's 
		if( ! hasParams() && !other.hasParams()) return true;
		
		Set<TParam> params2 = other.getParams();

		for (TAttribute param : params) {
			if(params2.contains(param)){
				return false;
			}
		}
		
		return true;
	}
    
    public void setLocalVariables(Set<TAttribute> localVariables) {
		this.localVariables = localVariables;
	}
    
    /**
     * Variables defined in body of functions
     * @return
     */
    public Set<TAttribute> getLocalVariables() {
		return localVariables;
	}

	public void addAllLocalVariables(Set<TAttribute> localVariable) {
		for (TAttribute tAttribute : localVariable) {
            addLocalVariable(tAttribute);
        }
	}
   
	public void addLocalVariable(TAttribute localVariable) {
		this.localVariables.add(localVariable);
		localVariable.setLocal(true);
		if(localVariable.getParent() == null) localVariable.setParent(this);
	}
	
	
	@Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        if(isConstructor()){
            return name() +  getParamsAsString();
        }else{
            if(getParent() != null) sb.append(getParent().name());
            
            sb.append((isStatic ? STR_STATIC_TOKEN : STR_DOT_TOKEN) + toDeclarationString());
            
            return sb.toString();
        }

    }
	    
    public String getParamsAsString(){
        
        if(!hasParams()) return STR_EMPTY_PARAM;
        
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        for (TAttribute tAttribute : params) {
            sb.append(tAttribute.getType());
            sb.append(STR_WHITESPACE);
            sb.append(tAttribute.name());
            sb.append(',');
        }
        
        sb.deleteCharAt(sb.length() - 1);
        sb.append(')');
        
        if(getReturnType() != null && getReturnType().length() > 0){
            sb.append(" : ").append(getReturnType());
        }
        
        return sb.toString();
    }

}
