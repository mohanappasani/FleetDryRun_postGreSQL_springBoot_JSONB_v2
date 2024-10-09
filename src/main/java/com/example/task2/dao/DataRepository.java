package com.example.task2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.task2.entity.DataEntity;

public interface DataRepository extends JpaRepository<DataEntity, Long> {
}
