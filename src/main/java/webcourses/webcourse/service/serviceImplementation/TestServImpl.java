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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import webcourses.webcourse.entity.*;
import webcourses.webcourse.entity.dto.UserResult;
import webcourses.webcourse.repos.TestRepo;
import webcourses.webcourse.service.*;
import webcourses.webcourse.util.ControllerUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class TestServImpl implements TestServ {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestServImpl.class);
    //todo: добавить логгер
    private static final String OPTION = "option";
    private static final String TEXT = "text";

    private final TestRepo testRepo;
    private final UserServ userServ;
    private final QuestionServ questionServ;
    private final AnswerServ answerServ;
    private final AttemptServ attemptServ;
    private final ResultServ resultServ;

    @Autowired
    public TestServImpl(TestRepo testRepo,
                        UserServ userServ,
                        QuestionServ questionServ,
                        AnswerServ answerServ,
                        AttemptServ attemptServ,
                        ResultServ resultServ) {
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
    public String createTest(Course course, Lesson lesson, Model model) {
        model.addAttribute("course", course);
        model.addAttribute("lesson", lesson);

        return "course/lesson/test/create";
    }

    @Override
    public String createTest(Course course, Lesson lesson, String name, String date) {
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
        model.addAttribute("isCreator", userServ.isCreator(course));

        return "course/lesson/test/homePage";
    }

    @Override
    public String createQuestion(Course course, Lesson lesson, Test test, Model model) {
        if (userServ.isCreator(course)) {
            model.addAttribute("course", course);
            model.addAttribute("lesson", lesson);
            model.addAttribute("test", test);
        }

        return "course/lesson/test/createQuestion";
    }

    @Override
    public String createQuestion(HttpServletRequest request,
                                 Course course,
                                 Lesson lesson,
                                 Test test,
                                 String text,
                                 String maxValue,
                                 String type,
                                 String count) {
        if (userServ.isCreator(course)) {
            Question question = new Question();
            question.setText(text);
            question.setMaxVal((long) Float.parseFloat(maxValue));
            question.setQType(type);
            question.setTest(test);
            questionServ.save(question);

            switch (type) {
                case TEXT:
                    fillTextAnswer(request, maxValue, type, question, new Answer());
                    break;
                case OPTION:
                    fillOptionAnswer(count, request, maxValue, type, question, true);
                    break;
                default:
                    break;
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
            question.setQType(type);
            question.setTest(test);
            questionServ.save(question);

            switch (type) {
                case TEXT:
                    fillTextAnswer(request, maxValue, type, question, answerServ.getAllAnswers(question).get(0));
                    break;
                case OPTION:
                    String count = request.getParameter("count");
                    fillOptionAnswer(count, request, maxValue, type, question, false);
                    break;
                default:
                    break;
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
        User currUser = userServ.getCurrUser();
        int attemptCount = attemptServ.getTestAttempt(currUser, test);
        Attempt attempt = attemptServ.getAttempt(currUser, test);

        if (attempt == null) {
            Attempt newAttempt = new Attempt();
            newAttempt.setTest(test);
            newAttempt.setUser(currUser);
            attemptCount = 1;
            newAttempt.setCount((long) attemptCount);
            attemptServ.save(newAttempt);
        } else {
            attemptCount += 1;
            attempt.setCount((long) attemptCount);
            attemptServ.save(attempt);
        }

        int newAttemptCount = attemptCount;

        for (Question question :
                questionServ.getAllQuestions(test)) {
            for (Answer answer :
                    question.getAnswers()) {

                String answerText = request.getParameter(answer.getId().toString());

                switch (answer.getAType()) {
                    case TEXT:
                        checkTextAnswer(answer, currUser, answerText, newAttemptCount);
                        break;
                    case OPTION:
                        checkOptionAnswer(answer, currUser, answerText, newAttemptCount);
                        break;
                    default:
                        fillResult(currUser, answer, 0, newAttemptCount);
                        break;
                }
            }
        }

        return "redirect:/courses/" + course.getId() + "/lesson/" + lesson.getId() + "/test/" + test.getId() + "/result/" + newAttemptCount;
    }

    @Override
    public String resultForTeacher(Course course, Lesson lesson, Test test, Model model) {
        Set<User> allUsers = course.getUsers();

        List<UserResult> allUserResult = new ArrayList<>();
        int maxPossibleResult = calcMaxPossibleResult(test);

        for (User user :
                allUsers) {
            UserResult userResult = new UserResult();
            int attemptCount = attemptServ.getTestAttempt(user, test);
            int bestUserResult = calcMaxUserResult(test, user);

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

        double avgOfResult = (double) c / countOfUser;

        model.addAttribute("course", course);
        model.addAttribute("lesson", lesson);
        model.addAttribute("test", test);
        model.addAttribute("userResult", allUserResult);
        model.addAttribute("maxPossibleResult", maxPossibleResult);
        model.addAttribute("avgOfResult", avgOfResult);
        model.addAttribute("countOfUser", countOfUser);

        return "course/lesson/test/resultForTeacher";
    }

    @Override
    public String result(Course course, Lesson lesson, Test test, int attempt, Model model) {
        commonResult(course, lesson, test, userServ.getCurrUser(), attempt, model);

        return "course/lesson/test/result";
    }

    @Override
    public String userResultForTeacher(Course course, Lesson lesson, Test test, User user, int attempt, Model model) {
        commonResult(course, lesson, test, user, attempt, model);

        model.addAttribute("user", user);

        return "course/lesson/test/userResultForTeacher";
    }

    private void commonResult(Course course, Lesson lesson, Test test, User user, int attempt, Model model) {
        int attemptCount = attemptServ.getTestAttempt(user, test);
        List<Integer> aCount = new LinkedList<>();

        for (int i = 0; i < attemptCount; i++) {
            aCount.add(i);
        }

        model.addAttribute("course", course);
        model.addAttribute("lesson", lesson);
        model.addAttribute("test", test);
        model.addAttribute("maxResult", calcMaxPossibleResult(test));
        model.addAttribute("attemptCount", aCount);
        model.addAttribute("currentAttempt", attempt);
        model.addAttribute("userResult", calcUserResult(test, user, attempt));
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

    private void fillTextAnswer(HttpServletRequest request, String maxValue, String type, Question question, Answer answer) {
        String answerText = request.getParameter("answerText");

        fillAnswer(answer, answerText.toLowerCase(Locale.ROOT), Long.parseLong(maxValue), type, question);
    }

    private void fillOptionAnswer(String count, HttpServletRequest request, String maxValue, String type, Question question, boolean create) {
        int countTmp = Integer.parseInt(count);
        int correctAnswerCount = 0;

        for (int i = 1; i < countTmp + 1; i++) {
            String checkBox = request.getParameter("cb" + i);
            if (!(checkBox == null)) {
                correctAnswerCount = correctAnswerCount + 1;
            }
        }

        for (int i = 1; i < countTmp + 1; i++) {
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
            Answer answer;
            if (create) {
                answer = new Answer();
            } else {
                answer = answerServ.getAllAnswers(question).get(i - 1);
            }

            fillAnswer(answer, answerText, answerValue, type, question);
        }
    }

    private void checkOptionAnswer(Answer answer, User user, String answerText, long attemptCount) {
        long answerVal;
        if (answerText != null && answerText.equals("on")) {
            answerVal = answer.getVal();
        } else {
            answerVal = 0;
        }
        fillResult(user, answer, answerVal, attemptCount);
    }

    private void checkTextAnswer(Answer answer, User user, String answerText, long attemptCount) {
        long answerVal = 0;
        if (answer.getText().equals(answerText)) {
            answerVal = answer.getVal();
        }

        fillResult(user, answer, answerVal, attemptCount);
    }

    private void fillResult(User user, Answer answer, long resultValue, long attemptCount) {
        Result res = new Result();
        res.setResultValue(resultValue);
        res.setAttempt(attemptCount);
        res.setUser(user);
        res.setAnswer(answer);
        resultServ.save(res);
        LOGGER.info("User result was successful save.");
    }

    private void fillAnswer(Answer answer, String text, long val, String type, Question question) {
        answer.setText(text);
        answer.setVal(val);
        answer.setAType(type);
        answer.setQuestion(question);
        answerServ.save(answer);
        LOGGER.info("Question answer was successful save.");
    }
}
