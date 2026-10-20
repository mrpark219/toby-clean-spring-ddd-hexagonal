package mr.park.tobycleanspringdddhexagonal.support.test;

import mr.park.tobycleanspringdddhexagonal.application.course.provided.CourseCreator;
import mr.park.tobycleanspringdddhexagonal.application.curriculum.provided.CurriculumFinder;
import mr.park.tobycleanspringdddhexagonal.application.enrollment.provied.EnrollRequest;
import mr.park.tobycleanspringdddhexagonal.application.enrollment.provied.Enroller;
import mr.park.tobycleanspringdddhexagonal.application.instructor.provided.InstructorApplication;
import mr.park.tobycleanspringdddhexagonal.application.member.provided.MemberRegister;
import mr.park.tobycleanspringdddhexagonal.domain.course.Course;
import mr.park.tobycleanspringdddhexagonal.domain.course.CourseFixture;
import mr.park.tobycleanspringdddhexagonal.domain.curriculum.Curriculum;
import mr.park.tobycleanspringdddhexagonal.domain.enrollment.Enrollment;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.Instructor;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.InstructorFixture;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;
import mr.park.tobycleanspringdddhexagonal.domain.member.MemberFixture;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ApplicationServiceTest;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationServiceTest
public class BaseApplicationServiceTest {
    @Autowired
    MemberRegister memberRegister;

    @Autowired
    InstructorApplication instructorApplication;

    @Autowired
    CourseCreator courseCreator;

    @Autowired
    Enroller enroller;

    @Autowired
    CurriculumFinder curriculumFinder;

    protected Member member;
    protected Instructor instructor;
    protected Course course;
    protected Enrollment enrollment;
    protected Curriculum curriculum;

    protected Instructor prepareInstructor() {
        prepareActiveMember();

        this.instructor = instructorApplication.apply(InstructorFixture.createApplyRequest(member));
        this.instructor.approve();

        return this.instructor;
    }

    protected @NonNull Member prepareActiveMember() {
        this.member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        this.member.activate();
        return this.member;
    }

    protected Course prepareCourse() {
        prepareInstructor();

        this.course = courseCreator.create(CourseFixture.createCourseCreateRequest(instructor.getId(), null));
        this.course.updateInfo(CourseFixture.createCourseInfoUpdateRequest(null).toInfo());

        return this.course;
    }

    protected Course preparePublishedCourse() {
        prepareCourse();

        this.course.submitForReview();
        this.course.publish();

        return this.course;
    }

    protected Enrollment prepareEnrollment() {
        Member member = prepareActiveMember();
        Course course = preparePublishedCourse();

        this.enrollment = enroller.enroll(new EnrollRequest(member.getId(), course.getId()));

        return this.enrollment;
    }

    protected Curriculum prepareCurriculumSectionsAndLessons(Course course) {
        Curriculum curriculum = curriculumFinder.findByCourse(course.getId());

        curriculum.addSection("S1");
        curriculum.addLesson(0, "L1");
        curriculum.addLesson(0, "L2");
        curriculum.addSection("S2");
        curriculum.addLesson(1, "L3");
        curriculum.addLesson(1, "L4");
        curriculum.addSection("S3");
        curriculum.addLesson(2, "L5");

        this.curriculum = curriculum;

        return this.curriculum;
    }

    protected Course prepareCourseWithCurriculum() {
        prepareCourse();
        prepareCurriculumSectionsAndLessons(course);

        return this.course;
    }
}
