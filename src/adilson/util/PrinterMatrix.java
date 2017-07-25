package adilson.util;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class PrinterMatrix
{
  protected String[][] page;
  
  public void setOutSize(int lin, int col)
  {
    this.page = new String[lin][col];
  }
  
  public void printCharAtCol(int lin, int colunaIn, int colunaEnd, String text)
  {
    for (int i = 0; i < colunaEnd; i++) {
      if ((i >= colunaIn - 1) && (i <= colunaEnd - 1)) {
        this.page[(lin - 1)][i] = text;
      }
    }
  }
  
  public void printCharAtLin(int linhaIn, int linhaEnd, int coluna, String text)
  {
    for (int i = 0; i < linhaEnd; i++) {
      if ((i >= linhaIn - 1) && (i <= linhaEnd - 1)) {
        this.page[i][(coluna - 1)] = text;
      }
    }
  }
  
  public void printTextLinCol(int lin, int coluna, String text)
  {
    for (int i = 0; i < coluna; i++) {
      if (i == coluna - 1) {
        this.page[(lin - 1)][i] = text;
      }
    }
  }
  public void printCharAtLinCol(int linIn, int linEnd, int colunaIn, int colunaEnd, String text)
  {
    for (int i = 0; i < linEnd; i++) {
      if ((i >= linIn - 1) && (i <= linEnd - 1)) {
        for (int c = 0; c < colunaEnd; c++) {
          if ((c >= colunaIn - 1) && (c <= colunaEnd - 1)) {
            this.page[i][c] = text;
          }
        }
      }
    }
  }
  
  protected void printTextWrap(int linI, int linE, int colI, int colE, String text)
  {
    int textSize = text.length();
    int limitH = linE - linI;
    int limitV = colE - colI;
    int wrap = 0;
    if (textSize > limitV)
    {
      wrap = textSize / limitV;
      if (textSize % limitV > 0) {
        wrap += 1;
      }
    }
    else
    {
      wrap = 1;
    }
    
    String[] wraped = new String[wrap];
    
    int end = 0;
    int init = 0;
    for (int i = 1; i <= wrap; i++)
    {
      end = i * limitV;
      if (end > text.length()) {
        end = text.length();
      }
      wraped[(i - 1)] = text.substring(init, end);
      System.out.println("wraped[" + (i - 1) + "]:" + wraped[(i - 1)]);
      init = end;
    }
    for (int b = 0; b < wraped.length; b++) {
      if (b <= linE)
      {
        this.page[linI][colI] = wraped[b];
        System.out.println("page[" + linI + "][" + colI + "]:" + this.page[linI][colI]);
        linI++;
      }
    }
  }
  
  public void show()
  {
    for (int i = 0; i < this.page.length; i++)
    {
      for (int b = 0; b < this.page[i].length;)
      {
        String tmp = this.page[i][b];
        if ((tmp != null) && (!tmp.equals("")))
        {
          int size = tmp.length();
          
          b += size;
        }
        else
        {
          System.out.print(" ");
          b++;
        }
      }
      System.out.println();
    }
  }
  
  public String toFile(String fileName)
  {
    try
    {
       File file = new File( System.getProperty("user.home") + "\\" + fileName);
       
       String path = file.getAbsolutePath();
       
       System.out.println(System.getProperty("user.home") + " - " + path);
        //FilePermission permission = new FilePermission(file.getAbsolutePath(), "read");
       file.setReadable(true);
       file.setWritable(true);
   
      FileOutputStream fo = new FileOutputStream(file);

      for (int i = 0; i < this.page.length; i++)
      {
        for (int b = 0; b < this.page[i].length;)
        {
          String tmp = this.page[i][b];
          if ((tmp != null) && (!tmp.equals("")))
          {
            int size = tmp.length();
            
            b += size;
            fo.write(tmp.getBytes());
          }
          else
          {
            fo.write(" ".getBytes());
            b++;
          }
        }
        fo.write("\n".getBytes());
      }
      fo.flush();
      fo.close();
      
      
      file = null;
      
      return path;
    }
    catch (Exception e)
    {
        
      e.printStackTrace();
    }
    
    return "error";
  }
  
  public void toPrinter(String printerName)
  {
    try
    {
      FileOutputStream fo = new FileOutputStream(printerName);
      for (int i = 0; i < this.page.length; i++)
      {
        for (int b = 0; b < this.page[i].length;)
        {
          String tmp = this.page[i][b];
          if ((tmp != null) && (!tmp.equals("")))
          {
            int size = tmp.length();
            
            b += size;
            fo.write(tmp.getBytes());
          }
          else
          {
            fo.write(" ".getBytes());
            b++;
          }
        }
        fo.write("\n".getBytes());
      }
      fo.flush();
      fo.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void toPrinter(String printerName, byte[] escCommands)
  {
    try
    {
      FileOutputStream fo = new FileOutputStream(printerName);
      
      fo.write(escCommands);
      for (int i = 0; i < this.page.length; i++)
      {
        for (int b = 0; b < this.page[i].length;)
        {
          String tmp = this.page[i][b];
          if ((tmp != null) && (!tmp.equals("")))
          {
            int size = tmp.length();
            
            b += size;
            fo.write(tmp.getBytes());
          }
          else
          {
            fo.write(" ".getBytes());
            b++;
          }
        }
        fo.write("\n".getBytes());
      }
      fo.flush();
      fo.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void toPrintStream(PrintStream out)
  {
    try
    {
      for (int i = 0; i < this.page.length; i++)
      {
        for (int b = 0; b < this.page[i].length;)
        {
          String tmp = this.page[i][b];
          if ((tmp != null) && (!tmp.equals("")))
          {
            int size = tmp.length();
            
            b += size;
            out.print(tmp);
          }
          else
          {
            out.print(" ");
            b++;
          }
        }
        out.println("");
      }
      out.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void toPrinterServer(String ipServer, int port)
  {
    try
    {
      Socket socket = new Socket(ipServer, port);
      
      System.out.println("Porta:" + socket.getPort());
      System.out.println("Host IP......:" + socket.getInetAddress().getHostAddress());
      System.out.println("Host Name......:" + socket.getInetAddress().getHostName());
      
      OutputStream fo = socket.getOutputStream();
      StringBuffer saida = new StringBuffer();
      for (int i = 0; i < this.page.length; i++)
      {
        for (int b = 0; b < this.page[i].length;)
        {
          String tmp = this.page[i][b];
          if ((tmp != null) && (!tmp.equals("")))
          {
            int size = tmp.length();
            
            b += size;
            saida.append(tmp);
          }
          else
          {
            saida.append(" ");
            b++;
          }
        }
        saida.append("\n");
      }
      fo.write(saida.toString().getBytes());
      fo.flush();
      fo.close();
      socket.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public String pullRight(int tamCampo, String text)
  {
    int spaces = tamCampo - text.length();
    String newText = "";
    for (int i = 0; i < spaces; i++) {
      newText = newText + " ";
    }
    return newText + text;
  }
  
  public String pullLeft(int tamCampo, String text)
  {
    int spaces = tamCampo - text.length();
    String newText = "";
    for (int i = 0; i < spaces; i++) {
      newText = newText + " ";
    }
    return text + newText;
  }
  
  public String centralizar(int tamCampo, String text)
  {
    int spaces = tamCampo - text.length() / 2;
    String newText = "";
    for (int i = 0; i < spaces; i++) {
      newText = newText + " ";
    }
    return newText + text + newText;
  }
  
  public void mapearDocumento(int linha, int coluna, String printer)
  {
    setOutSize(linha, coluna);
    for (int i = 1; i <= coluna; i++)
    {
      String print = i + "";
      if ((i >= 10) && (i <= 99)) {
        print = print.substring(1, 2);
      }
      if ((i >= 100) && (i <= coluna)) {
        print = print.substring(2, 3);
      }
      printTextLinCol(1, i, print);
    }
    for (int i = 1; i <= linha; i++)
    {
      String print = "" + i;
      
      printTextLinCol(i, 1, print);
    }
    toPrinter(printer);
  }
  
  public void mapearDocumento(int linha, int coluna, String printer, byte[] escCommands)
  {
    setOutSize(linha, coluna);
    for (int i = 1; i <= coluna; i++)
    {
      String print = i + "";
      if ((i >= 10) && (i <= 99)) {
        print = print.substring(1, 2);
      }
      if ((i >= 100) && (i <= coluna)) {
        print = print.substring(2, 3);
      }
      printTextLinCol(1, i, print);
    }
    for (int i = 1; i <= linha; i++)
    {
      String print = "" + i;
      
      printTextLinCol(i, 1, print);
    }
    toPrinter(printer, escCommands);
  }
  
  public void mapearDocumentoImageFile(int linha, int coluna, String fileName)
  {
    setOutSize(linha, coluna);
    for (int i = 1; i <= coluna; i++)
    {
      String print = i + "";
      if ((i >= 10) && (i <= 99)) {
        print = print.substring(1, 2);
      }
      if ((i >= 100) && (i <= coluna)) {
        print = print.substring(2, 3);
      }
      printTextLinCol(1, i, print);
    }
    for (int i = 1; i <= linha; i++)
    {
      String print = "" + i;
      
      printTextLinCol(i, 1, print);
    }
    toImageFile(fileName);
  }
  
  public void toImageFile(String fileName)
  {
    int width = this.page[0].length * 10;
    int height = this.page.length * 10;
    BufferedImage image = new BufferedImage(width, height, 1);
    
    Graphics2D g = image.createGraphics();
    
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
    g.setPaint(Color.white);
    
    Rectangle2D rectangle = new Rectangle2D.Double(0.0D, 0.0D, width, height);
    
    g.fill(rectangle);
    
    GradientPaint gp = new GradientPaint(0.0F, 0.0F, Color.black, 400.0F, 100.0F, Color.black);
    
    g.setPaint(gp);
    try
    {
      int b;
      for (int i = 0; i < this.page.length; i++) {
        for (b = 0; b < this.page[i].length;)
        {
          String tmp = this.page[i][b];
          if ((tmp != null) && (!tmp.equals("")))
          {
            int size = tmp.length();
            
            g.drawString(tmp, b * 10, (i + 1) * 10);
            b += size;
          }
          else
          {
            g.drawString(" ", b * 10, (i + 1) * 10);
            b++;
          }
        }
      }
      FileOutputStream file = new FileOutputStream(fileName);
/*      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(file);
      
      JPEGEncodeParam jpegParams = encoder.getDefaultJPEGEncodeParam(image);
      
      jpegParams.setQuality(1.0F, false);
      encoder.setJPEGEncodeParam(jpegParams);
      encoder.encode(image);
      
      file.close();*/
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static void main(String[] args)
  {
    PrinterMatrix t = new PrinterMatrix();
    
    t.setOutSize(25, 80);
    
    t.printCharAtLin(2, 25, 1, "*");
    
    t.printCharAtCol(1, 1, 80, "*");
    
    t.printCharAtLin(2, 25, 80, "*");
    
    t.printTextLinCol(3, 39, "OK");
    
    t.printTextLinCol(14, 40, "Teste");
    
    t.printCharAtCol(25, 1, 80, "*");
    
    t.printTextLinCol(14, 10, "Imprimindo em modo texto");
    
    t.printCharAtCol(13, 2, 79, "-");
    
    t.printCharAtCol(15, 2, 79, "-");
    
    t.printCharAtLinCol(17, 24, 50, 79, "+");
    
    t.printCharAtLinCol(17, 24, 2, 40, "+");
    
    t.printCharAtLinCol(17, 20, 41, 49, "+");
    t.toImageFile("printermatrix.jpg");
    
    t.mapearDocumentoImageFile(25, 80, "printermatrix1.jpg");
  }
}