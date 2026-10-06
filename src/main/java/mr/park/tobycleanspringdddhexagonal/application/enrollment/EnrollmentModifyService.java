package mr.park.tobycleanspringdddhexagonal.application.enrollment;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.application.course.provided.CourseFinder;
import mr.park.tobycleanspringdddhexagonal.application.enrollment.provied.EnrollRequest;
import mr.park.tobycleanspringdddhexagonal.application.enrollment.provied.Enroller;
import mr.park.tobycleanspringdddhexagonal.application.enrollment.provied.EnrollmentFinder;
import mr.park.tobycleanspringdddhexagonal.application.enrollment.required.EnrollmentRepository;
import mr.park.tobycleanspringdddhexagonal.application.member.provided.MemberFinder;
import mr.park.tobycleanspringdddhexagonal.domain.course.Course;
import mr.park.tobycleanspringdddhexagonal.domain.enrollment.Enrollment;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ValidatedApplicationService;

@ValidatedApplicationService
@RequiredArgsConstructor
public class EnrollmentModifyService implements Enroller {
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentFinder enrollmentFinder;
    private final MemberFinder memberFinder;
    private final CourseFinder courseFinder;

    @Override
    public Enrollment enroll(EnrollRequest enrollRequest) {
        Member member = memberFinder.find(enrollRequest.memberId());
        Course course = courseFinder.find(enrollRequest.courseId());

        checkDuplication(member, course);

        Enrollment enrollment = Enrollment.enroll(member, course);

        return enrollmentRepository.save(enrollment);
    }

    private void checkDuplication(Member member, Course course) {
        if (enrollmentRepository.findByMemberIdAndCourseId(member.getId(), course.getId()).isPresent()) {
            throw new IllegalArgumentException("이미 수강 중인 강의 입니다.");
        }
    }

    @Override
    public Enrollment startStudying(Long enrollmentId) {
        Enrollment enrollment = enrollmentFinder.find(enrollmentId);

        enrollment.startStudying();

        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment complete(Long enrollmentId) {
        Enrollment enrollment = enrollmentFinder.find(enrollmentId);

        enrollment.complete();

        return enrollmentRepository.save(enrollment);
    }
}
