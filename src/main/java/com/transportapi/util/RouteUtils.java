package com.transportapi.util;

import java.text.DecimalFormat;

public class RouteUtils {
    public static String formatCost(double cost) {
        // Formato para separar los primeros cuatro dígitos por puntos/comas, el resto ceros y ocultar los demás
        DecimalFormat df = new DecimalFormat("#,###,###,###.0000");
        String formattedCost = df.format(cost);

        // Obtener la parte antes del punto
        String[] parts = formattedCost.split("\\.");
        String integerPart = parts[0];
        String decimalPart = parts.length > 1 ? parts[1] : "";

        // Formatear solo los primeros cuatro dígitos
        StringBuilder sb = new StringBuilder();
        if (integerPart.length() > 4) {
            sb.append(integerPart.substring(0, integerPart.length() - 4));
            sb.append(".");
            sb.append(integerPart.substring(integerPart.length() - 4));
        } else {
            sb.append(integerPart);
        }

        // Agregar los ceros y ocultar los demás
        if (decimalPart.isEmpty()) {
            sb.append(".0000");
        } else {
            sb.append(".");
            sb.append(decimalPart.substring(0, Math.min(4, decimalPart.length())));
            for (int i = decimalPart.length(); i < 4; i++) {
                sb.append("0");
            }
        }

        return sb.toString();
    }

    // Métodos para formatDistance y formatDuration también deben estar presentes
    public static String formatDistance(double distanceInKm) {
        if (distanceInKm < 1) {
            double distanceInMeters = distanceInKm * 1000;
            return String.format("%.2f metros", distanceInMeters);
        } else {
            return String.format("%.2f kilómetros", distanceInKm);
        }
    }

    public static String formatDuration(int estimatedDurationInMinutes) {
        if (estimatedDurationInMinutes < 60) {
            return estimatedDurationInMinutes + " minutos";
        } else {
            int hours = estimatedDurationInMinutes / 60;
            int minutes = estimatedDurationInMinutes % 60;
            if (minutes == 0) {
                return hours + " horas";
            } else {
                return hours + " horas y " + minutes + " minutos";
            }
        }
    }
}
