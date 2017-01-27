function [r] = basicRewardFunction(x, u, g)
%UNTITLED4 Summary of this function goes here
%   Detailed explanation goes here

w1 = 1;
w2 = 1;
w3 = 5;

X = [x.Px; x.Py; x.b];
G = [g.Px; g.Py; g.b];
U = [u.Vx; u.Vy];

W = [0, 0, w1; 0, w2, 0; 0, 0, w3];

r = -(X - G)' * W * (X - G);
end

