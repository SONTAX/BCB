/*
 *  The scanner definition for COOL.
 */
package PA2J;
import java_cup.runtime.Symbol;


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 65536;
	private final int YY_EOF = 65537;

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
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */
    // empty for now
	}

	private boolean yy_eof_done = false;
	private final int STRING = 3;
	private final int ML_COMMENT = 2;
	private final int STRING_ERROR = 4;
	private final int YYINITIAL = 0;
	private final int COMMENT = 1;
	private final int yy_state_dtrans[] = {
		0,
		60,
		82,
		85,
		82
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NOT_ACCEPT,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NOT_ACCEPT,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NOT_ACCEPT,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"42,41:8,49,43,41,49,46,41:18,49,41,38,41:4,47,44,48,45,54,62,55,50,53,34:10" +
",60,61,56,57,63,41,51,3,35,1,25,9,12,35,17,13,35:2,2,35,15,24,29,35,18,4,19" +
",35,23,31,35:3,41,39,41:2,36,41,7,40,5,28,10,11,37,20,14,37:2,6,37,16,27,30" +
",37,21,8,22,33,26,32,37:3,58,41,59,52,41:65409,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,168,
"0,1,2,3,4,1:3,5,6,7,1:7,8,9,1:5,10,11,12,11,1:5,11:8,10,11:2,10,11:3,1:3,13" +
",1:2,13:5,14,15,16,11,10,17,10:14,18,19,20,21,22,23,24,25,26,27,28,29,30,31" +
",32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56" +
",57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81" +
",82,83,84,85,86,87,88,89,90,91,92,93,11,10,94,95,96,97,98,99,100,101,102,10" +
"3")[0];

	private int yy_nxt[][] = unpackFromString(104,64,
"1,2,117,156:2,3,118,157:2,158,159,62,61,83,84,119,120,156:2,160,157:2,161,1" +
"56,86,156,87,89,157,162,163,164,165,157,4,156,5,157,6,5,157,5:2,7,8,9,10,5," +
"11,10,12,13,14,15,16,17,18,19,20,21,22,23,24,5,-1:65,156,166,121,156:34,-1:" +
"2,156,-1:24,157:5,167,122,157:30,-1:2,157,-1:57,4,-1:74,29,-1:66,30,-1:41,1" +
"0,-1:19,10,-1:2,10,-1:69,31,-1,32,-1:69,33,-1,157:37,-1:2,157,-1:24,156:37," +
"-1:2,156,-1:24,156:16,141,156:20,-1:2,156,-1:24,52:37,-1,52:3,-1,52:4,-1,52" +
":16,1,49:42,50,49,80,50,49:17,-1,156:12,63,156:24,-1:2,156,-1:24,157:6,130," +
"157:6,25,157:23,-1:2,157,-1:24,157:19,148,157:17,-1:2,157,-1:71,51,-1:16,52" +
":10,55,52:4,56,52:4,57,58,52:15,-1,52,59,52,-1,52:4,-1,52:16,1,5:42,7,5:2,-" +
"1,5:17,-1,156:3,129,156:7,26,156:2,27,156:22,-1:2,156,-1:24,157:7,132,157:2" +
",64,157:4,65,157:21,-1:2,157,-1:23,1,52:37,53,81,52:2,54,52:4,5,52:16,-1,15" +
"6:11,28,156:25,-1:2,156,-1:24,157:25,87,157:11,-1:2,157,-1:5,10,-1:2,10,-1:" +
"15,156:18,34,156:18,-1:2,156,-1:24,157:10,66,157:26,-1:2,157,-1:24,156:30,3" +
"5,156:6,-1:2,156,-1:24,157:21,67,157:15,-1:2,157,-1:24,156:18,36,156:18,-1:" +
"2,156,-1:24,157:31,68,157:5,-1:2,157,-1:24,156:8,37,156:28,-1:2,156,-1:24,1" +
"57:21,69,157:15,-1:2,157,-1:24,156:28,38,156:8,-1:2,156,-1:24,157:9,70,157:" +
"27,-1:2,157,-1:24,156:8,39,156:28,-1:2,156,-1:24,157:29,71,157:7,-1:2,157,-" +
"1:24,40,156:36,-1:2,156,-1:24,157:9,72,157:27,-1:2,157,-1:24,156:14,41,156:" +
"22,-1:2,156,-1:24,157:4,73,157:32,-1:2,157,-1:24,156,43,156:35,-1:2,156,-1:" +
"24,157:15,74,157:21,-1:2,157,-1:24,156:3,44,156:33,-1:2,156,-1:24,157:9,42," +
"157:27,-1:2,157,-1:24,156:8,46,156:28,-1:2,156,-1:24,157:5,75,157:31,-1:2,1" +
"57,-1:24,156:24,47,156:12,-1:2,156,-1:24,157:7,76,157:29,-1:2,157,-1:24,156" +
":3,48,156:33,-1:2,156,-1:24,157:9,45,157:27,-1:2,157,-1:24,157:9,77,157:27," +
"-1:2,157,-1:24,157:27,78,157:9,-1:2,157,-1:24,157:7,79,157:29,-1:2,157,-1:2" +
"4,156:8,88,156:14,123,156:13,-1:2,156,-1:24,157:9,91,157:16,124,157:10,-1:2" +
",157,-1:24,156:8,90,156:14,92,156:13,-1:2,156,-1:24,157:9,93,157:16,95,157:" +
"10,-1:2,157,-1:24,156:3,94,156:33,-1:2,156,-1:24,157:7,97,157:29,-1:2,157,-" +
"1:24,156:23,96,156:13,-1:2,156,-1:24,157:26,99,157:10,-1:2,157,-1:24,156:3," +
"98,156:33,-1:2,156,-1:24,157:7,101,157:29,-1:2,157,-1:24,156:2,100,156:34,-" +
"1:2,156,-1:24,157:6,103,157:30,-1:2,157,-1:24,156:22,139,156:14,-1:2,156,-1" +
":24,157:5,144,157:31,-1:2,157,-1:24,156:8,102,156:28,-1:2,156,-1:24,157:25," +
"146,157:11,-1:2,157,-1:24,156:23,104,156:13,-1:2,156,-1:24,157:9,105,157:27" +
",-1:2,157,-1:24,156:12,143,156:24,-1:2,156,-1:24,157:32,107,157:4,-1:2,157," +
"-1:24,156:3,106,156:33,-1:2,156,-1:24,157:26,109,157:10,-1:2,157,-1:24,156:" +
"23,145,156:13,-1:2,156,-1:24,157:13,150,157:23,-1:2,157,-1:24,156:8,147,156" +
":28,-1:2,156,-1:24,157:7,111,157:29,-1:2,157,-1:24,156,108,156:35,-1:2,156," +
"-1:24,157:7,113,157:29,-1:2,157,-1:24,156:12,110,156:24,-1:2,156,-1:24,157:" +
"26,152,157:10,-1:2,157,-1:24,156:17,149,156:19,-1:2,156,-1:24,157:9,153,157" +
":27,-1:2,157,-1:24,156:12,151,156:24,-1:2,156,-1:24,157:5,114,157:31,-1:2,1" +
"57,-1:24,156:18,112,156:18,-1:2,156,-1:24,157:13,115,157:23,-1:2,157,-1:24," +
"157:20,154,157:16,-1:2,157,-1:24,157:13,155,157:23,-1:2,157,-1:24,157:21,11" +
"6,157:15,-1:2,157,-1:24,156,125,156,127,156:33,-1:2,156,-1:24,157:5,126,157" +
",128,157:29,-1:2,157,-1:24,156:16,131,156:20,-1:2,156,-1:24,157:19,134,136," +
"157:16,-1:2,157,-1:24,156:23,133,156:13,-1:2,156,-1:24,157:26,138,157:10,-1" +
":2,157,-1:24,156:16,135,156:20,-1:2,156,-1:24,157:19,140,157:17,-1:2,157,-1" +
":24,156:2,137,156:34,-1:2,156,-1:24,157:6,142,157:30,-1:2,157,-1:23");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

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
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -3:
						break;
					case 3:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -4:
						break;
					case 4:
						{
        return new Symbol(TokenConstants.INT_CONST, AbstractTable.inttable.addString(yytext()));
}
					case -5:
						break;
					case 5:
						{
        return new Symbol(TokenConstants.ERROR, yytext());
}
					case -6:
						break;
					case 6:
						{
        string_buf.setLength(0);
        yybegin(STRING);
}
					case -7:
						break;
					case 7:
						{
        curr_lineno++;
}
					case -8:
						break;
					case 8:
						{
        return new Symbol(TokenConstants.LPAREN);
}
					case -9:
						break;
					case 9:
						{
        return new Symbol(TokenConstants.MULT);
}
					case -10:
						break;
					case 10:
						{
}
					case -11:
						break;
					case 11:
						{
        return new Symbol(TokenConstants.RPAREN);
}
					case -12:
						break;
					case 12:
						{
        return new Symbol(TokenConstants.DOT);
}
					case -13:
						break;
					case 13:
						{
        return new Symbol(TokenConstants.AT);
}
					case -14:
						break;
					case 14:
						{
        return new Symbol(TokenConstants.NEG);
}
					case -15:
						break;
					case 15:
						{
        return new Symbol(TokenConstants.DIV);
}
					case -16:
						break;
					case 16:
						{
        return new Symbol(TokenConstants.PLUS);
}
					case -17:
						break;
					case 17:
						{
        return new Symbol(TokenConstants.MINUS);
}
					case -18:
						break;
					case 18:
						{
        return new Symbol(TokenConstants.LT);
}
					case -19:
						break;
					case 19:
						{
        return new Symbol(TokenConstants.EQ);
}
					case -20:
						break;
					case 20:
						{
        return new Symbol(TokenConstants.LBRACE);
}
					case -21:
						break;
					case 21:
						{
        return new Symbol(TokenConstants.RBRACE);
}
					case -22:
						break;
					case 22:
						{
        return new Symbol(TokenConstants.COLON);
}
					case -23:
						break;
					case 23:
						{
        return new Symbol(TokenConstants.SEMI);
}
					case -24:
						break;
					case 24:
						{
        return new Symbol(TokenConstants.COMMA);
}
					case -25:
						break;
					case 25:
						{
        return new Symbol(TokenConstants.FI);
}
					case -26:
						break;
					case 26:
						{
        return new Symbol(TokenConstants.IF);
}
					case -27:
						break;
					case 27:
						{
        return new Symbol(TokenConstants.IN);
}
					case -28:
						break;
					case 28:
						{
        return new Symbol(TokenConstants.OF);
}
					case -29:
						break;
					case 29:
						{
        yybegin(COMMENT);
}
					case -30:
						break;
					case 30:
						{
	return new Symbol(TokenConstants.ERROR, "Unmatched *)");
}
					case -31:
						break;
					case 31:
						{
        return new Symbol(TokenConstants.ASSIGN);
}
					case -32:
						break;
					case 32:
						{
        return new Symbol(TokenConstants.LE);
}
					case -33:
						break;
					case 33:
						{
        return new Symbol(TokenConstants.DARROW);
}
					case -34:
						break;
					case 34:
						{
        return new Symbol(TokenConstants.LET);
}
					case -35:
						break;
					case 35:
						{
        return new Symbol(TokenConstants.NEW);
}
					case -36:
						break;
					case 36:
						{
        return new Symbol(TokenConstants.NOT);
}
					case -37:
						break;
					case 37:
						{
        return new Symbol(TokenConstants.CASE);
}
					case -38:
						break;
					case 38:
						{
        return new Symbol(TokenConstants.LOOP);
}
					case -39:
						break;
					case 39:
						{
        return new Symbol(TokenConstants.ELSE);
}
					case -40:
						break;
					case 40:
						{
        return new Symbol(TokenConstants.ESAC);
}
					case -41:
						break;
					case 41:
						{
        return new Symbol(TokenConstants.THEN);
}
					case -42:
						break;
					case 42:
						{
        return new Symbol(TokenConstants.BOOL_CONST, true);
}
					case -43:
						break;
					case 43:
						{
        return new Symbol(TokenConstants.POOL);
}
					case -44:
						break;
					case 44:
						{
        return new Symbol(TokenConstants.CLASS);
}
					case -45:
						break;
					case 45:
						{
        return new Symbol(TokenConstants.BOOL_CONST, false);
}
					case -46:
						break;
					case 46:
						{
        return new Symbol(TokenConstants.WHILE);
}
					case -47:
						break;
					case 47:
						{
        return new Symbol(TokenConstants.ISVOID);
}
					case -48:
						break;
					case 48:
						{
        return new Symbol(TokenConstants.INHERITS);
}
					case -49:
						break;
					case 49:
						{
}
					case -50:
						break;
					case 50:
						{
        curr_lineno++;
}
					case -51:
						break;
					case 51:
						{
        yybegin(YYINITIAL);
}
					case -52:
						break;
					case 52:
						{
        string_buf.append(yytext());
}
					case -53:
						break;
					case 53:
						{
        yybegin(YYINITIAL);
        String string = string_buf.toString();
        if (string.length() < MAX_STR_CONST) {
            return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(string));
        } else {
            return new Symbol(TokenConstants.ERROR, "String constant too long");
        }
}
					case -54:
						break;
					case 54:
						{
        yybegin(STRING_ERROR);
        return new Symbol(TokenConstants.ERROR, "String contains null character");
}
					case -55:
						break;
					case 55:
						{
        string_buf.append("\f");
}
					case -56:
						break;
					case 56:
						{
        string_buf.append("\n");
}
					case -57:
						break;
					case 57:
						{
        string_buf.append("\r");
}
					case -58:
						break;
					case 58:
						{
        string_buf.append("\t");
}
					case -59:
						break;
					case 59:
						{
        string_buf.append("\b");
}
					case -60:
						break;
					case 61:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -61:
						break;
					case 62:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -62:
						break;
					case 63:
						{
        return new Symbol(TokenConstants.FI);
}
					case -63:
						break;
					case 64:
						{
        return new Symbol(TokenConstants.IF);
}
					case -64:
						break;
					case 65:
						{
        return new Symbol(TokenConstants.IN);
}
					case -65:
						break;
					case 66:
						{
        return new Symbol(TokenConstants.OF);
}
					case -66:
						break;
					case 67:
						{
        return new Symbol(TokenConstants.LET);
}
					case -67:
						break;
					case 68:
						{
        return new Symbol(TokenConstants.NEW);
}
					case -68:
						break;
					case 69:
						{
        return new Symbol(TokenConstants.NOT);
}
					case -69:
						break;
					case 70:
						{
        return new Symbol(TokenConstants.CASE);
}
					case -70:
						break;
					case 71:
						{
        return new Symbol(TokenConstants.LOOP);
}
					case -71:
						break;
					case 72:
						{
        return new Symbol(TokenConstants.ELSE);
}
					case -72:
						break;
					case 73:
						{
        return new Symbol(TokenConstants.ESAC);
}
					case -73:
						break;
					case 74:
						{
        return new Symbol(TokenConstants.THEN);
}
					case -74:
						break;
					case 75:
						{
        return new Symbol(TokenConstants.POOL);
}
					case -75:
						break;
					case 76:
						{
        return new Symbol(TokenConstants.CLASS);
}
					case -76:
						break;
					case 77:
						{
        return new Symbol(TokenConstants.WHILE);
}
					case -77:
						break;
					case 78:
						{
        return new Symbol(TokenConstants.ISVOID);
}
					case -78:
						break;
					case 79:
						{
        return new Symbol(TokenConstants.INHERITS);
}
					case -79:
						break;
					case 80:
						{
}
					case -80:
						break;
					case 81:
						{
        string_buf.append(yytext());
}
					case -81:
						break;
					case 83:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -82:
						break;
					case 84:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -83:
						break;
					case 86:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -84:
						break;
					case 87:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -85:
						break;
					case 88:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -86:
						break;
					case 89:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -87:
						break;
					case 90:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -88:
						break;
					case 91:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -89:
						break;
					case 92:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -90:
						break;
					case 93:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -91:
						break;
					case 94:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -92:
						break;
					case 95:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -93:
						break;
					case 96:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -94:
						break;
					case 97:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -95:
						break;
					case 98:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -96:
						break;
					case 99:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -97:
						break;
					case 100:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -98:
						break;
					case 101:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -99:
						break;
					case 102:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -100:
						break;
					case 103:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -101:
						break;
					case 104:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -102:
						break;
					case 105:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -103:
						break;
					case 106:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -104:
						break;
					case 107:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -105:
						break;
					case 108:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -106:
						break;
					case 109:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -107:
						break;
					case 110:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -108:
						break;
					case 111:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -109:
						break;
					case 112:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -110:
						break;
					case 113:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -111:
						break;
					case 114:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -112:
						break;
					case 115:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -113:
						break;
					case 116:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -114:
						break;
					case 117:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -115:
						break;
					case 118:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -116:
						break;
					case 119:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -117:
						break;
					case 120:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -118:
						break;
					case 121:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -119:
						break;
					case 122:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -120:
						break;
					case 123:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -121:
						break;
					case 124:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -122:
						break;
					case 125:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -123:
						break;
					case 126:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -124:
						break;
					case 127:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -125:
						break;
					case 128:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -126:
						break;
					case 129:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -127:
						break;
					case 130:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -128:
						break;
					case 131:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -129:
						break;
					case 132:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -130:
						break;
					case 133:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -131:
						break;
					case 134:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -132:
						break;
					case 135:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -133:
						break;
					case 136:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -134:
						break;
					case 137:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -135:
						break;
					case 138:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -136:
						break;
					case 139:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -137:
						break;
					case 140:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -138:
						break;
					case 141:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -139:
						break;
					case 142:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -140:
						break;
					case 143:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -141:
						break;
					case 144:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -142:
						break;
					case 145:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -143:
						break;
					case 146:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -144:
						break;
					case 147:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -145:
						break;
					case 148:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -146:
						break;
					case 149:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -147:
						break;
					case 150:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -148:
						break;
					case 151:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -149:
						break;
					case 152:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -150:
						break;
					case 153:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -151:
						break;
					case 154:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -152:
						break;
					case 155:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -153:
						break;
					case 156:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -154:
						break;
					case 157:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -155:
						break;
					case 158:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -156:
						break;
					case 159:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -157:
						break;
					case 160:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -158:
						break;
					case 161:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -159:
						break;
					case 162:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -160:
						break;
					case 163:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -161:
						break;
					case 164:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -162:
						break;
					case 165:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -163:
						break;
					case 166:
						{
        return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
}
					case -164:
						break;
					case 167:
						{
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
}
					case -165:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
