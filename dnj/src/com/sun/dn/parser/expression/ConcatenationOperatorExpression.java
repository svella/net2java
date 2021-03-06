
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
package com.sun.dn.parser.expression;

import java.util.*;
import com.sun.dn.*;
import com.sun.dn.parser.*;
import com.sun.dn.util.*;

	/** A .NET expression adding two stings together. Defined as
	** ConcatenationOperatorExpression ::= Expression & Expression
	** @author danny.coward@sun.com
	*/

public class ConcatenationOperatorExpression extends BinaryOperatorExpression {

	public static ConcatenationOperatorExpression createVBConcatenationOperatorExpression(String code,  InterpretationContext context) {
		ConcatenationOperatorExpression coe = new ConcatenationOperatorExpression(code, context);
		coe.context = context;
		String sep = getVBOperatorString(code);
		coe.parseVB(code, sep);
		return coe;
	}
        
        public String getOperator() {
            return "&";
        }

	private ConcatenationOperatorExpression(String code, InterpretationContext context) {
            super(code, context);
        }

	ConcatenationOperatorExpression(Expression left, Expression right, InterpretationContext context) {
		super("<Expression created by parser>", context);
		super.leftExpression = left;
		super.rightExpression = right;
	}


	public String toString() {
		return "ConcatOpExpressn of " + leftExpression + " and " + rightExpression;
	}

	public String getTypeName() {
		return "System.String";
	}

	public DNType getDNType() {
		return super.context.getLibrary().getLibraryData().getLibraryClass(this.getTypeName());
	}

	public String tryAsJava() {
		return leftExpression.asJava() + " + " + rightExpression.asJava();
	}
}
 