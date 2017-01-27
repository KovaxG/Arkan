function [xplus, rplus, terminal] = transnav_mdp(m, x, u)
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here

% Check if terminal state
if isequal([x.Px; x.Py], m.terminalPosition)
    terminal = 1;
    xplus = x;
    rplus = 0;
    return;
end

% Change in states
xplus.Px = x.Px + u.Vx * m.Ts;
xplus.Py = x.Py + u.Vy * m.Ts;
xplus.b  = x.b - m.Ts * m.t(xplus.Px, xplus.Py);

rplus = m.r(x, u);
terminal = 0;

end

