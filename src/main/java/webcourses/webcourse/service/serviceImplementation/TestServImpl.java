/*
 * Copyright (c) 2011-2012, Qulice.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the Qulice.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package webcourses.webcourse.service.serviceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import webcourses.webcourse.entity.*;
import webcourses.webcourse.entity.dto.UserResult;
import webcourses.webcourse.repos.TestRepo;
import webcourses.webcourse.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class TestServImpl implements TestServ {
    private final TestRepo testRepo;
    private final UserServ userServ;
    private final QuestionServ questionServ;
    private final AnswerServ answerServ;
    private final AttemptServ attemptServ;
    private final ResultServ resultServ;

    @Autowired
    public TestServImpl(TestRepo testRepo, UserServ userServ, QuestionServ questionServ, AnswerServ answerServ, AttemptServ attemptServ, ResultServ resultServ) {
        this.testRepo = testRepo;
        this.userServ = userServ;
        this.questionServ = questionServ;
        this.answerServ = answerServ;
        this.attemptServ = attemptServ;
        this.resultServ = resultServ;
    }

    @Override
    public List<Test> getAllTests(Lesson lesson) {
        return testRepo.findAllByLesson(lesson);
    }

    private void save(Test test) {
        testRepo.save(test);
    }

    @Override
    public String creatTest(Course course, Lesson lesson, Model model) {
        model.addAttribute("course", course);
        model.addAttribute("lesson", lesson);

        return "course/lesson/test/create";
    }

    @Override
    public String creatTest(Course course, Lesson lesson, String name, String date) {
        if (userServ.isCreator(course)) {
            Test test = new Test();
            test.setName(name);
            test.setExpDate(date);
            test.setLesson(lesson);
            save(test);
        }

        return "redirect:/courses/" + course.getId() + "/lesson/" + lesson.getId();
    }

    @Override
    public String homePage(Course course, Lesson lesson, Test test, Model model) {
        List<Question> questions = questionServ.getAllQuestions(test);
        model.addAttribute("course", course);
        model.addAttribute("lesson", lesson);
        model.addAttribute("test", test);
        model.addAttribute("questions", questions);
        model.addAttribute("is_creator", userServ.isCreator(course));
        model.addAttribute("q_count", questions.size());

        return "course/lesson/test/homePage";
    }

    @Override
    public String creatQuestion(Course course, Lesson lesson, Test test, Model model) {
        if (userServ.isCreator(course)) {
            model.addAttribute("course", course);
            model.addAttribute("lesson", lesson);
            model.addAttribute("test", test);
        }

        return "course/lesson/test/createQuestion";
    }

    @Override
    public String creatQuestion(HttpServletRequest request, Course course, Lesson lesson, Test test, String text, String maxValue, String type, String count) {
        if (userServ.isCreator(course)) {
            Question question = new Question();
            question.setText(text);
            question.setMaxVal((long) Float.parseFloat(maxValue));
            question.setqType(type);
            question.setTest(test);
            questionServ.save(question);

            if (type.equals("option")) {
                int c = Integer.parseInt(count);
                int correctAnswerCount = 0;

                for (int i = 1; i < c + 1; i++) {
                    String checkBox = request.getParameter("cb" + i);
                    if (!(checkBox == null)) {
                        correctAnswerCount = correctAnswerCount + 1;
                    }
                }

                for (int i = 1; i < c + 1; i++) {
                    String checkBox = request.getParameter("cb" + i);
                    String answerText = request.getParameter("text" + i);

                    int answerValue = 0;

                    if (correctAnswerCount != 0) {
                        if (!(checkBox == null)) {
                            answerValue = Integer.parseInt(maxValue) / correctAnswerCount;
                        } else {
                            answerValue = -Integer.parseInt(maxValue) / correctAnswerCount;
                        }
                    }

                    Answer answer = new Answer();
                    answer.setText(answerText);
                    answer.setVal((long) answerValue);
                    answer.setAType(type);
                    answer.setQuestion(question);
                    answerServ.save(answer);
                }
            } else {
                if (type.equals("text")) {
                    String answerText = request.getParameter("answer_text");
                    Answer answer = new Answer();
                    answer.setText(answerText.toLowerCase(Locale.ROOT));
                    answer.setVal((long) Float.parseFloat(maxValue));
                    answer.setAType(type);
                    answer.setQuestion(question);
                    answerServ.save(answer);
                }
            }
        }

        return "redirect:/courses/" + course.getId() + "/lesson/" + lesson.getId() + "/test/" + test.getId();
    }

    @Override
    public String editQuestion(Course course, Lesson lesson, Test test, Question question, Model model) {
        if (userServ.isCreator(course)) {
            Set<Answer> answers = question.getAnswers();
            model.addAttribute("course", course);
            model.addAttribute("lesson", lesson);
            model.addAttribute("test", test);
            model.addAttribute("question", question);
            model.addAttribute("answers", answers);
        }

        return "course/lesson/test/editQuestion";
    }

    @Override
    public String editQuestion(HttpServletRequest request, Course course, Lesson lesson, Test test, Question question, String text, String maxValue, String type) {
        if (userServ.isCreator(course)) {
            question.setText(text);
            question.setMaxVal((long) Float.parseFloat(maxValue));
            question.setqType(type);
            question.setTest(test);
            questionServ.save(question);

            if (type.equals("option")) {
                int c = Integer.parseInt(request.getParameter("count"));
                int correctAnswerCount = 0;

                for (int i = 1; i < c + 1; i++) {
                    String checkBox = request.getParameter("cb" + i);
                    if (!(checkBox == null)) {
                        correctAnswerCount = correctAnswerCount + 1;
                    }
                }

                for (int i = 1; i < c + 1; i++) {
                    String checkBox = request.getParameter("cb" + i);
                    String answerText = request.getParameter("text" + i);

                    int answerValue = 0;

                    if (correctAnswerCount != 0) {
                        if (!(checkBox == null)) {
                            answerValue = Integer.parseInt(maxValue) / correctAnswerCount;
                        } else {
                            answerValue = -Integer.parseInt(maxValue) / correctAnswerCount;
                        }
                    }
                    Answer answer = answerServ.getAllAnswers(question).get(i - 1);
                    answer.setText(answerText);
                    answer.setVal((long) answerValue);
                    answer.setAType(type);
                    answer.setQuestion(question);
                    answerServ.save(answer);
                }
            } else {
                if (type.equals("text")) {
                    String answerText = request.getParameter("answer_text");
                    Answer answer = answerServ.getAllAnswers(question).get(0);
                    answer.setText(answerText.toLowerCase(Locale.ROOT));
                    answer.setVal((long) Float.parseFloat(maxValue));
                    answer.setAType(type);
                    answer.setQuestion(question);
                    answerServ.save(answer);
                }
            }
        }

        return "redirect:/courses/" + course.getId() + "/lesson/" + lesson.getId() + "/test/" + test.getId();
    }

    @Override
    public String deleteQuestion(Course course, Lesson lesson, Test test, Question question) {
        questionServ.delete(question);

        return "redirect:/courses/" + course.getId() + "/lesson/" + lesson.getId() + "/test/" + test.getId();
    }

    @Override
    public String check(HttpServletRequest request, Course course, Lesson lesson, Test test) {
        int attemptCount = attemptServ.getTestAttempt(userServ.getCurrUser(), test);
        Attempt attempt = attemptServ.getAttempt(userServ.getCurrUser(), test);
        if (attempt == null) {
            Attempt newAttempt = new Attempt();
            newAttempt.setTest(test);
            newAttempt.setUser(userServ.getCurrUser());
            newAttempt.setCount(1L);
            attemptServ.save(newAttempt);
        } else {
            int newAttemptCount = attemptCount + 1;
            attempt.setCount((long) newAttemptCount);
            attemptServ.save(attempt);
        }

        int newAttemptCount = attemptServ.getTestAttempt(userServ.getCurrUser(), test);

        for (Question question :
                questionServ.getAllQuestions(test)) {
            for (Answer answer :
                    question.getAnswers()) {
                String answerText = request.getParameter(answer.getId().toString());

                if (answer.getaType().equals("text")) {
                    float answerVal = 0;
                    if (answer.getText().equals(answerText)) {
                        answerVal = answer.getVal();
                    }

                    Result res = new Result();
                    res.setResultValue((long) answerVal);
                    res.setAttempt((long) newAttemptCount);
                    res.setUser(userServ.getCurrUser());
                    res.setAnswer(answer);
                    resultServ.save(res);
                } else {
                    if (answer.getaType().equals("option")) {
                        float answerVal;
                        if (answerText != null && answerText.equals("on")) {
                            answerVal = answer.getVal();
                        } else {
                            answerVal = 0;
                        }
                        Result res = new Result();
                        res.setResultValue((long) answerVal);
                        res.setAttempt((long) newAttemptCount);
                        res.setUser(userServ.getCurrUser());
                        res.setAnswer(answer);
                        resultServ.save(res);
                    } else {
                        Result res = new Result();
                        res.setResultValue(0L);
                        res.setAttempt((long) newAttemptCount);
                        res.setUser(userServ.getCurrUser());
                        res.setAnswer(answer);
                        resultServ.save(res);
                    }
                }

            }
        }

        return "redirect:/courses/" + course.getId() + "/lesson/" + lesson.getId() + "/test/" + test.getId() + "/result/" + newAttemptCount;
    }

    @Override
    public String resultForTeacher(Course course, Lesson lesson, Test test, Model model) {
        Set<User> allUsers = course.getUsers();

        List<UserResult> allUserResult = new LinkedList<>();
        int maxPossibleResult = calcMaxPossibleResult(test);

        for (User user :
                allUsers) {
            UserResult userResult = new UserResult();
            int attemptCount;
            int bestUserResult;
            attemptCount = attemptServ.getTestAttempt(user, test);
            bestUserResult = calcMaxUserResult(test, user);

            userResult.setUser(user);
            userResult.setBestUserResult(bestUserResult);
            userResult.setAttemptCount(attemptCount);
            allUserResult.add(userResult);
        }

        int countOfUser = allUserResult.size();
        int c = 0;
        for (UserResult userResult :
                allUserResult) {
            c += userResult.getBestUserResult();
        }

        double avgOfResult = (double) c/countOfUser;

        model.addAttribute("course", course);
        model.addAttribute("lesson", lesson);
        model.addAttribute("test", test);
        model.addAttribute("user_result", allUserResult);
        model.addAttribute("max_possible_result", maxPossibleResult);
        model.addAttribute("avg_of_result", avgOfResult);
        model.addAttribute("count_of_user", countOfUser);

        return "course/lesson/test/resultForTeacher";
    }

    @Override
    public String result(Course course, Lesson lesson, Test test, int attempt, Model model) {
        globalResult(course, lesson, test, userServ.getCurrUser(), attempt, model);

        return "course/lesson/test/result";
    }

    @Override
    public String userResultForTeacher(Course course, Lesson lesson, Test test, User user, int attempt, Model model) {
        globalResult(course, lesson, test, user, attempt, model);

        model.addAttribute("user", user);

        return "course/lesson/test/userResultForTeacher";
    }

    private void globalResult(Course course, Lesson lesson, Test test, User user, int attempt, Model model) {
        int attemptCount = attemptServ.getTestAttempt(user, test);
        List<Integer> aCount = new LinkedList<>();
        for (int i = 0; i < attemptCount; i++) {
            aCount.add(i);
        }
        model.addAttribute("course", course);
        model.addAttribute("lesson", lesson);
        model.addAttribute("test", test);
        model.addAttribute("max_result", calcMaxPossibleResult(test));
        model.addAttribute("attempt_count", aCount);
        model.addAttribute("current_attempt", attempt);
        model.addAttribute("user_result", calcUserResult(test, user, attempt));

    }

    private int calcMaxUserResult(Test test, User user) {
        int attemptCount = attemptServ.getTestAttempt(userServ.getCurrUser(), test);
        int maxUserResult = 0;
        for (int i = 1; i < attemptCount + 1; i++) {
            int resultInAttempt = calcUserResult(test, user, i);
            if (maxUserResult < resultInAttempt) {
                maxUserResult = resultInAttempt;
            }
        }
        return maxUserResult;
    }

    private int calcUserResult(Test test, User user, int attempt) {
        int userResult = 0;

        for (Question question :
                questionServ.getAllQuestions(test)) {
            int questionResult = 0;
            for (Answer answer :
                    answerServ.getAllAnswers(question)) {
                questionResult += resultServ.answerResult(user, answer, attempt);
            }

            if (questionResult < 0) {
                questionResult = 0;
            }
            userResult = userResult + questionResult;
        }
        return userResult;
    }

    private int calcMaxPossibleResult(Test test) {
        int maxResult = 0;
        for (Question question :
                questionServ.getAllQuestions(test)) {
            maxResult = (int) (maxResult + question.getMaxVal());
        }
        return maxResult;
    }
}
