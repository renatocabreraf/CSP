package gt.edu.url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSP <V, D>{
    private List<V> variables;
    private Map<V, List<D>> domains;
    private Map<V, List<Constraint<V, D>>> constraints = new HashMap<>();

    public CSP(List<V> variables, Map<V, List<D>> domains){
        this.variables = variables;
        this.domains = domains;

        for (V variable: variables) {
            constraints.put(variable, new ArrayList<Constraint<V, D>>());
            // Cada variable debe de tenewr un dominio asignado
            if(!domains.containsKey(variable)){
                throw new IllegalArgumentException("La variable " + variable + ", no contiene un dominio");
            }
        }
    }

    public void addConstraint(Constraint<V, D> constraint){
        for (V variable: constraint.variables){
            // Variable que se encuentra en el constraint tambien sea parte del CSP
            if(!this.variables.contains(variable)){
                throw new IllegalArgumentException("La variable " + variable + ", no se encuentra en el CSP");
            }
            constraints.get(variable).add(constraint);
        }
    }

    public boolean consistent(V variable, Map<V, D> assignment){
        for (Constraint<V, D> constraint:this.constraints.get(variable)) {
            if(!constraint.satisfied(assignment)){
                return  false;
            }
        }
        return  true;
    }

    public Map<V, D> backtrack(){
        return backtrack(new HashMap<>());
    }

    public Map<V, D> backtrack(final Map<V, D> assigment){
        // Si la asignación es completa (si cada variable tiene un valor)
        if (variables.size() == assigment.size()){
            return  assigment;
        }

        // Seleccionar una variable no asignada
        V unassigned = variables.stream()
                .filter(v -> !assigment.containsKey(v))
                .findFirst().get();

        for (D value: domains.get(unassigned)){
            System.out.println("Variable: " + unassigned + ", valor: " + value);
            // Probar una asignación
            // 1- Crar una copia de la asignación anterior
            Map<V, D> localAssigment = new HashMap<>(assigment);
            // 2- Probar (aka asignar un valor)
            localAssigment.put(unassigned, value);
            // 3- Verificar la consistencia de la asignación
            if (consistent(unassigned, localAssigment)){
                Map<V, D> result = backtrack(localAssigment);

                if (result != null){
                    return result;
                }
            }
        }
        return null;
    }
}
