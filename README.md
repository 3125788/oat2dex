# oat2dex
[Android] Extract Dalvik DEX files from ART ELF/OAT/ODEX file

After Android 5.0L, the installed apk will be odexed to ELF file.

This ELF file cannot be reversed to smali by baksmali tool.

We can use this oat2dex tool to extract dex file from ELF file first, then everything goes on.
