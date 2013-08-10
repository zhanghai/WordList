#WordList

![WordList](https://raw.github.com/DreaminginCodeZH/WordList/master/res/drawable-xxhdpi/ic_launcher.png)

##Introduction

A simple android application to read and display word lists in a
format I created.

##Author

Zhang Hai

##The word list format

A wordlist primarily consists of its name (the first line) and entries
with annotations indicating the relationship between these entries to
form a tree structure. The annotations are `^` (similar word to
distinguish), `~` (synonym), `@` (word usage), `#` (Derived words),
and empty for a root element/word.

Here is an short example of this format:

    Words[10]
    Census n.人口调查
    ^Consensus adj.意见一致
    Captivate v.迷惑
    #Captivating adj.
    #Captivation n.迷惑;魅力
    #Captive adj.被监禁的 n.俘虏
    #Captivity n.监禁
    #^Capability n.能力;容量
    Concur v.同意;同时发生
    #Concurrent adj.
    #Concurrently adv.
    #Concurrence n.
    Pervade v.弥漫的
    #Pervasive adj.弥漫的;普遍的
    #~Prevalent adj.普遍的
    #~#Prevalence n.普及;盛行

Some word list files I authored can be found in the .`WordList`
directory.

##How to use

Simply install the app and place the word list files under
`\mnt\sdcard\WordList\`

##About the code

This is my second android application so it's simple enough without
much UI design. (The first app? Of course it's HelloAndroid :) )

Simple though it is, there are also some interesting (at least for
myself) pieces of code inside this app, namely
`ExpandableListFragment` and `WrapContentExpandableList`. The first
one gives an implementation of the corresponding class that does not
exist in android support library; the second one makes the list
correctly calculate its size using its own method when `wrap_content`
is specified (it won't be very efficient though).

It seems that the android library developers doesn't like the
`ExpandableListView` very much for performance reasons...

##License

    Copyright 2013 Zhang Hai
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
    http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an "AS
    IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
    express or implied.  See the License for the specific language
    governing permissions and limitations under the License.
