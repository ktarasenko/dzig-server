package com.dzig.model;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;



@Entity
public class Coordinate {
 @Id private String id;
 private double lat;
 private double lon;
 private double accuracy;
 private Date  date;
 private String creatorId;
 
 public Coordinate() {
}
 

}
