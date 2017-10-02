package pl.breku.course.list;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import pl.breku.page.AbstractPage;

/**
 * Created by breku on 13.09.17.
 */
@SpringView(name = InvoicePage.VIEW_NAME)
public class InvoicePage extends AbstractPage {


	public static final String VIEW_NAME = "invoice";

	private static final long serialVersionUID = -5867976681717716832L;


	@Override
	protected void createComponent(VerticalLayout wrapper) {
		wrapper.addComponent(new Label("This is Invoice page"));
	}
}
