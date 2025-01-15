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

    // Method to convert data to PDF
    public ByteArrayInputStream DataToPdf(List<TripDetails> tripDetails) {
        Document document = new Document(PageSize.A3);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            // Title Table
            PdfPTable titleTable = new PdfPTable(1);
            titleTable.setWidthPercentage(100);

            PdfPCell titleCell = new PdfPCell();
            titleCell.setBorderWidth(2);
            titleCell.setPadding(10);
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 22);
            Paragraph title = new Paragraph("Trips Information", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            titleCell.addElement(title);
            titleTable.addCell(titleCell);

            document.add(titleTable);
            document.add(Chunk.NEWLINE);

            // Data Table
            PdfPTable table = new PdfPTable(28); // 28 columns (adjust based on your content)
            table.setWidthPercentage(100);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Define column widths (adjustable based on your content)
            float[] columnWidths = new float[] { 1.5f, 3.5f, 2.5f, 4.0f, 4.0f, 4.f, 4.0f, 2f, 2f, 1.5f, 2f, 1.5f, 1.5f,
                    1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 2f, 1.5f, 1.5f,
                    1.5f, 1.5f}; // Adjusted widths for better readability
            table.setWidths(columnWidths);

            // Add Header Row
            Stream.of("id", "driverName", "month", "loadingDate", "tripStartDate", "tripEndDate", "paymentStatus",
                            "loadingPoint", "deliveryPoint", "loadQuantity", "goodsDescription", "loadAmount", "loadAdvance",
                            "weightBridge", "brokerAmount", "upAmount", "downAmount", "tollAmount", "dieselExpenses",
                            "rtoAmount", "pcAmount", "othersExpenses", "totalExpenses", "clientReqId",
                            "createdDate", "createdBy", "modifiedDate", "modifiedBy")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        Font headerFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 8);
                        header.setMinimumHeight(15);
                        header.setBackgroundColor(Color.cyan);
                        header.setVerticalAlignment(Element.ALIGN_CENTER);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorderWidth(1);
                        header.setPhrase(new Phrase(headerTitle, headerFont));
                        table.addCell(header);
                    });

            // DateTimeFormatter to format LocalDate fields
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Add Data Rows (ensure 28 columns per row)
            for (TripDetails trip : tripDetails) {
                Font rowFont = FontFactory.getFont(FontFactory.TIMES, 8); // Reduced font size for better readability

                table.addCell(new PdfPCell(new Phrase(String.valueOf(trip.getId()), rowFont)));
                table.addCell(new PdfPCell(new Phrase(trip.getDriverName(), rowFont)));
                table.addCell(new PdfPCell(new Phrase(trip.getMonth(), rowFont)));

                // Format date fields if they are of type Date or LocalDate
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

            document.add(table);

        } catch (DocumentException e) {
            throw new RuntimeException("Error while generating PDF: " + e.getMessage(), e);
        } finally {
            document.close();
        }

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    // Helper method for date formatting, handles both Date and LocalDate
    private String formatDate(Object date, DateTimeFormatter dateFormatter) {
        if (date == null) {
            return ""; // return empty string if the date is null
        }

        if (date instanceof Date) {
            return new SimpleDateFormat("yyyy-MM-dd").format((Date) date); // Format java.util.Date
        } else if (date instanceof LocalDate) {
            return ((LocalDate) date).format(dateFormatter); // Format LocalDate
        } else {
            return ""; // Return empty string if it's not a supported date type
        }
    }
}
