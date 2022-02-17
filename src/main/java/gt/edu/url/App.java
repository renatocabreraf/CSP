package gt.edu.url;

import gt.edu.url.AC3.MapCSP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String cnsl = "1";
        try {
            while (true) {
                System.out.println("\n\n\n ¿Que inciso desea realizar? \n   1. CSP \n   2. AC-3 \n   3. CSP - Complex Constraint \n   0. Para salir");
                System.out.print("x: ");
                cnsl = reader.readLine();

                if (cnsl.equals("1")) {
                    // CSP
                    // Variables
                    List<String> variables = List.of("Western Australia", "Northern Territory", "Queensland", "South Australia", "New South Wales", "Victoria", "Tasmania");

                    // Dominios
                    Map<String, List<String>> domains = new HashMap<>();
                    for (var variable : variables) {
                        domains.put(variable, List.of("rojo", "verde", "azul"));
                    }

                    // Restricciones
                    CSP<String, String> problema = new CSP<>(variables, domains);
                    problema.addConstraint(new AustraliaColoringConstraint("Western Australia", "Northern Territory"));
                    problema.addConstraint(new AustraliaColoringConstraint("Western Australia", "South Australia"));
                    problema.addConstraint(new AustraliaColoringConstraint("Northern Territory", "South Australia"));
                    problema.addConstraint(new AustraliaColoringConstraint("Northern Territory", "Queensland"));
                    problema.addConstraint(new AustraliaColoringConstraint("South Australia", "Queensland"));
                    problema.addConstraint(new AustraliaColoringConstraint("South Australia", "New South Wales"));
                    problema.addConstraint(new AustraliaColoringConstraint("South Australia", "Victoria"));
                    problema.addConstraint(new AustraliaColoringConstraint("Queensland", "New South Wales"));
                    problema.addConstraint(new AustraliaColoringConstraint("New South Wales", "Victoria"));
                    problema.addConstraint(new AustraliaColoringConstraint("Victoria", "Tasmania"));

                    var solution = problema.backtrack();
                    System.out.println(solution);
                } else if (cnsl.equals("2")) {
                    MapCSP map = new MapCSP();
                    map.solve();
                } else if (cnsl.equals("3")) {
                    System.out.println("solution");
                } else {
                    System.exit(0);
                }

                System.out.println("Enter para continuar...");
                cnsl = reader.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
