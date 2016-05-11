# How to deploy wicketstuff portlets in Websphere portal v8.5

Build wicketstuff-portlet-examples with maven to produce its war file.

> cd core/portlet-parent/wicketstuff-portlet-examples

> mvn install -P websphere

Login on websphere portal as administrator

![w1](https://github.com/wicketstuff/core/blob/master/portlet-parent/img/w1.png)

Go to Portlet management and click on "Web Modules"

![w2](https://github.com/wicketstuff/core/blob/master/portlet-parent/img/w2.png)


Click on "install" button

![w3](https://github.com/wicketstuff/core/blob/master/portlet-parent/img/w3.png)


Select the wicketstuff-portlet-examples-7.3.0-SNAPSHOT.war.

It is in the core/portlet-parent/wicketstuff-portlet-examples/target folder.

After war selection click on the "next" button.

![w4](https://github.com/wicketstuff/core/blob/master/portlet-parent/img/w4.png)


In the bottom of the confirmation page select the "start application" option and click on the "finish" button.

![w5](https://github.com/wicketstuff/core/blob/master/portlet-parent/img/w5.png)


After successful installation you will be informed by a message.

![w6](https://github.com/wicketstuff/core/blob/master/portlet-parent/img/w6.png) 


Now you are ready to put the wicketstuff portlets in your page!

![w6](https://github.com/wicketstuff/core/blob/master/portlet-parent/img/w7.png) 


# How to deploy wicketstuff portlets in Liferay portal v6.2

Download liferay 6.2 community edition ga6 from 

https://www.liferay.com/downloads/liferay-portal/available-releases

![1](https://github.com/wicketstuff/core/blob/master/portlet-parent/img/1.png)

Build wicketstuff-portlet-examples with maven to produce its war file.

> cd core/portlet-parent/wicketstuff-portlet-examples

> mvn install -P liferay

Start liferay portal and login as administrator. Default credentials are test@liferay.com/test.


Go to Control Panel
![2](https://github.com/wicketstuff/core/blob/master/portlet-parent/img/2.png)


Click on App Manager under Apps section
![3](https://github.com/wicketstuff/core/blob/master/portlet-parent/img/3.png)


Install the wicketstuff-portlet-examples-7.3.0-SNAPSHOT.war.

It is in the core/portlet-parent/wicketstuff-portlet-examples/target folder.

![4](https://github.com/wicketstuff/core/blob/master/portlet-parent/img/4.png)


After you have installed successfully the examples, you can add them on your page. 
![5](https://github.com/wicketstuff/core/blob/master/portlet-parent/img/6.png)
