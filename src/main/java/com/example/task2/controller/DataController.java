package com.example.task2.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.task2.entity.DataEntity;
import com.example.task2.service.DataService;

@RestController
@RequestMapping("/api/data")
public class DataController {

	private final DataService dataService;

	public DataController(DataService dataService) {
		this.dataService = dataService;
	}

	@PostMapping
	public ResponseEntity<DataEntity> createData(@RequestBody DataEntity dataEntity) {
		return new ResponseEntity<>(dataService.saveData(dataEntity), HttpStatus.CREATED);
	}

	@GetMapping
	public List<DataEntity> getAllData() {
		return dataService.getAllData();
	}

	@GetMapping("/export")
	public ResponseEntity<ByteArrayResource> exportData(@RequestParam String format) {
		try {
			ByteArrayResource resource = dataService.exportData(format);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data." + format)
					.body(resource);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ByteArrayResource("Error while exporting data".getBytes()));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ByteArrayResource("Unsupported format".getBytes()));
		}
	}

}
