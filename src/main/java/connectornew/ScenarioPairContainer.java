package connectornew;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by srg on 04.07.16.
 */
public class ScenarioPairContainer<T> {
    private byte method;
    private T command;
    private List<VariablesDescriptor> variables = new ArrayList<VariablesDescriptor>();
    private List<byte[]> inBytes = new ArrayList<byte[]>();


    //Constructors
    public ScenarioPairContainer(byte method, T command) {
        this.method = method;
        this.command = command;
    }

    //static methods
    public static ScenarioPairContainer getCommand(Map<String, Object> scenario, String[] state, int level) {
        Iterator iterator = scenario.entrySet().iterator();
        Map.Entry<String, Object> object = null;
        boolean onNextStep = false;
        ScenarioPairContainer spc = null;

        while (iterator.hasNext()) {
            if (!onNextStep) object = (Map.Entry<String, Object>) iterator.next();
            else onNextStep = false;

            if (state[level] == null) state[level] = object.getKey();

            //ссылка на указанный в String[] state ключ элемента
            if (object.getKey().equals(state[level])) {
                //если элемент содержит мап(внутренняя вложеность)
                if (object.getValue() instanceof Map) {
                    spc = getCommand((Map<String, Object>) object.getValue(), state, level + 1);
                    // если spc==null работа с вложенными элементами закончена и необходимо перейти на следующий элемент.
                    if (spc == null) {
                        //если iterator.hasNext()==false более элементов нет, нужно переходить на уровень выше
                        if (!iterator.hasNext()) return null;
                        //переход на следующий элемент. что не выполнять повторного iterator.next() выше введена переменная onNextStep
                        object = (Map.Entry<String, Object>) iterator.next();
                        state[level] = object.getKey();
                        onNextStep = true;
                    }
                    return spc;
                } else {
                    List tmp = (List) object.getValue();
                    //обработка первого прохода по методу
                    if (state[level + 1] == null) state[level + 1] = String.valueOf(0);

                    int positionInList = Integer.valueOf(state[level + 1]);
                    if (tmp.size() < positionInList + 1) return null;
                    spc = (ScenarioPairContainer) tmp.get(positionInList);
                    state[level + 1] = String.valueOf(positionInList + 1);
                    return spc;
                }
            }
        }
        return spc;
    }

    //Getters and Setters
    public byte getMethod() {
        return method;
    }

    public void setMethod(byte method) {
        this.method = method;
    }

    public T getCommand() {
        return command;
    }

    public void setCommand(T command) {
        this.command = command;
    }

    public List<VariablesDescriptor> getVariables() {
        return variables;
    }

    public void setVariables(List<VariablesDescriptor> variables) {
        this.variables = variables;
    }

    public List<byte[]> getInBytes() {
        return inBytes;
    }

    public void setInBytes(List<byte[]> inBytes) {
        this.inBytes = inBytes;
    }

    //Methods
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ScenarioPairContainer{");
        sb.append("method=").append(method);
        sb.append(", command=").append(command);
        sb.append('}');
        return sb.toString();
    }
}
