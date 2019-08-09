from enum import Enum
import sqlite3
import os

SQL = sqlite3.connect('../../quiz1/app/src/main/assets/quizdb2.sqlite3')
SQLCursor = SQL.cursor()
for row in SQLCursor.execute("SELECT count(*) FROM questions"):
    nextQuestionId = row[0] + 1

if __name__ == '__main__':
    SQLCursor.execute("INSERT INTO questions VALUES (null,?,?,?,?)", )