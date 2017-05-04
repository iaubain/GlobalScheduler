package com.oltranz.globalscheduler.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-27T10:43:55")
@StaticMetamodel(Tasks.class)
public class Tasks_ { 

    public static volatile SingularAttribute<Tasks, String> jobId;
    public static volatile SingularAttribute<Tasks, String> reportChanel;
    public static volatile SingularAttribute<Tasks, String> objectDomain;
    public static volatile SingularAttribute<Tasks, String> freq;
    public static volatile SingularAttribute<Tasks, Date> ended;
    public static volatile SingularAttribute<Tasks, String> description;
    public static volatile SingularAttribute<Tasks, Integer> position;
    public static volatile SingularAttribute<Tasks, String> taskId;
    public static volatile SingularAttribute<Tasks, Date> creation;
    public static volatile SingularAttribute<Tasks, Integer> status;

}