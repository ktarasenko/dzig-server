package com.dzig.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;



@Entity
public class Coordinate {
 @Id 
 @GeneratedValue(strategy = GenerationType.IDENTITY) 
 private Long id;
 private double lat;
 private double lon;
 private double accuracy;
 private Date  date;
 private String creatorId;
 
 public Coordinate() {
	 id =1l;
}
 

}
