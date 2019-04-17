/*
 *
 *   Copyright (C) 2019 Truongpx Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.truongpx.ckcportfolio.features.basecrypto.domainlayer.usecases

import com.truongpx.ckcportfolio.core.exception.Failure
import com.truongpx.ckcportfolio.core.functional.Either
import com.truongpx.ckcportfolio.core.interactor.UseCase
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.repository.CryptoRepository
import javax.inject.Inject

class DeleteHoldingItemByPortfolioIdUC @Inject constructor(val cryptoRepository: CryptoRepository) :
    UseCase<UseCase.None, Int>() {
    override suspend fun run(params: Int): Either<Failure, None> {
        return cryptoRepository.deletePortfolioDataById(params)
    }
}