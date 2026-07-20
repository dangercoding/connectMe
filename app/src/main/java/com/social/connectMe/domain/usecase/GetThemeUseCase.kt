package com.social.connectMe.domain.usecase

import com.social.connectMe.domain.repository.ThemeRepository
import com.social.connectMe.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    operator fun invoke(): Flow<ThemeMode> = repository.themeMode
}
