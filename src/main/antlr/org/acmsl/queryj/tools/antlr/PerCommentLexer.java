// $ANTLR 3.0b6 PerComment.g 2007-02-02 10:11:00

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
    public static final int WHITESPACE=18;
    public static final int STATIC=5;
    public static final int EOF=-1;
    public static final int TEXT=4;
    public static final int OPEN_PAREN=12;
    public static final int Tokens=19;
    public static final int NAMECHAR=16;
    public static final int DIGIT=17;
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
            // PerComment.g:405:7: ( ( LETTER | '_' ) ( NAMECHAR )* )
            // PerComment.g:405:7: ( LETTER | '_' ) ( NAMECHAR )*
            {
            // PerComment.g:405:7: ( LETTER | '_' )
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
                    new NoViableAltException("405:7: ( LETTER | '_' )", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // PerComment.g:405:9: LETTER
                    {
                    mLETTER(); 

                    }
                    break;
                case 2 :
                    // PerComment.g:405:18: '_'
                    {
                    match('_'); 

                    }
                    break;

            }

            // PerComment.g:405:24: ( NAMECHAR )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);
                if ( (LA2_0=='$'||(LA2_0>='-' && LA2_0<='.')||(LA2_0>='0' && LA2_0<=':')||(LA2_0>='A' && LA2_0<='Z')||LA2_0=='_'||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // PerComment.g:405:25: NAMECHAR
            	    {
            	    mNAMECHAR(); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

        }
        finally {
            ruleNestingLevel--;
        }
    }
    // $ANTLR end ID

    // $ANTLR start TEXT
    public void mTEXT() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = TEXT;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // PerComment.g:408:9: ( (~ '@' )+ )
            // PerComment.g:408:9: (~ '@' )+
            {
            // PerComment.g:408:9: (~ '@' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);
                if ( ((LA3_0>='\u0000' && LA3_0<='?')||(LA3_0>='A' && LA3_0<='\uFFFE')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // PerComment.g:408:10: ~ '@'
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
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
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
    // $ANTLR end TEXT

    // $ANTLR start STATIC
    public void mSTATIC() throws RecognitionException {
        try {
            ruleNestingLevel++;
            // PerComment.g:410:19: ( '@static' )
            // PerComment.g:410:19: '@static'
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
            // PerComment.g:411:16: ( '@isa' )
            // PerComment.g:411:16: '@isa'
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
            // PerComment.g:412:20: ( '@isatype' )
            // PerComment.g:412:20: '@isatype'
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
            // PerComment.g:413:20: ( '@isarefs' )
            // PerComment.g:413:20: '@isarefs'
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
            // PerComment.g:414:21: ( '@readonly' )
            // PerComment.g:414:21: '@readonly'
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
            // PerComment.g:415:17: ( '@bool' )
            // PerComment.g:415:17: '@bool'
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
            // PerComment.g:416:23: ( '(' )
            // PerComment.g:416:23: '('
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
            // PerComment.g:417:24: ( ')' )
            // PerComment.g:417:24: ')'
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
            // PerComment.g:418:18: ( ',' )
            // PerComment.g:418:18: ','
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
            // PerComment.g:422:7: ( LETTER | DIGIT | '.' | '-' | '_' | ':' | '$' )
            int alt4=7;
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
                alt4=1;
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
                alt4=2;
                break;
            case '.':
                alt4=3;
                break;
            case '-':
                alt4=4;
                break;
            case '_':
                alt4=5;
                break;
            case ':':
                alt4=6;
                break;
            case '$':
                alt4=7;
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("421:10: fragment NAMECHAR : ( LETTER | DIGIT | '.' | '-' | '_' | ':' | '$' );", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // PerComment.g:422:7: LETTER
                    {
                    mLETTER(); 

                    }
                    break;
                case 2 :
                    // PerComment.g:422:16: DIGIT
                    {
                    mDIGIT(); 

                    }
                    break;
                case 3 :
                    // PerComment.g:422:24: '.'
                    {
                    match('.'); 

                    }
                    break;
                case 4 :
                    // PerComment.g:422:30: '-'
                    {
                    match('-'); 

                    }
                    break;
                case 5 :
                    // PerComment.g:422:36: '_'
                    {
                    match('_'); 

                    }
                    break;
                case 6 :
                    // PerComment.g:422:42: ':'
                    {
                    match(':'); 

                    }
                    break;
                case 7 :
                    // PerComment.g:422:48: '$'
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
            // PerComment.g:426:10: ( '0' .. '9' )
            // PerComment.g:426:10: '0' .. '9'
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
            // PerComment.g:430:5: ( ('a'..'z'|'A'..'Z'))
            // PerComment.g:430:7: ('a'..'z'|'A'..'Z')
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

    // $ANTLR start WHITESPACE
    public void mWHITESPACE() throws RecognitionException {
        try {
            ruleNestingLevel++;
            int _type = WHITESPACE;
            int _start = getCharIndex();
            int _line = getLine();
            int _charPosition = getCharPositionInLine();
            int _channel = Token.DEFAULT_CHANNEL;
            // PerComment.g:434:14: ( ( ('\\t'|' '|'\\r'|'\\n'|'\\u000C'))+ )
            // PerComment.g:434:14: ( ('\\t'|' '|'\\r'|'\\n'|'\\u000C'))+
            {
            // PerComment.g:434:14: ( ('\\t'|' '|'\\r'|'\\n'|'\\u000C'))+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);
                if ( ((LA5_0>='\t' && LA5_0<='\n')||(LA5_0>='\f' && LA5_0<='\r')||LA5_0==' ') ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // PerComment.g:434:16: ('\\t'|' '|'\\r'|'\\n'|'\\u000C')
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
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
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

    public void mTokens() throws RecognitionException {
        // PerComment.g:1:10: ( TEXT | WHITESPACE )
        int alt6=2;
        alt6 = dfa6.predict(input);
        switch (alt6) {
            case 1 :
                // PerComment.g:1:10: TEXT
                {
                mTEXT(); 

                }
                break;
            case 2 :
                // PerComment.g:1:15: WHITESPACE
                {
                mWHITESPACE(); 

                }
                break;

        }

    }


    protected DFA6 dfa6 = new DFA6(this);
    public static final String DFA6_eotS =
        "\1\uffff\1\2\1\uffff";
    public static final String DFA6_eofS =
        "\3\uffff";
    public static final String DFA6_minS =
        "\1\0\1\11\1\uffff";
    public static final String DFA6_maxS =
        "\1\ufffe\1\40\1\uffff";
    public static final String DFA6_acceptS =
        "\2\uffff\1\1";
    public static final String DFA6_specialS =
        "\3\uffff}>";
    public static final String[] DFA6_transition = {
        "\11\2\2\1\1\2\2\1\22\2\1\1\37\2\1\uffff\uffbe\2",
        "\2\1\1\uffff\2\1\22\uffff\1\1",
        ""
    };

    class DFA6 extends DFA {
        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = DFA.unpackEncodedString(DFA6_eotS);
            this.eof = DFA.unpackEncodedString(DFA6_eofS);
            this.min = DFA.unpackEncodedStringToUnsignedChars(DFA6_minS);
            this.max = DFA.unpackEncodedStringToUnsignedChars(DFA6_maxS);
            this.accept = DFA.unpackEncodedString(DFA6_acceptS);
            this.special = DFA.unpackEncodedString(DFA6_specialS);
            int numStates = DFA6_transition.length;
            this.transition = new short[numStates][];
            for (int i=0; i<numStates; i++) {
                transition[i] = DFA.unpackEncodedString(DFA6_transition[i]);
            }
        }
        public String getDescription() {
            return "1:1: Tokens : ( TEXT | WHITESPACE );";
        }
    }
 

}