package org.usfirst.frc.team1829.robot;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.CANTalon;

public class CarbonCANTalon extends CANTalon
{
	public static final double RAMP_STEP = 0.05;
	public static final long RAMP_TIME = 20;
	
	private Timer rampTimer;
	private boolean rampEnabled = false;
	
	private double target = 0.0;
	private double rampStep = RAMP_STEP; //default values
	private long rampTime = RAMP_TIME; //default values
	
	public CarbonCANTalon(int deviceNumber, double step, long time) 
	{	
		super(deviceNumber);
		target = 0.0;
		rampStep = step;
		rampTime = time;
		rampTimer = new Timer();
		rampTimer.schedule(new RampTask(), 0, rampTime);
	}
	
	public CarbonCANTalon(int deviceNumber)
	{
		this(deviceNumber, RAMP_STEP, RAMP_TIME);
	}
	
	public void setRampEnabled(boolean enabled)
	{
		if(rampEnabled == enabled)
		{
			return;
		}
		
		rampEnabled = enabled;
		if(rampEnabled)
		{
			rampTimer.schedule(new RampTask(), 0, rampTime);
		}
		else
		{
			rampTimer.cancel();
		}
	}
	
	public void setRampStep(double size)
	{
		if(size < 0)
		{
			size = 0;
		}
		
		this.rampStep = size;
	}
	
	public double getRampStep()
	{
		return this.rampStep;
	}
	
	public void setRampTime(long time)
	{
		if(time < 20)
		{
			time = 20;
		}
		
		this.rampTime = time;
	}
	
	public long getRampTime()
	{
		return this.rampTime;
	}
	
	/**
	 * Sets motor speed, applying a ramp if
	 * it's enabled.
	 */
	public void set(double power)
	{
		if(rampEnabled)
		{
			this.target = power;
		}
		else
		{
			_set(power);
		}
	}
	
	//Calls super.set, use inside with ramp
	private void _set(double power)
	{
		super.set(power);
	}
	
	/**
	 * Thread that runs continuously in order to process the 
	 * ramp of this CarbonCANTalon.
	 * @author Nick Mosher, Team 1829 Carbonauts Captain
	 */
	public class RampTask extends TimerTask
	{
		private double currentOutput;
		
		@Override
		public void run() 
		{
			currentOutput = CarbonCANTalon.this.get();
			if(rampEnabled) 
			{
                boolean isUp = (currentOutput < target);
                double step = isUp ? RAMP_STEP : -RAMP_STEP;

                if((isUp && (currentOutput + step >= target)) || (!isUp && (currentOutput + step <= target))) 
                {
                    currentOutput = target;
                } 
                else 
                {
                    currentOutput += step;
                }
                _set(currentOutput);
	        }
			else
			{
				RampTask.this.cancel();
			}
		}
	}
}
