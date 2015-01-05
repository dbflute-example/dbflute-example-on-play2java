
# ========================================================================================
#                                                                                 Overview
#                                                                                 ========
Play2 (Java) と DBFlute を組み合わせた Example プロジェクトです。

o Play2 version: 2.3.6
o DIContainer: Google Guice
o Play2 EBean: unused (use DBFlute instead)
o Play2 BoneCP: used
o Transaction: Spring Framework


# ========================================================================================
#                                                                              Environment
#                                                                              ===========
# ----------------------------------------------------------
#                                                    Compile
#                                                    -------
Eclipse の場合は、.classpath が gitignore されているので、
activator にて eclipse with-source=true を叩きます。
その後、Eclipse の Package Explorer で refresh (F5) をしましょう。

e.g.
 ...$ sh activator
    ↓
 [dbflute-example-on-play2java] $ eclipse with-source=true

# ----------------------------------------------------------
#                                                   Database
#                                                   --------
データベースは、MySQL が 3306 ポートで起動している状態で、
DBFluteクライアント(dbflute_maihamadb) の manage.bat|sh を叩いて、
replace-schema (0) を実行すると、スキーマが構築されます。

e.g.
 ...$ sh manage.sh
    ↓
 0 => enter

# ----------------------------------------------------------
#                                                Application
#                                                -----------
(コンパイルやデータベースの準備が整っていることが前提として...)
activator にて run を実行します。

e.g.
 ...$ sh activator
    ↓
 [dbflute-example-on-play2java] $ run

そして、ブラウザから以下のURLにアクセスします。
すると、TopControllerが実行されます。

http://localhost:9000/
