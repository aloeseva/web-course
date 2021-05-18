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
import webcourses.webcourse.entity.Attempt;
import webcourses.webcourse.entity.Test;
import webcourses.webcourse.entity.User;
import webcourses.webcourse.repos.AttemptRepo;
import webcourses.webcourse.service.AttemptServ;

@Service
public class AttemptServImpl implements AttemptServ {
    private final AttemptRepo attemptRepo;

    @Autowired
    public AttemptServImpl(AttemptRepo attemptRepo) {
        this.attemptRepo = attemptRepo;
    }

    @Override
    public Integer getTestAttempt(User user, Test test) {
        long attempts = 0;
        Attempt attempt = attemptRepo.findByUserAndTest(user, test);
        if (attempt != null) {
            attempts = attempt.getCount();
        }
        return Math.toIntExact(attempts);
    }

    @Override
    public Attempt getAttempt(User user, Test test) {
        return attemptRepo.findByUserAndTest(user, test);
    }

    @Override
    public void save(Attempt attempt) {
        attemptRepo.save(attempt);
    }
}
