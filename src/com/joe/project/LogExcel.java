package com.joe.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@Component
public class LogExcel {
	
	
	public LogExcel()
	{
		System.out.println("Creating LogExcel Class");	
	}
	
	/* Returns a string representing a row of data read from Excel */
	public String devolverObjeto( Sheet sheet1,int row, int nFields)
	{
		String str_aux="";
			for(int i=3; i<nFields+3; i++)
			{
				Cell aux = sheet1.getCell(i, row);
				
				if( i == nFields+2 )
					str_aux=str_aux + aux.getContents();
				else
					str_aux = str_aux+aux.getContents()+"_";
			}
		return str_aux;
	}
	
	public List<String> readExcel()
	{
		List<String> logExcel = new ArrayList<String>();
		int renglones=0;
		int nFields=0;
		String dataRead = "";
		
		
		try {
            //Creates an object workbook from File specified
            Workbook wrk1 =  Workbook.getWorkbook(new File("/Users/Sabluj/Desktop/sample2.xls"));
             
            //Gets reference to the first element in the excel sheet
            Sheet sheet1 = wrk1.getSheet(0);
           
            //Get info from cells (column,row)
            Cell A1 = sheet1.getCell(0, 0);
    		renglones = Integer.parseInt(A1.getContents())+2;  
            
            for(int j=2; j<renglones;j++)
            {
            		Cell A3 = sheet1.getCell(0, j);
            		String user = A3.getContents();
            		
            		Cell B3 = sheet1.getCell(1, j);
            		String action = B3.getContents();
            		
            		Cell C3 = sheet1.getCell(2, j);
            		String table = C3.getContents();
            		
            		//System.out.println("Log:"+user+"| "+action+"| "+table+"\n" );
            		
            		/** Validation of the operations read from Excel **/
            		if( action.equalsIgnoreCase("insert") )
            		{
            			switch(table){
            				case "ALUMNO":
            					nFields=6;
            					dataRead="RegisterStudent_"+devolverObjeto(sheet1,j,nFields);
            					logExcel.add(dataRead);
            					break;
            				case "PROFESOR":
            					nFields=7;
            					dataRead="Register_"+devolverObjeto(sheet1,j,nFields);
            					logExcel.add(dataRead);
            					break;
            				default: 
            					System.out.println("Invalid table "+table+" at row "+j+" \n");
	
            			}
            		}
            		else if(action.equalsIgnoreCase("update"))
            		{
            				if  (table.equalsIgnoreCase("DEPARTAMENTOSAD"))
            				{
                				nFields=2;
            					dataRead=devolverObjeto(sheet1,j,nFields);
            					logExcel.add(dataRead);
            				}
            				else
            					System.out.println("Invalid table "+table+" at row "+j+" \n");			
            		}
            		else
            		{
            			System.out.println("Invalid operation "+action+" at row "+j+" \n");
            		}
            }
             
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		 return logExcel;
	}
	
}
