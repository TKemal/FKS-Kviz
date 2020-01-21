package com.tihic.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.tihic.quiz.QuizContract.CategoriseTable;
import com.tihic.quiz.QuizContract.QuestionTable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quiz.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    private QuizDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context){
        if (instance == null){
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriseTable.TABLE_NAME + "( " +
                CategoriseTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriseTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriseTable.TABLE_NAME + "(" + CategoriseTable._ID + ")" + "ON DELETE CASCADE" +
                ")";
        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriseTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable(){
        Category c1 = new Category("Utakmice");
        insertCategory(c1);
        Category c2 = new Category("Igrači");
        insertCategory(c2);
        Category c3 = new Category("Istorija");
        insertCategory(c3);
    }

    public void addCategory(Category category){
        db = getReadableDatabase();
        insertCategory(category);
    }

    public void addCategories(List<Category> categories){
        db = getReadableDatabase();

        for (Category category : categories){
            insertCategory(category);
        }
    }

    private void insertCategory(Category category){
        ContentValues cv = new ContentValues();
        cv.put(CategoriseTable.COLUMN_NAME, category.getName());
        db.insert(CategoriseTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionTable() {
        //              *************   Difficulty : EASY  Category: ISTORIJA   ***********
        Question q1 = new Question("Datum osnivanja F.K. Sarajevo?",
                "24.10.1946", "24.08.1946", "3.11.1946", 1, Question.DIFFICULTY_EASY, Category.ISTORIJA);
        insertQuestion(q1);
        Question q2 = new Question("31.08.1957 godine F.K. Sarajevo igra utakmicu pred rekordnim brojem gledalaca, označi tačan broj gledalaca",
                "210.000", "55.000", "120.000", 3, Question.DIFFICULTY_EASY, Category.ISTORIJA);
        insertQuestion(q2);
        Question q3 = new Question("Sarajevo šampion Jugoslavije!:",
                "18.03.1969", "02.05.1967", "02.07.1967", 3, Question.DIFFICULTY_EASY, Category.ISTORIJA);
        insertQuestion(q3);
        Question q4 = new Question("Sarajevo - UNPROFOR 4:0. Nakon pune dvije godine, stadion Koševo je ponovo otvorio svoje kapije dana:",
                "20.03.1994", "15.05.1996", "01.03.1995", 1, Question.DIFFICULTY_EASY, Category.ISTORIJA);
        insertQuestion(q4);
        Question q5 = new Question("24.12.2013 je datum iz novije istorije FKS, tog dana je:",
                "Sarajevo osvojilo KUP BiH", "Ozvaničena kupovina većine upravljačkih uloga od strane malezijskog biznismena Vincenta Tana",
                "Klub otišao na pripreme u Antaliju", 2, Question.DIFFICULTY_EASY, Category.ISTORIJA);
        insertQuestion(q5);

        //              *************   Difficulty : MEDIUM  Category: ISTORIJA   ***********
        Question q6 = new Question("U decembru 1962 godine donosi se jedna od najvažnijih odluka u istoriji FK Sarajevo",
                "Stadion Koševo postaje Olimpijski stadion", "Datum osnivanja F.K. Sarajevo", "Na redovnoj skupštini kluba odlučeno je da Sarajevo za zvaničnu boju dresova uzme bordo. Također, promijenjen je i grb kluba",
                3, Question.DIFFICULTY_MEDIUM, Category.ISTORIJA);
        insertQuestion(q6);
        Question q7 = new Question("02.08.1997 godine je:",
                "Osvojen Superkup BiH", "Osvojen Kup BiH", "Prvak BiH", 1, Question.DIFFICULTY_MEDIUM, Category.ISTORIJA);
        insertQuestion(q7);
        Question q8 = new Question("02.04.2014 godine počinje sa radom...",
                "Said Husejinović", "Akademija FK Sarajevo", "Škola golmana FK Sarajevo", 2, Question.DIFFICULTY_MEDIUM, Category.ISTORIJA);
        insertQuestion(q8);
        Question q9 = new Question("Spajanjem koja dva kluba nastaje FK Torpedo",
                "OFD Sloboda i RSD Udarnik", "OFD Sloboda i OFK Udarnik", "NK Hajduk i Đerzelez", 1, Question.DIFFICULTY_MEDIUM, Category.ISTORIJA);
        insertQuestion(q9);
        Question q10 = new Question("Dana petog oktobra 1947. godine...",
                "u Sarajevo stiže velika zvijezda jugoslovenskog fudbala, Mostarac Miroslav Brozović.", "Sarajevo je ispalo iz Prve lige Jugoslavije", "donesena odluka da Društvo promjeni ime iz Torpedo u Sarajevo", 3, Question.DIFFICULTY_MEDIUM, Category.ISTORIJA);
        insertQuestion(q10);

        //              *************   Difficulty : HARD  Category: ISTORIJA   ***********
        Question q11 = new Question("03.06.1945 godine:",
                "Osniva se OFD Sloboda", "Prva zvanična utakmica FKS", "Osniva se RSD Udarnik", 3, Question.DIFFICULTY_HARD, Category.ISTORIJA);
        insertQuestion(q11);
        Question q12 = new Question("Debi Asima Ferhatovića Haseta bio je:",
                "11.11.1951.", "05.10.1947.", "29.02.1948", 1, Question.DIFFICULTY_HARD, Category.ISTORIJA);
        insertQuestion(q12);
        Question q13 = new Question("Prvi zvanični duel sarajevskih rivala, odigran je u okviru Kupa Maršala Tita, a Sarajevo je slavilo sa:",
                "2:0", "5:2", "6:1", 2, Question.DIFFICULTY_HARD, Category.ISTORIJA);
        insertQuestion(q13);
        Question q14 = new Question("Sarajevo vicešampion Jugoslavije:",
                "19.10.1957.", "10.06.1965.", "24.05.1967.", 2, Question.DIFFICULTY_HARD, Category.ISTORIJA);
        insertQuestion(q14);
        Question q15 = new Question("Juniori Sarajeva su iz trećeg pokušaja uspjeli osvojiti Omladinski kup Jugoslavije na dan:",
                "25.05.1976.", "29.06.1980.", "24.05.1983.", 1, Question.DIFFICULTY_HARD, Category.ISTORIJA);
        insertQuestion(q15);

        //              *************   Difficulty : EASY  Category: IGRAČI   ***********
        Question q16 = new Question("Safet Sušić je rođen u:",
                "Zavidovićima.", "Sarajevu", "Visokom", 1, Question.DIFFICULTY_EASY, Category.IGRAČI);
        insertQuestion(q16);
        Question q17 = new Question("Igrač sa najviše nastupa u bordo dresu:",
                "Janjoš Mehmed", "Zukić Muhidin", "Biogradlić Ibrahim", 3, Question.DIFFICULTY_EASY, Category.IGRAČI);
        insertQuestion(q17);
        Question q18 = new Question("Najbolji strijelac FK Sarajevo u prvoligaškim utakmicama je:",
                "Obuća Emir", "Ferhatović Asim", "Sušić Safet", 3, Question.DIFFICULTY_EASY, Category.IGRAČI);
        insertQuestion(q18);
        Question q19 = new Question("Husref Musemić u dresu FK Sarajevo postigao je:",
                "65 golova", "50 golova", "preko 100 golova", 1, Question.DIFFICULTY_EASY, Category.IGRAČI);
        insertQuestion(q19);
        Question q20 = new Question("Drugi pogodak u susretu protiv Levskog na Koševu postigao je:",
                "Zoran Belošević", "Emir Hadžić",
                "Asmir Suljić", 3, Question.DIFFICULTY_EASY, Category.IGRAČI);
        insertQuestion(q20);

        //              *************   Difficulty : MEDIUM  Category: IGRAČI   ***********
        Question q21 = new Question("Jedan od terena u kampu Akademije FK Sarajevo nosi ime:",
                "Asim Feerhatović Hase", "Slobodan Janjuš", "Želimir Vidović Keli",
                3, Question.DIFFICULTY_MEDIUM, Category.IGRAČI);
        insertQuestion(q21);
        Question q22 = new Question("Safet Sušić iz FK Sarajevo odlazi u:",
                "NK Krivaja", "Paris Saint-Germain", "HSV", 2, Question.DIFFICULTY_MEDIUM, Category.IGRAČI);
        insertQuestion(q22);
        Question q23 = new Question("Emir Obuća osim Sarajeva i Olimpika u BiH je igrao i za:",
                "Sloboda Tuzla", "Borac Banja Luka", "Čelik Zenica", 3, Question.DIFFICULTY_MEDIUM, Category.IGRAČI);
        insertQuestion(q23);
        Question q24 = new Question("Alen Škoro je bio najbolji strijelac Premijer Lige BiH u sezoni:",
                "1998/99", "2003/04", "2009/10", 2, Question.DIFFICULTY_MEDIUM, Category.IGRAČI);
        insertQuestion(q24);
        Question q25 = new Question("Nadimak Predraga Pašića je:",
                "Paja", "Nono", "Peđa",
                1, Question.DIFFICULTY_MEDIUM, Category.IGRAČI);
        insertQuestion(q25);

        //              *************   Difficulty : HARD  Category: IGRAČI   ***********
        Question q26 = new Question("Ko je bio asistent Husrefu Musemiću za pogodak na utakmici sa Crvenom Zvezdom za titulu: ",
                "Predrag Pašić", "Dragan Jakovljević", "Slaviša Vukičević", 3, Question.DIFFICULTY_HARD, Category.IGRAČI);
        insertQuestion(q26);
        Question q27 = new Question("1950. godine Sarajevo odlazi na turneju u Belgiju, najefikasniji igrač na toj turneji bio je:",
                "Franjo Lovrić", "Đuka Lovrić", "Miroslav Brozović", 1, Question.DIFFICULTY_HARD, Category.IGRAČI);
        insertQuestion(q27);
        Question q28 = new Question("Vahidin Musemić u bordo dresu odigrao je:",
                "155 utakmica", "121 utakmicu", "163 utakmice", 3, Question.DIFFICULTY_HARD, Category.IGRAČI);
        insertQuestion(q28);
        Question q29 = new Question("1. februara 1977. godine, pokojni Želimir Vidović Keli je debitovao za reprezentaciju Jugoslavije na prijateljskoj utakmici sa:",
                "Italijom", "Meksikom", "Argentinom", 2, Question.DIFFICULTY_HARD, Category.IGRAČI);
        insertQuestion(q29);
        Question q30 = new Question("Igrač FK Sarajevo sa najviše nastupa u UEFA takmičenjima:",
                "Muhamed Alaim", "Emir Obuća", "Nijaz Ferhatović", 1, Question.DIFFICULTY_HARD, Category.IGRAČI);
        insertQuestion(q30);
        Question q41 = new Question("02.07.1967. Sarajevo - Čelik 5:2, koji fudbaler Sarajeva je postigao het-trik u toj utakmici: ",
                "Prodanović", "Antić", "Musemić", 3, Question.DIFFICULTY_HARD, Category.IGRAČI);
        insertQuestion(q41);
        Question q42 = new Question("Igrač sa najviše nastupa u sezoni 2000/01 :",
                "Šašivarević Fuad", "Ferhatović Amar", "Pelak Albin", 2, Question.DIFFICULTY_HARD, Category.IGRAČI);
        insertQuestion(q42);
        Question q43 = new Question("Jean Louis Nouken u bordo dresu je odigrao:",
                "28 utakmica", "51 utakmicu", "48 utakmica", 3, Question.DIFFICULTY_HARD, Category.IGRAČI);
        insertQuestion(q43);
        Question q44 = new Question("Igrač sa najviše nastupa protiv Željezničara u svim zvaničnim utakmicama: ",
                "Svetozar Vujović", "Faruk Ihtijarević", "Alen Škoro", 2, Question.DIFFICULTY_HARD, Category.IGRAČI);
        insertQuestion(q44);
        Question q45 = new Question("Koji igrač je igrao do jeseni 1959. godine, a onda je otisao u NK Rijeku:",
                "Miroslav Blažević", "Stjepan Blažević", "Zijad Arslanagić", 1, Question.DIFFICULTY_HARD, Category.IGRAČI);
        insertQuestion(q45);

        //              *************   Difficulty : EASY  Category: UTAKMICE   ***********
        Question q31 = new Question("Fudbaleri Sarajeva, uprkos sjajnoj igri zabilježili su poraz od njemačkog predstavnika, Borusije Monchengladbachna rezultatom:",
                "2:3.", "2:5", "1:2", 1, Question.DIFFICULTY_EASY, Category.UTAKMICE);
        insertQuestion(q31);
        Question q32 = new Question("15.11.1967 dan kada je na Koševu gostovao slavni:",
                "Anderleht", "HSV", "Mančester Junajted", 3, Question.DIFFICULTY_EASY, Category.UTAKMICE);
        insertQuestion(q32);
        Question q33 = new Question("2012 godine u drugom pretkolu kvalifikacija za ligu Europe sarajevo je eliminisalo:",
                "Atromitos", "Orebro", "Levski", 3, Question.DIFFICULTY_EASY, Category.UTAKMICE);
        insertQuestion(q33);
        Question q34 = new Question("Prvo kolo Kupa UEFA za sezonu 2002/03,  protivnik bordo tima bio je slavni turski klub Bešiktaš. Rezultat prve utakmice odigrane u Istambulu bio je:",
                "1:1", "2:2", "0:3", 2, Question.DIFFICULTY_EASY, Category.UTAKMICE);
        insertQuestion(q34);
        Question q35 = new Question("2009 godina, još jedna od od evropskih utakmica. Pred 26.000 navijača na Koševu je gostovao rumunski ",
                "Levski", "Zeta", "Cluj", 3, Question.DIFFICULTY_EASY, Category.UTAKMICE);
        insertQuestion(q35);

        //              *************   Difficulty : MEDIUM  Category: UTAKMICE   ***********
        Question q36 = new Question("18.10.1959. godine u prijateljskoj utakmici od fudbala oprašta se Franjo Lovrić Franco, " +
                "jedan od najboljih fudbalera FK Sarajevo. Protivinik Sarajevu na toj utamici bila je ekipa:",
                "Dinamo", "Crvena Zvezda", "Željezničar",
                3, Question.DIFFICULTY_MEDIUM, Category.UTAKMICE);
        insertQuestion(q36);
        Question q37 = new Question("17. septembra 1980 godine na stadionu Volksparkstadion u Hamburgu FK Sarajevo je gostovalo slavnom HSV-u. " +
                "Oznaci račan rezutltat iz te utakmice:",
                "2:4", "4:2", "2:2", 2, Question.DIFFICULTY_MEDIUM, Category.UTAKMICE);
        insertQuestion(q37);
        Question q38 = new Question("10.08.2006, drugoo pretkolo Kupa UEFA, protivnik Sarajevu bio je:",
                "Helsingborg", "Genk", "Rapid", 3, Question.DIFFICULTY_MEDIUM, Category.UTAKMICE);
        insertQuestion(q38);
        Question q39 = new Question("Sarajevo - Čelik (2014) Stadion AFH finale KUP-a BiH, strijelac drugog pogotka na utakmici bio je:",
                "Gojko Cimirot", "Bojan Puzigaća", "Jasmin Mešanović", 2, Question.DIFFICULTY_MEDIUM, Category.UTAKMICE);
        insertQuestion(q39);
        Question q40 = new Question("Atromitos FC 1:3 FK Sarajevo, dva pogotka na toj utakmici postigao je:",
                "Nemanja Bilbija", "Haris Duljević", "Krste Velkoski",
                1, Question.DIFFICULTY_MEDIUM, Category.UTAKMICE);
        insertQuestion(q40);

        //              *************   Difficulty : HARD  Category: UTAKMICE   ***********
        Question q46 = new Question("30.03.1996 FK Sarajevo - NK Zenica, konačan rezultat: ",
                "0:1", "4:0", "0:0", 1, Question.DIFFICULTY_HARD, Category.UTAKMICE);
        insertQuestion(q46);
        Question q47 = new Question("'Krvnik' Bobana Božovića:",
                "Fadilj Murići", "Fadilj Murići :(", "Fadilj Murići", 2, Question.DIFFICULTY_HARD, Category.UTAKMICE);
        insertQuestion(q47);
        Question q48 = new Question("07.03.1965. FK Sarajevo - ???? 1:2:",
                "NK Hajduk", "V.M.C", "SSSR", 3, Question.DIFFICULTY_HARD, Category.UTAKMICE);
        insertQuestion(q48);
        Question q49 = new Question("Transfer Predraga Pašića u Stuttgart iznosio je: ",
                "500.000DEM", "800.000DEM", "1.000.000DEM", 2, Question.DIFFICULTY_HARD, Category.UTAKMICE);
        insertQuestion(q49);
        Question q50 = new Question("Prezime bivšeg igrača FK Sarajevo Bahrudina zvani Kurško:",
                "Omerbegović", "Bajramović", "Alihodžić ", 1, Question.DIFFICULTY_HARD, Category.UTAKMICE);
        insertQuestion(q50);

    }

    public void addQuestion(Question question){
        db = getReadableDatabase();
        insertQuestion(question);
    }

    public void addQuestions(List<Question> questions){
        db = getReadableDatabase();
        for (Question question : questions){
            insertQuestion(question);
        }
    }

    private void insertQuestion(Question question) {

        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionTable.TABLE_NAME, null, cv);

    }

    public List<Category> getAllCategory(){
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriseTable.TABLE_NAME, null);
            if (c.moveToFirst()){
                do {
                    Category category = new Category();
                    category.setId(c.getInt(c.getColumnIndex(CategoriseTable._ID)));
                    category.setName(c.getString(c.getColumnIndex(CategoriseTable.COLUMN_NAME)));
                    categoryList.add(category);
                } while (c.moveToNext());
            }
            c.close();
            return categoryList;
    }

    public ArrayList<Question> getAllQuestions() {

        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME, null);

        if (c.moveToFirst()) {

            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {

        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

       String selection = QuestionTable.COLUMN_CATEGORY_ID + " = ? " +
               " AND " + QuestionTable.COLUMN_DIFFICULTY + " = ? ";
       String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};
       Cursor c = db.query(
               QuestionTable.TABLE_NAME,
               null,
               selection,
               selectionArgs,
               null,
               null,
               null
       );

        if (c.moveToFirst()) {

            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

}
