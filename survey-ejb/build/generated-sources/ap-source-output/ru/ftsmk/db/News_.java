package ru.ftsmk.db;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(News.class)
public abstract class News_ {

	public static volatile SingularAttribute<News, String> name;
	public static volatile SingularAttribute<News, String> description;
	public static volatile SingularAttribute<News, Integer> id;
	public static volatile SingularAttribute<News, Date> startdate;
	public static volatile SingularAttribute<News, Date> stopdate;
	public static volatile SingularAttribute<News, Date> adddate;

}

