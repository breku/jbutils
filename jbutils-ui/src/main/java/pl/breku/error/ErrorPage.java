package pl.breku.error;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.context.annotation.Scope;
import pl.breku.page.AbstractPage;

import static pl.breku.error.ErrorPage.VIEW_NAME;

/**
 * Created by breku on 14.09.17.
 */
@Scope("prototype")
@SpringView(name = VIEW_NAME)
public class ErrorPage extends AbstractPage {


	public static final String VIEW_NAME = "error";

	private static final long serialVersionUID = -1275813301618495176L;

	@Override
	protected void init() {
		setMargin(false);
		setSpacing(false);
		final VerticalLayout wrapper = createWrapper();
		addComponent(wrapper);
		createComponent(wrapper);
	}

	@Override
	protected void createComponent(VerticalLayout wrapper) {
		wrapper.addComponent(new Label("ErrorPage"));
	}
}
