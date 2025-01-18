package com.hariSolution.invoiceGenerator;

import com.hariSolution.DTOs.TripDetailsDTO;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;

@Service
public class InvoiceService {

    // This method generates a PDF invoice for a given trip DTO (Data Transfer Object)
    public ByteArrayInputStream invoiceCreation(TripDetailsDTO tripDto) throws FileNotFoundException, MalformedURLException {

        // Create a ByteArrayOutputStream to hold the generated PDF content
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Initialize PdfWriter and PdfDocument to create the PDF document
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4); // Set page size to A4
        Document document = new Document(pdfDocument); // Initialize Document object for writing content

        // Load the company logo image (used as a watermark)
        String imagePath = "C:\\Users\\Admin\\Desktop\\PdfItext\\src\\main\\java\\truck.jpg"; // File path of the image
        ImageData imageData = ImageDataFactory.create(imagePath); // Create image data from the image file
        Image image = new Image(imageData); // Create Image object

        // Scale and position the image (e.g., centering it as a watermark)
        float pageWidth = pdfDocument.getDefaultPageSize().getWidth();
        float pageHeight = pdfDocument.getDefaultPageSize().getHeight();
        float scaleFactor = Math.min(pageWidth / (2 * image.getImageWidth()), pageHeight / (2 * image.getImageHeight())); // Scale the image

        image.scale(scaleFactor, scaleFactor); // Apply scaling to the image
        image.setFixedPosition((pageWidth - image.getImageScaledWidth()) / 2, (pageHeight - image.getImageScaledHeight()) / 2); // Position the image at the center
        image.setOpacity(0.1f); // Set opacity for the watermark effect (transparent)
        document.add(image); // Add the image to the document

        // Add Header Information Table (Invoice Report and Trip ID)
        Table headerTable = new Table(new float[]{285f, 150f}); // Create a table with two columns
        headerTable.addCell(new Cell().add("Invoice Report").setFontSize(20f).setBold().setBorder(Border.NO_BORDER)); // Add header text
        headerTable.addCell(new Cell().add("Trip ID").setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT)); // Add Trip ID placeholder
        document.add(headerTable); // Add the header table to the document

        // Add Billing and Shipping Information Table
        Table billingTable = new Table(new float[]{285f, 150f}); // Create a table with two columns
        billingTable.addCell(getBillingAndShippingCell("Billing Information")); // Add Billing Information label
        billingTable.addCell(getBillingAndShippingCell("Shipping Information")); // Add Shipping Information label
        billingTable.addCell(new Cell().add("Company: ").setBorder(Border.NO_BORDER)); // Add company placeholder
        billingTable.addCell(new Cell().add("Destination:").setBorder(Border.NO_BORDER)); // Add destination placeholder
        document.add(billingTable); // Add billing table to document

        // Expenses Table header
        Table expensesTable = new Table(new float[]{190f, 190f, 190f}); // Create a table with three columns for expenses
        expensesTable.setBackgroundColor(Color.BLACK, 0.7f); // Set background color for the table header
        expensesTable.addCell(new Cell().add("Description").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER)); // Column header for Description
        expensesTable.addCell(new Cell().add("Quantity").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER)); // Column header for Quantity
        expensesTable.addCell(new Cell().add("Amount").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER)); // Column header for Amount
        document.add(expensesTable); // Add the expenses header table to the document

        // Expenses Data Table (List of expenses)
        Table expenseDataTable = new Table(new float[]{190f, 190f, 190f}); // Create another table for expense data
        List<String> expenses = List.of("WeightBridge", "BrokerAmount", "UpAmount", "DownAmount", "TollAmount", "DieselExpenses", "RtoAmount", "PcAmount", "OthersExpenses"); // List of expense descriptions
        for (String expense : expenses) {
            expenseDataTable.addCell(new Cell().add(expense).setBorder(Border.NO_BORDER)); // Add expense description
            expenseDataTable.addCell(new Cell().add("1").setBorder(Border.NO_BORDER)); // Add placeholder for quantity
            expenseDataTable.addCell(new Cell().add("1000").setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)); // Add placeholder for amount (set to 1000)
        }
        document.add(expenseDataTable); // Add the expense data table to the document

        // Calculate and add the Total Amount Table
        Table totalTable = new Table(new float[]{190f, 190f, 190f});
        totalTable.addCell(new Cell().add("").setBorder(Border.NO_BORDER)); // Placeholder cell for empty space
        totalTable.addCell(new Cell().add("Total").setBold().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)); // Total label
        totalTable.addCell(new Cell().add("9000").setBold().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)); // Total amount placeholder (9000)
        document.add(totalTable); // Add the total table to the document

        // Add Terms and Conditions Table
        Table termsTable = new Table(new float[]{1f}); // Table with a single column
        termsTable.addCell(new Cell().add("Terms and Conditions").setBold().setBorder(Border.NO_BORDER)); // Add "Terms and Conditions" header
        termsTable.addCell(new Cell().add("1. Payment must be made within 7 days of receipt.").setBorder(Border.NO_BORDER)); // Payment terms
        termsTable.addCell(new Cell().add("2. All goods are subject to availability.").setBorder(Border.NO_BORDER)); // Availability terms
        document.add(termsTable); // Add terms table to the document

        document.close(); // Finalize and close the document

        // Return the generated PDF as a ByteArrayInputStream
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    // Helper method to create a cell for billing and shipping info (with bold text and no border)
    private static Cell getBillingAndShippingCell(String text) {
        return new Cell().add(text).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
}
