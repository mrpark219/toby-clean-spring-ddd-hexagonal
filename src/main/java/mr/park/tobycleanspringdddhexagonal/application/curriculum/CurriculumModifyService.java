package mr.park.tobycleanspringdddhexagonal.application.curriculum;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.application.curriculum.provided.CurriculumCoordinator;
import mr.park.tobycleanspringdddhexagonal.application.curriculum.provided.CurriculumFinder;
import mr.park.tobycleanspringdddhexagonal.application.curriculum.required.CurriculumRepository;
import mr.park.tobycleanspringdddhexagonal.application.curriculum.required.LessonRepository;
import mr.park.tobycleanspringdddhexagonal.application.curriculum.required.SectionRepository;
import mr.park.tobycleanspringdddhexagonal.domain.course.Course;
import mr.park.tobycleanspringdddhexagonal.domain.curriculum.Curriculum;
import mr.park.tobycleanspringdddhexagonal.domain.curriculum.InvalidCurriculumException;
import mr.park.tobycleanspringdddhexagonal.domain.curriculum.Lesson;
import mr.park.tobycleanspringdddhexagonal.domain.curriculum.Section;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ApplicationService;

@ApplicationService
@RequiredArgsConstructor
public class CurriculumModifyService implements CurriculumCoordinator {
    private final CurriculumRepository curriculumRepository;
    private final SectionRepository sectionRepository;
    private final LessonRepository lessonRepository;
    private final CurriculumFinder curriculumFinder;


    @Override
    public Long createCurriculum(Course course) {
        Curriculum curriculum = new Curriculum(course);

        return curriculumRepository.save(curriculum).getId();
    }

    @Override
    public Curriculum addSection(Long curriculumId, String title) {
        Curriculum curriculum = curriculumFinder.find(curriculumId);

        curriculum.addSection(title);

        return curriculumRepository.save(curriculum);
    }

    @Override
    public Curriculum addSection(Long curriculumId, int sectionIndex, String title) {
        Curriculum curriculum = curriculumFinder.find(curriculumId);

        curriculum.addSection(sectionIndex, title);

        return curriculumRepository.save(curriculum);
    }

    @Override
    public Curriculum addLesson(Long curriculumId, int sectionIndex, String title) {
        Curriculum curriculum = curriculumFinder.find(curriculumId);

        curriculum.addLesson(sectionIndex, title);

        return curriculumRepository.save(curriculum);
    }

    @Override
    public Curriculum updateSectionTitle(Long curriculumId, int sectionIndex, String title) {
        Curriculum curriculum = curriculumFinder.find(curriculumId);

        curriculum.updateSectionTitle(sectionIndex, title);

        return curriculumRepository.save(curriculum);
    }

    @Override
    public Curriculum updateLessonTitle(Long curriculumId, int sectionIndex, int lessonIndex, String title) {
        Curriculum curriculum = curriculumFinder.find(curriculumId);

        curriculum.updateLessonTitle(sectionIndex, lessonIndex, title);

        return curriculumRepository.save(curriculum);
    }

    @Override
    public Curriculum removeLesson(Long curriculumId, int sectionIndex, int lessonIndex) {
        Curriculum curriculum = curriculumFinder.find(curriculumId);

        Lesson lesson = curriculum.removeLesson(sectionIndex, lessonIndex);
        lessonRepository.delete(lesson);

        return curriculumRepository.save(curriculum);
    }

    @Override
    public Curriculum removeSection(Long curriculumId, int sectionIndex) {
        Curriculum curriculum = curriculumFinder.find(curriculumId);

        Section section = curriculum.removeSection(sectionIndex);
        sectionRepository.delete(section);

        return curriculumRepository.save(curriculum);
    }

    @Override
    public Curriculum moveLesson(Long curriculumId, int fromSectionIndex, int fromLessonIndex, int toSectionIndex, int toLessonIndex) {
        Curriculum curriculum = curriculumFinder.find(curriculumId);

        curriculum.moveLesson(fromSectionIndex, fromLessonIndex, toSectionIndex, toLessonIndex);

        return curriculumRepository.save(curriculum);
    }

    @Override
    public void validate(Long courseId) throws InvalidCurriculumException {
        Curriculum curriculum = curriculumFinder.findByCourse(courseId);

        curriculum.validate();
    }
}
