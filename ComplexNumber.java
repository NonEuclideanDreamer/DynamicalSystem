//***************************************************************************************************************
// Author: Non-Euclidean Dreamer
// Gathering handy methods to deal with Complex Numbers
//****************************************************************************************************************


public class ComplexNumber 
{
	public double x,y,r,phi;//Attributes: real part, imaginary part, radius, argument
	
	//Constants: often used numbers
	public static ComplexNumber one=new ComplexNumber(1,0), i=new ComplexNumber(0,1),
			zero=new ComplexNumber(0,0), half=new ComplexNumber(0.5,0),
			mi= new ComplexNumber(0,-1),mone=new ComplexNumber(-1,0);
	
	public ComplexNumber(double x0,double y0)
	{
		x=x0;
		y=y0;
		r=Math.sqrt(x0*x0+y0*y0);
		phi=Math.atan2(y,x);
		if(phi>Math.PI)phi-=2*Math.PI;//I prefer discontinuity to be left...
	}
	

	public static ComplexNumber polar(double r0,double phi0)
	{
		ComplexNumber out=new ComplexNumber(r0*Math.cos(phi0),r0*Math.sin(phi0));
		out.phi=phi0;
		return out;
	}
	public ComplexNumber power(ComplexNumber z)
	{
		double radial=Math.pow(r,z.x)*Math.exp(-phi*z.y);
		double angle=z.y*Math.log(r)+phi*z.x;
		return polar(radial,angle);
	}
	
	public ComplexNumber Re()
	{
		return new ComplexNumber(x,0);
	}
	
	public ComplexNumber Im()
	{
		return new ComplexNumber(0,y);
	}
	public ComplexNumber add(ComplexNumber z)
	{
		return new ComplexNumber(x+z.x,y+z.y);
	}
	public ComplexNumber acos(int branch)
	{
		return new ComplexNumber(Math.PI/2,0).add(i.times((one.subtract(this.power(2))).power(0.5).add(this.times(i)).log(branch)));
	}
	public ComplexNumber flip()
	{
		return new ComplexNumber(y,x);
	}
	public ComplexNumber times(ComplexNumber z)
	{
		return new ComplexNumber(x*z.x-y*z.y,x*z.y+y*z.x);
	}

	public ComplexNumber copy() {
		
		return new ComplexNumber(x,y);
	}

	public ComplexNumber times(double j) 
	{
		return new ComplexNumber(x*j,y*j);
	}
	public ComplexNumber divided(ComplexNumber z)
	{
		return polar(r/z.r,phi-z.phi);
	}
	public ComplexNumber power(double n)
	{
		return polar(Math.pow(r,n),n*phi);
	}
	public ComplexNumber exp()
	{
		double r0=Math.exp(x);
		double phi0=y;
		return polar(r0,phi0);
	}
	public ComplexNumber log(int branch)
	{
		return new ComplexNumber(Math.log(r),phi+2*Math.PI*branch);
	}
	public ComplexNumber sin()
	{
		return times(i).exp().subtract(times(mi).exp()).times(new ComplexNumber(0,-0.5));
	}
	public ComplexNumber cos()
	{
		return times(i).exp().add(times(mi).exp()).times(0.5);
	}
	public ComplexNumber tan()
	{
		return sin().divided(cos());				
	}
	public ComplexNumber conjugate()
	{
		return new ComplexNumber(x,-y);
	}
	public ComplexNumber asin()
	{
		return mi.times((i.times(this).add((one.subtract(this.times(this))).power(0.5))).log(0));
	}
	public ComplexNumber acos()
	{
		return mi.times((this.add(i.times((one.subtract(this.times(this))).power(0.5)))).log(0));
	}
	public ComplexNumber atan(int branch)
	{
		return new ComplexNumber(0,0.5).times((one.subtract(this.times(i))).log(branch).subtract((one.add(i.times(this))).log(branch)));
	}
	public ComplexNumber atanh(int branch)
	{
		return add(one).log(branch).subtract(one.subtract(this).log(branch)).times(half);
	}
	public String toString()
	{
		return x+"+"+y+"i";
	}
	public ComplexNumber asinh()
	{
		return power(2).add(one).power(0.5).add(this).log(0);
	}
	public ComplexNumber acosh()
	{
		return one.subtract(power(2)).power(0.5).add(this).log(0);
	}
	public ComplexNumber tanh()
	{
		return (exp().subtract((zero.subtract(this)).exp())).divided(this.exp().add((zero.subtract(this)).exp()));
	}
	public ComplexNumber subtract(ComplexNumber z)
	{
		return new ComplexNumber(x-z.x,y-z.y);
	}
	public ComplexNumber sinh() 
	{
		
		return (exp().subtract(times(mone).exp())).times(half);
	}
	public ComplexNumber cosh()
	{
		return (exp().add(times(mone).exp())).times(half);
	}
	
	public double norm()
	{
		return Math.sqrt(x*x+y*y);
	}

	public void print()
	{
		System.out.print(x);
		System.out.print("+");
		System.out.print(y);
		System.out.print("i");
	}


	public ComplexNumber mod(double r)
	{
		return new ComplexNumber((x/r-(int)(x/r))*r,(y/r-(int)(y/r)*r));
	}
	public String toString(int ex)
	{
		if (norm()==0) return"";
		String out="";
		if(x!=0) 
		{	if(y!=0) out="(";
			
			out=out.concat(Double.toString(Math.rint(x*Math.pow(10, ex))/Math.pow(10, ex)));
			if(y>0)out=out.concat("+");
		}
		if(y!=0)
		{
			out=out.concat(Double.toString(Math.rint(y*ex)/(1.0*ex))+"i");
			if(x!=0)out=out.concat(")");
		}
		return out;
	}

	public ComplexNumber floor(boolean x0, boolean y0, int digits)
	{
		double x1=x;if(x0) x1=Math.floor(x1*Math.pow(10, digits))/Math.pow(10, digits);
		double y1=y;if(y0) y1=Math.floor(y1*Math.pow(10, digits))/Math.pow(10, digits);
		
		return new ComplexNumber(x1,y1);
	}


	public preciseCN precise() 
	{
		int precision=preciseFloat.precision,
				dec=preciseFloat.dec;
		double ix=Math.abs(x),
				yps=Math.abs(y);
		int xsign=(int)Math.signum(x),
				ysign=(int)Math.signum(y);
		int[] xdig=new int[precision],
				ydig=new int[precision];
		xdig[preciseFloat.dec]=(int)ix;
		ydig[preciseFloat.dec]=(int)yps;
		for(int i =1;i<precision-dec;i++)
		{
			ix=ix%1;
			yps=yps%1;
			ix*=1000000000;
			yps*=1000000000;
			xdig[dec+i]=(int)ix;
			ydig[dec+i]=(int)yps;
		}
		return new preciseCN(new preciseFloat(xsign,xdig),new preciseFloat(ysign,ydig));
	}
	
}