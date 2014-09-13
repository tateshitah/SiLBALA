![SiLBALA](https://github.com/tateshitah/SiLBALA/blob/master/SiLBALA.png)
======
SiLBALA: Simple Location Based AR Library for Android

Usage
------
+ copy the src folder of the package org.braincopy.silbala to your src folder.
+ create your Activity and ARView class, refering SampleMainActivity and SampleARView.

Functions
------
+ simple sample AR application for android
+ get x, y on the display of android device from azimuth and elevation information of target.
+ get x, y on the display of android device from latitude, longitude, and altitude of target.

Theory!?
------
###Coordinates

In this library, two kinds of coordinates are used.

(1) real world coordinate:

* right-handed coordinate system
* x axis is south direction,
* y axis is down direction,
* z axis is east direction,
* angle increases for clockwise for all axis.

(2) screen coordinate:

 * right-handed coordinate system</li>
 * when the camera directs east without any lean and incline, azimuth, pitch, and roll will be 0 (zero).
 * x axis is direction of moving,
 * y axis is horizontal right direction,
 * z axis is vertical down direction,
 * angle increases for clockwise for all axis. The coordinate system should be adjusted for each devices.

License
------

Copyright (c) 2013 braincopy.org

Permission is hereby granted, free of charge, to any person obtaining a copy 
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
copies of the Software, and to permit persons to whom the Software is furnished
to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
IN THE SOFTWARE.



