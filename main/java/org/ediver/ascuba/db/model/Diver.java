package org.ediver.ascuba.db.model;

import android.provider.BaseColumns;

//firstname = Column(Unicode(120))
//middlename = Column(Unicode(120))
//lastname = Column(Unicode(120))
//title = Column(Unicode(120))
//sex = Column(SmallInteger)
//birthdate = Column(DateTime)
//passport = Column(Unicode(120))
//address_id = Column(Integer,ForeignKey('Address.id'))
//address = relation(Address)
//contact_id = Column(Integer,ForeignKey('Contact.id'))
//contact = relation(Contact)

public class Diver implements BaseColumns{
	public final static String TABLE = "Diver";
	public final static int ID  = 0;
	public final static int FIRSTNAME  = 1;
	public final static int MIDDLENAME  = 2;
	public final static int TITLE  = 3;
	public final static int SEX  = 4;
	
	public final static int DIFFICUTY  = 5;
	public final static int VISMAX  = 6;
	public final static int VISMIN  = 7;
	public final static int MAXDEPTH  = 8;
	public final static int RATING  = 9;
	public final static int NOTES  = 10;
	
	
	public final static String [] columns = {BaseColumns._ID,"name","latitude","longitude","altitude","difficulty","visMax","visMin","maxDepth","rating","notes"};
	
    
    private int id;
    
    private Double  latitude ;
    private Double  longitude ;
    private Double  altitude ;
    private Integer  difficulty ;
    private Integer  visMax ;
    private Integer  visMin ;
    private Integer  maxDepth ;
    private Integer  rating ;
    
    
    private String  name ;
    private String  notes ;
}
