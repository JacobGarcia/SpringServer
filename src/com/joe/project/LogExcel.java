package com.joe.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

public class LogExcel {
	public LogExcel()
	{
		System.out.println("Creating LogExcel");	
	}
	
	public String devolverObjeto( Sheet sheet1,int row, int nFields)
	{
		//List<String> regos = new ArrayList<LogEntry>();
		String str_aux="";
			for(int i=3; i<nFields+3; i++)
			{
				Cell aux = sheet1.getCell(i, row);
				
				if( i == nFields+2 )
					str_aux=str_aux + aux.getContents();
				else
					str_aux = str_aux+aux.getContents()+"_";
			}
		System.out.println(str_aux+"*");
		return str_aux;
	}
	
	public List<String> readExcel()
	{
		List<String> logExcel = new ArrayList<String>();
		int renglones=0;
		int nFields=0;
		String dataRead = "";
		
		try {
            //Crear un objeto workbook del archivo especificado 
            Workbook wrk1 =  Workbook.getWorkbook(new File("/Users/Sabluj/Desktop/sample2.xls"));
             
            //Obtener referencia a la primera pagina del libro de excel
            Sheet sheet1 = wrk1.getSheet(0);
           
            //Obtener informacion de la celda
            Cell A1 = sheet1.getCell(0, 0);
    		renglones = Integer.parseInt(A1.getContents())+2;  
            
            for(int j=2; j<renglones;j++)
            {
            		Cell A3 = sheet1.getCell(0, j);// Formato Cell (columna,renglon)
            		String user = A3.getContents();
            		
            		Cell B3 = sheet1.getCell(1, j);
            		String action = B3.getContents();
            		
            		Cell C3 = sheet1.getCell(2, j);
            		String table = C3.getContents();
            		
            		
            		System.out.println("Log:"+user+"| "+action+"| "+table+"\n" );
            		
            		if( action.equals("insert") )
            		{
            			switch(table){
            				case "ALUMNO":
            					nFields=6;
            					dataRead=devolverObjeto(sheet1,j,nFields);
            					logExcel.add(dataRead);
            					break;
            				case "PROFESOR":
            					nFields=7;
            					dataRead=devolverObjeto(sheet1,j,nFields);
            					logExcel.add(dataRead);
            					break;
            				default: 
            					System.out.println("Invalid table "+table+" at row "+j+" \n");
            				
            					
            			}
            		}
            		else if(action.equals("update"))
            		{
            				if  (table.equals("DEPARTAMENTOSAD"))
            				{
                				nFields=2;
            					dataRead=devolverObjeto(sheet1,j,nFields);
            					logExcel.add(dataRead);
            					System.out.println("Leyo correctamente:\n"+dataRead);
            				}
            				else
            					System.out.println("Invalid table "+table+" at row "+j+" \n");			
            		}
            		else
            		{
            			System.out.println("Invalid operation "+action+" at row "+j+" \n");
            		}
            
            		 
            		//LogEntry newLogEntry=new LogEntry(str_A3,str_B3, str_C3);
                    //logExcel.add(newLogEntry);
                    
                    //System.out.println("Nombre de objeto"+j+":"+newLogEntry.toString());
            		
            		
            }
             
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		 return logExcel;
	}
	
	
	/*public void writeExcel( List<LogEntry> usersList)
	{
	  try 
	  {
		File excelFile = new File("/Users/Sabluj/Desktop/LogTable.xls");
		WritableWorkbook writableWorkbook = Workbook.createWorkbook(excelFile);
		WritableSheet writableSheet = writableWorkbook.createSheet("LogTable", 0);
		
		//Especificacion de fuentes y formato de celdas
		WritableFont headersFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
		WritableCellFormat headersFormat = new WritableCellFormat(headersFont);
		WritableFont registersFont= new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD);
		WritableCellFormat registersFormat = new WritableCellFormat(registersFont);

        //Boolean bool = new Boolean(3, 0, true);
        //Number num = new Number(4, 0, 9.99);
        
        //Agregar encabezados a archivo
        //writableSheet.addCell(new Label(1, 0, "Registros leidos",headersFormat));  			//Se especifican coordenadas en el contructor
        writableSheet.addCell(new Label(3, 0, "Version",headersFormat ));
        writableSheet.addCell(new DateTime(2, 0, new Date() ));
        writableSheet.addCell(new Label(0, 1, "user", headersFormat ));
        writableSheet.addCell(new Label(1, 1, "action",headersFormat ));
        
        int i=2;
      //Agregar lista de usuarios a archivo
		for (LogEntry user : usersList) 
		{
			writableSheet.addCell(new Label (0,i,user.getUser(),registersFormat));
			writableSheet.addCell(new Label (1,i,user.getAction(),registersFormat));
			i++;
		}

       //Write and close the workbook
        writableWorkbook.write();
        writableWorkbook.close();
        
	 } catch (IOException e) {
         e.printStackTrace();
     } catch (RowsExceededException e) {
         e.printStackTrace();
     } catch (WriteException e) {
         e.printStackTrace();
     }
 }*/
}
