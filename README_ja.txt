
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
#                                                Bad KnowHow
#                                                -----------
<< とにかく >>
EclipseのコンパイルとPlay2のコンパイルという二つの概念があることを理解する。

　Eclipseのコンパイル :: Javaだけコンパイル
　Play2のコンパイル :: JavaとScalaHTMLテンプレートのコンパイル

Play2のコンパイルは、activator で compile を実行するか、
runの状態で何かしらコードに変更を加えて画面にアクセスすると発生する。

Play2のrunで実行されるのは、あくまでPlay2のコンパイル結果である。
Eclipseのコンパイルは、単なるコードを書くときに補完ができるようにするためのものと考えてよい。

<< ScalaHTMLテンプレートの作成 >>
ScalaHTMLテンプレートのコンパイル結果をJavaのクラスが参照するので、Eclipseのコンパイルだけでは足りない。
つまり、HTMLテンプレートを新たに作成した場合は、Play2のコンパイルを走らせないとEclipse上で補完ができない。
なので、以下のような手順となる。

1. ScalaHTMLテンプレートを作成
2. activator で compile
3. EclipseでF5する
4. すると、補完できるようになる

※run状態であれば、何かしら画面にアクセスすれば、2と3が実行される (3はこのプロジェクトでそういう仕組みにしている)

<< HTMLテンプレートの引数の変更 >>
ScalaHTMLテンプレートで引数を増やしたり型を変えたり変更しても、
Eclipseがすぐにそれを検知して補完できるわけではなく、render()メソッドは変更前の状態である。

Play2のコンパイルをしないと、その変更が Eclipse 上の render() に反映されないが、
そのままコンパイルしても、Controller の render() 呼び出し部分でコンパイルエラーになってしまい、
ScalaHTMLテンプレートのコンパイル結果が反映されない。
「いやいや、HTMLの方ができあがってからEclipseでControllerを直したいんだけど」という気持ちは置いておいて、
Eclipse側でコンパイルエラーのまま、render()の呼び出し部分をつじつま合わせた状態でPlay2のコンパイルをする必要がある。

1. ScalaHTMLテンプレートで引数の変更
2. Eclipse で render() 呼び出し部分でつじつま合わせる ※引数の数合わせて、とりあえず null を突っ込むでもOK
3. activator で compile ※run状態なら画面アクセスでOK
4. EclipseでF5する ※run状態なら画面アクセスでOK
5. すると、補完できるようになる

これでいけるはずだが、なんだかうまくいかないなぁと思ったら、
activator で clean をして、Eclipse で F5 してビルドエラーにしてから compile して Eclipse で F5 すると、
うまくいくかもしれない。。。(Play2の中でキャッシュが残ってしまっているのかも)


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
