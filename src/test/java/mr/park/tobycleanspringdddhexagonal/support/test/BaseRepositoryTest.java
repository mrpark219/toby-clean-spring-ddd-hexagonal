package mr.park.tobycleanspringdddhexagonal.support.test;

import jakarta.persistence.EntityManager;
import mr.park.tobycleanspringdddhexagonal.application.course.required.CourseRepository;
import mr.park.tobycleanspringdddhexagonal.application.enrollment.required.EnrollmentRepository;
import mr.park.tobycleanspringdddhexagonal.application.instructor.required.InstructorRepository;
import mr.park.tobycleanspringdddhexagonal.application.member.required.MemberRepository;
import mr.park.tobycleanspringdddhexagonal.domain.course.Course;
import mr.park.tobycleanspringdddhexagonal.domain.course.CourseFixture;
import mr.park.tobycleanspringdddhexagonal.domain.enrollment.Enrollment;
import mr.park.tobycleanspringdddhexagonal.domain.enrollment.EnrollmentFixture;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.Instructor;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.InstructorFixture;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;
import mr.park.tobycleanspringdddhexagonal.domain.member.MemberFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BaseRepositoryTest {
    @Autowired
    protected EntityManager entityManager;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    EnrollmentRepository enrollmentRepository;

    protected Member member;

    protected Instructor instructor;

    protected Course course;

    protected Enrollment enrollment;

    protected Course preparePublishedCourse() {
        prepareActiveInstructor();

        this.course = courseRepository.save(CourseFixture.createCourse(instructor, null));
        this.course.updateInfo(CourseFixture.createCourseInfoUpdateRequest(null).toInfo());
        this.course.submitForReview();
        this.course.publish();

        return this.course;
    }

    protected Instructor prepareActiveInstructor() {
        prepareActiveMember();

        this.instructor = instructorRepository.save(InstructorFixture.createActiveInstructor(member));

        return this.instructor;
    }

    protected Member prepareActiveMember() {
        this.member = memberRepository.save(MemberFixture.createActiveMember());
        return this.member;
    }

    protected Enrollment prepareEnrollment(Member member, Course course) {
        this.enrollment = enrollmentRepository.save(EnrollmentFixture.createEnrollment(member, course));
        return this.enrollment;
    }
}
