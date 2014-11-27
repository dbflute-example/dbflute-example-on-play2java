
# ========================================================================================
#                                                                                 Overview
#                                                                                 ========
Play2 (Java) と DBFlute を組み合わせた Example プロジェクトです。

o play2のバージョンは2.3.6です。
o DIContainer として Google Guice を使用しています。
o Play本体のDBまわりの機構(BoneCP, Ebean)は使用していません。


# ========================================================================================
#                                                                              Environment
#                                                                              ===========
# ----------------------------------------------------------
#                                         Compile & Database
#                                         ------------------
Eclipse の場合は、.classpath が gitignore されているので、
activator にて、eclipse with-source=true を叩く必要があります。

データベースは、MySQLを利用しているので、特に準備は不要です。


# ----------------------------------------------------------
#                                                 Setup Flow
#                                                 ----------
1. etc/tools/の下に Play を配置

Play のバージョンは 2.2.2 です。

http://downloads.typesafe.com/play/2.2.2/play-2.2.2.zip
をダウンロードして、以下のように配置してください。

dbflute-play-java-example
 |-dbflute_exampledb
 |-etc
 |  |-license
 |  |-tools
 |  |  |-play // *here
 |  |  |  |-framework
 |  |  |  |-repository
 |  |  |  |-samples
 |  |  |  |-play
 |  |  |  |-...
 |  |  |
 |  |  |-sbt // (sbtを利用するなら)
 |  |  |  |-sbt-launch.jar
 |  |  |
 |-mydbflute
 |-script
 |  |-play.sh // すると、この sh が使えるようになる
 |  |-sbt.sh  // (sbt-launch.jarがあれば使えるようになる)
 |-...

すると、play.sh を使って play コマンドが叩けるようになります。
playディレクトリは、gitignoreになっているのでコミット対象にはなりません。

※もちろん、ローカルPCの任意の場所に Play をインストールして、
pathを通すやり方でも構いません。

e.g.
  $ export PLAY_HOME=${HOME}/java/play-2.2.2
  $ PATH=${PLAY_HOME}:${PATH}
  $ export PATH

※また、sbtを利用する場合は、tools配下に sbt/sbt-launch.jar を置けば、
sbt.sh が使って sbt コマンドが叩けるようになります。


2. まずは画面を起動してみましょう

play.sh を叩くと、playと対話モードになります。
そこで、run というコマンドを入力して実行してください。

/- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
       _
 _ __ | | __ _ _  _
| '_ \| |/ _' | || |
|  __/|_|\____|\__ /
|_|            |__/

play 2.2.2 built with Scala 2.10.3 (running Java 1.7.0_25), http://www.playframework.com

> Type "help play" or "license" for more information.
> Type "exit" or use Ctrl+D to leave this console.

[dbflute-play-java-example] $ run

--- (Running the application from SBT, auto-reloading is enabled) ---

[info] play - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

(Server started, use Ctrl+D to stop and go back to the console...)
- - - - - - - - - -/

「Server started」が表示されたらブラウザにて、

http://localhost:9000/

にアクセスしてみてください。会員一覧画面が表示されます。
(初回はコンパイルが走るので何十秒か時間が掛かります)


3. Eclipse 上でコンパイルできるように (.classpath)

playコマンドで .classpath を生成します。

play.sh を叩いて play の対話モードにして、
「eclipse with-source=true」というコマンドを入力して実行してください。

/- - - - - - - - - - - - - - - - - - - - - - - - - - - - -
[dbflute-play-java-example] $ eclipse with-source=true
- - - - - - - - - -/

すると、.classpathが自動生成され、Eclipse上でコンパイルできるようになります。
(Eclipse上で F5 をしましょう)

もし、routesクラスでコンパイルエラーになるようであれば、
(手動修正で) .classpath に target/scala-2.10/classes に対する path を追加してください。

path="[...]/dbflute-play/dbflute-play-java-example/target/scala-2.10/classes"

既に追加されているはずの classes_managed の行をコピーして、classes_managed を classes に修正でもOKです。


4. 必要に応じて、Scala IDE プラグインをインストール

// Scala IDE トップページ
http://scala-ide.org/

から、
利用している Eclipse のバージョンに対応した update site のURLを探し、
(変わってなければこのページ: http://scala-ide.org/download/current.html)

Eclipse の update site に追加して、以下の二つのプラグインをインストールする。

- Scala IDE for Eclipse
- Play2 support in Scala IDE

