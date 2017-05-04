package com.oltranz.globalscheduler.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-27T10:43:55")
@StaticMetamodel(Jobs.class)
public class Jobs_ { 

    public static volatile SingularAttribute<Jobs, String> jobId;
    public static volatile SingularAttribute<Jobs, String> reportChanel;
    public static volatile SingularAttribute<Jobs, String> freq;
    public static volatile SingularAttribute<Jobs, String> objectDomain;
    public static volatile SingularAttribute<Jobs, String> description;
    public static volatile SingularAttribute<Jobs, String> conractId;
    public static volatile SingularAttribute<Jobs, Date> expiration;
    public static volatile SingularAttribute<Jobs, String> id;
    public static volatile SingularAttribute<Jobs, Boolean> isChained;
    public static volatile SingularAttribute<Jobs, String> timeFrame;
    public static volatile SingularAttribute<Jobs, Date> creation;
    public static volatile SingularAttribute<Jobs, Integer> status;

}