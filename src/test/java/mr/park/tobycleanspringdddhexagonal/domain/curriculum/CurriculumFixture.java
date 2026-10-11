package mr.park.tobycleanspringdddhexagonal.domain.curriculum;

import mr.park.tobycleanspringdddhexagonal.domain.course.CourseFixture;

public class CurriculumFixture {

    public static Curriculum createCurriculum() {
        return new Curriculum(CourseFixture.createCourse());
    }
}
