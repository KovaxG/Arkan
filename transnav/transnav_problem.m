function out = transnav_problem(mode, varargin)
% [Insert description here]
%   OUT = TRANSNAV_PROBLEM(MODE, [PARAM1, [PARAM2, ...]])

switch mode,

    % Offer basic info about the model (without creating the actual model)
    case 'info',    
        info.id = 'transnav';               % an identifier
        info.problem = @transnav_problem;   % handle to model function
        info.det = 1;                      % can be either deterministic or stochastic
        info.p = 3;                        % # of state variables
        info.q = 2;                        % # of control variables
        
        out = info;
        
    % create the model
    case 'model',
        model.p = 3;                        % number of states
        model.q = 2;                        % number of control inputs (actions)
        model.det = 1;
        model.Ts = 1;                       % no sample time per second, just a discrete time index
        model.fun = 'transnav_mdp';          % MDP function

        
        % configurable properties              
        CFG.size = [10, 10];
        CFG.transmitter = [5; 5]; % The location of the transmitter
		CFG.goal = [10; 10]; % The location of the final goal
        CFG.minSpeed = [-10, -10]; % The maximum negative speed
        CFG.maxSpeed = [10, 10]; % The maxium positive speed
        CFG.transmissionFunction = @(Px, Py) stepTransmission(Px, Py, CFG.goal(1), CFG.goal(2), 5, 1);
        goal = struct;
        goal.Px = CFG.goal(1);
        goal.Py = CFG.goal(2);
        goal.b = 0;
        CFG.rewardFunction = @(x, u) basicRewardProblem(x, u, goal);
        
        % Handle input configuration here later
        cfg = CFG;
        
        model.transmitter = cfg.transmitter;
        model.terminalPosition = cfg.goal;
        
        model.r = cfg.rewardFunction;
        model.t = cfg.transmissionFunction;
        
        model.minPx = 0;
        model.minPy = 0;
        model.maxPx = cfg.size(1);
        model.maxPy = cfg.size(2);
        
        model.minVx = cfg.minSpeed(1);
        model.minVy = cfg.minSpeed(2);
        model.maxVx = cfg.maxSpeed(1);
        model.maxVy = cfg.maxSpeed(2);
        
        out = model;
end % End of switch 
end % End of function