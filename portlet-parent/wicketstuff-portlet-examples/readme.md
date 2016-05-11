# How to deploy wicketstuff portlets in Liferay 6.2

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
