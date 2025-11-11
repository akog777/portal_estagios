package mackenzie.estagio.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import mackenzie.estagio.entities.Estudante;
import mackenzie.estagio.entities.AreaInteresse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfService {

    public byte[] gerarCurriculoPdf(Estudante estudante) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // Criar fontes usando Standard14Fonts (fontes embutidas do PDF)
            PDFont fonteBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDFont fonteRegular = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(fonteBold, 18);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("CURRÍCULO - " + estudante.getNome().toUpperCase());
                contentStream.endText();

                contentStream.setFont(fonteRegular, 12);
                float yPosition = 700;

                // Informações pessoais
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("Nome: " + estudante.getNome());
                contentStream.endText();

                yPosition -= 20;
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("CPF: " + estudante.getCpf());
                contentStream.endText();

                yPosition -= 20;
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("Curso: " + estudante.getCurso());
                contentStream.endText();

                yPosition -= 20;
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("Email: " + estudante.getEmail());
                contentStream.endText();

                if (estudante.getTelefone() != null && !estudante.getTelefone().isEmpty()) {
                    yPosition -= 20;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText("Telefone: " + estudante.getTelefone());
                    contentStream.endText();
                }

                // Áreas de interesse
                yPosition -= 40;
                contentStream.setFont(fonteBold, 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("ÁREAS DE INTERESSE");
                contentStream.endText();

                contentStream.setFont(fonteRegular, 12);
                List<AreaInteresse> areas = estudante.getAreasInteresse();
                if (areas != null && !areas.isEmpty()) {
                    for (AreaInteresse area : areas) {
                        yPosition -= 20;
                        if (yPosition < 50) {
                            // Se não há espaço, cria nova página
                            PDPage newPage = new PDPage();
                            document.addPage(newPage);
                            contentStream.close();
                            PDPageContentStream newContentStream = new PDPageContentStream(document, newPage);
                            newContentStream.setFont(fonteRegular, 12);
                            newContentStream.beginText();
                            newContentStream.newLineAtOffset(50, 750);
                            newContentStream.showText("• " + area.getTitulo());
                            newContentStream.endText();
                            newContentStream.close();
                            return finalizarDocumento(document);
                        }
                        contentStream.beginText();
                        contentStream.newLineAtOffset(50, yPosition);
                        contentStream.showText("• " + area.getTitulo());
                        contentStream.endText();
                    }
                } else {
                    yPosition -= 20;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText("Nenhuma área de interesse cadastrada");
                    contentStream.endText();
                }

                // Informações adicionais
                yPosition -= 40;
                if (yPosition > 100) {
                    contentStream.setFont(fonteBold, 14);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText("EXPERIÊNCIA PROFISSIONAL");
                    contentStream.endText();

                    contentStream.setFont(fonteRegular, 12);
                    yPosition -= 20;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText("Em busca da primeira oportunidade de estágio na área de " + estudante.getCurso());
                    contentStream.endText();
                }
            }

            return finalizarDocumento(document);
        }
    }

    private byte[] finalizarDocumento(PDDocument document) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        document.close();
        return outputStream.toByteArray();
    }
}
