---
title: "@PersistenceUnit annotation"
---

The samples on this page were written for Wicket 1.3/1.4 and Java EE 5. The injector registration is current; for the rest see the `javaee-inject-examples` module for working code.

**Beware**! Due to classloading limitations for entities, you cannot mix the usage of @PersistenceUnit and @EJB to persist your entities. So you have to choose only one of these approaches in your application.

1. Develop a Java EE 5 entity, i.e.
```java
@Entity
public class Contact {

  private Long id;
  private String name;
  private String email;

  public Contact() {}

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  //other setters, getters, equals and hashCode follows
}
```

2. Create a persistence.xml file, i.e.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://java.sun.com/xml/ns/persistence" version="1.0">
 <persistence-unit name="defaultPersistenceUnit" transaction-type="JTA">
 <jta-data-source>jdbc/__default</jta-data-source>
 <class>wicket.javaee.model.Contact</class>
 <properties>
 <property name="toplink.platform.class.name"
 value="oracle.toplink.essentials.platform.database.DerbyPlatform"/>
  <property name="toplink.ddl-generation" value="drop-and-create-tables"/>
  <property name="toplink.create-ddl-jdbc-file-name" value="create.jdbc"/>
  <property name="toplink.drop-ddl-jdbc-file-name" value="drop.jdbc"/>
 </properties>
 </persistence-unit>
</persistence>
```

3. Create your wicket page using the @PersistenceUnit annotation to refer to the EntityManagerFactory, i.e.
```java
public class ListContacts extends WebPage {
  @PersistenceUnit(unitName="defaultPersistenceUnit") private EntityManagerFactory emf;

  public ListContacts() {
    new ListView<Contact>(this, "contacts",getContacts()){
      protected void populateItem(final ListItem<Contact> item) {
	new Label(item, "name", new PropertyModel(item.getModel(), "name"));
	new Link(item, "delete", item.getModel()) {
	  public void onClick() {
	    EntityManager entityManager = emf.createEntityManager();
	    entityManager.getTransaction().begin();
	    Contact managed = entityManager.merge(item.getModelObject());
	    entityManager.remove(managed);
	    entityManager.getTransaction().commit();
	    entityManager.close();
	    setResponsePage(new ListContacts());
	  }
	};
      };
    }

  private List<Contact> getContacts() {
    EntityManager entityManager = emf.createEntityManager();
    List contacts = entityManager.createQuery("select c from Contact c").getResultList();
    entityManager.close();
    return contacts;
  }
}
```

4. Add the line
```java
getComponentInstantiationListeners().add(new JavaEEComponentInjector(this));
```

inside the init() method of your Wicket WebApplication, like in the example:
```java
public class WicketJavaEEApplication extends WebApplication {

   protected void init() {
      super.init();
      getComponentInstantiationListeners().add(new JavaEEComponentInjector(this));
   }
}
```

5. Package your application in a single war file

Note:

I found that Glassfish complained about getting the transaction from the entity manager. To solve this I found injecting the user transaction resource worked. This may not be the 'correct' way, but it seems to match how the JPA in a web app tutorial example. <http://java.sun.com/javaee/5/docs/tutorial/doc/bnbrm.html>
```java
public class ListContacts extends WebPage {
  @Resource(name="java:comp/env/UserTransaction")
  UserTransaction utx;

  @PersistenceUnit(unitName="defaultPersistenceUnit") private EntityManagerFactory emf;

  public ListContacts() {
    new ListView<Contact>(this, "contacts",getContacts()){
      protected void populateItem(final ListItem<Contact> item) {
	new Label(item, "name", new PropertyModel(item.getModel(), "name"));
	new Link(item, "delete", item.getModel()) {
	  public void onClick() {
	    EntityManager entityManager = emf.createEntityManager();
	    utx.begin();
	    Contact managed = entityManager.merge(item.getModelObject());
	    entityManager.remove(managed);
	    utx.commit();
	    entityManager.close();
	    setResponsePage(new ListContacts());
	  }
	};
      };
    }

  private List<Contact> getContacts() {
    EntityManager entityManager = emf.createEntityManager();
    List contacts = entityManager.createQuery("select c from Contact c").getResultList();
    entityManager.close();
    return contacts;
  }
}
```
