import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void ventasNYC(List<Ventas> ventas) {
        ventas.stream()
                .filter(v -> v.getCity().equals("NYC"))
                .forEach(v -> {
                    System.out.print("orderNumber: " + v.getOrderNumber() + ", ");
                    System.out.print("quantityOrdered: " + v.getQuantityOrdered() + ", ");
                    System.out.print("priceEach: " + v.getPriceEach() + ", ");
                    System.out.print("orderLineNumber: " + v.getOrderLineNumber() + ", ");
                    System.out.print("sales: " + v.getSales() + ", ");
                    System.out.print("orderDate: " + v.getOrderDate() + ", ");
                    System.out.print("status: " + v.getStatus() + ", ");
                    System.out.print("qtr_id: " + v.getQtrId() + ", ");
                    System.out.print("month_id: " + v.getMonthId() + ", ");
                    System.out.print("year_id: " + v.getYearId() + ", ");
                    System.out.print("productLine: " + v.getProductLine() + ", ");
                    System.out.print("msrp: " + v.getMsrp() + ", ");
                    System.out.print("productCode: " + v.getProductCode() + ", ");
                    System.out.print("customerName: " + v.getCustomerName() + ", ");
                    System.out.print("phone: " + v.getPhone() + ", ");
                    System.out.print("adressLine1: " + v.getAddressLine1() + ", ");
                    System.out.print("adressLine2: " + v.getAddressLine2() + ", ");
                    System.out.print("city: " + v.getCity() + ", ");
                    System.out.print("state: " + v.getState() + ", ");
                    System.out.print("postalCode: " + v.getPostalCode() + ", ");
                    System.out.print("country: " + v.getCountry() + ", ");
                    System.out.print("territory: " + v.getTerritory() + ", ");
                    System.out.print("contactLastName: " + v.getContactLastName() + ", ");
                    System.out.print("contactFirstName: " + v.getContactFirstName() + ", ");
                    System.out.println("dealSize: " + v.getDealSize());
                });
    }

    public static double ventasNewYork(List<Ventas> ventas, String city) {
        return ventas.stream()
                .filter(v -> {
                    return v.getCity().equals(city);
                })
                .mapToDouble(v -> v.getSales())
                .sum();
    }

    public static int carrosClasicosNYC(List<Ventas> ventas, String city){
        return ventas.stream()
                .filter(v-> {
                    return v.getCity().equals(city) && v.getProductLine().equals("Classic Cars");
                })
                .mapToInt(v-> v.getQuantityOrdered())
                .sum();

    }

    public static double ventasCarrosClasicosNYC(List<Ventas> ventas, String city){
        return ventas.stream()
                .filter(v-> {
                    return v.getCity().equals(city) && v.getProductLine().equals("Classic Cars");
                })
                .mapToDouble(v-> v.getSales())
                .sum();

    }


    public static int motocicletasNYC(List<Ventas> ventas, String city){
        return ventas.stream()
                .filter(v -> {
                    return v.getCity().equals(city) && v.getProductLine().equals("Motorcycles");
                })
                .mapToInt(v -> v.getQuantityOrdered())
                .sum();
    }

    public static double ventasMotocicletasNYC(List<Ventas> ventas, String city){
        return ventas.stream()
                .filter(v -> {
                    return v.getCity().equals(city) && v.getProductLine().equals("Motorcycles");
                })
                .mapToDouble(v -> v.getSales())
                .sum();
    }



    public static String clienteMasAutosComproEnNY(List<Ventas> ventas) {
        Map<String, Integer> cantidadAutosPorCliente = new HashMap<>();
        ventas.stream()
                .filter(v -> v.getCity().equals("NYC") && (v.getProductLine().equals("Classic Cars") || v.getProductLine().equals("Vintage Cars")))
                .forEach(v -> {
                    String cliente = v.getCustomerName();
                    Integer cantidadAutos = v.getQuantityOrdered();
                    cantidadAutosPorCliente.put(cliente, cantidadAutosPorCliente.getOrDefault(cliente, 0) + cantidadAutos);
                });
        return cantidadAutosPorCliente.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }

    public static String clienteMasCompro(List<Ventas> ventas) {
        Map<String, Integer> cantidadPorCliente = new HashMap<>();
        ventas.forEach(v -> {
            String cliente = v.getCustomerName();
            Integer cantidad = v.getQuantityOrdered();
            cantidadPorCliente.put(cliente, cantidadPorCliente.getOrDefault(cliente, 0) + cantidad);
        });
        return cantidadPorCliente.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No hay cliente que compró más");
    }

    public static String clienteMenosCompro(List<Ventas> ventas) {
        Map<String, Integer> cantidadPorCliente = new HashMap<>();
        ventas.forEach(v -> {
            String cliente = v.getCustomerName();
            Integer cantidad = v.getQuantityOrdered();
            cantidadPorCliente.put(cliente, cantidadPorCliente.getOrDefault(cliente, 0) + cantidad);
        });
        return cantidadPorCliente.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No hay un cliente que compró menos");
    }




    public static void main(String[] args) throws IOException {
        Path path = Path.of("src/sales_data.csv");
        //La parte "Charset.forName("ISO-8859-1")" es porque la codificación de mi pc es UTF-8, y para leer el archivo la codificación debe ser ISO-8859-1
        //Esto es porque compré mi pc venia con una configuración determinada de codificación diferente,
        // no tiene los mismos caracteres entonces no puede leer algunos caracteres.
        List<Ventas> ventas = Files.lines(path, Charset.forName("ISO-8859-1"))
                .skip(1)
                .map(line -> {
                    String[] attributes = line.split(",");
                    Ventas venta = new Ventas();
                    venta.setOrderNumber(Integer.parseInt(attributes[0]));
                    venta.setQuantityOrdered(Integer.parseInt(attributes[1]));
                    venta.setPriceEach(Double.parseDouble(attributes[2]));
                    venta.setOrderLineNumber(Integer.parseInt(attributes[3]));
                    venta.setSales(Double.parseDouble(attributes[4]));
                    venta.setOrderDate(attributes[5]);
                    venta.setStatus(attributes[6]);
                    venta.setQtrId(Integer.parseInt(attributes[7]));
                    venta.setMonthId(Integer.parseInt(attributes[8]));
                    venta.setYearId(Integer.parseInt(attributes[9]));
                    venta.setProductLine(attributes[10]);
                    venta.setMsrp(Integer.parseInt(attributes[11]));
                    venta.setProductCode(attributes[12]);
                    venta.setCustomerName(attributes[13]);
                    venta.setPhone(attributes[14]);
                    venta.setAddressLine1(attributes[15]);
                    venta.setAddressLine2(attributes[16]);
                    venta.setCity(attributes[17]);
                    venta.setState(attributes[18]);
                    venta.setPostalCode(attributes[19]);
                    venta.setCountry(attributes[20]);
                    venta.setTerritory(attributes[21]);
                    venta.setContactLastName(attributes[22]);
                    venta.setContactFirstName(attributes[23]);
                    venta.setDealSize(attributes[24]);
                    return venta;
                })
                .collect(Collectors.toList());

        Main.ventasNYC(ventas);
        System.out.println("El total de ventas de New York es de: " + Main.ventasNewYork(ventas, "NYC"));
        System.out.println("New York vendió " + Main.carrosClasicosNYC(ventas, "NYC") + " autos clásicos");
        System.out.println("El total de ventas de autos clásicos en New York es de : " + Main.ventasCarrosClasicosNYC(ventas, "NYC"));
        System.out.println("New York vendió " + Main.motocicletasNYC(ventas, "NYC") + " motocicletas");
        System.out.println("El total de ventas de motocicletas en New York es de : " + Main.ventasMotocicletasNYC(ventas, "NYC"));
        System.out.println("El cliente que más autos compró en New York es: " + Main.clienteMasAutosComproEnNY(ventas));
        System.out.println("El cliente que más compró es: " + Main.clienteMasCompro(ventas));
        System.out.println("El cliente que menos compró es: " + Main.clienteMenosCompro(ventas));

    }
}
