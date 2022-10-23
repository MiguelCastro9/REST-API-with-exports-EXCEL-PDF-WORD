package com.exports.resources;

import com.exports.model.Produto;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author Miguel Castro
 */
public class ExportWord {

    public static ByteArrayInputStream produtosExportWord(List<Produto> produto) {

        try ( XWPFDocument documento = new XWPFDocument()) {

            for (Produto p : produto) {
                XWPFParagraph xWPFParagraph = documento.createParagraph();
                XWPFRun xWPFRun = xWPFParagraph.createRun();
                xWPFRun.setText(Long.toString(p.getId()));
                xWPFRun.setText(" - " + p.getNome());
                xWPFRun.setText(" - " + Double.toString(p.getValor()));
                xWPFRun.setText(" - " + p.getDescricao());

            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            documento.write(outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException erro) {
            erro.getMessage();
            return null;
        }
    }
}
