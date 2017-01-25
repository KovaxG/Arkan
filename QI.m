function [Q, h] = QI(model, gamma, e_qiter)
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here

n = prod(model.size); % The number of possible states
m = length(model.U{1}); % The number of possible actions
k = 0;

Qo = zeros(n, m);
Qchange = ones(n, m) * 999;

% Because I want to go through all the states, using 
% these functions I can convert from a number to an actual state
% TODO Change Q, so that it has three dimensions instead of two.
% After checking again, it seems like this was a stupid mistake, 
% because Q should have a size of (5, 5, 4), not (25, 4)...
getRow = @(x) floor(((x - 1) / 5) + 1);
getCol = @(x) x - (5 * (getRow(x) - 1));
getNum = @(x) (x(1) - 1)* 5 + x(2);

while max(max(Qchange)) > e_qiter
    Qn = zeros(n, m); % Initialize new Q
    for x = 1:n
        for u = 1:m
            % Get reward and next state
            [x2, r, final] = gridnav_mdp(model, [getCol(x); getRow(x)], u);
            if 1 %~(x == getNum(x2))
                % Skip if hit obstacle
                Qn(x,u) = r + gamma * max(Qo(getNum(x2), :));
            end
        end
    end
    %Qn
    
    Qchange = Qn - Qo; % Used to get the biggest change
    Qo = Qn; % The current value will be the old value
    k = k + 1;
end

Q = Qn; % Output Q is assigned here

% Get the optimal policy (hopefully)
h = zeros(model.size);
for i = 1:n
    [val, ind] = max(Qn(i, :));
    h(getCol(i), getRow(i)) = ind;
end

end

