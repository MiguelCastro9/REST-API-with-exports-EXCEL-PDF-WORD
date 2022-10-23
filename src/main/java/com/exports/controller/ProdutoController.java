package com.exports.controller;

import com.exports.model.Produto;
import com.exports.resources.ExportExcel;
import com.exports.resources.ExportPdf;
import com.exports.resources.ExportWord;
import com.itextpdf.text.BadElementException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Miguel Castro
 */
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @GetMapping
    public List<Produto> listProdutos() {

        List<Produto> produtos = new ArrayList<>();
        Produto p1 = new Produto();
        Produto p2 = new Produto();
        Produto p3 = new Produto();

        p1.setId(1L);
        p1.setNome("Teclado");
        p1.setValor(150D);
        p1.setDescricao("Teclado Mecânico Gamer 2.0");

        p2.setId(2L);
        p2.setNome("Mouse");
        p2.setValor(90D);
        p2.setDescricao("Mouse Gamer RGB");

        p3.setId(3L);
        p3.setNome("Monitor");
        p3.setValor(1500D);
        p3.setDescricao("Monitor Gamer Curvo 75Hz");

        produtos.add(p1);
        produtos.add(p2);
        produtos.add(p3);

        return produtos;
    }

    @GetMapping("/exportExel")
    public ResponseEntity exportExel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=Produtos.xlsx");
        ByteArrayInputStream stream = ExportExcel.produtosExportExel(listProdutos());
        IOUtils.copy(stream, response.getOutputStream());

        return ResponseEntity.ok().body(this);
    }

    @GetMapping("/exportPdf")
    public ResponseEntity<InputStreamResource> exportPdf(ModelMap model) throws IOException, BadElementException {
        model.addAttribute("produtos", listProdutos());
        ByteArrayInputStream bais = ExportPdf.produtosExportPdf(listProdutos());

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bais));
    }

    @GetMapping("/exportWord")
    public ResponseEntity exportWord(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=Produtos.docx");
        ByteArrayInputStream stream = ExportWord.produtosExportWord(listProdutos());
        IOUtils.copy(stream, response.getOutputStream());

        return ResponseEntity.ok().body(this);
    }
}
