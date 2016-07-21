Welcome to ModalX: Generic Modal Windows for Wicket
---------------------------------------------------
Copyright (c) 2009,2011. Step Ahead Software Pty Ltd. All rights reserved.

Open Source License for Free and Commercial Applications
--------------------------------------------------------

This software is released under the Apache 2 license and can be
used in both free and commerical applications.

What is it?
-----------

ModalX stands for Modal eXtension.

ModalX is a small, elegant set of classes arranged in such a way that makes the creation
and use of ModalWindows and Modal Forms in wicket just as easy as in the good old
days of desktop development using MFC, OWL or Swing. Back in those days all you needed to
do was derive from a ModalWindow class, add your 'extra' bits then simply instantiate
the modal window and voila!

Currently with Wicket (1.4.x, 1.5.x) modal windows are not as simple as they were in the desktop days.
There's a bit of scaffolding to perform to make a modal window happen and there isn't the equivalent of a 
CDialog or TDialog that implements the convenience of the OK, Cancel button handling automatically for you.

The desktop world has the concept of a message box, implemented by a specific MessageBox in most traditional
desktop environments. ModalX brings back the ease of use of a message box which has a self sizing height option
so that the border fits snuggly around your text.

The other issue you'll know with ModalWindow's in wicket is that you have to declare the correct HTML components
in any component (or parent component) from which you might launch a ModalWindow. This can get pretty 
mechanical and boring (developers hate mechanical and boring!) especially when your app can pop up
a MessageBox from many, many different places. Typically you would assign each their own wicket:id and have
a corresponding name in your ModalWindow class.

ModalX solves this problem too by allowing you to declare a series of generic ModalWindowS elements in 
your base page's HTML which then become available to any ModalWindow in your application. This supports nested 
modal windows in a generic and very convenient way. ModalX completely generifies this so that it's 
only necessary to place 'n' numbered modal window elements in your base page HTML file. ModalX 
automatically allocates the 'next' ModalWindow everytime it opens a nested modal window. 
'n' is the maximum amount of Modal nesting that you will ever need to occur in your application 
at any time.

ModalX brings a whole lot of good ole' desktop development stuff into Wicket with reusable 
ModalContentPanel and ModalFormPanel classes that are *really* easy to use.

