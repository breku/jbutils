package pl.breku.dashboard;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import pl.breku.page.AbstractPage;

/**
 * Created by breku on 13.09.17.
 */
@SpringView(name = DashboardPage.VIEW_NAME)
public class DashboardPage extends AbstractPage {

	public static final String VIEW_NAME = "home";

	private static final long serialVersionUID = 2111328748647906890L;


	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {

	}


	@Override
	protected void createComponent(VerticalLayout wrapper) {
		wrapper.addComponent(createDashboardImage());
		wrapper.addComponent(new Label("This is home page"));
	}




}
