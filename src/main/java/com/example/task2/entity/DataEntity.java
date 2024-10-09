package com.example.task2.entity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.example.task2.dto.Address;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_table_jsonb")
@Data // This is from Lombok to generate getters and setters
@NoArgsConstructor
@AllArgsConstructor
public class DataEntity {

	@Id
    @Column(name = "student_id", length = 8)
    private String id;

    @Column(name = "admit_year", length = 4)
    private String admitYear;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "address", columnDefinition = "jsonb")
    private Address address;
}
