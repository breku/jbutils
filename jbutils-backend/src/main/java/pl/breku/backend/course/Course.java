package pl.breku.backend.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by breku on 14.09.17.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Course {

	private long id;

	private String name;
}
