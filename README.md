# MyCalculator
A small, simple calculator

The calculator allows simple calculations using floating point numbers, there is no keypad provided the default phone numeric pad should open automatically.

The calculations run in series, so each time there is a need to break up a calculation a new calculation object is created, for example:

5 * (7-4) / 23

Here a second calculation object is created to solve (7-4), both of these objects are added to a list, after the brackets close a new object is created to handle the remaining items.

This allows the application to be scaled later and for more complex calculations to be added later.

The edit text used is a FloatHintEdit text by Brian Nicholson: https://github.com/thebnich/FloatingHintEditText
It is used under the  Mozilla Public License, v. 2.0.
