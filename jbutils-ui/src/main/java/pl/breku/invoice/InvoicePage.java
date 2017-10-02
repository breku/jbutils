package pl.breku.invoice;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import pl.breku.page.AbstractPage;

/**
 * Created by breku on 13.09.17.
 */
@SpringView(name = InvoicePage.VIEW_NAME)
public class InvoicePage extends AbstractPage {

	public static final String VIEW_NAME = "invoice";

	private static final long serialVersionUID = -5867976681717716832L;

	private TextArea textArea;

	private Button button;

	@Override
	protected void createComponent(VerticalLayout wrapper) {
		wrapper.addComponent(createDashboardImage());
		wrapper.addComponent(new Label("This is Invoice page"));
		wrapper.addComponent(createTextArea());
		wrapper.addComponent(createButton());
	}

	private VerticalLayout createTextArea() {
		VerticalLayout verticalLayout = new VerticalLayout();
		textArea = new TextArea("Mail content");
		textArea.setSizeFull();
		verticalLayout.addComponent(textArea);
		verticalLayout.setSpacing(true);
		return verticalLayout;
	}

	private VerticalLayout createButton() {
		VerticalLayout verticalLayout = new VerticalLayout();
		button = new Button("Generate invoices");
		verticalLayout.addComponent(button);
		verticalLayout.setSpacing(true);
		return verticalLayout;
	}
}
