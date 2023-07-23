package io.swagger.api;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.data.AgentStates;
import io.swagger.model.Agent;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-09-17T23:11:18.807Z[GMT]")
@RestController
public class AgentsApiController implements AgentsApi {

    private static final Logger log = LoggerFactory.getLogger(AgentsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    // object that represents data of all agents
    private final AgentStates agentsStates;
    @org.springframework.beans.factory.annotation.Autowired
    public AgentsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.agentsStates = new AgentStates();
    }

    public ResponseEntity<List<Agent>> findAgentsByStatus(@Parameter(in = ParameterIn.QUERY, description = "Status values that need to be considered for filter" ,schema=@Schema(allowableValues={"off", "ready", "busy", "afterCall", "preCall", "notReady", "break", "lunch" }
, defaultValue="available")) @Valid @RequestParam(value = "status", required = false, defaultValue="available") String status) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                // TODO: call your class to get real data and put it instead of hardcoded string below
                return new ResponseEntity<List<Agent>>(objectMapper.readValue("[ {\n  \"firstName\" : \"John\",\n  \"lastName\" : \"James\",\n  \"password\" : \"abc12345\",\n  \"phone\" : \"12345\",\n  \"id\" : 10,\n  \"agentStatus\" : 1,\n  \"email\" : \"john@email.com\",\n  \"username\" : \"theUser\"\n}, {\n  \"firstName\" : \"John\",\n  \"lastName\" : \"James\",\n  \"password\" : \"abc12345\",\n  \"phone\" : \"12345\",\n  \"id\" : 10,\n  \"agentStatus\" : 1,\n  \"email\" : \"john@email.com\",\n  \"username\" : \"theUser\"\n} ]", List.class),
                        HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Agent>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Agent>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Agent>> getAgents() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Agent>>(agentsStates.getAllAgents(), HttpStatus.OK);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Agent>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Agent>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Agent> getAgentsById(@Parameter(in = ParameterIn.PATH, description = "ID of pet to return", required=true, schema=@Schema()) @PathVariable("agentId") Long agentId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                // TODO: call your class to get real data and put it instead of hardcoded string below
                return new ResponseEntity<Agent>(objectMapper.readValue("{\n  \"firstName\" : \"John\",\n  \"lastName\" : \"James\",\n  \"password\" : \"abc12345\",\n  \"phone\" : \"12345\",\n  \"id\" : 10,\n  \"agentStatus\" : 1,\n  \"email\" : \"john@email.com\",\n  \"username\" : \"theUser\"\n}", Agent.class),
                        HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Agent>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Agent>(HttpStatus.NOT_IMPLEMENTED);
    }

}
