package pl.breku.backend.course;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by breku on 14.09.17.
 */
@Slf4j
@Component
public class CourseListService {

	private static final List<Course> result = Arrays.asList(
			new Course(1L, "Kurs na żeglarza"),
			new Course(2L, "Inny kurs1"),
			new Course(3L, "Inny kurs2"),
			new Course(4L, "Inny kurs3"),
			new Course(5L, "Inny kurs4"),
			new Course(6L, "Inny kurs5")
	);

	public List<Course> getCourseList() {
		return result;
	}

	public Course getCourse(final long id) {
		Optional<Course> first = result.stream().filter(x -> x.getId() == id).findFirst();
		if (first.isPresent()) {
			return first.get();
		} else {
			throw new IllegalStateException(String.format("Course with id=%s not found", id));
		}
	}
}
