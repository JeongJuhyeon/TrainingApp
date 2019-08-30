from enum import Enum
import sqlite3
import os

SQL = sqlite3.connect('../../../Android/quiz1/app/src/main/assets/quizdb2.sqlite3')
SQLCursor = SQL.cursor()


def insertItems():
    for idIngredientA in range(2, 9):
        for idIngredientB in range(idIngredientA, 9):
            print(f"idIngredientA: {idIngredientA}, idIngredientB: {idIngredientB}")
            drawable = input("Drawable: ")
            fullName = input("Full name: ")
            SQLCursor.execute("INSERT INTO items VALUES (null,?,?,0,?, ?)", (drawable,
                                                                             fullName,
                                                                             idIngredientA,
                                                                             idIngredientB))
            SQL.commit()


def insertItemCombinationQuestions():
    for r in SQLCursor.execute("SELECT MAX(_id) FROM questions"):
        nextQuestionID = r[0] + 1

    for row in SQLCursor.execute("SELECT * FROM items WHERE isIngredient=0").fetchall():
        itemId, drawable, fullName, isIngredient, idIngredientA, idIngredientB = row
        questionString = f"Which items combine into {fullName}?"
        numberOfAnswers = 1
        questionType = "textandimage"
        SQLCursor.execute("INSERT INTO questions VALUES (null,?,?,?,?)", (questionString,
                                                                         drawable,
                                                                         numberOfAnswers,
                                                                         questionType))
        SQLCursor.execute('INSERT INTO answers VALUES (null,?,1,1,null,"itemcombination",?)', (nextQuestionID,
                                                                                               itemId))
        nextQuestionID += 1

    SQL.commit()


insertItemCombinationQuestions()