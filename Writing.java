import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Writing 
{
	
	public static int black=Color.black.getRGB(),
			white=Color.white.getRGB();
	static int write(BufferedImage canvas, String text, int x, int y, int size)
	{
		int locx=x,locy=y, out=0,end;
		if( text.isEmpty())return locx;
			char ch=text.charAt(0);
			if(ch=='^')
			{
				end=text.indexOf('}');
				String txt=text.substring(2, end);
				locx=write(canvas,txt,x,y,size/2);
				
			}
			else 
			{
				locx+=writechar(canvas,ch ,locx,locy,size,white,black);
				end=0;
			}
		return 	write(canvas,text.substring(end+1),locx,locy,size);
			
		
	}

	private static int writechar(BufferedImage canvas, char ch, int locx, int locy, int size,int color,int col2) 
	{
		System.out.print(ch);
		String c="";
		//digital display
		if(ch==' ')return size;
		if(ch=='.')c+="dot";
		else if(ch=='|')c+="abs";
		else c+=ch;
		File letterFile=new File(c+".bmp");
		BufferedImage letter;
		try {
			letter = ImageIO.read(letterFile);	
			double factor=size/400.0;
		for(int i=0;i<letter.getWidth();i++)
			for(int j=0;j<letter.getHeight();j++)
				if(letter.getRGB(i,j)==black) {canvas.setRGB((int)((i+20)*factor)+locx, (int)((j+15)*factor)+locy, col2);canvas.setRGB((int)(i*factor)+locx, (int)(j*factor)+locy, color);}
			return (int) (letter.getWidth()*factor);} 
		catch (IOException e) {
			System.out.println("Can't read "+ch);
			e.printStackTrace();
		}
		
		return 0;
	}
	
}
