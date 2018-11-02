package org.ediver.ascuba.db.model;

import android.provider.BaseColumns;

public class DiveSite implements BaseColumns{
	public final static String TABLE = "DiveSite";
	public final static int ID  = 0;
	public final static int NAME  = 1;
	public final static int LATITUDE  = 2;
	public final static int LONGITUDE  = 3;
	public final static int ALTITUDE  = 4;
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
