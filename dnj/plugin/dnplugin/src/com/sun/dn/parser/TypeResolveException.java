
 /* 
 * Copyright (c) 2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *  
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *  
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *  
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *  
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */
package com.sun.dn.parser;

import java.util.*;

	/** Exception class for failures to find
	** Java code for a piece of .NET Code.
	@author danny.coward@sun.com
	*/

public class TypeResolveException extends RuntimeException {
	private String code;
	private String containingStatement;


	public TypeResolveException(String code, String message) {
		super(message);
		this.code = code;
	}

	public void setContainingStatement(String containingStatement) {
		this.containingStatement = containingStatement;
	}

	public String getContainingStatement() {
            if (containingStatement == null) {
                return this.getCode();
            }
            return this.containingStatement;
	}

	public String getCode() {
		return code;
	}

}
 