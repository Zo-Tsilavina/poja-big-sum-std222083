import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class BigSumHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            String query = exchange.getRequestURI().getQuery();
            String[] params = query.split("&");
            BigInteger a = BigInteger.ZERO;
            BigInteger b = BigInteger.ZERO;
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    if (keyValue[0].equals("a")) {
                        a = new BigInteger(keyValue[1]);
                    } else if (keyValue[0].equals("b")) {
                        b = new BigInteger(keyValue[1]);
                    }
                }
            }

            BigInteger result = BigSumCalculator.calculateSum(a, b);

            String response = "Sum of " + a + " and " + b + " is: " + result;
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            exchange.sendResponseHeaders(405, 0); // Method not allowed
        }
    }
}
