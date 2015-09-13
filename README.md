# oat2dex
[Android] Extract Dalvik DEX files from ART ELF/OAT/ODEX file

After Android 5.0L, the installed apk will be odexed to ELF file.

This ELF file cannot be reversed to smali by baksmali tool.

We can use this oat2dex tool to extract dex files from ELF file first, then everything goes on.

For example, run: java -jar oat2dex.jar classes.odex

Then classes.dex will be saved in same folder.

One odex file maybe contains several dex files. They will be named as classes.dex, classes-2.dex, classes-3.dex, etc.
