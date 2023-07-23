// hardcoded state names - should be taken from backend
const stateNames = [ "off", "ready", "busy", "afterCall", "preCall", "notReady", "break", "lunch" ];
const stateColors = [
    'rgb(100, 0, 0)', // off
    'cornflowerblue', // ready
    'cyan', // busy
    'mediumblue', // afterCall
    'blue', // preCall
    'midnightblue', // notReady
    'navy', // break
    'cadetblue', // lunch
];
var states = [0, 0, 0, 0, 0, 0, 0, 0]; // counters
const c = document.getElementById("myCanvas");
var width = c.width = window.innerWidth;
var height = c.height = window.innerHeight;
const ctx = c.getContext("2d");
var agents = []; //stores the agents to draw them in static place
var agentsMap = new Map(); // also stores the agents with id as the key to access by id
const nC = 100;//number of columns
const nR = 100;//number of rows
var rad;
var gap1;
var gap2;
//var ids = [];

//background
ctx.fillStyle = 'white';
ctx.fillRect(0, 0, width, height);

class circle {
    constructor(X, Y, rad){
        this.X = X;//position
        this.Y = Y;//position
        this.rad = rad;//radius
        this.color = 'rgb(100, 0, 100)';
        this.id = null;//agent id
        this.state = null;//agent state
    }
    clear(){
        ctx.clearRect(
            this.X - this.rad - gap1,
            this.Y - this.rad - gap2,
            this.X + this.rad + gap1,
            this.Y + this.rad + gap2
        );
    }
    circUp(x, y, rad){
        this.X = x;
        this.Y = y;
        this.rad = rad;
    }
    online(id){
        this.id = id;
        this.stateChange(0);
    }
    stateChange(code){
        this.state = code;
        if (code >= states.length || code === null) {
            this.color = stateColors[0];
            states[0]++;
        } else {
            this.color = stateColors[code];
            states[code]++;
        }
        this.update();
    }
    update(){
        this.clear();
        ctx.fillStyle = this.color;
        ctx.beginPath();
        ctx.arc(this.X, this.Y, this.rad, degToRad(0), degToRad(360));
        ctx.fill();
    }
}
function createAgent(x, y, r){
    agents.push(new circle(x, y, r));
}
function random(n){
    return Math.floor(Math.random()*n);
}
function degToRad(degrees){
    return degrees * Math.PI/180;
}

function reset(){
    width = c.width = window.innerWidth-20;
    height = c.height = window.innerHeight-75;
    gap1 = 1;
    gap2 = 1;
    states.forEach(function (s,i,a){a[i]=0});
}
function gaps(){//creates the size for each agent
    reset();
    const rad1 = (width-gap1*(nC+1))/nC/2;
    const rad2 = (height-gap2*(nR+1))/nR/2;
    //to determine which radius will fit both directions
    if(rad1 > rad2){
        rad = rad2;
        gap1 = (width-(nC*2*rad))/(nC+1);
    }else{
        rad = rad1;
        gap2 = (height-(nR*2*rad))/(nR+1);
    }
    gapsUpdate();
}
function gapsUpdate(){//creates the location for each agent
    var index = 0;
    var m = rad+gap2;
    for(var p=0; p<nR; p++){
        var n = rad+gap1;
        for(var i=0; i<nC; i++){
            if(agents.length>=nC*nR){
                agents[index].circUp(n, m, rad);
                index++;
            }else{
                createAgent(n, m, rad);
            }
            n+=2*rad+gap1;
        }
        m+=2*rad+gap2;
    }
}

gaps();

function test(){
    gaps();
    getAllAgents();
    for(var i=0; i<agents.length; i++){
        if(agents[i].state == null){
            //if there is no id, it remains unchanging
            agents[i].stateChange(null);
        }else{
            //if there is an id, it changes accordingly
            agents[i].stateChange(agents[i].state);
        }
    }

    var statesHtml = "";
    for(var i=0; i<states.length; i++) {
        statesHtml += stateNames[i] + ": " + states[i] + " ";
    }
    document.getElementById("states").innerHTML = statesHtml;
}
setInterval(test, 1000);//speed/rate

function getAllAgents() {
    var myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("api_key", "<API Key>");

    var requestOptions = {
        method: 'GET',
        headers: myHeaders,
        redirect: 'follow'
    };

    fetch("/api/v3/agents", requestOptions)
        .then(response => response.text())
        .then(result => updateAgents(result))
        .catch(error => console.log('error', error));
}

function updateAgents(agentStates) {
    var agentsObj = JSON.parse(agentStates);
    for(var i=0;i<agentsObj.length;i++) {
        var id = agentsObj[i].id;
        if (!agentsMap.has(id)) {
            // map agents(circles) to ids
            agentsMap.set(id, agents[agentsMap.size]);
        }
        // assign the agent id and status from response
        agentsMap.get(id).online(id);
        agentsMap.get(id).stateChange(agentsObj[i].agentStatus);
    }
    console.log("Agent map: ", agentsMap);
}