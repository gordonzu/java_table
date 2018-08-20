package com.gordonzu;

import java.util.Hashtable;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TableTest {

	private static final Action ACTION_1 = new Action("action1");
	private static final Action ACTION_2 = new Action("action2");
	private static final Action ACTION_3 = new Action("action3");
    private AbstractAgent agent;
    private Table table;

    @Before
    public void setUp() throws Exception {
		
        Map<List<Percept>, Action> perceptSequenceActions = new HashMap<List<Percept>, Action>();
		perceptSequenceActions.put(createPerceptSequence(new DynamicPercept("key1", "value1")), ACTION_1);

		perceptSequenceActions.put(createPerceptSequence(new DynamicPercept("key1", "value1"),
						new DynamicPercept("key1", "value2")), ACTION_2);

		perceptSequenceActions.put(createPerceptSequence(new DynamicPercept("key1", "value1"),
						new DynamicPercept("key1", "value2"),
						new DynamicPercept("key1", "value3")), ACTION_3);

		agent = new MockAgent(new TableDrivenAgentProgram(perceptSequenceActions));

    }

    @After
    public void tearDown() throws Exception {
        table = null;
    }

    @Test
    public void addItemToTableTest() throws Exception {
        assertTrue(true);
    }

	private static List<Percept> createPerceptSequence(Percept... percepts) {
		List<Percept> perceptSequence = new ArrayList<Percept>();

		for (Percept p : percepts) {
			perceptSequence.add(p);
		}

		return perceptSequence;
	}
}


///////////////////////////////////////////////////////////////////////////////////////////////////////
//
//////////////////////////////////////////////////////////////////////////////////////////////////////


interface Percept {}

class DynamicPercept implements Percept {
    String key;
    String value;

    public DynamicPercept(String key, String value) {
        this.key = key;
        this.value = value;
    }

}

class Action {
    String message;

    public Action(String msg) {
        this.message = msg;
    }
}

class NoOpAction extends Action {

	public static final NoOpAction NO_OP = new NoOpAction();

	public boolean isNoOp() {
		return true;
	}

	private NoOpAction() {
		super("NoOp");
	}
}

interface AgentProgram {
    Action execute(Percept percept);
}

class AbstractAgent {
    protected AgentProgram program;

    public AbstractAgent(AgentProgram p) {
        this.program = p;
    }
}

class MockAgent extends AbstractAgent {

    public MockAgent(AgentProgram program) {
        super(program);
    }
}

class TableDrivenAgentProgram implements AgentProgram {

	private List<Percept> percepts = new ArrayList<Percept>();
	private Table<List<Percept>, String, Action> table;
	private static final String ACTION = "action";

	public TableDrivenAgentProgram(Map<List<Percept>, Action> perceptSequenceActions) {

		List<List<Percept>> rowHeaders = new ArrayList<List<Percept>>(
				perceptSequenceActions.keySet());

		List<String> colHeaders = new ArrayList<String>();
		colHeaders.add(ACTION);

		table = new Table<List<Percept>, String, Action>(rowHeaders, colHeaders);

		for (List<Percept> row : rowHeaders) {
			table.set(row, ACTION, perceptSequenceActions.get(row));
		}
	}

    public Action execute(Percept percept) {
		percepts.add(percept);

		return lookupCurrentAction();
	}

	private Action lookupCurrentAction() {
		Action action = null;

		action = table.get(percepts, ACTION);
		if (null == action) {
			action = NoOpAction.NO_OP;
		}

		return action;
	}
} 








