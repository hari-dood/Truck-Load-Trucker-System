package com.hariSolution.xlGenerator;

import com.hariSolution.model.TripDetails;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ExcelGeneratorService {

    public static final String[] HEADER = {
            "id", "driverName", "month", "loadingDate", "tripStartDate", "tripEndDate", "paymentStatus",
            "loadingPoint", "deliveryPoint", "loadQuantity", "goodsDescription", "loadAmount", "loadAdvance",
            "weightBridge", "brokerAmount", "upAmount", "downAmount", "tollAmount", "dieselExpenses",
            "rtoAmount", "pcAmount", "othersExpenses", "totalExpenses", "clientReqId",
            "createdDate", "createdBy", "modifiedDate", "modifiedBy"
    };

    public static final String SHEET_NAME = "TripDetails";

    public ByteArrayInputStream dataToExcel(List<TripDetails> trips) {
        try (Workbook workbook = new XSSFWorkbook();

             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET_NAME);
            Row headerRow = sheet.createRow(0);

            // Create header row
            for (int i = 0; i < HEADER.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADER[i]);
            }

            int rowIndex = 1;

            // Populate rows with trip details
            for (TripDetails trip : trips) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(trip.getId());
                dataRow.createCell(1).setCellValue(trip.getDriverName());
                dataRow.createCell(2).setCellValue(trip.getMonth());
                dataRow.createCell(3).setCellValue(trip.getLoadingDate() != null ? trip.getLoadingDate().format(DateTimeFormatter.ISO_DATE) : "");
                dataRow.createCell(4).setCellValue(trip.getTripStartDate() != null ? trip.getTripStartDate().format(DateTimeFormatter.ISO_DATE) : "");
                dataRow.createCell(5).setCellValue(trip.getTripEndDate() != null ? trip.getTripEndDate().format(DateTimeFormatter.ISO_DATE) : "");
                dataRow.createCell(6).setCellValue(trip.getPaymentStatus());
                dataRow.createCell(7).setCellValue(trip.getLoadingPoint());
                dataRow.createCell(8).setCellValue(trip.getDeliveryPoint());
                dataRow.createCell(9).setCellValue(trip.getLoadQuantity());
                dataRow.createCell(10).setCellValue(trip.getGoodsDescription());
                dataRow.createCell(11).setCellValue(trip.getLoadAmount());
                dataRow.createCell(12).setCellValue(trip.getLoadAdvance());
                dataRow.createCell(13).setCellValue(trip.getWeightBridge());
                dataRow.createCell(14).setCellValue(trip.getBrokerAmount());
                dataRow.createCell(15).setCellValue(trip.getUpAmount());
                dataRow.createCell(16).setCellValue(trip.getDownAmount());
                dataRow.createCell(17).setCellValue(trip.getTollAmount());
                dataRow.createCell(18).setCellValue(trip.getDieselExpenses());
                dataRow.createCell(19).setCellValue(trip.getRtoAmount());
                dataRow.createCell(20).setCellValue(trip.getPcAmount());
                dataRow.createCell(21).setCellValue(trip.getOthersExpenses());
                dataRow.createCell(22).setCellValue(trip.getTotalExpenses());
                dataRow.createCell(23).setCellValue(trip.getClientReqId());
                dataRow.createCell(24).setCellValue(trip.getCreatedDate() != null ? trip.getCreatedDate().format(DateTimeFormatter.ISO_DATE) : "");
                dataRow.createCell(25).setCellValue(trip.getCreatedBy());
                dataRow.createCell(26).setCellValue(trip.getModifiedDate() != null ? trip.getModifiedDate().format(DateTimeFormatter.ISO_DATE) : "");
                dataRow.createCell(27).setCellValue(trip.getModifiedBy());
            }

            workbook.write(byteArrayOutputStream);
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Error while generating Excel file: " + e.getMessage(), e);
        }
    }
}
