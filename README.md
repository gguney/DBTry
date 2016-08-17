DBTry

Trying to code ORMish structure for Android (SQLite) written in Java.

Currently it takes a model and its columns + types and create table for it. Also, it adds default fields like create, edit time etc. 
I'm trying on "vehicle" class which extends "model" class.
You can extend model class and find element by id like in Laravel. You can generate create statement, insert an object.
Model class also convert ContentValues to proper object and vice versa.