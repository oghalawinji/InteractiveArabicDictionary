package DictionaryBeans.Util;

// Created by Lawrence PC Dol.  Released into the public domain.
//
// Source is licensed for any use, provided this copyright notice is retained.
// No warranty for any purpose whatsoever is implied or expressed.  The author
// is not liable for any losses of any kind, direct or indirect, which result
// from the use of this software.

import java.io.*;
import java.math.*;
import java.util.*;

/**
 * A pull-event parser for JSON data.
 * <p>
 * Note that the event and member values are only valid while the parser is not searching for
 * the next event.
 * <p>
 * Threading Design : [x] Single Threaded  [ ] Threadsafe  [ ] Immutable  [ ] Isolated
 *
 * @author          Lawrence Dol
 * @since           Build 2007.0726.0032
 */

public class JsonParser
extends Object
{

// *****************************************************************************
// INSTANCE PROPERTIES
// *****************************************************************************

// PARSER CONSTANTS
private final String                    inputName;                              // the name of the input source (for location reporting)
private Reader                          reader;                                 // input reader
private final boolean                   close;                                  // close input reader when it reaches end of stream
private final LinkedList                objectStack;                            // stack of object data for nested objects
private final StringBuffer              accumulator;                            // text accumulator

// PARSER OPTIONS
private final boolean                   optUnquotedKeywords;
private final boolean                   optEolIsComma;
private final boolean                   optMultilineComments;
private final boolean                   optMultilineStrings;

// PARSER VARIABLES
private ObjectData                      objectData;                             // current object data
private int                             pushBack;                               // push-back character (will be returned by next read)
private int                             inpLine;                                // input line number
private int                             inpColumn;                              // input column

// CURRENT EVENT VARIABLES
private int                             evtCode;                                // current event code
private int                             evtLine;                                // current event line number
private int                             evtColumn;                              // current event column number
private String                          mbrName;                                // current event member name
private String                          mbrValue;                               // current event member value
private boolean                         mbrArray;                               // current event member array flag

// *****************************************************************************
// INSTANCE CREATE/DELETE
// *****************************************************************************

private JsonParser(String inpnam, boolean cls, int opt) {
    super();

    inputName=inpnam;
    // reader initialized by public constructors
    close=cls;
    objectStack=new LinkedList();
    accumulator=new StringBuffer();

    optUnquotedKeywords =((opt&OPT_UNQUOTED_KEYWORDS )!=0);
    optEolIsComma       =((opt&OPT_EOL_IS_COMMA      )!=0);
    optMultilineComments=((opt&OPT_MULTILINE_COMMENTS)!=0);
    optMultilineStrings =((opt&OPT_MULTILINE_STRINGS )!=0);

    pushBack=-1;
    objectData=new ObjectData("");
    inpLine=1;
    inpColumn=0;

    evtCode=0;
    evtLine=0;
    evtColumn=0;
    mbrName="";
    mbrValue="";
    }

/**
 * Construct a JSON parser from a character input source.
 * @param inpnam    A text description of the source, used only for location text.
 * @param inpsrc    Input source.
 * @param cls       Whether to close the input source at end-of-input.
 * @param opt       Parsing option flags, combined from OPT_xxx values.
 */
public JsonParser(String inpnam, Reader inpsrc, boolean cls, int opt) {
    this(inpnam,cls,opt);

    reader=inpsrc;
    }

/**
 * Construct a JSON parser from a byte input source.
 * @param inpnam    A text description of the source, used only for location text.
 * @param inpsrc    Input source.
 * @param inpenc    Character encoding used by the input source.
 * @param cls       Whether to close the input source at end-of-input.
 * @param opt       Parsing option flags, combined from OPT_xxx values.
 * @throws Escape.ENCODING If the encoding string is not supported by the JVM.
 */
public JsonParser(String inpnam, InputStream inpsrc, String inpenc, boolean cls, int opt) {
    this(inpnam,cls,opt);

    try { reader=new InputStreamReader(inpsrc,inpenc); }
    catch(UnsupportedEncodingException thr) { throw new Escape(Escape.BAD_ENCODING,"The encoding '"+inpenc+"' is not supported by this Java Runtime Engine"); }
    }

/**
 * Construct a JSON parser from a file input source.
 * @param inpfil    Input source.
 * @param enc       Character encoding used by the input source.
 * @param opt       Parsing option flags, combined from OPT_xxx values.
 * @throws Escape.ENCODING If the encoding string is not supported by the JVM.
 * @throws Escape.IOERROR If the file could not be found or accessed.
 */
public JsonParser(String inpfil, String enc, int opt) {
    this(inpfil,true,opt);

    try { reader=new InputStreamReader(new BufferedInputStream(new FileInputStream(inpfil),1024),enc); }
    catch(UnsupportedEncodingException thr) { throw new Escape(Escape.BAD_ENCODING,"The encoding '"+enc+"' is not supported by this Java Runtime Engine",thr); }
    catch(IOException                  thr) { throw new Escape(Escape.IOERROR     ,("Could not access file \""+inpfil+"\": "+thr)                       ,thr); }
    }

// *****************************************************************************
// INSTANCE METHODS - ACCESSORS
// *****************************************************************************

/** Get the name assigned to the input source at construction.  This is informational only. */
public String getInputName() {
    return inputName;
    }

/** Get the current location in the input source. */
public String getInputLocation() {
    return location(inpLine,inpColumn);
    }

/** Get the name of the current object. */
public String getObjectName() {
    return objectData.name;
    }

/** Get the code for the current event. */
public int getEventCode() {
    return evtCode;
    }

/** Get the name for the current event. */
public String getEventName() {
    return getEventName(evtCode);
    }

/** Get the input source line number for the current event. */
public int getEventLine() {
    return evtLine;
    }

/** Get the input source column number for the current event. */
public int getEventColumn() {
    return evtColumn;
    }

/** Get the input source location for the current event. */
public String getEventLocation() {
    return location(evtLine,evtColumn);
    }

/** Get the current object member name. */
public String getMemberName() {
    return mbrName;
    }

/**
 * Get the current object member value (valid only if the current event is EVT_OBJECT_MEMBER).
 * <p>
 * <b><u>NOTE</u></b>
 * <p>
 * All values are returned with EMCA-262 unescaping already applied.  However if the value was specified
 * in quotes it is return enclosed in quotes in order to reflect the explicit indication of a text value.
 * This means that the string may contain ambiguous quotes when observed at face value, which situation is
 * easily resolve using the static methods <code>isQuoted()</code> and <code>stripQuotes</code>.
 * Alternatively, the methods <code>getTypedMemberValue()</code> or <code>createTypeValue()</code> may be
 * used to get a Java typed object, which may be identified using <code>instanceof</code>.
 */
public String getMemberValue() {
    return mbrValue;
    }

/** Get the current object member array flag. This indicates that the current member is an array. */
public boolean getMemberArray() {
    return mbrArray;
    }

/**
 * Get the current object member value  applying JSON typing rules (valid only if the current event is EVT_OBJECT_MEMBER).
 * All text values must be quoted, otherwise they will be treated as a special value or number.
 * <p>
 * Actual return types are as follows:
 * <ul>
 * <li>"..." - String value after quotes are stripped.
 * <li>true  - Boolean.TRUE (not case sensitive, unlike strict JSON specification requirement)
 * <li>false - Boolean.FALSE (not case sensitive, unlike strict JSON specification requirement)
 * <li>null  - null (not case sensitive, unlike strict JSON specification requirement)
 * <li>Anything else - BigDecimal (exception thrown from the BigDecimal constructor if not a valid number)
 * </ul>
 * @throws NumberFormatException If an unquoted value which is no null, true or false is not a valid decimal number.
 */
static public Object getTypedMemberValue(String val) {
    return createTypedValue(val);
    }

// *****************************************************************************
// INSTANCE METHODS
// *****************************************************************************

/**
 * Parse next event from input source.
 * @throws Escape.IOERROR If an Input/Output exception occurs.
 */
public int next() {
    if(evtCode==EVT_INPUT_ENDED) {
        return EVT_INPUT_ENDED;
        }

    try {
        int                             pet=evtCode;                            // previous event code
        boolean                         qut=false;                              // flags when within quotes
        boolean                         amd=true;                               // flags whether we can accumulate more data (set false after closing quote, true after appropriate divider (: or ,))
        boolean                         pws=false;                              // flags that the previous input character was whitespace
        int                             ich;                                    // input character as an integer

        if     (evtCode==EVT_OBJECT_BEGIN ) { mbrName="";          }
        else if(evtCode==EVT_OBJECT_ENDED ) { restoreObjectData(); }
        else if(evtCode==EVT_ARRAY_BEGIN  ) { mbrName="";          }
        else if(evtCode==EVT_ARRAY_ENDED  ) { mbrName="";          }
        else if(evtCode==EVT_OBJECT_MEMBER) { mbrName="";          }
        evtCode=0;
        evtLine=0;
        evtColumn=0;
        mbrValue=null;

        while((ich=readChar())!=-1) {
            // TEST FOR COMMENTS
            if(!qut) {
                if(ich=='*' || ich=='#') {
                    while((ich=readChar())!=-1 && ich!='\n') {;}
                    }
                else if(ich=='/') {
                    int tmp=readChar();
                    if(tmp=='/') {
                        while((ich=readChar())!=-1 && ich!='\n') {;}
                        }
                    else if(tmp=='*') {
                        if(!optMultilineComments) {
                            throw parserError(Escape.MALFORMED,"Multiline comment not permitted by parser",null,evtLine,evtColumn);
                            }
                        while((tmp=readChar())!=-1) {
                            if(tmp=='*') {
                                if((tmp=readChar())!='/') { unreadChar(tmp); }
                                else                      { break;           }
                                }
                            }
                        if(tmp!='/') { throw parserError(Escape.MALFORMED,"Multiline comment not closed before EOF",null,evtLine,evtColumn); }
                        ich=' ';
                        }
                    else {                                                      // one slash, but not two
                        unreadChar(tmp);
                        ich='/';
                        }
                    }
                if(ich==-1) { break; }
                }

            if(ich=='\\') {
                int lin=inpLine,col=inpColumn;
                if((ich=readChar())==-1) { throw parserError(Escape.BAD_ESCAPE,"The input stream ended with an incomplete escape sequence",null,lin,col); }
                switch(ich) {
                    case '"'  : { storeChar('\"'    ); } break;                 // double quote
                    case '\\' : { storeChar('\\'    ); } break;                 // backslash
                    case '/'  : { storeChar('\u2044'); } break;                 // solidus (I was suprised too!!)
                    case 'b'  : { storeChar('\b'    ); } break;                 // backspace
                    case 'f'  : { storeChar('\f'    ); } break;                 // form feed (aka vertical tab)
                    case 'n'  : { storeChar('\n'    ); } break;                 // line feed
                    case 'r'  : { storeChar('\r'    ); } break;                 // carriage return
                    case 't'  : { storeChar('\t'    ); } break;                 // horizontal tab
                    case 'u'  : {                                               // unicode 0x0000 - 0xFFFF
                        int ic1=readChar();
                        int ic2=readChar();
                        int ic3=readChar();
                        int ic4=readChar();
                        if(ic4==-1) { throw parserError(Escape.BAD_ESCAPE,"The input stream ended with an incomplete escape sequence",null,lin,col); }
                        storeChar((char)decodeHexChar((char)ic1,(char)ic2,(char)ic3,(char)ic4,lin,col));
                        } break;
                    case 'x'  : {                                               // ascii 0x00-0xFF
                        int ic1=readChar();
                        int ic2=readChar();
                        if(ic2==-1) { throw parserError(Escape.BAD_ESCAPE,"The input stream ended with an incomplete escape sequence",null,lin,col); }
                        storeChar((char)decodeHexByte((char)ic1,(char)ic2,lin,col));
                        } break;
                    default   : {
                        throw parserError(Escape.BAD_ESCAPE,("The text string contains the invalid escape sequence '\\"+(char)ich),null,lin,col);
                        } // THROWS
                    }
                }
            else if(qut) {
                if(ich<0x0020 && !(optMultilineStrings && (ich=='\r' || ich=='\n'))) {
                    throw parserError(Escape.MALFORMED,"A quoted literal may not contain any control characters ("+(optMultilineStrings ? "except" : "including")+" CR and LF) - controls must be escaped using \\uHHHH");
                    }
                if(ich=='"') { qut=false; amd=false; }
                storeChar((char)ich);
                }
            else if(ich=='"') {
                if(accumulator.length()!=0) {
                    throw parserError(Escape.MALFORMED,"Text was found preceding an unescaped opening quote: \""+accumulator+"\" (this is usually caused by a missing comma or colon, or by using equals instead of a colon); Text=\""+accumulator.toString()+"\"");
                    }
                if(!amd) {
                    throw parserError(Escape.MALFORMED,"A string value cannot contain unescaped quotes (this can also be caused by a missing comma between members)");
                    }
                qut=true;
                storeChar((char)ich);
                }
            else if(ich==':') {
                if(mbrName.length()>0) {
                    throw parserError(Escape.MALFORMED,"A object member value contained a colon but was not enclosed in quotes (this can often be caused by a missing comma between members)");
                    }
                if(objectData.arrayDepth!=0) {
                    throw parserError(Escape.MALFORMED,"An array element cannot be a Name:Value pair - it must be only a value (this is most likely caused by misplaced or missing closing bracket)");
                    }
                String txt=getAccumulatedText();
                mbrName=objectData.mbrNameSave=stripQuotes(txt);
                if(mbrName.length()==0) {
                    throw parserError(Escape.MALFORMED,"A object member name cannot be blank");
                    }
                if(!optUnquotedKeywords && !isQuoted(txt)) {
                    throw parserError(Escape.MALFORMED,"A object member name was not enclosed in quotes");
                    }
                amd=true;
                }
            else if((ich=='\r' || ich=='\n') && !optEolIsComma) {
                // ignore CR & LF if not treating EOL as a comma
                }
            else if(ich==',' || ich=='\r' || ich=='\n') {
                if(accumulator.length()==0) {                                   // empty value ignored
                    mbrName="";                                                 // continue as if member-value event was previously generated
                    pet=EVT_OBJECT_MEMBER;                                      // ditto
                    }
                else if(pet==EVT_OBJECT_ENDED) {
                    throw parserError(Escape.MALFORMED,"Text was found between an object's closing brace and a subsequent comma or end of line (this is usually caused by a missing comma); Text=\""+accumulator.toString()+"\"");
                    }
                else if(pet==EVT_ARRAY_ENDED) {
                    throw parserError(Escape.MALFORMED,"Text was found between a list's closing bracket and a subsequent comma (this is usually caused by a missing comma); Text=\""+accumulator.toString()+"\"");
                    }
                else if(objectData.arrayDepth==0 && mbrName.length()==0) {
                    throw parserError(Escape.MALFORMED,"Object member name or value is missing in a Name:Value pair (this is possibly caused by missing array brackets in a list)");
                    }
                else {
                    mbrValue=getAccumulatedText();
                    if(mbrName.length()==0) { mbrName=objectData.mbrNameSave; }
                    return (evtCode=EVT_OBJECT_MEMBER);
                    }
                amd=true;
                }
            else if(ich=='{') {
                if(accumulator.length()!=0) {
                    throw parserError(Escape.MALFORMED,"Text was found preceding an object's opening brace (this is usually caused by a missing comma or colon, or by using equals instead of a colon); Text=\""+accumulator.toString()+"\"");
                    }
                if(mbrName.length()==0) { mbrName=objectData.mbrNameSave; }
                saveObjectData();
                objectData=new ObjectData(mbrName);
                return (evtCode=EVT_OBJECT_BEGIN);
                }
            else if(ich=='}') {
                mbrValue=getAccumulatedText();
                if(objectData.arrayDepth==0 && mbrName.length()==0 && mbrValue.length()>0) {
                    throw parserError(Escape.MALFORMED,"Object member name or value is missing in a Name:Value pair (this is possibly caused by missing array brackets in a list)");
                    }
                else if(/*!amd || */mbrValue.length()>0) {                      // !amd flags that a quoted value has been closed (LD:20090219 - But that mean that mbrValue must be at least 2 characters long, the quotes)
                    unreadChar(ich);
                    if(mbrName.length()==0) { mbrName=objectData.mbrNameSave; }
                    return (evtCode=EVT_OBJECT_MEMBER);
                    }

                mbrName=objectData.name;
                return (evtCode=EVT_OBJECT_ENDED);
                }
            else if(ich=='[') {
                if(accumulator.length()!=0) {
                    throw parserError(Escape.MALFORMED,"Text was found preceding a list's opening bracket (this is usually caused by a missing comma or colon, or by using equals instead of a colon); Text=\""+accumulator.toString()+"\"");
                    }
                objectData.arrayDepth++;
                mbrArray=true;
                if(mbrName.length()==0) { mbrName=objectData.mbrNameSave; }
                return (evtCode=EVT_ARRAY_BEGIN);
                }
            else if(ich==']') {
                mbrValue=getAccumulatedText();
                if(/*!amd || */mbrValue.length()>0) {                           // !amd flags that a quoted value has been closed (LD:20090219 - But that mean that mbrValue must be at least 2 characters long, the quotes)
                    unreadChar(ich);
                    if(mbrName.length()==0) { mbrName=objectData.mbrNameSave; }
                    return (evtCode=EVT_OBJECT_MEMBER);
                    }

                if(objectData.arrayDepth==0) {
                    throw parserError(Escape.MALFORMED,"An explicit list was not closed before its declaring object was closed (this is most likely caused by misplaced, missing or extraneous brackets)");
                    }
                objectData.arrayDepth--;
                mbrArray=(objectData.arrayDepth==0);
                if(mbrName.length()==0) { mbrName=objectData.mbrNameSave; }
                return (evtCode=EVT_ARRAY_ENDED);
                }
            else if(Character.isWhitespace((char)ich)) {
                pws=true;
                }
            else {
                if(!amd) {
                    throw parserError(Escape.MALFORMED,"A string value cannot contain data after its closing quote (this is most likely caused by a missing comma between members)");
                    }
                if(pws && accumulator.length()!=0) {
                    throw parserError(Escape.MALFORMED,"Text with embedded spaces was found but not enclosed in quotes (this is often caused by a missing comma following an unquoted value); Text=\""+accumulator.toString()+"\"");
                    }
                storeChar((char)ich);
                pws=false;
                }
            }

        mbrName=null;
        mbrValue=null;
        if(objectStack.size()!=0) {
            if(qut) { throw parserError(Escape.MALFORMED,"A string's closing quote was missing from the input data (end of input was reached before string was terminated)"); }
            else    { throw parserError(Escape.MALFORMED,"An object's closing brace was missing from the input data (end of input was reached before object was terminated)"); }
            }
        evtLine=inpLine;
        evtColumn=inpColumn;
        if(close) { try { reader.close(); } catch(Throwable ign) {;} }
        return (evtCode=EVT_INPUT_ENDED);
        }
    catch(IOException thr) {
        if(close) { try { reader.close(); } catch(Throwable ign) {;} }
        throw new Escape(Escape.IOERROR,("I/O Exception: "+thr),thr);
        }
    }

/**
 * Skip the object that the parser is currently positioned at.  The current event must be EVT_OBJECT_BEGIN.
 */
public void skipObject() {
    if(getEventCode()!=EVT_OBJECT_BEGIN) {
        throw parserError(Escape.INVALID_STATE,"An object can only be skipped when the current event is EVT_OBJECT_BEGIN");
        }

    int level=1;
    while(level>0) {
        int eventCode=next();
        if     (eventCode==EVT_OBJECT_BEGIN) { level++; }
        else if(eventCode==EVT_OBJECT_ENDED) { level--; }
        }
    }

/**
 * Skip the array that the parser is currently positioned at.  The current event must be EVT_ARRAY_BEGIN.
 */
public void skipArray() {
    if(getEventCode()!=EVT_ARRAY_BEGIN) {
        throw parserError(Escape.INVALID_STATE,"An object can only be skipped when the current event is EVT_ARRAY_BEGIN");
        }

    int level=1;
    while(level>0) {
        int eventCode=next();
        if     (eventCode==EVT_ARRAY_BEGIN  ) { level++;      }
        else if(eventCode==EVT_ARRAY_ENDED  ) { level--;      }
        else if(eventCode==EVT_OBJECT_BEGIN) { skipObject(); }
        }
    }

private void storeChar(char ch) {
    if(evtLine==0) { evtLine=inpLine; evtColumn=inpColumn; }
    accumulator.append(ch);
    }

private int decodeHexByte(char c1, char c2, int lin, int col) {
    try { return decodeHexByte(c1,c2); }
    catch(Exception thr) { throw parserError(Escape.BAD_ESCAPE,thr.getMessage(),null,lin,col); }
    }

private int decodeHexChar(char c1, char c2, char c3, char c4, int lin, int col) {
    try { return decodeHexChar(c1,c2,c3,c4); }
    catch(Exception thr) { throw parserError(Escape.BAD_ESCAPE,thr.getMessage(),null,lin,col); }
    }

// *****************************************************************************
// INSTANCE METHODS - PRIVATE
// *****************************************************************************

private int readChar() throws IOException {
    int                                 ich;

    if(pushBack!=-1) {
        ich=pushBack;
        pushBack=-1;
        inpColumn++;
        }
    else {
        if((ich=reader.read())!=-1) {
            if     (ich=='\n'    ) { inpColumn=0; inpLine++; }
            else if(ich=='\uFEFF') { ich=' ';                }                  // FEFF is used as BOM; otherwise is a zero width space
            else                   { inpColumn++;            }
            }
        }
    return ich;
    }

private void unreadChar(int ich) throws IOException {
    if(ich!=-1) {
        if(pushBack!=-1) {
            throw parserError(Escape.GENERAL,"Cannot unread '"+(char)ich+"' the character '"+(char)pushBack+"' is already pending");
            }
        pushBack=ich;
        if(ich=='\n') { inpColumn=0; inpLine--; }
        else          { inpColumn--;            }
        }
    }

private void saveObjectData() {
    objectStack.addLast(objectData);
    }

private void restoreObjectData() {
    if(objectStack.size()==0) {
        throw parserError(Escape.MALFORMED,"An extraneous object closing brace was present in the input data");
        }
    objectData=(ObjectData)objectStack.removeLast();
    mbrName =objectData.name;
    mbrValue=null;
    mbrArray=(objectData.arrayDepth==0);
    }

private String getAccumulatedText() {
    String                              val;

    val=accumulator.toString().trim();
    if(val.startsWith("/**/")) { val=val.substring(4).trim(); }
    accumulator.setLength(0);
    return val;
    }

private String location(int lin, int col) {
    String                              mn=((mbrName!=null && mbrName.length()!=0) ? mbrName : objectData.mbrNameSave);

    if(mn==null      ) { mn="(unknown)"; }
    if(mn.length()==0) { mn="";          }
    if(col>0) { return ("Input Source: \""+inputName+"\", Line: "+lin+", Column: "+col+", Member Name: "+mn); }
    else      { return ("Input Source: \""+inputName+"\", Line: "+(lin-1)+", Column: EOL, Member Name: "+mn); }
    }

private Escape parserError(int cod, String txt) {
    return parserError(cod,txt,null);
    }

private Escape parserError(int cod, String txt, Throwable thr) {
    return parserError(cod,txt,thr,inpLine,inpColumn);
    }

private Escape parserError(int cod, String txt, Throwable thr, int lin, int col) {
    return new Escape(cod,(txt+"; at "+location(lin,col)),thr);
    }

// *****************************************************************************
// INSTANCE INNER CLASSES
// *****************************************************************************

// *****************************************************************************
// STATIC NESTED CLASSES
// *****************************************************************************

    static class ObjectData
    extends Object
    {
    String                              name;                                   // object name (may be "")
    String                              mbrNameSave;                            // last member name for this object (initially "")
    int                                 arrayDepth;                             // depth of explicitly declared lists in this object to detect when list and object boundaries cross

    ObjectData(String nam) {
        name=nam;
        mbrNameSave="";
        arrayDepth=0;
        }
    }

// *****************************************************************************
// STATIC PROPERTIES
// *****************************************************************************

/** Returned from next() when the beginning of an object is read. */
static public final int                 EVT_OBJECT_BEGIN    =1;

/** Returned from next() when the end of an object is read. */
static public final int                 EVT_OBJECT_ENDED    =2;

/** Returned from next() when the beginning of a declared array is read. */
static public final int                 EVT_ARRAY_BEGIN     =3;

/** Returned from next() when the end of a declared array is read. */
static public final int                 EVT_ARRAY_ENDED     =4;

/** Returned from next() when the end of the input source is reached. */
static public final int                 EVT_INPUT_ENDED     =5;

/** Returned from next() when a simple object member (Name:Value pair or array element) is read. */
static public final int                 EVT_OBJECT_MEMBER   =6;

static private String[] EVT_NAMES={
    "Invalid",                                                                  // 0
    "ObjectBegin",                                                              // 1
    "ObjectEnded",                                                              // 2
    "ArrayBegin",                                                               // 3
    "ArrayEnded",                                                               // 4
    "InputEnded",                                                               // 6
    "ObjectMember",                                                             // 5
    };

/** Option to allow keywords to be unquoted. */
static public final int                 OPT_UNQUOTED_KEYWORDS   =0x00000001;
/** Option to allow an end-of-line to be treated as a comma. */
static public final int                 OPT_EOL_IS_COMMA        =0x00000002;
/** Option to allow multiline comments using &#47;* and *&#47;. */
static public final int                 OPT_MULTILINE_COMMENTS  =0x00000004;
/** Option to allow mutiline strings - this permits strings to be broken over multiple lines in an unambigous manner. */
static public final int                 OPT_MULTILINE_STRINGS   =0x00000008;

/** Sets strict parsing mode - all options off. */
static public final int                 OPT_STRICT              =0;
/** Sets relaxed parsing mode - all options are on, except OPT_MULTILINE_STRINGS. */
static public final int                 OPT_RELAXED             =(OPT_UNQUOTED_KEYWORDS | OPT_EOL_IS_COMMA | OPT_MULTILINE_COMMENTS);
/** Sets loose parsing mode - all options on. */
static public final int                 OPT_LOOSE               =(OPT_UNQUOTED_KEYWORDS | OPT_EOL_IS_COMMA | OPT_MULTILINE_COMMENTS | OPT_MULTILINE_STRINGS);

// *****************************************************************************
// STATIC INIT & MAIN
// *****************************************************************************

// *****************************************************************************
// STATIC METHODS - UTILITY
// *****************************************************************************

/**
 * Create a typed member value applying JSON typing rules.  All text values must be quoted.
 * <p>
 * Actual return types are as follows:
 * <ul>
 * <li>true - Boolean.TRUE (not case sensitive)
 * <li>false - Boolean.FALSE (not case sensitive)
 * <li>null - Java null (not case sensitive)
 * <li>"..." - String value after quotes are stripped
 * <li>Anything else - BigDecimal (exception thrown from the BigDecimal constructor if not a valid number)
 * </ul>
 * @throws NumberFormatException If an unquoted value which is no null, true or false is not a valid decimal number.
 */
static public Object createTypedValue(String val) {
    if     (val.equalsIgnoreCase("null" )) { return null;                }
    else if(val.equalsIgnoreCase("true" )) { return Boolean.TRUE;        }
    else if(val.equalsIgnoreCase("false")) { return Boolean.FALSE;       }
    else if(isQuoted (val)               ) { return stripQuotes(val);    }
    else if(val.length()==0              ) { return val;                 }
    else                                   { return new BigDecimal(val); }
    }

/**
 * Get a text name for the event code.
 */
static public String getEventName(int cod) {
    if     (cod<1                ) { return EVT_NAMES[  0]; }
    else if(cod>=EVT_NAMES.length) { return ("EVT_"+cod);   }
    else                           { return EVT_NAMES[cod]; }
    }

/**
 * Strip the text-value-indicating quotes from the supplied member value, if any.
 */
static public String stripQuotes(String val) {
    if(isQuoted(val)) { val=val.substring(1,val.length()-1); }
    return val;
    }

/**
 * Test if the member value is enclosed in text-value-indicating quotes.
 */
static public boolean isQuoted(String val) {
    int                                 len;

    if(val!=null && (len=val.length())>1) {
        char ch0=val.charAt(0);
        if(ch0=='"' && ch0==val.charAt(len-1)) {
            return true;                                                        // NOTE: Don't test for unescaped quotes because the value has already been unescaped as it was parsed.
            }
        }
    return false;
    }

// *****************************************************************************
// STATIC METHODS - GENERAL
// *****************************************************************************

static int[]                            decodeHex;                              // hex table for decoding hex-based escapes
static {
    decodeHex=new int[256];
    for(int xa=0; xa<decodeHex.length; xa++) { decodeHex[xa]=-1; }
    decodeHex['0']= 0; decodeHex['1']= 1; decodeHex['2']= 2; decodeHex['3']= 3;
    decodeHex['4']= 4; decodeHex['5']= 5; decodeHex['6']= 6; decodeHex['7']= 7;
    decodeHex['8']= 8; decodeHex['9']= 9;
    decodeHex['A']=10; decodeHex['B']=11; decodeHex['C']=12; decodeHex['D']=13;
    decodeHex['E']=14; decodeHex['F']=15;
    decodeHex['a']=10; decodeHex['b']=11; decodeHex['c']=12; decodeHex['d']=13;
    decodeHex['e']=14; decodeHex['f']=15;
    }

static private int decodeHexByte(char hex1, char hex2) {
    int                                 n1;                                 // nibble 1
    int                                 n2;                                 // nibble 2

    if(hex1>0xFF || (n1=decodeHex[hex1])==-1) {
        throw new RuntimeException("Escape sequence contains the invalid hexadecimal digit '"+hex1+"'; not 0-9, a-f or A-F");
        }
    if(hex2>0xFF || (n2=decodeHex[hex2])==-1) {
        throw new RuntimeException("Escape sequence contains the invalid hexadecimal digit '"+hex2+"'; not 0-9, a-f or A-F");
        }
    return ((n1<<4) | n2);
    }

static private int decodeHexChar(char hex1, char hex2, char hex3, char hex4) {
    return ((decodeHexByte(hex1,hex2)<<8) | (decodeHexByte(hex3,hex4)));
    }

// *****************************************************************************
// STATIC NESTED CLASSES - ESCAPE (COULD DROP THIS FOR RUNTIME EXCEPTION
// *****************************************************************************

    static public class Escape
    extends RuntimeException
    {
    /** General exception. */
    static public final int                 GENERAL         =    1;

    /** Input/Output error. */
    static public final int                 IOERROR         =    2;

    /** Invalid encoding. */
    static public final int                 BAD_ENCODING    =    3;

    /** Malformed input data - the data was not valid JSON data-interchange format. */
    static public final int                 MALFORMED       =    4;

    /** A bad escape sequence was encountered. */
    static public final int                 BAD_ESCAPE      =    6;

    /** An method was invokeD when the parser was in an invalid state for it. */
    static public final int                 INVALID_STATE   =    7;

    /** An method could not be reflectively retrieved or invoked. */
    static public final int                 METHOD_ERROR    =    8;

    /** Data-type conversion error. */
    static public final int                 CONVERSION      =    9;

    /** Minimum code required for any sub-class. */
    static public final int                 SUBCLSMIN       = 1000;

    private final int                       code;

    /**
     * Create an exception with a code and details.  It is recommened that the detail always include very specific information about the cause of the error.
     * @param code      The error code.
     * @param detail    Specific detail text indicating the cause of the error.
     */
    public Escape(int code,String detail) {
        this(code,detail,null);
        }

    /**
     * Create an exception with a code and details.  It is recommened that the detail always include very specific information about the cause of the error.
     * @param code      The error code.
     * @param detail    Specific detail text indicating the cause of the error.
     * @param cause     The causitive throwable object, if any.
     */
    public Escape(int code,String detail,Throwable cause) {
        super(detail,cause);

        this.code=code;
        }

    /** Return the numeric code for this exception condition. */
    public int getCode() {
        return code;
        }
    }

} // END PUBLIC CLASS
