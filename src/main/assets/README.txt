
Tooltip Plus
=================================

(c) 2013 alalwww
https://github.com/alalwww

--------------------

Minecraft 非公式JPフォーラム
http://forum.minecraftuser.jp/viewtopic.php?t=2321

ファイル置き場 (DropBox)
http://goo.gl/aThkh


About this mod
--------------------

画面の四隅のどこかに、現在手に持っているアイテムの、名前や個数などの情報を表示します。
表示位置、文字の色、表示する内容のいくつかを、設定により変更可能です。
Minecraft 1.1 で行われた、ローカライズによるアイテム名などの日本語表示にも対応しています。

表示できる情報は以下になります。

-アイテムの名称
-アイテムのスタック数
-インベントリ内含む、総スタック数
-エンチャントの情報
-耐久度のあるアイテムの、現在耐久度と最大耐久度 (default: 非表示)
-アイテム(またはブロック)ID (default: 非表示)
-(弓を装備している場合のみ)、矢の総スタック数

----------------------------------------

設定を変えるには設定ファイルを手動で修正する必要があります。
ただし、GuiAPIが導入されている場合は、ゲーム起動中にいくつかの設定をGUIから変更できます。オススメ。

----------------------------------------
mod_Tooltip r8 (Minecraft beta 1.7.3 版) を参考にさせて頂きました。
パクリともいう。

設定変更用のGUIを作るのは何かと面倒だったので、
参考とさせていただいた mod_Tooltip r8 同様に、GuiAPIによる設定変更に対応しています。
設定画面を表示するためだけに、キーバインドを追加したくなかったので、この形にしています。


Installation
--------------------

### 必須 mod

 * MinecraftForge (もしくは FML)
    http://www.minecraftforge.net/
    http://files.minecraftforge.net/ (ダウンロード専用ページ)

 * AwA mod commons (前提Mod)
    http://goo.gl/jCtMI (alalwww作のmod共通処理)

### 推奨mod

 * GuiAPI
    http://www.minecraftforum.net/topic/612536-

------------------------------

▼Minecraft Forge 同梱FML
 http://www.minecraftforge.net/

 Minecraft Forge のAPIは利用していないため必須ではありませんが、Forgeに同梱されている FML で動作します。
  http://files.minecraftforge.net/ (ダウンロード専用ページ)
  https://github.com/MinecraftForge/MinecraftForge (GitHub)

 基本的には、プレイする Minecraft のバージョンに対応した Recommended バージョンの
 universal のJarファイルをダウンロードし、導入すればOKです。

 導入方法は
  1. minecraft.jarファイルを 7-ZIP などで開く。
  2. META-INF フォルダーを削除する。
  3. forge の Jar ファイル内にある全てのファイルとフォルダーを minecraft.jar 内にコピーする。

  ※手順が変更されている可能性もあるので Minecraft Forge のサイトや JPWiki などを参照し、正しい手順で導入してください。

 minecraft.jar に元から入っている META-INF 内の3つのファイルは全て削除しないと動作しません。
 ForgeのZIPについてくるMETA-INFフォルダーは、消しても動作しますが、消さなくても問題ありません。

▼FML単体
 http://files.minecraftforge.net/fml/ (ダウンロード専用ページ)
 https://github.com/cpw/FML (GitHub)

 Forge を使わない場合に、FML単体でのインストールも可能です。
 しかし、同じものが Minecraft Forge にも同梱されているため、特別な理由がない限りは Minecraft Forge を導入してしまうことをオススメします。
 入手するには、上記のダウンロード専用ページ から fml-universal で始まるZIPファイルをダウンロードします。

 ※より過去のものは GitHub のダウンロードページ ( https://github.com/cpw/FML/downloads/ ) にあるかもしれません。

 導入方法は Forge とおなじく、jar に最初からある META-INF フォルダーを削除し、universal のファイルを全て追加するだけです。

------------------------------

▼GuiAPI (GUIによる設定変更)
http://www.minecraftforum.net/topic/612536-

導入は必須ではありませんが、導入したほうが便利です。
ゲーム内から設定を変更できるようになります。

※Minecraft1.4.6 以降対応の GuiAPI は、FML もしくは Forge が必須です。
インストールは、coremods フォルダーにダウンロードした jar ファイルを配置するだけです。

------------------------------

[[インストール手順]]

  1. MinecraftForge もしくは FML単体を導入します。
     例に漏れず minecraft.jar に最初からある META-INF フォルダーの中身は必ず消してください。
     Forge についてくる META-INF で上書きしても、最初からあるファイルのいくつかは残ります。
     必ず手動で削除します。

     ※META-INF は class ファイルが変更されていないことを保障するための情報です。
       悪意ある改変などからも保護されなくなるということです。
       MODを導入するということは、そういうことです。意味を理解してから削除したほうが無難です。

     任意でGuiAPIも導入します。こちらは %appdata%\.minecraft\coremods フォルダーに配置するだけです。
     このフォルダーは、Forge (もしくはFML) を導入した状態で、一度 Minecraft を起動すると作成されます。

  2. ダウンロードしたJarファイルを展開せずに、%appdata%\.minecraft\mods フォルダーに配置します。

  3. Minecraftを起動します。

     設定を変更する場合は、一度 Minecraft を起動しメインメニューを表示したあと
     Minecraftを終了し、生成された設定ファイルを編集してください。

  ※過去バージョンの当MODを導入していた場合、その jar ファイルは、忘れずに削除してください。


   なお、耐久度の表示はデフォルトでは「非表示」です。
   通常では確認できない情報なので、デフォルトでは表示しない設定にしています。

   数値設定に文字など、不正な値が設定されている場合は起動に失敗します。
   数値設定で有効な範囲外の値が書かれていた場合は、デフォルト値で初期化されます。


■設定変更方法 (GuiAPIを使わない場合)
========================================
1. 一度 Tooltip Plus を導入した状態で Minecraft を起動し、終了します。
2. .minecraft/config/TooltipPlus.cfg ファイルが作成されているので、テキストエディターで開きます。
3. 変更したい設定値を変えて、保存します。

プロパティファイルです。#で始まる行はコメント行です。
自動で上書きされるため、コメントを書き足したりキーの順番を変更したりしても、情報が消える可能性があります。

値の変更は Minecraft を終了した状態で行なってください。
値の説明については下記を参照してください。


■設定変更方法 (GuiAPIを導入しており、GuiAPIによる設定を行う場合)
========================================
1. ゲームを起動し、オプション画面を開きます。
2. "Global Mod Settings" という項目が追加されているので、選択します。
3. "Tooltip plus setting" ボタンを選択します。

設定値の説明は、下記を参照してください。大体わかると思います。


■設定値の詳細

-tooltipplus.enabled = true / false
 機能の有効、無効設定。を無効にした場合画面にも表示されなくなります。
 GuiAPIを利用している際の設定であり、コンフィグからは変更する必要のない設定です。
 default true

-tooltipplus.position = 0-3
 表示位置。0～3の数値で指定します。
  0:top left
  1:top right
  2:bottom left
  3:bottom right
 default 1

-tooltipplus.update_duration = 10 - 10000
 更新頻度です。ミリ秒(ms)で指定します。
 default 100

-tooltipplus.offset.horizontal = 0 - 50
-tooltipplus.offset.vertical = 0 - 50
 描画位置のオフセットです。水平方向と垂直方向の描画位置を微調整します。
 default 2

-tooltipplus.show.arrow_count = true / false
 矢の残数表示。
 default true

-tooltipplus.show.durability = true / false
 武器やツール類の耐久度表示。
 default false

-tooltipplus.show.enchantment = true / false
 武器などのエンチャント情報の表示。
 default true

-tooltipplus.show.id = true / false
 アイテムIDとサブタイプ(メタデーターまたはダメージ値)の表示。
 defautl false

-tooltipplus.rgb.tipcolor = RRGGBB
 ツールチップの文字色です。
 3桁もしくは6桁のRGB文字列をサポートしています。
 16進数(0～F)でそれぞれの色を設定します。A～Fは大文字小文字を問いません。

 例)
  6桁の場合は各色2桁で256段階、3桁の場合は各色1桁16段階での指定になります
  FFFFFF:白、FF0000:赤、00FF00:緑、0000FF:青、FFFF00:黄、909090:グレー、000000:黒
  F00:赤、0FF:青緑 など

 先頭に # や 0x などは不要です。(つけても無視されます)
 特に # はコメント文字にも利用されており、値に含めるにはエスケープが必要になるため、つけないことをオススメします。

 default FFFFFF


■設定の初期化について
========================================
.minecraft/config/TooltipPlus.dfg を削除してください。

GuiAPIの出力する設定の保存ファイルは無視されます。
常に settings.properties で設定されている値が起動後に反映されます。


■アンインストール方法
========================================
.minecraft/mods/以下にある、このModのjarファイルを削除します。
特に残す必要がなければ、.minecraft/config/TooltipPlus.dfg や、
GuiAPIの設定ファイルフォルダーなど設定関連のファイルも削除します。

license
--------------------

This mod is distributed under the terms of the Minecraft Mod Public License 1.0, or MMPL.
Please check the contents of the license located in http://www.mod-buildcraft.com/MMPL-1.0.txt

この MOD は、Minecraft Mod Public License (MMPL) 1.0 の条件のもとに配布されています。
ライセンスの内容は次のサイトを確認してください。 http://www.mod-buildcraft.com/MMPL-1.0.txt


動画やスクリーンショット、配信内での利用はご自由にどうぞ。確認も報告も不要です。

mod pack に含めることは可能です。
連絡は不要ですが、連絡を行いたい場合は、Twitterか日本語フォーラムに投稿してください。
ただし、必ず mod pack 使用者に配布サイトが伝わるようにしてください。

ソースコードはGitHubにて公開されています。

========================================
mod_Tooltip r8 がなければこのModは作成されませんでした。
// そしてbeta1.8.1対応がされていなかったために、生まれました…！

素晴らしいModをソースつきで公開していただけたことに、この場を借りお礼を申し上げます。

