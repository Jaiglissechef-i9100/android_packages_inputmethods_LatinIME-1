/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#ifndef LATINIME_ERROR_TYPE_UTILS_H
#define LATINIME_ERROR_TYPE_UTILS_H

#include <cstdint>

#include "defines.h"

namespace latinime {

class ErrorTypeUtils {
 public:
    // ErrorType is mainly decided by CorrectionType but it is also depending on if
    // the correction has really been performed or not.
    typedef uint32_t ErrorType;

    static const ErrorType NOT_AN_ERROR;
    static const ErrorType MATCH_WITH_CASE_ERROR;
    static const ErrorType MATCH_WITH_ACCENT_ERROR;
    static const ErrorType MATCH_WITH_DIGRAPH;
    // Treat error as an intentional omission when the CorrectionType is omission and the node can
    // be intentional omission.
    static const ErrorType INTENTIONAL_OMISSION;
    // Substitution, omission and transposition
    static const ErrorType EDIT_CORRECTION;
    // Proximity error
    static const ErrorType PROXIMITY_CORRECTION;
    // Completion
    static const ErrorType COMPLETION;
    // New word
    // TODO: Remove.
    // A new word error should be an edit correction error or a proximity correction error.
    static const ErrorType NEW_WORD;

    static bool isExactMatch(const ErrorType containedErrorTypes) {
        return (containedErrorTypes & ~ERRORS_TREATED_AS_AN_EXACT_MATCH) == 0;
    }

    static bool isEditCorrectionError(const ErrorType errorType) {
        return (errorType & EDIT_CORRECTION) != 0;
    }

    static bool isProximityCorrectionError(const ErrorType errorType) {
        return (errorType & PROXIMITY_CORRECTION) != 0;
    }

    static bool isCompletion(const ErrorType errorType) {
        return (errorType & COMPLETION) != 0;
    }

 private:
    DISALLOW_IMPLICIT_CONSTRUCTORS(ErrorTypeUtils);

    static const ErrorType ERRORS_TREATED_AS_AN_EXACT_MATCH;
};
} // namespace latinime
#endif // LATINIME_ERROR_TYPE_UTILS_H