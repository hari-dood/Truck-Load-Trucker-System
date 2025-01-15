package com.hariSolution.notification;

import com.hariSolution.DTOs.TripDetailsDTO;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class MailNotificationDisplay {

    public String formatTripDetails(TripDetailsDTO tripDTO) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: 'Arial', sans-serif; margin: 0; padding: 0; background-color: #f0f4f8; }" +
                ".email-container { max-width: 750px; margin: 40px auto; background-color: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 25px rgba(0, 0, 0, 0.1); }" +
                ".header { background: linear-gradient(135deg, #3498db, #8e44ad); color: white; text-align: center; padding: 40px; font-size: 32px; font-weight: bold; letter-spacing: 2px; }" +
                ".sub-header { text-align: center; color: #2c3e50; padding: 15px 20px; font-size: 22px; font-weight: 600; border-bottom: 3px solid #ecf0f1; margin-top: 0; }" +
                ".content { padding: 40px 45px; color: #7f8c8d; font-size: 16px; line-height: 1.8; background-color: #ecf0f1; }" +
                ".content p { margin: 15px 0; }" +
                ".highlight { color: #2980b9; font-weight: bold; }" +
                ".table { width: 100%; border-collapse: collapse; margin-top: 20px; font-size: 16px; border: 1px solid #ddd; }" +
                ".table th, .table td { padding: 15px; text-align: left; }" +
                ".table th { background-color: #f7f7f7; color: #34495e; font-weight: 600; }" +
                ".table tr:nth-child(even) { background-color: #f9f9f9; }" +
                ".table td { background-color: #ffffff; color: #2c3e50; }" +
                ".footer { background-color: #2c3e50; text-align: center; padding: 30px 20px; font-size: 14px; color: white; }" +
                ".footer a { color: #3498db; text-decoration: none; font-weight: 500; }" +
                ".footer a:hover { text-decoration: underline; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='email-container'>" +
                "<div class='header'>Sairam Transport</div>" +
                "<div class='sub-header'>Trip Details Summary</div>" +
                "<div class='content'>" +
                "<p>Dear <span class='highlight'>Admin</span>,</p>" +
                "<p>We are pleased to provide the details of your recent trip:</p>" +
                "<table class='table'>" +
                "<tr><th>Driver Name</th><td>" + tripDTO.getDriverName() + "</td></tr>" +
                "<tr><th>Loading Point</th><td>" + tripDTO.getLoadingPoint() + "</td></tr>" +
                "<tr><th>Delivery Point</th><td>" + tripDTO.getDeliveryPoint() + "</td></tr>" +
                "<tr><th>Month</th><td>" + tripDTO.getMonth() + "</td></tr>" +
                "<tr><th>Trip Start Date</th><td>" + tripDTO.getTripStartDate().format(dateFormatter) + "</td></tr>" +
                "<tr><th>Trip End Date</th><td>" + tripDTO.getTripEndDate().format(dateFormatter) + "</td></tr>" +
                "<tr><th>Goods Description</th><td>" + tripDTO.getGoodsDescription() + "</td></tr>" +
                "<tr><th>Load Advance</th><td>₹" + tripDTO.getLoadAdvance() + "</td></tr>" +
                "<tr><th>Load Amount</th><td>₹" + tripDTO.getLoadAmount() + "</td></tr>" +
                "<tr><th>Payment Status</th><td>" + tripDTO.getPaymentStatus() + "</td></tr>" +
                "<tr><th>Total Expenses</th><td>₹" + tripDTO.getTotalExpenses() + "</td></tr>" +
                "</table>" +
                "<p>Thank you for trusting <span class='highlight'>Sairam Transport</span> with your logistics needs. We look forward to serving you again.</p>" +
                "</div>" +
                "<div class='footer'>" +
                "© 2025 Sairam Transport. All Rights Reserved.<br>" +
                "For any inquiries, contact us at <a href='mailto:support@sairamtransport.com'>support@sairamtransport.com</a><br>" +
                "<a href='https://www.sairamtransport.com' target='_blank'>Visit Our Website</a>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}
