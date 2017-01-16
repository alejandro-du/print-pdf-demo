package com.example;

import com.vaadin.annotations.Theme;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.UI;
import org.vaadin.freemarker.FreemarkerLayout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author Alejandro Duarte.
 */
@Theme("valo")
public class VaadinUI extends UI {

    private String url;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Button button = new Button("Print it", e -> print());
        setContent(button);
    }

    private void print() {
        StreamResource resource = new StreamResource(() -> getPdf(), "file.pdf");
        setResource("pdf", resource);
        url = getState().resources.get("pdf").getURL();

        FreemarkerLayout pdfLayout = new FreemarkerLayout("template.html", this);
        pdfLayout.setSizeFull();
        setContent(pdfLayout);
        JavaScript.getCurrent().execute(
                "pdf = document.getElementById('pdf');" +
                        "pdf.focus();" +
                        "pdf.contentWindow.print();"
        );
    }

    private InputStream getPdf() {
        try {
            return new FileInputStream("src/main/webapp/VAADIN/file.pdf");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getUrl() {
        return url;
    }
}
