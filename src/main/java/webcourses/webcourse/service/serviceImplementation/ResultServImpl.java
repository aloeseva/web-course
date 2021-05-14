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
import webcourses.webcourse.entity.Answer;
import webcourses.webcourse.entity.Result;
import webcourses.webcourse.entity.User;
import webcourses.webcourse.repos.ResultRepo;
import webcourses.webcourse.service.ResultServ;

@Service
public class ResultServImpl implements ResultServ {
    private final ResultRepo resultRepo;

    @Autowired
    public ResultServImpl(ResultRepo resultRepo) {
        this.resultRepo = resultRepo;
    }

    @Override
    public void delete(Result result) {
        resultRepo.delete(result);
    }

    @Override
    public int answerResult(User user, Answer answer, int attempt) {
        long result = 0;
        if (resultRepo.findAllByAnswerAndUserAndAttempt(answer, user, attempt) != null) {
            result = resultRepo.findAllByAnswerAndUserAndAttempt(answer, user, attempt).getResultValue();
        }
        return Math.toIntExact(result);
    }

    @Override
    public void save(Result result) {
        resultRepo.save(result);
    }


}