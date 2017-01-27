function [trans] = stepTransmission(Px, Py, Gx, Gy, range, rate)
%stepTransmission The simplest transmission function I could think of.
%The transmission rate is max if the target is in radius, and it is
%0 if the target is not.
%   
%Input parameters:
%   Px - X position of the target
%   Py - Y position of the target
%   Gx - X position of transmitter
%   Gy - Y position of transmitter
%   dist - transmitter range
%   rate - transmitter transmit rate
%Ouput parameters:
%   trans - the transmission rate

if sqrt((Gx - Px)^2 + (Gy - Py)^2) <= range
    % Target is in range of the transmitter
    trans = rate;
else
    % Target is out of range
    trans = 0;
end

end

