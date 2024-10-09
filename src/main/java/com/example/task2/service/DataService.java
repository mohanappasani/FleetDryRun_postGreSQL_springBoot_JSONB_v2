package com.example.task2.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.example.task2.dao.DataRepository;
import com.example.task2.entity.DataEntity;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Service
public class DataService {

	private final DataRepository dataRepository;

	public DataService(DataRepository dataRepository) {
		this.dataRepository = dataRepository;
	}

	public DataEntity saveData(DataEntity data) {
		return dataRepository.save(data);
	}

	public List<DataEntity> getAllData() {
		return dataRepository.findAll();
	}

	public ByteArrayResource exportData(String format) throws IOException {

		/* fetching all the data from the database */
		List<DataEntity> dataEntities = dataRepository.findAll();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		String filename = "data." + format.toLowerCase();

		// Update file path to save in the project's exports directory
		String projectPath = System.getProperty("user.dir");

		String filePath = projectPath + File.separator + "exports" + File.separator + filename;

		System.out.println("The file will be saved in this location - " + filePath);

		switch (format.toLowerCase()) {
		case "xlsx":
			exportToExcel(dataEntities, outputStream);
			break;
		case "pdf":
			exportToPdf(dataEntities, outputStream);
			break;
		case "txt":
			exportToText(dataEntities, outputStream);
			break;
		default:
			throw new IllegalArgumentException("Unsupported format: " + format);
		}

		File exportDir = new File(projectPath + File.separator + "exports");
		if (!exportDir.exists()) {
			System.out.println("Directory doesn't exist, creating the directory");
			exportDir.mkdirs(); 
		}else {
			System.out.println("Directory exist, creating the file");
		}

		// Save the file locally
		try (FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath))) {
			outputStream.writeTo(fileOutputStream);
		}

		return new ByteArrayResource(outputStream.toByteArray());
	}

	private void exportToExcel(List<DataEntity> dataEntities, ByteArrayOutputStream outputStream) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Students");

		// Create header row
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("ID");
		headerRow.createCell(1).setCellValue("Admit Year");
		headerRow.createCell(2).setCellValue("Post Code");
		headerRow.createCell(3).setCellValue("City");

		// Populate the sheet with data
		int rowNum = 1;
		for (DataEntity entity : dataEntities) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(entity.getId());
			row.createCell(1).setCellValue(entity.getAdmitYear());
			row.createCell(2).setCellValue(entity.getAddress().getPostCode());
			row.createCell(3).setCellValue(entity.getAddress().getCity());
		}

		workbook.write(outputStream);
		workbook.close();
	}

	private void exportToPdf(List<DataEntity> dataEntities, ByteArrayOutputStream outputStream) throws IOException {
		PdfWriter writer = new PdfWriter(outputStream);
		PdfDocument pdfDoc = new PdfDocument(writer);
		Document document = new Document(pdfDoc);

		for (DataEntity entity : dataEntities) {
			document.add(new Paragraph("ID: " + entity.getId()));
			document.add(new Paragraph("Admit Year: " + entity.getAdmitYear()));
			document.add(new Paragraph("Post Code: " + entity.getAddress().getPostCode()));
			document.add(new Paragraph("City: " + entity.getAddress().getCity()));
			document.add(new Paragraph("")); // Empty line
		}

		document.close();
	}

	private void exportToText(List<DataEntity> dataEntities, ByteArrayOutputStream outputStream) {
		StringBuilder sb = new StringBuilder();

		for (DataEntity entity : dataEntities) {
			sb.append("ID: ").append(entity.getId()).append("\n");
			sb.append("Admit Year: ").append(entity.getAdmitYear()).append("\n");
			sb.append("Post Code: ").append(entity.getAddress().getPostCode()).append("\n");
			sb.append("City: ").append(entity.getAddress().getCity()).append("\n");
			sb.append("\n"); // Empty line
		}

		try {
			outputStream.write(sb.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
