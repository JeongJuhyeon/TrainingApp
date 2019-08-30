## Android

* _activity_: "a type of app component that provides a UI". The "main"
activity is what starts when the user taps the app icon. But if the
user opens the app from other places, e.g. a notification, you can take
them into a different activity. 

* _intent_: "An Intent is an object that provides runtime binding 
between separate components, such as two activities."

* setContentView() also imports the relevant resources. So you can't 
e.g. initialize a widget from a certain view before setting that view.

### Design

#### Menu

A list of cards, with a picture and text.

#### Questions

For "which base items make completed item" questions, we can base them on ImageClick.
There are 44 items and I reckon it would be quickest to 1. create a separate category of question
where *all* correct areas need to be clicked on (like actual spot the difference) and then 2. create them by hand using ROI selector in Python.

### Activity Life cycle
    
#### When:
    When going back to app or when starting, always onStart+onResume
    When first loading app into memory: onCreate
    When app is already in memory: onRestart
    When app is at least slightly covered (focus goes to other app): onPause

#### What:
    onCreate: initializes UI
    onStart: initializes global variables and such
    onStop: Release e.g. camera resources
    onDestroy: Go out of memory
    onPause: Used for saving permanent data

### Layout

#### General

* In the layout editor, the attributes with a wrench icon next to them
are 'tools' attributes which are only visible in the designer,
not in the release version. So if you don't see something when testing,
make sure you didn't accidentally only put the value into the tools
attribute. 

* You can change app-wide colours in values/colors.xml. Especially
'colorAccent'.

* If multiple elements have "match_parent", internal order applies.

#### ConstraintLayout

* When using ConstraintLayout, you normally want views' widths to be 
set to "match constraint".

* If you have something that might change size, e.g. a textview w/
dynamic text, you probably want "match constraint". If it doesn't,
e.g. most buttons, you might want to set height and width to it using dp,
if you e.g. need to resize, but try to avoid using it. 

#### Other Layouts

* _layout_weight_: The higher the value, the more important. Giving
layout A '3' and layout B '7' means layout A will take up 3/10's
and layout B will take up 7/10's of their common parent layout.

* If you use a horizontal(vertical) layout with _layout_weight_, you 
can't set _layout_width(layout_height)_ to _wrap_content_ as it collides.

### Database

* Put the DB file directly in app/assets (Physical location: app/src/main/assets)


## Java


   * 
        ```
        int[] x = {10, 20, 30};
        int[] y = x;
        ```
        Assigns by pointer, x and y are the same array.