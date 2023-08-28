import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.imageio.ImageIO; 

public class DynamicalSystem 
{
	
	static String colorSymmetry="e", //"v","f","e":vertex-, face- or edgesymmetric in rgb-cube, "horizontal f", horizontal v": colors repeat along x (mostly for periodic(trig) functions)
			type="png",//output format
					func="(x,y)=(", tion=")";//annotation before & after current parameter value of d
			static boolean lambdaSpace=false; //Haven't gotten anything usable for "true", but who knows?
			static double[] loc= {0.01,1.995}; //center of screen
			static int start=450, //start of numeration of outpupics
					steps=200, //number of generated pics
					width=2560, height=1440, //picture dimensions
					maxit=30, 
					black=Color.black.getRGB();
			static double zoom=.3405, 
					factor=4.0/zoom/height,//do not change needed to zoom correctly
					colorwidth=1,//size of square where color varies( after that radially constant)
					t=0, d=0,e=0,f=0, g=1.24,h=-1.32;//parameters that can be used in the function & be changed...
			static String name0="b"/*+zoom/*+" ,loc("+loc[0]+","+loc[1]+")"*/;
			static DecimalFormat df=new DecimalFormat("0000"), p=new DecimalFormat("0.0");//df for filename, p for annotation

			//The function, change as needed
			public static ComplexNumber f(ComplexNumber z) 
			{			
				return new ComplexNumber(0,0);//Play around, or use some of the below
				//new ComplexNumber(Math.abs((d*z.x)%z.y),+z.x);//experiment
				//	double y=(1+e)*z.y+d*Mathd*.abd*s(z.x)*(Math.abs(z.x)-1)+f*Math.abs(z.x)*z.y; return new ComplexNumber(z.x+y,y);//bogdanov
				//if(z.x<0.5)return new ComplexNumber(2*z.x,0.5*z.y); else return new ComplexNumber(2-2*z.x,1-0.5*z.y);//bakerfolded
				//2*z.x-(int)(2*z.x+2)+2,(z.y+(int)(2*z.x+2)-2)*0.5);//baker unfolded
				//((2*z.x+z.y)%1+1)%1,((z.x+z.y)%1+1)%1);//arnoldscat
				//-z.y+t*(1+Math.abs(z.x)),z.x);//Gingerbread
					
	}

	//what location gives what color
	//one can change the signs +- in the definitions of x,y(,z) as well as theit order in the return statement for dirrering results
	public static int colormap(ComplexNumber z)
	{
		double factor=1;
		if(z.r<colorwidth&& (colorSymmetry.length()==1)) {factor=z.r*factor/colorwidth;} 
	
		if(colorSymmetry.equals("v"))//bw:+++ rc:-++ gm:+-+ by: ++-
		{
			double a=Math.cos(z.phi)*Math.sqrt(3)-Math.sin(z.phi),b=2*Math.sin(z.phi),c=-Math.cos(z.phi)*Math.sqrt(3)-Math.sin(z.phi),norm=Math.max(Math.abs(c),Math.max(Math.abs(a),Math.abs(b)))/factor;
			int x=(int)Math.max(0, Math.min((128-128*a/norm), 255)),y=(int)Math.max(0, Math.min(128+128*b/norm,255)),w=(int)Math.max(0, Math.min(128-128*c/norm,255));
			return new Color(x,y,w).getRGB();
		}
		else if(colorSymmetry.equals("f"))//w where constant
		{
			double a=Math.cos(z.phi),b=Math.sin(z.phi),norm=Math.max(Math.abs(a),Math.abs(b))/factor;
			int x=(int)Math.max(0, Math.min((128+128*a/norm), 255)),y=(int)Math.max(0, Math.min(128+128*b/norm,255)),w=128;
			return new Color(w,x,y).getRGB();
		}
		else if(colorSymmetry.equals("e"))//x different from y,w
		{
			double a=Math.cos(z.phi), b=Math.sin(z.phi),norm=Math.max(Math.abs(a), Math.abs(b))/factor;
			int x=(int)Math.max(0, Math.min((128-128*a/norm), 255)),y=(int)Math.max(0, Math.min(128+128*b/norm,255)),w=(int)Math.max(0, Math.min(128-128*b/norm,255));
			return new Color(w,x,y).getRGB();
		}
		else if(colorSymmetry.equals("horizontal f"))
		{	
			int w=(int) Math.max(0, Math.min(255,128+z.y/colorwidth*factor)),x,y,edge=(int)(((z.x+1000*colorwidth)%colorwidth)/(colorwidth/4.0)),pos=(int)Math.max(0,Math.min(255,(((z.x+1000*colorwidth)%(colorwidth/4.0))/(colorwidth/4.0)-0.5)*factor*256+128));
			
			if(edge==0||edge==3)x=(int)(128-factor*127.9); else x=(int) (128*(1+factor*0.999999));
			
			if(edge<2)y=pos; else y=255-pos;
		
			if (edge%2==0)return new Color(w,x,y).getRGB(); else return new Color(w,y,x).getRGB();
		}
		else if(colorSymmetry.equals("horizontal e"))
		{	
			double a=z.x, b=z.y%colorwidth, norm=Math.max(Math.abs(a), Math.abs(b))/factor;
			int x=(int)Math.max(0, Math.min((128+128*a/norm), 255)),y=(int)Math.max(0, Math.min(128-128*b/norm,255)),w=(int)Math.max(0, Math.min(128+128*b/norm,255));
			return new Color(w,y,x).getRGB();
		}
		
		else return 0;
	}
	public static void main(String[] args) 
	{	
		ComplexNumber one=ComplexNumber.one, zero=ComplexNumber.zero;
		/*	average=new ComplexNumber[width][height];
		for(int i=0;i<width;i++)
			for(int j=0;j<height;j++)
				average[i][j]=zero.copy();*/
		int counter=start; 
		double fc=Math.pow(4.0/3, .005);//.1/(Math.pow(1.1, 200)-1);
		System.out.println("factor="+fc);
		ComplexNumber z0=new ComplexNumber(-0.01,0.0005);//for lambdeSpace. exact zero likes to blow up
	
		for(int b=0;b<steps; b++)
		{
			System.out.println();System.out.print("t="+b);
		
		//Change zoom:
		//zoom/=fc;System.out.print("zoom="+zoom);factor=4/zoom/height;
	
		//Change range of colorgradient:
		//	colorwidth/=1.001;

		
		//change location:
		//	loc[0]-=0.003;
		//loc[1]-=0.04;
			
		//change parameter values:	
		d+=.04;System.out.print(name0+"d="+d);//Math.pow(1.1, b)/10000.0;//*fc;
		//f-=0.01;
		
		//Change Number of iterations:
		//	maxit++;//
		
	
		BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
	
		for(int i=0;i<width;i++)
		{ 
			System.out.print(".");
		
			for(int j=0;j<height;j++)
			{
				ComplexNumber z;int l=0;
			if(lambdaSpace)
			{
			
			
				ComplexNumber kay=new ComplexNumber(loc[0]+factor*(i-width/2.0),loc[1]+factor*(j-height/2.0));z=zero;//z0.copy();new ComplexNumber(0,0.01);//ComplexNumber.zero;//kay.copy().times(t);//.subtract(ComplexNumber.one);//ComplexNumber.i;///
				d=kay.x;e=kay.y-0.5;
				while(l<maxit)
				{
					z=f(z);
					l++;
				}
			
			}
			else
			{
				z=new ComplexNumber(loc[0]+factor*(i-width/2.0),loc[1]+factor*(j-height/2.0)); 
				ComplexNumber kay=new ComplexNumber(t, 0);
				while(l<maxit)
				{
					z=f(z);
					
					l++;
					//average[i][j]=av(average[i][j],z);
					int col=colormap(z);//average[i][j]);
					image.setRGB(i,j,col);
				}
			}
			}
				System.out.println();
		}
		Writing.write(image, func+p.format(d)+tion, 40, 40, 40);
		File outputfile = new File(name0+df.format(counter)+"."+type);
		try 
		{  
			ImageIO.write(image, type, outputfile);
		} 
		catch (IOException blob) 		
		{
			System.out.println("IOException");
			blob.printStackTrace();
		}
		counter++;
		}
	}

}