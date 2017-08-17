// Generated from D:/Code/github/mehmetatas/dev-db/src/main/antlr\Filter.g4 by ANTLR 4.7
package net.mehmetatas.devdb.filter.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FilterLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, AND=2, OR=3, EQ=4, NEQ=5, LT=6, LTE=7, GT=8, GTE=9, IN=10, LPAREN=11, 
		RPAREN=12, LBRACKET=13, RBRACKET=14, COMMA=15, NUMBER=16, STRING=17, BOOL=18, 
		NULL=19, DATE=20, DATETIME=21, IDENTIFIER=22, WS=23;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "DIGIT", "DIGIT2", "DIGIT3", "DIGIT4", "TIME", "ITEM", "AND", 
		"OR", "EQ", "NEQ", "LT", "LTE", "GT", "GTE", "IN", "LPAREN", "RPAREN", 
		"LBRACKET", "RBRACKET", "COMMA", "NUMBER", "STRING", "BOOL", "NULL", "DATE", 
		"DATETIME", "IDENTIFIER", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'.'", "'&'", "'|'", "'='", "'!='", "'<'", "'<='", "'>'", "'>='", 
		"'in'", "'('", "')'", "'['", "']'", "','", null, null, null, "'null'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, "AND", "OR", "EQ", "NEQ", "LT", "LTE", "GT", "GTE", "IN", 
		"LPAREN", "RPAREN", "LBRACKET", "RBRACKET", "COMMA", "NUMBER", "STRING", 
		"BOOL", "NULL", "DATE", "DATETIME", "IDENTIFIER", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public FilterLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Filter.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\31\u00c2\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\3\2\3\3\3"+
		"\3\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\5\7U\n\7\3\b\3\b\3\b\3\b\3\b\3\b\5\b]\n\b\3\t\3\t\3\n\3\n\3\13"+
		"\3\13\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\21"+
		"\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\5\27"+
		"\u0080\n\27\3\27\6\27\u0083\n\27\r\27\16\27\u0084\3\27\3\27\6\27\u0089"+
		"\n\27\r\27\16\27\u008a\5\27\u008d\n\27\3\30\3\30\3\30\3\30\7\30\u0093"+
		"\n\30\f\30\16\30\u0096\13\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\5\31\u00a3\n\31\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33"+
		"\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\35\3\35\7\35\u00b7\n\35\f\35"+
		"\16\35\u00ba\13\35\3\36\6\36\u00bd\n\36\r\36\16\36\u00be\3\36\3\36\2\2"+
		"\37\3\3\5\2\7\2\t\2\13\2\r\2\17\2\21\4\23\5\25\6\27\7\31\b\33\t\35\n\37"+
		"\13!\f#\r%\16\'\17)\20+\21-\22/\23\61\24\63\25\65\26\67\279\30;\31\3\2"+
		"\7\3\2\62;\3\2$$\5\2C\\aac|\6\2\62;C\\aac|\5\2\13\f\16\17\"\"\2\u00ca"+
		"\2\3\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31"+
		"\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2"+
		"\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2"+
		"\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2"+
		"\2\3=\3\2\2\2\5?\3\2\2\2\7A\3\2\2\2\tD\3\2\2\2\13H\3\2\2\2\rM\3\2\2\2"+
		"\17\\\3\2\2\2\21^\3\2\2\2\23`\3\2\2\2\25b\3\2\2\2\27d\3\2\2\2\31g\3\2"+
		"\2\2\33i\3\2\2\2\35l\3\2\2\2\37n\3\2\2\2!q\3\2\2\2#t\3\2\2\2%v\3\2\2\2"+
		"\'x\3\2\2\2)z\3\2\2\2+|\3\2\2\2-\177\3\2\2\2/\u008e\3\2\2\2\61\u00a2\3"+
		"\2\2\2\63\u00a4\3\2\2\2\65\u00a9\3\2\2\2\67\u00b0\3\2\2\29\u00b4\3\2\2"+
		"\2;\u00bc\3\2\2\2=>\7\60\2\2>\4\3\2\2\2?@\t\2\2\2@\6\3\2\2\2AB\5\5\3\2"+
		"BC\5\5\3\2C\b\3\2\2\2DE\5\5\3\2EF\5\5\3\2FG\5\5\3\2G\n\3\2\2\2HI\5\5\3"+
		"\2IJ\5\5\3\2JK\5\5\3\2KL\5\5\3\2L\f\3\2\2\2MN\5\7\4\2NO\7<\2\2OP\5\7\4"+
		"\2PQ\7<\2\2QT\5\7\4\2RS\7\60\2\2SU\5\t\5\2TR\3\2\2\2TU\3\2\2\2U\16\3\2"+
		"\2\2V]\5\63\32\2W]\5/\30\2X]\5-\27\2Y]\5\61\31\2Z]\5\65\33\2[]\5\67\34"+
		"\2\\V\3\2\2\2\\W\3\2\2\2\\X\3\2\2\2\\Y\3\2\2\2\\Z\3\2\2\2\\[\3\2\2\2]"+
		"\20\3\2\2\2^_\7(\2\2_\22\3\2\2\2`a\7~\2\2a\24\3\2\2\2bc\7?\2\2c\26\3\2"+
		"\2\2de\7#\2\2ef\7?\2\2f\30\3\2\2\2gh\7>\2\2h\32\3\2\2\2ij\7>\2\2jk\7?"+
		"\2\2k\34\3\2\2\2lm\7@\2\2m\36\3\2\2\2no\7@\2\2op\7?\2\2p \3\2\2\2qr\7"+
		"k\2\2rs\7p\2\2s\"\3\2\2\2tu\7*\2\2u$\3\2\2\2vw\7+\2\2w&\3\2\2\2xy\7]\2"+
		"\2y(\3\2\2\2z{\7_\2\2{*\3\2\2\2|}\7.\2\2},\3\2\2\2~\u0080\7/\2\2\177~"+
		"\3\2\2\2\177\u0080\3\2\2\2\u0080\u0082\3\2\2\2\u0081\u0083\5\5\3\2\u0082"+
		"\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2"+
		"\2\2\u0085\u008c\3\2\2\2\u0086\u0088\7\60\2\2\u0087\u0089\5\5\3\2\u0088"+
		"\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u0088\3\2\2\2\u008a\u008b\3\2"+
		"\2\2\u008b\u008d\3\2\2\2\u008c\u0086\3\2\2\2\u008c\u008d\3\2\2\2\u008d"+
		".\3\2\2\2\u008e\u0094\7$\2\2\u008f\u0090\7^\2\2\u0090\u0093\7$\2\2\u0091"+
		"\u0093\n\3\2\2\u0092\u008f\3\2\2\2\u0092\u0091\3\2\2\2\u0093\u0096\3\2"+
		"\2\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0097\3\2\2\2\u0096"+
		"\u0094\3\2\2\2\u0097\u0098\7$\2\2\u0098\60\3\2\2\2\u0099\u009a\7v\2\2"+
		"\u009a\u009b\7t\2\2\u009b\u009c\7w\2\2\u009c\u00a3\7g\2\2\u009d\u009e"+
		"\7h\2\2\u009e\u009f\7c\2\2\u009f\u00a0\7n\2\2\u00a0\u00a1\7u\2\2\u00a1"+
		"\u00a3\7g\2\2\u00a2\u0099\3\2\2\2\u00a2\u009d\3\2\2\2\u00a3\62\3\2\2\2"+
		"\u00a4\u00a5\7p\2\2\u00a5\u00a6\7w\2\2\u00a6\u00a7\7n\2\2\u00a7\u00a8"+
		"\7n\2\2\u00a8\64\3\2\2\2\u00a9\u00aa\7B\2\2\u00aa\u00ab\5\13\6\2\u00ab"+
		"\u00ac\7/\2\2\u00ac\u00ad\5\7\4\2\u00ad\u00ae\7/\2\2\u00ae\u00af\5\7\4"+
		"\2\u00af\66\3\2\2\2\u00b0\u00b1\5\65\33\2\u00b1\u00b2\7\"\2\2\u00b2\u00b3"+
		"\5\r\7\2\u00b38\3\2\2\2\u00b4\u00b8\t\4\2\2\u00b5\u00b7\t\5\2\2\u00b6"+
		"\u00b5\3\2\2\2\u00b7\u00ba\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8\u00b9\3\2"+
		"\2\2\u00b9:\3\2\2\2\u00ba\u00b8\3\2\2\2\u00bb\u00bd\t\6\2\2\u00bc\u00bb"+
		"\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf"+
		"\u00c0\3\2\2\2\u00c0\u00c1\b\36\2\2\u00c1<\3\2\2\2\16\2T\\\177\u0084\u008a"+
		"\u008c\u0092\u0094\u00a2\u00b8\u00be\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}