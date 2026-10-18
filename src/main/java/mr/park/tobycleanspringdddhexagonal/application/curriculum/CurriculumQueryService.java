package mr.park.tobycleanspringdddhexagonal.application.curriculum;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.application.curriculum.provided.CurriculumFinder;
import mr.park.tobycleanspringdddhexagonal.application.curriculum.required.CurriculumRepository;
import mr.park.tobycleanspringdddhexagonal.domain.curriculum.Curriculum;
import mr.park.tobycleanspringdddhexagonal.domain.curriculum.Lesson;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ApplicationService;

import java.util.Optional;

@ApplicationService
@RequiredArgsConstructor
public class CurriculumQueryService implements CurriculumFinder {
    private final CurriculumRepository curriculumRepository;

    @Override
    public Curriculum find(Long curriculumId) {
        return curriculumRepository.findById(curriculumId).orElseThrow(
                () -> new IllegalArgumentException("커리큘럼을 찾을 수 없습니다. Id: " + curriculumId)
        );
    }

    @Override
    public Curriculum findWithSections(Long curriculumId) {
        return curriculumRepository.findWithSectionsById(curriculumId).orElseThrow(
                () -> new IllegalArgumentException("커리큘럼을 찾을 수 없습니다. Id: " + curriculumId)
        );
    }

    @Override
    public Curriculum findByCourse(Long courseId) {
        return curriculumRepository.findByCourseId(courseId).orElseThrow(
                () -> new IllegalArgumentException("커리큘럼을 찾을 수 없습니다. CourseId: " + courseId)
        );
    }

    @Override
    public Optional<Lesson> firstLesson(Long curriculumId) {
        return findWithSections(curriculumId).firstLesson();
    }

    @Override
    public Optional<Lesson> nextLesson(Long curriculumId, Long lessonId) {
        return findWithSections(curriculumId).nextLesson(lessonId);
    }
}
