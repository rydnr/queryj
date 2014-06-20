//;-*- mode: antlr -*-
/*
                        QueryJ Debugging

    Copyright (C) 2002-today  Jose San Leandro Armendariz
                        chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: TemplateDebugging.g4
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the ANTLR parser rules for template debugging in
 *              QueryJ.
 *
 */
grammar TemplateDebugging;

@parser::header
{
/*
                        QueryJ Debugging

    Copyright (C) 2002-today  Jose San Leandro Armendariz
                        chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Generated from TemplateDebugging.g4 by ANTLR.
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ANTLR parser for TemplateDebugging.g4
 *
 */
}

@lexer::header
{
/*
                        QueryJ Debugging

    Copyright (C) 2002-today  Jose San Leandro Armendariz
                        chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Generated from TemplateDebugging.g4 by ANTLR.
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ANTLR lexer for TemplateDebugging.g4
 *
 */
}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

command : reload | nextTable | prevTable;

reload : RELOAD;
nextTable: NEXT;
prevTable: PREV;

/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

WS : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip;

// keywords
RELOAD: 'reload';
NEXT: 'next';
PREV : 'prev';
