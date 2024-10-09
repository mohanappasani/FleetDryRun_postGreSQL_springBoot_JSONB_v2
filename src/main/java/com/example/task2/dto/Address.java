package com.example.task2.dto;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class Address implements Serializable {
	private String postCode;
	private String city;
}