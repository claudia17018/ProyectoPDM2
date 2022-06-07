package com.example.proyectopdm;


import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Phaser;

public class TemplatePDF {

    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle=new Font(Font.FontFamily.TIMES_ROMAN,16,Font.BOLD);
    private Font fSubTitle=new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD);
    private Font fText=new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD);
    private Font fHighText=new Font(Font.FontFamily.TIMES_ROMAN,13,Font.BOLD, BaseColor.GRAY);
    private Font fBita=new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD, BaseColor.DARK_GRAY);



    public TemplatePDF(Context context) {
        this.context=context;
    }


    public void openDocument(){
        createFile();
        try{
            document=new Document(PageSize.A4);
            pdfWriter=PdfWriter.getInstance(document,new FileOutputStream(pdfFile));
            document.open();
        }catch (Exception e){
            Log.e("openDocument",e.toString());
        }
    }

    private void createFile(){
        File folder =new File(Environment.getExternalStorageDirectory().toString(),"ControlServicioSocial");
        if(!folder.exists()){
            folder.mkdir();

        }
        pdfFile=new File(folder,"resumenPDF.pdf");
    }
    public void closeDocument(){
        document.close();

    }
    public void addMetaData(String title,String subject,String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }
    public void addTitles(String title,String subTitle,String date){
        try{
            paragraph = new Paragraph();
            addChildP(new Paragraph(title,fTitle));
            addChildP(new Paragraph(subTitle,fSubTitle));
            addChildP(new Paragraph(date,fHighText));
            paragraph.setSpacingAfter(20);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addTitles",e.toString());
        }
    }
    private void addChildP(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);

    }
    private void addChildPl(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_RIGHT);
        paragraph.add(childParagraph);

    }
    public void addParagraph(String text){
        try{
            //paragraph=new Paragraph(text,fText);
            paragraph = new Paragraph();
            addChildP(new Paragraph(text,fText));
            paragraph.setSpacingAfter(15);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addParagraph",e.toString());
        }
    }
    public void addParagraphE(String text){
        try{
            //paragraph=new Paragraph(text,fText);
            paragraph = new Paragraph();
            addChildPl(new Paragraph(text,fText));
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(60);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addParagraph",e.toString());
        }
    }
    public void addParagraphC(String text){
        try{
            paragraph=new Paragraph(text,fBita);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(15);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addParagraph",e.toString());
        }
    }
    public void createTable(String[]header, ArrayList<String[]>clients) {
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC = 0;
            while (indexC < header.length) {
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fSubTitle));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.GRAY);
                pdfPTable.addCell(pdfPCell);

            }
            for (int indexR = 0; indexR < clients.size(); indexR++) {
                String[] row = clients.get(indexR);
                for (indexC = 0; indexC < header.length; indexC++) {
                    pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(20);
                    pdfPTable.addCell(pdfPCell);


                }

            }
            paragraph.add(pdfPTable);
            document.add(paragraph);

        } catch (Exception e) {
            Log.e("createTable", e.toString());
        }
    }
    public void viewPDF(){
        Intent intent=new Intent(context,AbrirPdfActivity.class);
        intent.putExtra("path",pdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}


