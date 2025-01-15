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

    public ByteArrayInputStream invoiceCreation(TripDetailsDTO tripDto) throws FileNotFoundException, MalformedURLException {
        // ByteArrayOutputStream for storing the PDF
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);

        // Load and scale the image
        String imagePath = "C:\\Users\\Admin\\Desktop\\PdfItext\\src\\main\\java\\truck.jpg";
        ImageData imageData = ImageDataFactory.create(imagePath);
        Image image = new Image(imageData);

        float pageWidth = pdfDocument.getDefaultPageSize().getWidth();
        float pageHeight = pdfDocument.getDefaultPageSize().getHeight();
        float scaleFactor = Math.min(pageWidth / (2 * image.getImageWidth()), pageHeight / (2 * image.getImageHeight()));

        image.scale(scaleFactor, scaleFactor);
        image.setFixedPosition((pageWidth - image.getImageScaledWidth()) / 2, (pageHeight - image.getImageScaledHeight()) / 2);
        image.setOpacity(0.1f); // Set as a watermark
        document.add(image);

        // Add Header Information
        Table headerTable = new Table(new float[]{285f, 150f});
        headerTable.addCell(new Cell().add("Invoice Report").setFontSize(20f).setBold().setBorder(Border.NO_BORDER));
        headerTable.addCell(new Cell().add("Trip ID").setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));
        document.add(headerTable);

        // Billing Information
        Table billingTable = new Table(new float[]{285f, 150f});
        billingTable.addCell(getBillingAndShippingCell("Billing Information"));
        billingTable.addCell(getBillingAndShippingCell("Shipping Information"));
        billingTable.addCell(new Cell().add("Company: "  ).setBorder(Border.NO_BORDER));
        billingTable.addCell(new Cell().add("Destination:").setBorder(Border.NO_BORDER));
        document.add(billingTable);

        // Expenses Table
        Table expensesTable = new Table(new float[]{190f, 190f, 190f});
        expensesTable.setBackgroundColor(Color.BLACK, 0.7f);

        expensesTable.addCell(new Cell().add("Description").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER));
        expensesTable.addCell(new Cell().add("Quantity").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER));
        expensesTable.addCell(new Cell().add("Amount").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER));

        document.add(expensesTable);

        Table expenseDataTable = new Table(new float[]{190f, 190f, 190f});
        List<String> expenses = List.of("WeightBridge", "BrokerAmount", "UpAmount", "DownAmount", "TollAmount", "DieselExpenses", "RtoAmount", "PcAmount", "OthersExpenses");
        for (String expense : expenses) {
            expenseDataTable.addCell(new Cell().add(expense).setBorder(Border.NO_BORDER));
            expenseDataTable.addCell(new Cell().add("1").setBorder(Border.NO_BORDER)); // Placeholder for quantity
            expenseDataTable.addCell(new Cell().add("1000").setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)); // Placeholder for amount
        }
        document.add(expenseDataTable);

        // Calculate Total
        Table totalTable = new Table(new float[]{190f, 190f, 190f});
        totalTable.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        totalTable.addCell(new Cell().add("Total").setBold().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        totalTable.addCell(new Cell().add("9000").setBold().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        document.add(totalTable);

        // Add Footer
        Table termsTable = new Table(new float[]{1f});
        termsTable.addCell(new Cell().add("Terms and Conditions").setBold().setBorder(Border.NO_BORDER));
        termsTable.addCell(new Cell().add("1. Payment must be made within 7 days of receipt.").setBorder(Border.NO_BORDER));
        termsTable.addCell(new Cell().add("2. All goods are subject to availability.").setBorder(Border.NO_BORDER));
        document.add(termsTable);

        document.close();
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    // Helper Methods
    private static Cell getBillingAndShippingCell(String text) {
        return new Cell().add(text).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
}
