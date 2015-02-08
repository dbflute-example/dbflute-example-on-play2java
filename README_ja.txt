
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


# ========================================================================================
#                                                                              Development
#                                                                              ===========
# ----------------------------------------------------------
#                                                 Management
#                                                 ----------
perrotta : ProductList, Purchase *ネギone
rabichan : SignUp, SignIn
light    : MyPage, Profile
jflute   : English test data, English table comment, modeling, framework

# ----------------------------------------------------------
#                                                     Policy
#                                                     ------
o 同じworkspaceで、DBFluteハンズオンのセクション１までやっておくこと (workspaceの設定のために)
o 海外へのプロモーションを意識したものなので残すコメントは英語、でもコミットコメントやtodoコメントは日本語でOK
o Tasksビューを活用してタスク管理するので、todo は _to を使って名前付きで
o コードにはタグコメントを入れていく _ta...
o developにじゃんじゃんコミットしていってOK

# ----------------------------------------------------------
#                                                 MacOSX Key
#                                                 ----------
<< Mac >>
Spotlight :: alt + command + L (ランチャー)
画面をふいー :: control + 右左
画面をぶわー :: 四本指で上にふいっと
なにかと設定を開く :: command + , (カンマ)
なにかと左のタブへ :: shift + command + {
なにかと右のタブへ :: shift + command + }

# ----------------------------------------------------------
#                                                Eclipse Key
#                                                -----------
<< basic: general >>
リソースの検索 :: command + shift + R
Preferences :: command + , (カンマ)

<< basic: editor >>
一行削除 :: command + D
一行コピー :: alt + command + 下
一行移動 :: alt + 下
単語移動 :: alt + 左右

<< basic: Java >>
変数の補完 :: command + 2 => L
リファクタリング :: alt + command + R

<< Git: original key bind >>
Fetch  :: alt + shift + U
Pull   :: alt + shift + P
Commit :: alt + shift + C
Push   :: alt + shift + V
Comp   :: alt + shift + L

<< Move: original key bind >>
パッケージエクスプローラへ :: shift + command + +
いま開いているエディターへ :: shift + command + *
コンソールへ            :: shift + command + _
Git と Java を行き来する :: shift + command + @
Gitリポジトリビューをひらく :: shift + command + >
Tasksビュー(todo)をひらく => shift + command + ¥
左のタブへ :: shift + command + {
右のタブへ :: shift + command + }
