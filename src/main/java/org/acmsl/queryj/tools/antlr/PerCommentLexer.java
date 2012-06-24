// $ANTLR 3.4 PerComment.g 2012-06-22 06:18:26

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
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class PerCommentLexer extends Lexer {
    public static final int EOF=-1;
    public static final int AT=4;
    public static final int BOOL=5;
    public static final int CLOSE_PAREN=6;
    public static final int COMMA=7;
    public static final int DECORATOR=8;
    public static final int DIGIT=9;
    public static final int DQUOTE=10;
    public static final int ID=11;
    public static final int ISA=12;
    public static final int ISAREFS=13;
    public static final int ISATYPE=14;
    public static final int LETTER=15;
    public static final int NAMECHAR=16;
    public static final int OPEN_PAREN=17;
    public static final int ORASEQ=18;
    public static final int QUOTE=19;
    public static final int READONLY=20;
    public static final int RELATIONSHIP=21;
    public static final int SQUOTE=22;
    public static final int STATIC=23;
    public static final int TEXT=24;
    public static final int WS=25;

    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public PerCommentLexer() {} 
    public PerCommentLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public PerCommentLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "PerComment.g"; }

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PerComment.g:631:5: ( ( ( '@static' )=> STATIC | ( '@isa' )=> ISA | ( '@isatype' )=> ISATYPE | ( '@decorator' )=> DECORATOR | ( '@isarefs' )=> ISAREFS | ( '@readonly' )=> READONLY | ( '@bool' )=> BOOL | ( '@oraseq' )=> ORASEQ | ( '@relationship' )=> RELATIONSHIP | '@' ) )
            // PerComment.g:631:8: ( ( '@static' )=> STATIC | ( '@isa' )=> ISA | ( '@isatype' )=> ISATYPE | ( '@decorator' )=> DECORATOR | ( '@isarefs' )=> ISAREFS | ( '@readonly' )=> READONLY | ( '@bool' )=> BOOL | ( '@oraseq' )=> ORASEQ | ( '@relationship' )=> RELATIONSHIP | '@' )
            {
            // PerComment.g:631:8: ( ( '@static' )=> STATIC | ( '@isa' )=> ISA | ( '@isatype' )=> ISATYPE | ( '@decorator' )=> DECORATOR | ( '@isarefs' )=> ISAREFS | ( '@readonly' )=> READONLY | ( '@bool' )=> BOOL | ( '@oraseq' )=> ORASEQ | ( '@relationship' )=> RELATIONSHIP | '@' )
            int alt1=10;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='@') ) {
                int LA1_1 = input.LA(2);

                if ( (LA1_1=='s') && (synpred1_PerComment())) {
                    alt1=1;
                }
                else if ( (LA1_1=='i') ) {
                    int LA1_3 = input.LA(3);

                    if ( (LA1_3=='s') ) {
                        int LA1_9 = input.LA(4);

                        if ( (LA1_9=='a') ) {
                            int LA1_11 = input.LA(5);

                            if ( (LA1_11=='t') && (synpred3_PerComment())) {
                                alt1=3;
                            }
                            else if ( (LA1_11=='r') && (synpred5_PerComment())) {
                                alt1=5;
                            }
                            else {
                                alt1=2;
                            }
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 1, 9, input);

                            throw nvae;

                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 1, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA1_1=='d') && (synpred4_PerComment())) {
                    alt1=4;
                }
                else if ( (LA1_1=='r') ) {
                    int LA1_5 = input.LA(3);

                    if ( (LA1_5=='e') ) {
                        int LA1_10 = input.LA(4);

                        if ( (LA1_10=='a') && (synpred6_PerComment())) {
                            alt1=6;
                        }
                        else if ( (LA1_10=='l') && (synpred9_PerComment())) {
                            alt1=9;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 1, 10, input);

                            throw nvae;

                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 1, 5, input);

                        throw nvae;

                    }
                }
                else if ( (LA1_1=='b') && (synpred7_PerComment())) {
                    alt1=7;
                }
                else if ( (LA1_1=='o') && (synpred8_PerComment())) {
                    alt1=8;
                }
                else {
                    alt1=10;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;

            }
            switch (alt1) {
                case 1 :
                    // PerComment.g:631:11: ( '@static' )=> STATIC
                    {
                    mSTATIC(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = STATIC;}

                    }
                    break;
                case 2 :
                    // PerComment.g:632:11: ( '@isa' )=> ISA
                    {
                    mISA(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = ISA;}

                    }
                    break;
                case 3 :
                    // PerComment.g:633:11: ( '@isatype' )=> ISATYPE
                    {
                    mISATYPE(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = ISATYPE;}

                    }
                    break;
                case 4 :
                    // PerComment.g:634:11: ( '@decorator' )=> DECORATOR
                    {
                    mDECORATOR(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = DECORATOR;}

                    }
                    break;
                case 5 :
                    // PerComment.g:635:11: ( '@isarefs' )=> ISAREFS
                    {
                    mISAREFS(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = ISAREFS;}

                    }
                    break;
                case 6 :
                    // PerComment.g:636:11: ( '@readonly' )=> READONLY
                    {
                    mREADONLY(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = READONLY;}

                    }
                    break;
                case 7 :
                    // PerComment.g:637:11: ( '@bool' )=> BOOL
                    {
                    mBOOL(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = BOOL;}

                    }
                    break;
                case 8 :
                    // PerComment.g:638:11: ( '@oraseq' )=> ORASEQ
                    {
                    mORASEQ(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = ORASEQ;}

                    }
                    break;
                case 9 :
                    // PerComment.g:639:11: ( '@relationship' )=> RELATIONSHIP
                    {
                    mRELATIONSHIP(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = RELATIONSHIP;}

                    }
                    break;
                case 10 :
                    // PerComment.g:640:11: '@'
                    {
                    match('@'); if (state.failed) return ;

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "AT"

    // $ANTLR start "SQUOTE"
    public final void mSQUOTE() throws RecognitionException {
        try {
            int _type = SQUOTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PerComment.g:643:8: ( ( '\\'' ) )
            // PerComment.g:643:10: ( '\\'' )
            {
            if ( input.LA(1)=='\'' ) {
                input.consume();
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQUOTE"

    // $ANTLR start "DQUOTE"
    public final void mDQUOTE() throws RecognitionException {
        try {
            int _type = DQUOTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PerComment.g:644:8: ( ( '\"' ) )
            // PerComment.g:644:10: ( '\"' )
            {
            if ( input.LA(1)=='\"' ) {
                input.consume();
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DQUOTE"

    // $ANTLR start "QUOTE"
    public final void mQUOTE() throws RecognitionException {
        try {
            // PerComment.g:645:16: ( ( SQUOTE | DQUOTE ) )
            // PerComment.g:
            {
            if ( input.LA(1)=='\"'||input.LA(1)=='\'' ) {
                input.consume();
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "QUOTE"

    // $ANTLR start "OPEN_PAREN"
    public final void mOPEN_PAREN() throws RecognitionException {
        try {
            int _type = OPEN_PAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PerComment.g:648:12: ( '(' )
            // PerComment.g:648:14: '('
            {
            match('('); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OPEN_PAREN"

    // $ANTLR start "CLOSE_PAREN"
    public final void mCLOSE_PAREN() throws RecognitionException {
        try {
            int _type = CLOSE_PAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PerComment.g:649:13: ( ')' )
            // PerComment.g:649:15: ')'
            {
            match(')'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CLOSE_PAREN"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PerComment.g:650:7: ( ',' )
            // PerComment.g:650:9: ','
            {
            match(','); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "STATIC"
    public final void mSTATIC() throws RecognitionException {
        try {
            // PerComment.g:653:17: ( '@static' )
            // PerComment.g:653:19: '@static'
            {
            match("@static"); if (state.failed) return ;



            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STATIC"

    // $ANTLR start "ISA"
    public final void mISA() throws RecognitionException {
        try {
            // PerComment.g:654:14: ( '@isa' )
            // PerComment.g:654:16: '@isa'
            {
            match("@isa"); if (state.failed) return ;



            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ISA"

    // $ANTLR start "ISATYPE"
    public final void mISATYPE() throws RecognitionException {
        try {
            // PerComment.g:655:18: ( '@isatype' )
            // PerComment.g:655:20: '@isatype'
            {
            match("@isatype"); if (state.failed) return ;



            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ISATYPE"

    // $ANTLR start "DECORATOR"
    public final void mDECORATOR() throws RecognitionException {
        try {
            // PerComment.g:656:20: ( '@decorator' )
            // PerComment.g:656:22: '@decorator'
            {
            match("@decorator"); if (state.failed) return ;



            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DECORATOR"

    // $ANTLR start "ISAREFS"
    public final void mISAREFS() throws RecognitionException {
        try {
            // PerComment.g:657:18: ( '@isarefs' )
            // PerComment.g:657:20: '@isarefs'
            {
            match("@isarefs"); if (state.failed) return ;



            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ISAREFS"

    // $ANTLR start "READONLY"
    public final void mREADONLY() throws RecognitionException {
        try {
            // PerComment.g:658:19: ( '@readonly' )
            // PerComment.g:658:21: '@readonly'
            {
            match("@readonly"); if (state.failed) return ;



            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "READONLY"

    // $ANTLR start "RELATIONSHIP"
    public final void mRELATIONSHIP() throws RecognitionException {
        try {
            // PerComment.g:659:23: ( '@relationship' )
            // PerComment.g:659:25: '@relationship'
            {
            match("@relationship"); if (state.failed) return ;



            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RELATIONSHIP"

    // $ANTLR start "BOOL"
    public final void mBOOL() throws RecognitionException {
        try {
            // PerComment.g:660:15: ( '@bool' )
            // PerComment.g:660:17: '@bool'
            {
            match("@bool"); if (state.failed) return ;



            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "BOOL"

    // $ANTLR start "ORASEQ"
    public final void mORASEQ() throws RecognitionException {
        try {
            // PerComment.g:661:17: ( '@oraseq' )
            // PerComment.g:661:19: '@oraseq'
            {
            match("@oraseq"); if (state.failed) return ;



            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ORASEQ"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PerComment.g:663:4: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+ )
            // PerComment.g:663:6: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            {
            // PerComment.g:663:6: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0 >= '\t' && LA2_0 <= '\n')||(LA2_0 >= '\f' && LA2_0 <= '\r')||LA2_0==' ') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // PerComment.g:
            	    {
            	    if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||(input.LA(1) >= '\f' && input.LA(1) <= '\r')||input.LA(1)==' ' ) {
            	        input.consume();
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PerComment.g:666:5: ( ( LETTER | '_' | DIGIT ) ( NAMECHAR )* ( WS )? )
            // PerComment.g:666:7: ( LETTER | '_' | DIGIT ) ( NAMECHAR )* ( WS )?
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // PerComment.g:666:31: ( NAMECHAR )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='$'||(LA3_0 >= '-' && LA3_0 <= '.')||(LA3_0 >= '0' && LA3_0 <= ':')||(LA3_0 >= 'A' && LA3_0 <= 'Z')||LA3_0=='_'||(LA3_0 >= 'a' && LA3_0 <= 'z')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // PerComment.g:
            	    {
            	    if ( input.LA(1)=='$'||(input.LA(1) >= '-' && input.LA(1) <= '.')||(input.LA(1) >= '0' && input.LA(1) <= ':')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            // PerComment.g:666:43: ( WS )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( ((LA4_0 >= '\t' && LA4_0 <= '\n')||(LA4_0 >= '\f' && LA4_0 <= '\r')||LA4_0==' ') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // PerComment.g:666:43: WS
                    {
                    mWS(); if (state.failed) return ;


                    }
                    break;

            }


            if ( state.backtracking==0 ) {_type = ID;}

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "TEXT"
    public final void mTEXT() throws RecognitionException {
        try {
            int _type = TEXT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PerComment.g:670:4: ( ( ( ID )=> ID | ( '\\t' )=> WS | ( ' ' )=> WS | ( '\\r' )=> WS | ( '\\n' )=> WS | ( '\\u000C' )=> WS | ( '(' )=> OPEN_PAREN | ( ')' )=> CLOSE_PAREN | ( ',' )=> COMMA | ( '\\'' | '\"' )=> QUOTE | ( '@' )=> AT | ( (~ ( '@' | ',' | '(' | ')' | '\\'' | '\"' ) )+ ) ) )
            // PerComment.g:670:6: ( ( ID )=> ID | ( '\\t' )=> WS | ( ' ' )=> WS | ( '\\r' )=> WS | ( '\\n' )=> WS | ( '\\u000C' )=> WS | ( '(' )=> OPEN_PAREN | ( ')' )=> CLOSE_PAREN | ( ',' )=> COMMA | ( '\\'' | '\"' )=> QUOTE | ( '@' )=> AT | ( (~ ( '@' | ',' | '(' | ')' | '\\'' | '\"' ) )+ ) )
            {
            // PerComment.g:670:6: ( ( ID )=> ID | ( '\\t' )=> WS | ( ' ' )=> WS | ( '\\r' )=> WS | ( '\\n' )=> WS | ( '\\u000C' )=> WS | ( '(' )=> OPEN_PAREN | ( ')' )=> CLOSE_PAREN | ( ',' )=> COMMA | ( '\\'' | '\"' )=> QUOTE | ( '@' )=> AT | ( (~ ( '@' | ',' | '(' | ')' | '\\'' | '\"' ) )+ ) )
            int alt6=12;
            int LA6_0 = input.LA(1);

            if ( ((LA6_0 >= '0' && LA6_0 <= '9')||(LA6_0 >= 'A' && LA6_0 <= 'Z')||LA6_0=='_'||(LA6_0 >= 'a' && LA6_0 <= 'z')) ) {
                int LA6_1 = input.LA(2);

                if ( (synpred10_PerComment()) ) {
                    alt6=1;
                }
                else if ( (true) ) {
                    alt6=12;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 1, input);

                    throw nvae;

                }
            }
            else if ( ((LA6_0 >= '\t' && LA6_0 <= '\n')||(LA6_0 >= '\f' && LA6_0 <= '\r')||LA6_0==' ') ) {
                int LA6_2 = input.LA(2);

                if ( (synpred11_PerComment()) ) {
                    alt6=2;
                }
                else if ( (synpred12_PerComment()) ) {
                    alt6=3;
                }
                else if ( (synpred13_PerComment()) ) {
                    alt6=4;
                }
                else if ( (synpred14_PerComment()) ) {
                    alt6=5;
                }
                else if ( (synpred15_PerComment()) ) {
                    alt6=6;
                }
                else if ( (true) ) {
                    alt6=12;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 2, input);

                    throw nvae;

                }
            }
            else if ( (LA6_0=='(') && (synpred16_PerComment())) {
                alt6=7;
            }
            else if ( (LA6_0==')') && (synpred17_PerComment())) {
                alt6=8;
            }
            else if ( (LA6_0==',') && (synpred18_PerComment())) {
                alt6=9;
            }
            else if ( (LA6_0=='\"'||LA6_0=='\'') && (synpred19_PerComment())) {
                alt6=10;
            }
            else if ( (LA6_0=='@') && (synpred20_PerComment())) {
                alt6=11;
            }
            else if ( ((LA6_0 >= '\u0000' && LA6_0 <= '\b')||LA6_0=='\u000B'||(LA6_0 >= '\u000E' && LA6_0 <= '\u001F')||LA6_0=='!'||(LA6_0 >= '#' && LA6_0 <= '&')||(LA6_0 >= '*' && LA6_0 <= '+')||(LA6_0 >= '-' && LA6_0 <= '/')||(LA6_0 >= ':' && LA6_0 <= '?')||(LA6_0 >= '[' && LA6_0 <= '^')||LA6_0=='`'||(LA6_0 >= '{' && LA6_0 <= '\uFFFF')) ) {
                alt6=12;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;

            }
            switch (alt6) {
                case 1 :
                    // PerComment.g:670:9: ( ID )=> ID
                    {
                    mID(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = ID;}

                    }
                    break;
                case 2 :
                    // PerComment.g:671:9: ( '\\t' )=> WS
                    {
                    mWS(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = WS;}

                    }
                    break;
                case 3 :
                    // PerComment.g:672:9: ( ' ' )=> WS
                    {
                    mWS(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = WS;}

                    }
                    break;
                case 4 :
                    // PerComment.g:673:9: ( '\\r' )=> WS
                    {
                    mWS(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = WS;}

                    }
                    break;
                case 5 :
                    // PerComment.g:674:9: ( '\\n' )=> WS
                    {
                    mWS(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = WS;}

                    }
                    break;
                case 6 :
                    // PerComment.g:675:9: ( '\\u000C' )=> WS
                    {
                    mWS(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = WS;}

                    }
                    break;
                case 7 :
                    // PerComment.g:676:9: ( '(' )=> OPEN_PAREN
                    {
                    mOPEN_PAREN(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = OPEN_PAREN;}

                    }
                    break;
                case 8 :
                    // PerComment.g:677:9: ( ')' )=> CLOSE_PAREN
                    {
                    mCLOSE_PAREN(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = CLOSE_PAREN;}

                    }
                    break;
                case 9 :
                    // PerComment.g:678:9: ( ',' )=> COMMA
                    {
                    mCOMMA(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = COMMA;}

                    }
                    break;
                case 10 :
                    // PerComment.g:679:9: ( '\\'' | '\"' )=> QUOTE
                    {
                    mQUOTE(); if (state.failed) return ;


                    if ( state.backtracking==0 ) {_type = QUOTE;}

                    }
                    break;
                case 11 :
                    // PerComment.g:680:9: ( '@' )=> AT
                    {
                    mAT(); if (state.failed) return ;


                    }
                    break;
                case 12 :
                    // PerComment.g:681:9: ( (~ ( '@' | ',' | '(' | ')' | '\\'' | '\"' ) )+ )
                    {
                    // PerComment.g:681:9: ( (~ ( '@' | ',' | '(' | ')' | '\\'' | '\"' ) )+ )
                    // PerComment.g:681:10: (~ ( '@' | ',' | '(' | ')' | '\\'' | '\"' ) )+
                    {
                    // PerComment.g:681:10: (~ ( '@' | ',' | '(' | ')' | '\\'' | '\"' ) )+
                    int cnt5=0;
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0 >= '\u0000' && LA5_0 <= '!')||(LA5_0 >= '#' && LA5_0 <= '&')||(LA5_0 >= '*' && LA5_0 <= '+')||(LA5_0 >= '-' && LA5_0 <= '?')||(LA5_0 >= 'A' && LA5_0 <= '\uFFFF')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // PerComment.g:
                    	    {
                    	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '&')||(input.LA(1) >= '*' && input.LA(1) <= '+')||(input.LA(1) >= '-' && input.LA(1) <= '?')||(input.LA(1) >= 'A' && input.LA(1) <= '\uFFFF') ) {
                    	        input.consume();
                    	        state.failed=false;
                    	    }
                    	    else {
                    	        if (state.backtracking>0) {state.failed=true; return ;}
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt5 >= 1 ) break loop5;
                    	    if (state.backtracking>0) {state.failed=true; return ;}
                                EarlyExitException eee =
                                    new EarlyExitException(5, input);
                                throw eee;
                        }
                        cnt5++;
                    } while (true);


                    }


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TEXT"

    // $ANTLR start "NAMECHAR"
    public final void mNAMECHAR() throws RecognitionException {
        try {
            // PerComment.g:685:5: ( LETTER | DIGIT | '.' | '-' | '_' | ':' | '$' )
            // PerComment.g:
            {
            if ( input.LA(1)=='$'||(input.LA(1) >= '-' && input.LA(1) <= '.')||(input.LA(1) >= '0' && input.LA(1) <= ':')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NAMECHAR"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // PerComment.g:689:5: ( '0' .. '9' )
            // PerComment.g:
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                input.consume();
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // PerComment.g:693:5: ( 'a' .. 'z' | 'A' .. 'Z' )
            // PerComment.g:
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LETTER"

    public void mTokens() throws RecognitionException {
        // PerComment.g:1:8: ( AT | SQUOTE | DQUOTE | OPEN_PAREN | CLOSE_PAREN | COMMA | WS | ID | TEXT )
        int alt7=9;
        alt7 = dfa7.predict(input);
        switch (alt7) {
            case 1 :
                // PerComment.g:1:10: AT
                {
                mAT(); if (state.failed) return ;


                }
                break;
            case 2 :
                // PerComment.g:1:13: SQUOTE
                {
                mSQUOTE(); if (state.failed) return ;


                }
                break;
            case 3 :
                // PerComment.g:1:20: DQUOTE
                {
                mDQUOTE(); if (state.failed) return ;


                }
                break;
            case 4 :
                // PerComment.g:1:27: OPEN_PAREN
                {
                mOPEN_PAREN(); if (state.failed) return ;


                }
                break;
            case 5 :
                // PerComment.g:1:38: CLOSE_PAREN
                {
                mCLOSE_PAREN(); if (state.failed) return ;


                }
                break;
            case 6 :
                // PerComment.g:1:50: COMMA
                {
                mCOMMA(); if (state.failed) return ;


                }
                break;
            case 7 :
                // PerComment.g:1:56: WS
                {
                mWS(); if (state.failed) return ;


                }
                break;
            case 8 :
                // PerComment.g:1:59: ID
                {
                mID(); if (state.failed) return ;


                }
                break;
            case 9 :
                // PerComment.g:1:62: TEXT
                {
                mTEXT(); if (state.failed) return ;


                }
                break;

        }

    }

    // $ANTLR start synpred1_PerComment
    public final void synpred1_PerComment_fragment() throws RecognitionException {
        // PerComment.g:631:11: ( '@static' )
        // PerComment.g:631:12: '@static'
        {
        match("@static"); if (state.failed) return ;



        }

    }
    // $ANTLR end synpred1_PerComment

    // $ANTLR start synpred2_PerComment
    public final void synpred2_PerComment_fragment() throws RecognitionException {
        // PerComment.g:632:11: ( '@isa' )
        // PerComment.g:632:12: '@isa'
        {
        match("@isa"); if (state.failed) return ;



        }

    }
    // $ANTLR end synpred2_PerComment

    // $ANTLR start synpred3_PerComment
    public final void synpred3_PerComment_fragment() throws RecognitionException {
        // PerComment.g:633:11: ( '@isatype' )
        // PerComment.g:633:12: '@isatype'
        {
        match("@isatype"); if (state.failed) return ;



        }

    }
    // $ANTLR end synpred3_PerComment

    // $ANTLR start synpred4_PerComment
    public final void synpred4_PerComment_fragment() throws RecognitionException {
        // PerComment.g:634:11: ( '@decorator' )
        // PerComment.g:634:12: '@decorator'
        {
        match("@decorator"); if (state.failed) return ;



        }

    }
    // $ANTLR end synpred4_PerComment

    // $ANTLR start synpred5_PerComment
    public final void synpred5_PerComment_fragment() throws RecognitionException {
        // PerComment.g:635:11: ( '@isarefs' )
        // PerComment.g:635:12: '@isarefs'
        {
        match("@isarefs"); if (state.failed) return ;



        }

    }
    // $ANTLR end synpred5_PerComment

    // $ANTLR start synpred6_PerComment
    public final void synpred6_PerComment_fragment() throws RecognitionException {
        // PerComment.g:636:11: ( '@readonly' )
        // PerComment.g:636:12: '@readonly'
        {
        match("@readonly"); if (state.failed) return ;



        }

    }
    // $ANTLR end synpred6_PerComment

    // $ANTLR start synpred7_PerComment
    public final void synpred7_PerComment_fragment() throws RecognitionException {
        // PerComment.g:637:11: ( '@bool' )
        // PerComment.g:637:12: '@bool'
        {
        match("@bool"); if (state.failed) return ;



        }

    }
    // $ANTLR end synpred7_PerComment

    // $ANTLR start synpred8_PerComment
    public final void synpred8_PerComment_fragment() throws RecognitionException {
        // PerComment.g:638:11: ( '@oraseq' )
        // PerComment.g:638:12: '@oraseq'
        {
        match("@oraseq"); if (state.failed) return ;



        }

    }
    // $ANTLR end synpred8_PerComment

    // $ANTLR start synpred9_PerComment
    public final void synpred9_PerComment_fragment() throws RecognitionException {
        // PerComment.g:639:11: ( '@relationship' )
        // PerComment.g:639:12: '@relationship'
        {
        match("@relationship"); if (state.failed) return ;



        }

    }
    // $ANTLR end synpred9_PerComment

    // $ANTLR start synpred10_PerComment
    public final void synpred10_PerComment_fragment() throws RecognitionException {
        // PerComment.g:670:9: ( ID )
        // PerComment.g:670:10: ID
        {
        mID(); if (state.failed) return ;


        }

    }
    // $ANTLR end synpred10_PerComment

    // $ANTLR start synpred11_PerComment
    public final void synpred11_PerComment_fragment() throws RecognitionException {
        // PerComment.g:671:9: ( '\\t' )
        // PerComment.g:671:10: '\\t'
        {
        match('\t'); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred11_PerComment

    // $ANTLR start synpred12_PerComment
    public final void synpred12_PerComment_fragment() throws RecognitionException {
        // PerComment.g:672:9: ( ' ' )
        // PerComment.g:672:10: ' '
        {
        match(' '); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred12_PerComment

    // $ANTLR start synpred13_PerComment
    public final void synpred13_PerComment_fragment() throws RecognitionException {
        // PerComment.g:673:9: ( '\\r' )
        // PerComment.g:673:10: '\\r'
        {
        match('\r'); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred13_PerComment

    // $ANTLR start synpred14_PerComment
    public final void synpred14_PerComment_fragment() throws RecognitionException {
        // PerComment.g:674:9: ( '\\n' )
        // PerComment.g:674:10: '\\n'
        {
        match('\n'); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred14_PerComment

    // $ANTLR start synpred15_PerComment
    public final void synpred15_PerComment_fragment() throws RecognitionException {
        // PerComment.g:675:9: ( '\\u000C' )
        // PerComment.g:675:10: '\\u000C'
        {
        match('\f'); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred15_PerComment

    // $ANTLR start synpred16_PerComment
    public final void synpred16_PerComment_fragment() throws RecognitionException {
        // PerComment.g:676:9: ( '(' )
        // PerComment.g:676:10: '('
        {
        match('('); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred16_PerComment

    // $ANTLR start synpred17_PerComment
    public final void synpred17_PerComment_fragment() throws RecognitionException {
        // PerComment.g:677:9: ( ')' )
        // PerComment.g:677:10: ')'
        {
        match(')'); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred17_PerComment

    // $ANTLR start synpred18_PerComment
    public final void synpred18_PerComment_fragment() throws RecognitionException {
        // PerComment.g:678:9: ( ',' )
        // PerComment.g:678:10: ','
        {
        match(','); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred18_PerComment

    // $ANTLR start synpred19_PerComment
    public final void synpred19_PerComment_fragment() throws RecognitionException {
        // PerComment.g:679:9: ( '\\'' | '\"' )
        // PerComment.g:
        {
        if ( input.LA(1)=='\"'||input.LA(1)=='\'' ) {
            input.consume();
            state.failed=false;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            MismatchedSetException mse = new MismatchedSetException(null,input);
            recover(mse);
            throw mse;
        }


        }

    }
    // $ANTLR end synpred19_PerComment

    // $ANTLR start synpred20_PerComment
    public final void synpred20_PerComment_fragment() throws RecognitionException {
        // PerComment.g:680:9: ( '@' )
        // PerComment.g:680:10: '@'
        {
        match('@'); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred20_PerComment

    public final boolean synpred11_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred18_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred18_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred15_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred20_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred20_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred17_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred17_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred19_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred19_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_PerComment() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_PerComment_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA7_eotS =
        "\1\uffff\1\20\5\uffff\1\26\1\31\16\uffff\2\31\10\uffff\1\20\50\uffff";
    static final String DFA7_eofS =
        "\112\uffff";
    static final String DFA7_minS =
        "\1\0\1\142\5\uffff\2\0\1\uffff\1\164\1\163\2\145\1\157\1\162\7\uffff"+
        "\2\0\1\uffff\2\141\1\143\1\141\1\157\1\141\1\164\1\162\1\157\1\144"+
        "\1\141\1\154\1\163\1\151\1\171\1\145\1\162\1\157\1\164\1\uffff\1"+
        "\145\1\143\1\160\1\146\1\141\1\156\1\151\1\161\1\uffff\1\145\1\163"+
        "\1\164\1\154\1\157\3\uffff\1\157\1\171\1\156\1\162\1\uffff\1\163"+
        "\1\uffff\1\150\1\151\1\160\1\uffff";
    static final String DFA7_maxS =
        "\1\uffff\1\163\5\uffff\2\uffff\1\uffff\1\164\1\163\2\145\1\157\1"+
        "\162\7\uffff\2\uffff\1\uffff\2\141\1\143\1\154\1\157\1\141\2\164"+
        "\1\157\1\144\1\141\1\154\1\163\1\151\1\171\1\145\1\162\1\157\1\164"+
        "\1\uffff\1\145\1\143\1\160\1\146\1\141\1\156\1\151\1\161\1\uffff"+
        "\1\145\1\163\1\164\1\154\1\157\3\uffff\1\157\1\171\1\156\1\162\1"+
        "\uffff\1\163\1\uffff\1\150\1\151\1\160\1\uffff";
    static final String DFA7_acceptS =
        "\2\uffff\1\2\1\3\1\4\1\5\1\6\2\uffff\1\11\6\uffff\1\1\1\2\1\3\1"+
        "\4\1\5\1\6\1\7\2\uffff\1\10\23\uffff\1\1\10\uffff\1\1\5\uffff\3"+
        "\1\4\uffff\1\1\1\uffff\1\1\3\uffff\1\1";
    static final String DFA7_specialS =
        "\1\0\6\uffff\1\3\1\2\16\uffff\1\4\1\1\61\uffff}>";
    static final String[] DFA7_transitionS = {
            "\11\11\2\7\1\11\2\7\22\11\1\7\1\11\1\3\4\11\1\2\1\4\1\5\2\11"+
            "\1\6\3\11\12\10\6\11\1\1\32\10\4\11\1\10\1\11\32\10\uff85\11",
            "\1\16\1\uffff\1\14\4\uffff\1\13\5\uffff\1\17\2\uffff\1\15\1"+
            "\12",
            "",
            "",
            "",
            "",
            "",
            "\11\11\2\7\1\11\2\7\22\11\1\7\1\11\1\uffff\4\11\3\uffff\2\11"+
            "\1\uffff\23\11\1\uffff\uffbf\11",
            "\11\11\2\30\1\11\2\30\22\11\1\30\1\11\1\uffff\1\11\1\27\2\11"+
            "\3\uffff\2\11\1\uffff\2\27\1\11\13\27\5\11\1\uffff\32\27\4\11"+
            "\1\27\1\11\32\27\uff85\11",
            "",
            "\1\32",
            "\1\33",
            "\1\34",
            "\1\35",
            "\1\36",
            "\1\37",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\11\11\2\30\1\11\2\30\22\11\1\30\1\11\1\uffff\1\11\1\27\2\11"+
            "\3\uffff\2\11\1\uffff\2\27\1\11\13\27\5\11\1\uffff\32\27\4\11"+
            "\1\27\1\11\32\27\uff85\11",
            "\11\11\2\30\1\11\2\30\22\11\1\30\1\11\1\uffff\4\11\3\uffff"+
            "\2\11\1\uffff\23\11\1\uffff\uffbf\11",
            "",
            "\1\40",
            "\1\41",
            "\1\42",
            "\1\43\12\uffff\1\44",
            "\1\45",
            "\1\46",
            "\1\47",
            "\1\51\1\uffff\1\50",
            "\1\52",
            "\1\53",
            "\1\54",
            "\1\55",
            "\1\56",
            "\1\57",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\63",
            "\1\64",
            "",
            "\1\65",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\71",
            "\1\72",
            "\1\73",
            "\1\74",
            "",
            "\1\75",
            "\1\76",
            "\1\77",
            "\1\100",
            "\1\101",
            "",
            "",
            "",
            "\1\102",
            "\1\103",
            "\1\104",
            "\1\105",
            "",
            "\1\106",
            "",
            "\1\107",
            "\1\110",
            "\1\111",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( AT | SQUOTE | DQUOTE | OPEN_PAREN | CLOSE_PAREN | COMMA | WS | ID | TEXT );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA7_0 = input.LA(1);

                        s = -1;
                        if ( (LA7_0=='@') ) {s = 1;}

                        else if ( (LA7_0=='\'') ) {s = 2;}

                        else if ( (LA7_0=='\"') ) {s = 3;}

                        else if ( (LA7_0=='(') ) {s = 4;}

                        else if ( (LA7_0==')') ) {s = 5;}

                        else if ( (LA7_0==',') ) {s = 6;}

                        else if ( ((LA7_0 >= '\t' && LA7_0 <= '\n')||(LA7_0 >= '\f' && LA7_0 <= '\r')||LA7_0==' ') ) {s = 7;}

                        else if ( ((LA7_0 >= '0' && LA7_0 <= '9')||(LA7_0 >= 'A' && LA7_0 <= 'Z')||LA7_0=='_'||(LA7_0 >= 'a' && LA7_0 <= 'z')) ) {s = 8;}

                        else if ( ((LA7_0 >= '\u0000' && LA7_0 <= '\b')||LA7_0=='\u000B'||(LA7_0 >= '\u000E' && LA7_0 <= '\u001F')||LA7_0=='!'||(LA7_0 >= '#' && LA7_0 <= '&')||(LA7_0 >= '*' && LA7_0 <= '+')||(LA7_0 >= '-' && LA7_0 <= '/')||(LA7_0 >= ':' && LA7_0 <= '?')||(LA7_0 >= '[' && LA7_0 <= '^')||LA7_0=='`'||(LA7_0 >= '{' && LA7_0 <= '\uFFFF')) ) {s = 9;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA7_24 = input.LA(1);

                        s = -1;
                        if ( ((LA7_24 >= '\t' && LA7_24 <= '\n')||(LA7_24 >= '\f' && LA7_24 <= '\r')||LA7_24==' ') ) {s = 24;}

                        else if ( ((LA7_24 >= '\u0000' && LA7_24 <= '\b')||LA7_24=='\u000B'||(LA7_24 >= '\u000E' && LA7_24 <= '\u001F')||LA7_24=='!'||(LA7_24 >= '#' && LA7_24 <= '&')||(LA7_24 >= '*' && LA7_24 <= '+')||(LA7_24 >= '-' && LA7_24 <= '?')||(LA7_24 >= 'A' && LA7_24 <= '\uFFFF')) ) {s = 9;}

                        else s = 25;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA7_8 = input.LA(1);

                        s = -1;
                        if ( (LA7_8=='$'||(LA7_8 >= '-' && LA7_8 <= '.')||(LA7_8 >= '0' && LA7_8 <= ':')||(LA7_8 >= 'A' && LA7_8 <= 'Z')||LA7_8=='_'||(LA7_8 >= 'a' && LA7_8 <= 'z')) ) {s = 23;}

                        else if ( ((LA7_8 >= '\t' && LA7_8 <= '\n')||(LA7_8 >= '\f' && LA7_8 <= '\r')||LA7_8==' ') ) {s = 24;}

                        else if ( ((LA7_8 >= '\u0000' && LA7_8 <= '\b')||LA7_8=='\u000B'||(LA7_8 >= '\u000E' && LA7_8 <= '\u001F')||LA7_8=='!'||LA7_8=='#'||(LA7_8 >= '%' && LA7_8 <= '&')||(LA7_8 >= '*' && LA7_8 <= '+')||LA7_8=='/'||(LA7_8 >= ';' && LA7_8 <= '?')||(LA7_8 >= '[' && LA7_8 <= '^')||LA7_8=='`'||(LA7_8 >= '{' && LA7_8 <= '\uFFFF')) ) {s = 9;}

                        else s = 25;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA7_7 = input.LA(1);

                        s = -1;
                        if ( ((LA7_7 >= '\t' && LA7_7 <= '\n')||(LA7_7 >= '\f' && LA7_7 <= '\r')||LA7_7==' ') ) {s = 7;}

                        else if ( ((LA7_7 >= '\u0000' && LA7_7 <= '\b')||LA7_7=='\u000B'||(LA7_7 >= '\u000E' && LA7_7 <= '\u001F')||LA7_7=='!'||(LA7_7 >= '#' && LA7_7 <= '&')||(LA7_7 >= '*' && LA7_7 <= '+')||(LA7_7 >= '-' && LA7_7 <= '?')||(LA7_7 >= 'A' && LA7_7 <= '\uFFFF')) ) {s = 9;}

                        else s = 22;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA7_23 = input.LA(1);

                        s = -1;
                        if ( ((LA7_23 >= '\t' && LA7_23 <= '\n')||(LA7_23 >= '\f' && LA7_23 <= '\r')||LA7_23==' ') ) {s = 24;}

                        else if ( (LA7_23=='$'||(LA7_23 >= '-' && LA7_23 <= '.')||(LA7_23 >= '0' && LA7_23 <= ':')||(LA7_23 >= 'A' && LA7_23 <= 'Z')||LA7_23=='_'||(LA7_23 >= 'a' && LA7_23 <= 'z')) ) {s = 23;}

                        else if ( ((LA7_23 >= '\u0000' && LA7_23 <= '\b')||LA7_23=='\u000B'||(LA7_23 >= '\u000E' && LA7_23 <= '\u001F')||LA7_23=='!'||LA7_23=='#'||(LA7_23 >= '%' && LA7_23 <= '&')||(LA7_23 >= '*' && LA7_23 <= '+')||LA7_23=='/'||(LA7_23 >= ';' && LA7_23 <= '?')||(LA7_23 >= '[' && LA7_23 <= '^')||LA7_23=='`'||(LA7_23 >= '{' && LA7_23 <= '\uFFFF')) ) {s = 9;}

                        else s = 25;

                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}

            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 7, _s, input);
            error(nvae);
            throw nvae;
        }

    }
 

}