grammar ExtractFilter;
/*
 * Copyright Contributors to the GenevaERS Project.
 * (c) Copyright IBM Corporation 2020.
 * SPDX-License-Identifier: Apache-2.0  
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
import ExtractParser;

goal            : stmtList EOF
                ;

stmtList        : ( stmt )*
                ;

stmt            : ifStatementTop 
                | selectIf
                | writeStatement
                ;

ifStatementTop  : IF ifbody
                ;

ifbody          : predicate 
		    THEN thenStmtChild
                  ( ELSE ifStmtChild)? 
		    ENDIF
				    ;

thenStmtChild   : ifStmtChild 
                | selectStatement
                ;

ifStmtChild     : IF ifbody
                ;

selectStatement : SELECT | SKIPF
                ;

selectIf        : ( SKIPIF | SELECTIF ) LPAREN predicate RPAREN
                ;

                  
