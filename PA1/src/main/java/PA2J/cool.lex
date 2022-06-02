/*
 *  The scanner definition for COOL.
 */
package PA2J;

import java_cup.runtime.Symbol;

%%

%{

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */

    // Max size of string constants
    static int MAX_STR_CONST = 1025;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    private int curr_lineno = 1;
    int get_curr_lineno() {
	    return curr_lineno;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
	    filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
	    return filename;
    }
%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    // empty for now
%init}

%eofval{

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */

    switch(yy_lexical_state) {
    case YYINITIAL:
	    break;
	case COMMENT:
	    yybegin(YYINITIAL);
	    return new Symbol(TokenConstants.ERROR, "EOF in comment");
	case ML_COMMENT:
	    yybegin(YYINITIAL);
	    return new Symbol(TokenConstants.ERROR, "EOF in comment");
	case STRING:
	    yybegin(YYINITIAL);
	    return new Symbol(TokenConstants.ERROR, "EOF in string constant");
    }
    return new Symbol(TokenConstants.EOF);
%eofval}

%class CoolLexer
%cup
%unicode

INTEGERS = [0-9]+
TYPE_IDENTIFIERS = [A-Z][A-Za-z0-9_]*
OBJECT_IDENTIFIERS = [a-z][A-Za-z0-9_]*
CHAR = [^\"\0\\]+
WHITESPACE = [ \f\r\t\v]+

%state COMMENT
%state ML_COMMENT
%state STRING
%state STRING_ERROR

%%

<YYINITIAL>{INTEGERS} {
        return new Symbol(TokenConstants.INT_CONST, AbstractTable.inttable.addString(yytext()));
}
<YYINITIAL>{TYPE_IDENTIFIERS} {
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
<YYINITIAL>{OBJECT_IDENTIFIERS} {
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}


<YYINITIAL>"\"" {
        string_buf.setLength(0);
        yybegin(STRING);
}
<STRING>"\"" {
        yybegin(YYINITIAL);
        String string = string_buf.toString();
        if (string.length() < MAX_STR_CONST) {
            return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(string));
        } else {
            return new Symbol(TokenConstants.ERROR, "String constant too long");
        }
}
<STRING>"\b" {
        string_buf.append("\b");
}
<STRING>"\t" {
        string_buf.append("\t");
}
<STRING>"\n" {
        string_buf.append("\n");
}
<STRING>"\f" {
        string_buf.append("\f");
}
<STRING>"\r" {
        string_buf.append("\r");
}
<STRING>{CHAR} {
        string_buf.append(yytext());
}
<STRING>\0 {
        yybegin(STRING_ERROR);
        return new Symbol(TokenConstants.ERROR, "String contains null character");
}
<STRING>\n {
        yybegin(STRING_ERROR);
        return new Symbol(TokenConstants.ERROR, "Unterminated string");
}


<YYINITIAL>"(*" {
        yybegin(COMMENT);
}
<COMMENT>\n|\r {
        curr_lineno++;
}
<COMMENT>. {
}
<COMMENT>"*)" {
        yybegin(YYINITIAL);
}
<YYINITIAL>"*)" {
	return new Symbol(TokenConstants.ERROR, "Unmatched *)");
}


<YYINITIAL>"CLASS" {
        return new Symbol(TokenConstants.CLASS);
}
<YYINITIAL>"ELSE" {
        return new Symbol(TokenConstants.ELSE);
}
<YYINITIAL>"false" {
        return new Symbol(TokenConstants.BOOL_CONST, false);
}
<YYINITIAL>"FI" {
        return new Symbol(TokenConstants.FI);
}
<YYINITIAL>"IF" {
        return new Symbol(TokenConstants.IF);
}
<YYINITIAL>"IN" {
        return new Symbol(TokenConstants.IN);
}
<YYINITIAL>"INHERITS" {
        return new Symbol(TokenConstants.INHERITS);
}
<YYINITIAL>"ISVOID" {
        return new Symbol(TokenConstants.ISVOID);
}
<YYINITIAL>"LET" {
        return new Symbol(TokenConstants.LET);
}
<YYINITIAL>"LOOP" {
        return new Symbol(TokenConstants.LOOP);
}
<YYINITIAL>"POOL" {
        return new Symbol(TokenConstants.POOL);
}
<YYINITIAL>"THEN" {
        return new Symbol(TokenConstants.THEN);
}
<YYINITIAL>"WHILE" {
        return new Symbol(TokenConstants.WHILE);
}
<YYINITIAL>"CASE" {
        return new Symbol(TokenConstants.CASE);
}
<YYINITIAL>"ESAC" {
        return new Symbol(TokenConstants.ESAC);
}
<YYINITIAL>"NEW" {
        return new Symbol(TokenConstants.NEW);
}
<YYINITIAL>"OF" {
        return new Symbol(TokenConstants.OF);
}
<YYINITIAL>"NOT" {
        return new Symbol(TokenConstants.NOT);
}
<YYINITIAL>"true" {
        return new Symbol(TokenConstants.BOOL_CONST, true);
}


<YYINITIAL>{WHITESPACE} {
}


<YYINITIAL>"." {
        return new Symbol(TokenConstants.DOT);
}
<YYINITIAL>"@" {
        return new Symbol(TokenConstants.AT);
}
<YYINITIAL>"~" {
        return new Symbol(TokenConstants.NEG);
}
<YYINITIAL>"*" {
        return new Symbol(TokenConstants.MULT);
}
<YYINITIAL>"/" {
        return new Symbol(TokenConstants.DIV);
}
<YYINITIAL>"+" {
        return new Symbol(TokenConstants.PLUS);
}
<YYINITIAL>"-" {
        return new Symbol(TokenConstants.MINUS);
}
<YYINITIAL>"<=" {
        return new Symbol(TokenConstants.LE);
}
<YYINITIAL>"<" {
        return new Symbol(TokenConstants.LT);
}
<YYINITIAL>"=" {
        return new Symbol(TokenConstants.EQ);
}
<YYINITIAL>"<-" {
        return new Symbol(TokenConstants.ASSIGN);
}

<YYINITIAL>"(" {
        return new Symbol(TokenConstants.LPAREN);
}
<YYINITIAL>")" {
        return new Symbol(TokenConstants.RPAREN);
}
<YYINITIAL>"{" {
        return new Symbol(TokenConstants.LBRACE);
}
<YYINITIAL>"}" {
        return new Symbol(TokenConstants.RBRACE);
}
<YYINITIAL>":" {
        return new Symbol(TokenConstants.COLON);
}
<YYINITIAL>";" {
        return new Symbol(TokenConstants.SEMI);
}
<YYINITIAL>"," {
        return new Symbol(TokenConstants.COMMA);
}
<YYINITIAL>"=>" {
        return new Symbol(TokenConstants.DARROW);
}


\n {
        curr_lineno++;
}

. {
        return new Symbol(TokenConstants.ERROR, yytext());
}
