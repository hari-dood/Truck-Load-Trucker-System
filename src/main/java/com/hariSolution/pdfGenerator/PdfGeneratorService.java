package com.hariSolution.pdfGenerator;

import com.hariSolution.model.TripDetails;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Component
public class PdfGeneratorService {

    // Method to convert a list of TripDetails to a PDF document
    public ByteArrayInputStream DataToPdf(List<TripDetails> tripDetails) {
        // Create a document of A3 size
        Document document = new Document(PageSize.A3);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            // Initialize the PdfWriter to write the PDF content to the byte output stream
            PdfWriter.getInstance(document, byteArrayOutputStream);
            // Open the document to start adding content
            document.open();

            // Title Table creation
            PdfPTable titleTable = new PdfPTable(1); // Table with 1 column
            titleTable.setWidthPercentage(100); // Table width to cover entire page width

            PdfPCell titleCell = new PdfPCell();
            titleCell.setBorderWidth(2); // Set border width for the title cell
            titleCell.setPadding(10); // Padding around the title text
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER); // Align title in the center
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE); // Align title in the middle vertically

            // Create title font and add the title text
            Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 22);
            Paragraph title = new Paragraph("Trips Information", titleFont);
            title.setAlignment(Element.ALIGN_CENTER); // Center align title
            titleCell.addElement(title);
            titleTable.addCell(titleCell);

            // Add title table to the document
            document.add(titleTable);
            // Add some space after title
            document.add(Chunk.NEWLINE);

            // Data Table creation (for displaying trip details)
            PdfPTable table = new PdfPTable(28); // 28 columns (adjust based on the data you need to display)
            table.setWidthPercentage(100); // Table width to cover entire page width
            table.setHorizontalAlignment(Element.ALIGN_CENTER); // Center-align table content

            // Define column widths (adjusted for better readability)
            float[] columnWidths = new float[] { 1.5f, 3.5f, 2.5f, 4.0f, 4.0f, 4.f, 4.0f, 2f, 2f, 1.5f, 2f, 1.5f, 1.5f,
                    1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 2f, 1.5f, 1.5f,
                    1.5f, 1.5f}; // Adjusted column widths for better readability
            table.setWidths(columnWidths);

            // Add header row for the table, specifying column names
            Stream.of("id", "driverName", "month", "loadingDate", "tripStartDate", "tripEndDate", "paymentStatus",
                            "loadingPoint", "deliveryPoint", "loadQuantity", "goodsDescription", "loadAmount", "loadAdvance",
                            "weightBridge", "brokerAmount", "upAmount", "downAmount", "tollAmount", "dieselExpenses",
                            "rtoAmount", "pcAmount", "othersExpenses", "totalExpenses", "clientReqId",
                            "createdDate", "createdBy", "modifiedDate", "modifiedBy")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        Font headerFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 8); // Font for header
                        header.setMinimumHeight(15); // Set minimum height for cells
                        header.setBackgroundColor(Color.cyan); // Set background color for header row
                        header.setVerticalAlignment(Element.ALIGN_CENTER); // Align header vertically
                        header.setHorizontalAlignment(Element.ALIGN_CENTER); // Align header horizontally
                        header.setBorderWidth(1); // Set border width for header cells
                        header.setPhrase(new Phrase(headerTitle, headerFont)); // Set the header text
                        table.addCell(header); // Add header to the table
                    });

            // Create a DateTimeFormatter to format date fields into a "yyyy-MM-dd" format
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Add the data rows to the table
            for (TripDetails trip : tripDetails) {
                Font rowFont = FontFactory.getFont(FontFactory.TIMES, 8); // Font for the data rows

                // Adding data for each trip in respective columns
                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getId()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(trip.getDriverName(), rowFont)));
                table.addCell(new PdfPCell(new Phrase(trip.getMonth(), rowFont)));

                // Formatting date fields
                table.addCell(new PdfPCell(new Phrase(formatDate(trip.getLoadingDate(), dateFormatter), rowFont)));
                table.addCell(new PdfPCell(new Phrase(formatDate(trip.getTripStartDate(), dateFormatter), rowFont)));
                table.addCell(new PdfPCell(new Phrase(formatDate(trip.getTripEndDate(), dateFormatter), rowFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getPaymentStatus()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(trip.getLoadingPoint(), rowFont)));
                table.addCell(new PdfPCell(new Phrase(trip.getDeliveryPoint(), rowFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getLoadQuantity()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(trip.getGoodsDescription(), rowFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getLoadAmount()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getLoadAdvance()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getWeightBridge()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getBrokerAmount()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getUpAmount()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getDownAmount()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getTollAmount()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getDieselExpenses()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getRtoAmount()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getPcAmount()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getOthersExpenses()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getTotalExpenses()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getClientReqId()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(formatDate(trip.getCreatedDate(), dateFormatter), rowFont)));
                table.addCell(new PdfPCell(new Phrase(trip.getCreatedBy(), rowFont)));
                table.addCell(new PdfPCell(new Phrase(formatDate(trip.getModifiedDate(), dateFormatter), rowFont)));
                table.addCell(new PdfPCell(new Phrase(trip.getModifiedBy(), rowFont)));
            }

            // Add the data table to the document
            document.add(table);

        } catch (DocumentException e) {
            // Handle any exception that occurs during PDF generation
            throw new RuntimeException("Error while generating PDF: " + e.getMessage(), e);
        } finally {
            // Close the document after finishing all operations
            document.close();
        }

        // Return the generated PDF as a ByteArrayInputStream
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    // Helper method for date formatting, handles both java.util.Date and LocalDate
    private String formatDate(Object date, DateTimeFormatter dateFormatter) {
        // If the date is null, return an empty string
        if (date == null) {
            return "";
        }

        // If the date is of type java.util.Date, format it using SimpleDateFormat
        if (date instanceof Date) {
            return new SimpleDateFormat("yyyy-MM-dd").format((Date) date);
        }
        // If the date is of type LocalDate, format it using DateTimeFormatter
        else if (date instanceof LocalDate) {
            return ((LocalDate) date).format(dateFormatter);
        } else {
            // If the date is not a supported type, return an empty string
            return "";
        }
    }
}
