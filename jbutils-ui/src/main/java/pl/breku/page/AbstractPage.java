package pl.breku.page;

import com.vaadin.navigator.View;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import pl.breku.menu.MenuNavigationBar;

import javax.annotation.PostConstruct;

/**
 * Created by breku on 13.09.17.
 */
public abstract class AbstractPage extends VerticalLayout implements View {


	private static final long serialVersionUID = 4603458569679095452L;

	@Autowired
	protected SpringNavigator springNavigator;

	@PostConstruct
	protected void init() {
		setSpacing(false);
		setMargin(false);

		MenuNavigationBar menuNavigationBar = new MenuNavigationBar(springNavigator);
		addComponent(menuNavigationBar);
		VerticalLayout wrapper = createWrapper();
		addComponent(wrapper);
		createComponent(wrapper);
	}

	protected abstract void createComponent(VerticalLayout wrapper);


	protected VerticalLayout createWrapper() {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
		verticalLayout.setWidth("100%");
		verticalLayout.setMargin(false);
		verticalLayout.setSpacing(false);
		return verticalLayout;
	}
}
