package com.social.connectMe.domain.usecase

import com.social.connectMe.domain.repository.ThemeRepository
import com.social.connectMe.ui.theme.ThemeMode
import javax.inject.Inject

class SaveThemeUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    suspend operator fun invoke(mode: ThemeMode) = repository.saveTheme(mode)
}
