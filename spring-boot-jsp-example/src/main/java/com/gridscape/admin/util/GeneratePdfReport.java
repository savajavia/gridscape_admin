package com.gridscape.admin.util;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.gridscape.admin.model.Company;
import com.gridscape.admin.model.Employee;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

public class GeneratePdfReport {
	public static ByteArrayOutputStream generatePdfReport(Company company, List<Employee> employee, String imagePath,String pdfPath) throws Exception {              
	    
		  // Creating an ImageData object       
	      String imFile = imagePath;       
	      ImageData data = ImageDataFactory.create(imFile);              
	      ByteArrayOutputStream out = new ByteArrayOutputStream();  
	      // Creating an Image object        
	      Image image = new Image(data); 
	      
	   //   image.setAutoScale(true);
	     // image.setWidthPercent(50);
	      
	      image.scaleToFit(150F, 150F);
	      
	      // Creating a PdfWriter       
	      PdfWriter writer = new PdfWriter(out);        
	      
	      float [] pointColumnWidthsHeader = {200F, 300F}; 
	      Table headertable = new Table(pointColumnWidthsHeader); 
	      headertable.setBorder(Border.NO_BORDER);

	      Cell cell;

	      cell = new Cell(); 
	      cell.add(image); 
	      cell.setBorder(Border.NO_BORDER);
	      headertable.addCell(cell); 

	      cell = new Cell(); 
	      cell.add(new Paragraph("\n\n Company :" + company.getCompanyName() + "\n Email :" + company.getEmail())); 
	      cell.setBorder(Border.NO_BORDER);
	      headertable.addCell(cell);

	      float [] pointColumnWidths = {200F, 200F}; 
	      Table table = new Table(pointColumnWidths); 
	      table.setBorder(Border.NO_BORDER);

         cell = new Cell(); 
         cell.add(new Paragraph("Name")); 
        // cell.setBorder(Border.NO_BORDER);
         table.addCell(cell);
         
         cell = new Cell(); 
         cell.add("Username");          
         table.addCell(cell);
         
	      for (Employee emp : employee) {
	    	  cell = new Cell(); 
	          cell.add(emp.getEmployeeName()); 
	          table.addCell(cell);
	          
	          cell = new Cell(); 
	          cell.add(emp.getUsername());
	          table.addCell(cell);	          
          }
          
	      // Creating a PdfDocument       
	      PdfDocument pdf = new PdfDocument(writer);              
	      
	      // Creating a Document        
	      Document document = new Document(pdf);              
	      
	       // Adding image to the document       
	      document.add(headertable);              
	      document.add(new Paragraph("\n"));
	      //document.add(new Paragraph(company.getEmail() + "\n"));
	      document.add(new Paragraph("EMPLOYEES LIST"));
	      document.add(table);
	      // Closing the document    
	      
	      document.close();              
	      return out;
	   }
}
