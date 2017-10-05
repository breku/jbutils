package pl.breku.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServletRequest;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.breku.Person;
import pl.breku.backend.CrudService;
import pl.breku.backend.server.config.JbUtilsConfiguration;

import javax.servlet.annotation.WebServlet;


/**
 *
 */
@SpringUI
@Theme("mytheme")
@Slf4j
@Component
public class MyUI extends UI {

	@Autowired
	private CrudService<Person> service;

	private DataProvider<Person, String> dataProvider = new CallbackDataProvider<>(
			query -> service.findAll().stream(),
			query -> service.findAll().size());


	@Autowired
	private SpringViewProvider viewProvider;

	@Autowired
	private SpringNavigator springNavigator;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		log.info("Request to url={}", ((SpringVaadinServletRequest) vaadinRequest).getRequestURI());


		springNavigator.init(this, this);
		getNavigator().addProvider(viewProvider);


		defaultOne();
	}


	private void defaultOne() {
		final VerticalLayout layout = new VerticalLayout();
		final TextField name = new TextField();
		name.setCaption("Type your name here:");

		final Button button = new Button("Click Me");
		button.addClickListener(e -> {
			service.save(new Person(name.getValue()));
			dataProvider.refreshAll();
		});

		final Grid<Person> grid = new Grid<>();
		grid.addColumn(Person::getName).setCaption("Name");
		grid.setDataProvider(dataProvider);
		grid.setSizeFull();

		layout.addComponents(name, button, grid);
		layout.setSizeFull();
		layout.setExpandRatio(grid, 1.0f);

		setContent(layout);
	}


}
