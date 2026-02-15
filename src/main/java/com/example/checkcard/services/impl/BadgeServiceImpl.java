package com.example.checkcard.services.impl;

import com.example.checkcard.services.BadgeService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
@Service
public class BadgeServiceImpl implements BadgeService {
    @Override
    public byte[] generateBadge(
            String nom,
            String matricule,
            String classe,
            byte[] qrCode,
            byte[] logo
    ) throws Exception {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = new Document(new Rectangle(300, 450));
        PdfWriter writer = PdfWriter.getInstance(document, output);

        document.open();
        Image logoImg = Image.getInstance(logo);
        logoImg.scaleToFit(60, 60);
        logoImg.setAlignment(Element.ALIGN_CENTER);
        document.add(logoImg);

        Paragraph title = new Paragraph("CARTE ÉTUDIANT ISM",
                new Font(Font.HELVETICA, 14, Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph("\n"));

        Image qrImg = Image.getInstance(qrCode);
        qrImg.setAlignment(Element.ALIGN_CENTER);
        document.add(qrImg);

        document.add(new Paragraph("\n"));

        Paragraph info = new Paragraph(
                "Nom : " + nom + "\n" +
                        "Matricule : " + matricule + "\n" +
                        "Classe : " + classe,
                new Font(Font.HELVETICA, 11)
        );
        info.setAlignment(Element.ALIGN_CENTER);
        document.add(info);

        PdfContentByte canvas = writer.getDirectContent();
        Color ismColor = new Color(94, 54, 25);
        canvas.setColorFill(ismColor);
        canvas.rectangle(0, 0, 300, 20);
        canvas.fill();

        Paragraph copyright =
                new Paragraph("© ISM 2026",
                        new Font(Font.HELVETICA, 8));
        copyright.setAlignment(Element.ALIGN_CENTER);
        document.add(copyright);

        document.close();
        return output.toByteArray();
    }
}
