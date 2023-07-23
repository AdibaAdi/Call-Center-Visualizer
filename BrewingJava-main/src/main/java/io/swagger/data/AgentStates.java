package io.swagger.data;

import io.swagger.model.Agent;

import java.util.*;

public class AgentStates {
    private List<Agent> allAgents;

    public AgentStates() {
        allAgents = new ArrayList<>();
        // TODO: remove hardcoded values
        for(int i=0 ; i < 10000 ; i++) {
            Agent a = new Agent();
            a.setId(new Long(i));
            allAgents.add(a);
        }

    }
    public List<Agent> getAllAgents() {
        // Set random states for demo
        // TODO: get real states
        for (Agent a : allAgents) {
            a.setAgentStatus((int)(Math.random() * 8));
        }
        return allAgents;
    }
}
