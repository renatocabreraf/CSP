package gt.edu.url.AC3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class CSP2 {
    private List<Variable> variables;
    private List<Domain> domains;
    private List<Constraint2> constraints;

    private Hashtable<Variable, Integer> varIndexHash;
    private Hashtable<Variable, List<Constraint2>> cnet;

    private CSP2() {
    }

    public CSP2(List<Variable> vars) {
        variables = new ArrayList<Variable>(vars.size());
        domains = new ArrayList<Domain>(vars.size());
        constraints = new ArrayList<Constraint2>();
        varIndexHash = new Hashtable<Variable, Integer>();
        cnet = new Hashtable<Variable, List<Constraint2>>();
        Domain emptyDomain = new Domain(new ArrayList<Object>(0));
        int index = 0;
        for (Variable var : vars) {
            variables.add(var);
            domains.add(emptyDomain);
            varIndexHash.put(var, index++);
            cnet.put(var, new ArrayList<Constraint2>());
        }
    }

    public List<Variable> getVariables() {
        return Collections.unmodifiableList(variables);
    }

    public int indexOf(Variable var) {
        return varIndexHash.get(var);
    }

    public Domain getDomain(Variable var) {
        return domains.get(varIndexHash.get(var));
    }

    public void setDomain(Variable var, Domain domain) {
        domains.set(indexOf(var), domain);
    }


    public void removeValueFromDomain(Variable var, Object value) {
        Domain currDomain = getDomain(var);
        List<Object> values = new ArrayList<Object>(currDomain.size());
        for (Object v : currDomain)
            if (!v.equals(value))
                values.add(v);
        setDomain(var, new Domain(values));
    }

    public List<Constraint2> getConstraints() {
        return constraints;
    }


    public List<Constraint2> getConstraints(Variable var) {
        return cnet.get(var);
    }

    public void addConstraint(Constraint2 constraint) {
        constraints.add(constraint);
        for (Variable var : constraint.getScope())
            cnet.get(var).add(constraint);
    }


    public Variable getNeighbor(Variable var, Constraint2 constraint) {
        List<Variable> scope = constraint.getScope();
        if (scope.size() == 2) {
            if (var == scope.get(0))
                return scope.get(1);
            else if (var == scope.get(1))
                return scope.get(0);
        }
        return null;
    }


    public CSP2 copy() {
        CSP2 result = new CSP2();
        result.variables = variables;
        result.domains = new ArrayList<Domain>(domains.size());
        result.domains.addAll(domains);
        result.constraints = constraints;
        result.varIndexHash = varIndexHash;
        result.cnet = cnet;
        return result;
    }
}
