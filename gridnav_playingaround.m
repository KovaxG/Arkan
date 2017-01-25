% gridnav_playingaround by Kovacs Gyorgy
LEFT = 1;
RIGHT = 2;
DOWN = 3;
UP = 4;

% Step 1: Create a Model
cfg.size = [10, 10]; % Size of the map
cfg.x_obst = [[4; 1], [5; 1], [2; 3], [3; 4], [4; 5]]; % Obstacle placement
cfg.x_goal = [5; 5]; % Placement of the end goal
model = gridnav_problem('model', cfg); % Create model

% My custom policy
myH = ones(cfg.size);
myH(:, 1) = UP;
myH(1, 3) = DOWN;
myH(1, 4) = DOWN;
myH(1, 5) = DOWN;
myH(:, 2) = RIGHT;
myH(5, :) = UP;
myH(4, 3) = RIGHT;
myH(3, 3) = RIGHT;
myH(4, 4) = RIGHT;

% Step 2: Initialize variables
state = [5; 2]; % Starting location
terminalState = 0; % Not in a terminalstate
[Q, h] = QI(model, 0.8, 0.001); % The policy
cummulativeReward = 0; % Total rewards

% Step 3: Initialize the graphical renderer stuff
viscfg = struct; % Initialize display structure
viscfg.model = model; % Load it with a model
viscfg.h = h; % Show the control function
viscfg.Q = Q;
viscfg.x = state; % Load it with the position of the robot
viscfg.gview = gridnav_visualize(viscfg); % Show the model

pause(1); % Wait a bit to see the initial state

% Step 4: Main Loop
while ~terminalState
    action = h(state(1), state(2)); % Action to be performed based on the current state
    [state, reward, terminalState] = gridnav_mdp(model, state, action); % Perform Action
    cummulativeReward = cummulativeReward + reward;
    
    viscfg.x = state; % Update state in the display structure
    viscfg.gview = gridnav_visualize(viscfg); % Draw the new state of things
    pause(1); % Don't go too fast
end