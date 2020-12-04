import java.lang.*;
import java.util.*;
import java.io.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;

class Main
{
	public static void main(String args[])
	{
		Scanner sobj=new Scanner(System.in);

		while(true)
		{
			System.out.println("Enter Your Choice");
			System.out.println("1 : Packing");
			System.out.println("2 : Unpacking");
			System.out.println("3 : Exit");

			String Dir,Filename;
			int choice=0;
			choice=sobj.nextInt();

			switch(choice)
			{
				case 1 :
					System.out.println("Enter Directory Name");
					Dir=sobj.next();
					System.out.println("Enter the file name for packing");
					Filename=sobj.next();
					Packer pobj=new Packer(Dir,Filename);
					break;

				case 2 :
					System.out.println("Enter Packed File Name");
					String name=sobj.next();
					Unpacker obj=new Unpacker(name);
					break;

				case 3 :
					System.out.println("Thank you for using the Application\n");
					System.exit(0);
					break;

				default :
					System.out.println("Invalid Choice");
					break;
			}
		}
	}
}

class Packer
{
	//Object For File Writing
	public FileOutputStream outstream=null;

	//Parametrised Constructor
	public Packer(String FolderName,String FileName)
	{
		try
		{
			System.out.println("Inside Packer Constructor");
			//Create New File For Package
			File outfile=new File(FileName);
			outstream=new FileOutputStream(FileName);

			//Set the current working directory for folder traversal
			//System.setProperty("user.dir",FolderName);

			TravelDirectory(FolderName);
		}
		catch(Exception obj)
		{
			System.out.println(obj);
		}
	}

	public void TravelDirectory(String path)
	{
		File directoryPath=new File(path);

		//Get all file names from directory
		File arr[]=directoryPath.listFiles();

		for(File filename:arr)
		{
			//System.out.println(filename.getName());
			if(filename.getName().endsWith(".txt"))
			{
				PackFile(filename.getAbsolutePath());
			}
		}
		System.out.println("Packing Completed SucessFully");
	}

	public void PackFile(String FilePath)
    {
        byte Header[] = new byte[100];
        byte Buffer[] = new byte[1024]; 
        int length=0;
		FileInputStream istream=null;

        File fobj = new File(FilePath);
        String temp = FilePath+" "+fobj.length();
        
        // Create header of 100 bytes
        for(int i = temp.length(); i< 100; i++)
        {
            temp = temp + " ";
        }        
        Header=temp.getBytes();
       	
       	try
       	{
       		//Open File For Reading
       		istream=new FileInputStream(FilePath);

       		outstream.write(Header,0,Header.length);
       		while((length = istream.read(Buffer))>0)
       		{
       			outstream.write(Buffer,0,length);
       		}
       		istream.close();
       	}
       	catch(Exception obj)
       	{
       		System.out.println(obj);
       	}
    }
}

class Unpacker
{    
    public FileOutputStream outstream = null;
    public Unpacker(String src)
    {
        System.out.println("Inside unpacker");
        unpackFile(src);
    }
    public void unpackFile(String FilePath)
    {
        try
        {
        	System.out.println(FilePath);
            FileInputStream instream = new FileInputStream(FilePath);
            byte Header[] = new byte[100];
            int length = 0;
            while((length = instream.read(Header,0,100)) > 0)
            {
                String str = new String(Header);
                System.out.println(str);
                String ext = str.substring(str.lastIndexOf("\\"));
                System.out.println(ext);
                ext = ext.substring(1);
                String words[] = ext.split("\\s");
                String name = words[0];
                int size = Integer.parseInt(words[1]);
                byte arr[] = new byte[size];
                instream.read(arr,0,size);
                FileOutputStream fout = new FileOutputStream(name);
                fout.write(arr,0,size);
            }
        }
        catch(Exception obj)
        {
        	System.out.println(obj);
        }
    }
}