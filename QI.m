function [Q, h] = QI(model, gamma, error)
%QI Calculates the best policy(h) and Q using the "Q Iteration" method
%   model - The model of the system
%   gamma - The horizont of the control
%   e     - Maximum error before stopping the iteration
% Implemented by Kovacs Gyorgy

n1 = model.size(1); % The number of possible states 1
n2 = model.size(2); % The number of possible states 2
m = length(model.U{1}); % The number of possible actions

Qo = zeros(n1, n2, m);
Qchange = ones(n1, n2, m) * 999; % Random value to make the original error non null
                                 % If MatLab had a do {} while(cond)
                                 % structure, this would not be necessary

while abs(max(max(max(Qchange)))) > error
    Qn = zeros(n1, n2, m); % Initialize new Qn
    for x1 = 1:n1
        for x2 = 1:n2
            for u = 1:m
                % Get reward and next state
                [xnew, r, ~] = gridnav_mdp(model, [x1; x2], u);
                Qn(x1, x2, u) = r + gamma * max(Qo(xnew(1), xnew(2), :));
            end
        end
    end
    
    Qchange = Qn - Qo; % Used to get the biggest change
    Qo = Qn; % The current value will be the old value
end

Q = Qn; % Output Q is assigned here

% Get the optimal policy
h = zeros(n1, n2);
for x1 = 1:n1
    for x2 = 1:n2
        [~, ind] = max(Qn(x1, x2, :));
        h(x1, x2) = ind;
    end
end

end

