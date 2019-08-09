from enum import Enum
import sqlite3
import os

SQL = sqlite3.connect('../../quiz1/app/src/main/assets/quizdb2.sqlite3')
SQLCursor = SQL.cursor()
for row in SQLCursor.execute("SELECT count(*) FROM questions"):
    nextQuestionID = row[0] + 1


class QA:
    def __init__(self, question, AnswerType):
        super().__init__()
        self.AnswerType = AnswerType
        self.question = question
        self.answers = []

    def insert(self):
        global nextQuestionID
        self.insertQuestion()
        self.insertAnswers()
        SQL.commit()
        nextQuestionID += 1


    def insertQuestion(self):
        self.question.setId(nextQuestionID)
        if not self.question.QuestionImage and not self.question.QuestionString:
            print("insertQuestion error: first use defineQuestion")
        # _id, QuestionString, QuestionImage, NumberOfAnswers, QuestionType
        SQLCursor.execute("INSERT INTO questions VALUES (null,?,?,?,?)", (self.question.QuestionString,
                                                                          self.question.QuestionImage,
                                                                          len(self.answers),
                                                                          self.question.QuestionType))
    def insertAnswers(self):
        for answer in self.answers:
            answer.setQuestionID(nextQuestionID)
        # _id, QuestionID, AnswerNo, Correct, AnswerString, AnswerType
        answerTuples = list(map(lambda a: (a.QuestionID, a.AnswerNo, a.Correct, a.AnswerString, a.AnswerType), self.answers))
        SQLCursor.executemany("INSERT INTO answers VALUES (null,?,?,?,?,?)", answerTuples)

    def setAnswers(self):
        if self.AnswerType == "mc":
            self.answers = self.setMCAnswers()
        elif self.AnswerType == "keyboard":
            self.answers = self.setKeyboardAnswers()
        elif self.AnswerType == "anki":
            self.answers.append(Answer("anki"))
            self.answers[-1].setAnswerNo(1)
            self.answers[-1].setCorrect(1)
        elif self.AnswerType == "clickimage":
            # TODO
            print("TODO..")

    def setKeyboardAnswers(self):
        noOfAnswers = int(input("Number of possible string answers: "))
        answers = []
        for i in range(0, noOfAnswers):
            answers.append(Answer("keyboard"))
            answers[-1].setAnswerNo(i + 1)
            answers[-1].setAnswerString(input("Answer: "))
            answers[-1].setCorrect(1)
        return answers

    def setMCAnswers(self):
        noOfAnswers = int(input("Number of mc answers: "))
        answers = []
        for i in range(1, noOfAnswers + 1):
            nextAnswer = Answer("mc")
            nextAnswer.setAnswerNo(i)
            nextAnswer.setAnswerString(input("Answer: "))
            answers.append(nextAnswer)
        correctAnswer = int(input(f"Correct answer? 1-{noOfAnswers}: ")) - 1
        for i in range(noOfAnswers):
            answers[i].setCorrect(int(i == correctAnswer))
        return answers


class Question:
    def __init__(self):
        super().__init__()
        self.QuestionImage = None
        self.QuestionString = None
        self.QuestionType = getQuestionType()
        self.NumberOfAnswers = 0
        # Python does not have overloading..

    # def __init__(self, QuestionType):
    #     self.QuestionImage = ""
    #     self.QuestionString = ""
    #     self.QuestionType = QuestionType

    def setQuestion(self):
        if self.QuestionType in ("clickimage", "textandimage"):
            self.QuestionImage = input("Enter image name: ")
        if self.QuestionType in ("textonly", "textandimage"):
            self.QuestionString = input("Enter question text: ")

    def setId(self, questionId):
        self._id = questionId


class Answer:
    def __init__(self, AnswerType):
        self.AnswerType = AnswerType
        self.AnswerNo = None
        self.QuestionID = None
        self.AnswerString = None
        self.Correct = None

    def setAnswerNo(self, AnswerNo):
        self.AnswerNo = AnswerNo

    def setQuestionID(self, QuestionID):
        self.QuestionID = QuestionID

    def setAnswerString(self, AnswerString):
        self.AnswerString = AnswerString

    def setCorrect(self, Correct):
        self.Correct = Correct


def getAnswerType():
    atype = int(
        input("Answer type:\n(1) multiple choice buttons \n (2) keyboard \n (3) click image \n (4) Anki style\n"))
    if atype == 1:
        atype = "mc"
    elif atype == 2:
        atype = "keyboard"
    elif atype == 3:
        atype = "clickimage"
    elif atype == 4:
        atype = "anki"
    else:
        print("Unrecognized answer")
        raise ValueError

    return atype


def getQuestionType():
    qtype = int(input("Question type:\n(1) text + image\n(2) text \n(3) click image\n"))
    if qtype == 1:
        qtype = "clickimage"
    elif qtype == 2:
        qtype = "textonly"
    elif qtype == 3:
        qtype = "textandimage"
    else:
        print("Unrecognized answer")
        raise ValueError

    return qtype


def generateNewQA():
    question = Question()
    question.setQuestion()
    qa = QA(question, getAnswerType())
    qa.setAnswers()
    return qa


if __name__ == '__main__':
    for _ in range(1):
        qa = generateNewQA()
        qa.insert()
