// $ANTLR 3.0b6 PerComment.g 2007-02-01 16:20:53

/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Generated from PerComment.g by ANTLR.
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ANTLR lexer for PerComment.g
 *
 */
package org.acmsl.queryj.tools.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class PerCommentLexer extends Lexer {
    public static final int COMMA=13;
    public static final int LETTER=15;
    public static final int BOOL=9;
    public static final int CLOSE_PAREN=14;
    public static final int ISATYPE=7;
    public static final int READONLY=8;
    public static final int ISA=6;
    public static final int ISAREFS=11;
    public static final int WHITESPACE=17;
    public static final int STATIC=5;
    public static final int EOF=-1;
    public static final int TEXT=4;
    public static final int OPEN_PAREN=12;
    public static final int Tokens=19;
    public static final int NAMECHAR=16;
    public static final int DIGIT=18;
    public static final int ID=10;
    public PerCommentLexer() {;} 
    public PerCommentLexer(CharStream input) {
        super(input);
    }
    public String getGrammarFileName() { return "PerComment.g"; }

    // $ANTLR start ID
    public void mID() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = ID;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // PerComment.g:400:7: ( ( LETTER | '_' ) ( NAMECHAR )* )
            // PerComment.g:400:7: ( LETTER | '_' ) ( NAMECHAR )*
            {
            // PerComment.g:400:7: ( LETTER | '_' )
            int alt1=2;
            int LA1_0 = input.LA(1);
            if ( ((LA1_0>='A' && LA1_0<='Z')||(LA1_0>='a' && LA1_0<='z')) ) {
                alt1=1;
            }
            else if ( (LA1_0=='_') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("400:7: ( LETTER | '_' )", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // PerComment.g:400:9: LETTER
                    {
                    mLETTER(); 

                    }
                    break;
                case 2 :
                    // PerComment.g:400:18: '_'
                    {
                    match('_'); 

                    }
                    break;

            }

            // PerComment.g:400:24: ( NAMECHAR )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);
                if ( (LA2_0=='$'||(LA2_0>='-' && LA2_0<='.')||(LA2_0>='0' && LA2_0<=':')||(LA2_0>='A' && LA2_0<='Z')||LA2_0=='_'||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // PerComment.g:400:25: NAMECHAR
            	    {
            	    mNAMECHAR(); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end ID

    // $ANTLR start WHITESPACE
    public void mWHITESPACE() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = WHITESPACE;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // PerComment.g:403:14: ( ( ('\\t'|' '|'\\r'|'\\n'|'\\u000C'))+ )
            // PerComment.g:403:14: ( ('\\t'|' '|'\\r'|'\\n'|'\\u000C'))+
            {
            // PerComment.g:403:14: ( ('\\t'|' '|'\\r'|'\\n'|'\\u000C'))+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);
                if ( ((LA3_0>='\t' && LA3_0<='\n')||(LA3_0>='\f' && LA3_0<='\r')||LA3_0==' ') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // PerComment.g:403:16: ('\\t'|' '|'\\r'|'\\n'|'\\u000C')
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);

             _channel = HIDDEN; 

            }



                    if ( token==null && ruleNestingLevel==1 ) {
                        emit(_type,_line,_charPosition,_channel,_start,getCharIndex()-1);
                    }

                        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end WHITESPACE

    // $ANTLR start TEXT
    public void mTEXT() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // PerComment.g:405:18: ( (~ '@' )+ )
            // PerComment.g:405:18: (~ '@' )+
            {
            // PerComment.g:405:18: (~ '@' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);
                if ( ((LA4_0>='\u0000' && LA4_0<='?')||(LA4_0>='A' && LA4_0<='\uFFFE')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // PerComment.g:405:19: ~ '@'
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='?')||(input.LA(1)>='A' && input.LA(1)<='\uFFFE') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end TEXT

    // $ANTLR start STATIC
    public void mSTATIC() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // PerComment.g:406:19: ( '@static' )
            // PerComment.g:406:19: '@static'
            {
            match("@static"); 


            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end STATIC

    // $ANTLR start ISA
    public void mISA() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // PerComment.g:407:16: ( '@isa' )
            // PerComment.g:407:16: '@isa'
            {
            match("@isa"); 


            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end ISA

    // $ANTLR start ISATYPE
    public void mISATYPE() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // PerComment.g:408:20: ( '@isatype' )
            // PerComment.g:408:20: '@isatype'
            {
            match("@isatype"); 


            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end ISATYPE

    // $ANTLR start ISAREFS
    public void mISAREFS() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // PerComment.g:409:20: ( '@isarefs' )
            // PerComment.g:409:20: '@isarefs'
            {
            match("@isarefs"); 


            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end ISAREFS

    // $ANTLR start READONLY
    public void mREADONLY() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // PerComment.g:410:21: ( '@readonly' )
            // PerComment.g:410:21: '@readonly'
            {
            match("@readonly"); 


            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end READONLY

    // $ANTLR start BOOL
    public void mBOOL() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // PerComment.g:411:17: ( '@bool' )
            // PerComment.g:411:17: '@bool'
            {
            match("@bool"); 


            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end BOOL

    // $ANTLR start OPEN_PAREN
    public void mOPEN_PAREN() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // PerComment.g:412:23: ( '(' )
            // PerComment.g:412:23: '('
            {
            match('('); 

            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end OPEN_PAREN

    // $ANTLR start CLOSE_PAREN
    public void mCLOSE_PAREN() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // PerComment.g:413:24: ( ')' )
            // PerComment.g:413:24: ')'
            {
            match(')'); 

            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end CLOSE_PAREN

    // $ANTLR start COMMA
    public void mCOMMA() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // PerComment.g:414:18: ( ',' )
            // PerComment.g:414:18: ','
            {
            match(','); 

            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end COMMA

    // $ANTLR start NAMECHAR
    public void mNAMECHAR() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // PerComment.g:418:7: ( LETTER | DIGIT | '.' | '-' | '_' | ':' | '$' )
            int alt5=7;
            switch ( input.LA(1) ) {
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
                alt5=1;
                break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                alt5=2;
                break;
            case '.':
                alt5=3;
                break;
            case '-':
                alt5=4;
                break;
            case '_':
                alt5=5;
                break;
            case ':':
                alt5=6;
                break;
            case '$':
                alt5=7;
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("417:10: fragment NAMECHAR : ( LETTER | DIGIT | '.' | '-' | '_' | ':' | '$' );", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // PerComment.g:418:7: LETTER
                    {
                    mLETTER(); 

                    }
                    break;
                case 2 :
                    // PerComment.g:418:16: DIGIT
                    {
                    mDIGIT(); 

                    }
                    break;
                case 3 :
                    // PerComment.g:418:24: '.'
                    {
                    match('.'); 

                    }
                    break;
                case 4 :
                    // PerComment.g:418:30: '-'
                    {
                    match('-'); 

                    }
                    break;
                case 5 :
                    // PerComment.g:418:36: '_'
                    {
                    match('_'); 

                    }
                    break;
                case 6 :
                    // PerComment.g:418:42: ':'
                    {
                    match(':'); 

                    }
                    break;
                case 7 :
                    // PerComment.g:418:48: '$'
                    {
                    match('$'); 

                    }
                    break;

            }
        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end NAMECHAR

    // $ANTLR start DIGIT
    public void mDIGIT() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // PerComment.g:422:10: ( '0' .. '9' )
            // PerComment.g:422:10: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end DIGIT

    // $ANTLR start LETTER
    public void mLETTER() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // PerComment.g:426:5: ( ('a'..'z'|'A'..'Z'))
            // PerComment.g:426:7: ('a'..'z'|'A'..'Z')
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end LETTER

    public void mTokens() throws RecognitionException {
        // PerComment.g:1:10: ( ID | WHITESPACE )
        int alt6=2;
        int LA6_0 = input.LA(1);
        if ( ((LA6_0>='A' && LA6_0<='Z')||LA6_0=='_'||(LA6_0>='a' && LA6_0<='z')) ) {
            alt6=1;
        }
        else if ( ((LA6_0>='\t' && LA6_0<='\n')||(LA6_0>='\f' && LA6_0<='\r')||LA6_0==' ') ) {
            alt6=2;
        }
        else {
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( ID | WHITESPACE );", 6, 0, input);

            throw nvae;
        }
        switch (alt6) {
            case 1 :
                // PerComment.g:1:10: ID
                {
                mID(); 

                }
                break;
            case 2 :
                // PerComment.g:1:13: WHITESPACE
                {
                mWHITESPACE(); 

                }
                break;

        }

    }


 

}