Android Gestures Tutorial
=========================

Most applications tend to only listen for a simple touch gesture, perhaps the odd app with listen for a swipe or fling... what if you had an application that in a single view had to handle (for example) all the following views.

- Tap
- Double Tap
- 2 finger Tap
- 2 finger double tap
- 3 finger tap
- Pinch
- 1 finger down, second finger tap.
- 1 finger scroll (dragging and fling)
- 2 finger scroll (dragging and fling)
- 3 finger scroll (dragging and fling)
- tap and a half (basically tap, then finger down again)

That right, what if you wanted to emulate all the gesture of a touch-pad on your android screen? Google does provide some classes that you can use but they only handle frequently used gestures. Google provides a demo on complex gesture handling but this only allows you to create a database of gestures (with just one finger) and match against it. A lot of the 2 and 3 finger taps and gestures are not catered for.

How to detect 1 finger, 2 finger and pinch scroll
=================================================

Explanation of the code on how to detect one finger, two finger and pinch can be found on my blog in the article [Android: Detecting a Pinch Gesture](https://medium.com/@ali.muzaffar/android-detecting-a-pinch-gesture-64a0a0ed4b41).