package com.thousand.bosch.views.auth.di

import com.thousand.bosch.views.auth.presenters.login.LoginPresenter
import com.thousand.bosch.views.auth.presenters.registration.first.RegistrationPresenter
import com.thousand.bosch.views.auth.presenters.registration.second.RegistrationFragmentSecondPresenter
import com.thousand.bosch.views.auth.presenters.registration.third.RegThirdPresenter
import com.thousand.bosch.views.auth.presenters.restore.first.RestoreFragmentFirstPresenter
import com.thousand.bosch.views.auth.presenters.restore.second.RestoreSecondPresenter
import com.thousand.bosch.views.auth.presenters.restore.third.RestoreThirdPresenter
import com.thousand.bosch.views.main.presentation.activity.MainActivityPresenter
import com.thousand.bosch.views.main.presentation.game.category.first.CategorySelectPresenter
import com.thousand.bosch.views.main.presentation.game.category.second.CategoryConfirmPresenter
import com.thousand.bosch.views.main.presentation.game.results.friend.GameFriendPresenter
import com.thousand.bosch.views.main.presentation.game.round.friend.ResultsFriendPresenter
import com.thousand.bosch.views.main.presentation.game.results.main.country.CountryListPresenter
import com.thousand.bosch.views.main.presentation.game.results.main.friends.FriendsTopListPresenter
import com.thousand.bosch.views.main.presentation.game.results.main.top.TopListPresenter
import com.thousand.bosch.views.main.presentation.game.round.start.StartRoundPresenter
import com.thousand.bosch.views.main.presentation.game.start.NewGamePresenter
import com.thousand.bosch.views.main.presentation.help.HelpPresenter
import com.thousand.bosch.views.main.presentation.help.suggest_question.SuggestQuestionPresenter
import com.thousand.bosch.views.main.presentation.profile.black_list.BlackListPresenter
import com.thousand.bosch.views.main.presentation.profile.block_user.BlockUserPresenter
import com.thousand.bosch.views.main.presentation.profile.details.ProfileDetailsPresenter
import com.thousand.bosch.views.main.presentation.profile.edit.ProfileEditPresenter
import com.thousand.bosch.views.main.presentation.profile.friends.details.FriendsDetailsPresenter
import com.thousand.bosch.views.main.presentation.profile.friends.list.FriendsListPresenter
import com.thousand.bosch.views.main.presentation.profile.main.ProfilePresenter
import com.thousand.bosch.views.main.presentation.profile.settings.SettingsPresenter
import com.thousand.bosch.views.main.presentation.stats.department.post.PostDepartmentPresenter
import com.thousand.bosch.views.main.presentation.stats.department.pre.PreDepartmentPresenter
import com.thousand.bosch.views.main.presentation.stats.student.pre.PreStudentPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authModule = module {

    scope(named(AuthScope.MAIN_ACTIVITY_SCOPE)) {
        scoped { MainActivityPresenter() }
    }

    scope(named(AuthScope.LOGIN_SCOPE)) {
        scoped { LoginPresenter(get()) }
    }

    scope(named(AuthScope.REGISTER_SCOPE)) {
        scoped { RegistrationPresenter(get()) }
    }

    scope(named(AuthScope.REGISTER_SCOPE)) {
        scoped { RegistrationPresenter(get()) }
    }

    scope(named(AuthScope.CONFIRM_REG_SCOPE)) {
        scoped { RegistrationFragmentSecondPresenter(get()) }
    }

    scope(named(AuthScope.CREATE_USER_SCOPE)) {
        scoped { RegThirdPresenter(get()) }
    }

    scope(named(AuthScope.RESET_REQUEST_SCOPE)) {
        scoped { RestoreFragmentFirstPresenter(get()) }
    }

    scope(named(AuthScope.RESET_CONFIRM_SCOPE)) {
        scoped { RestoreSecondPresenter(get()) }
    }

    scope(named(AuthScope.RESET_NEW_PASSWORD)) {
        scoped { RestoreThirdPresenter(get()) }
    }

    scope(named(AuthScope.SEARCH_USERS)) {
        scoped { FriendsListPresenter(get()) }
    }

    scope(named(AuthScope.PROFILE_SCOPE)) {
        scoped { ProfilePresenter(get()) }
    }

    scope(named(AuthScope.SETTINGS_SCOPE)) {
        scoped { SettingsPresenter(get()) }
    }

    scope(named(AuthScope.PROFILE_DETAILS_SCOPE)) {
        scoped { ProfileDetailsPresenter(get()) }
    }

    scope(named(AuthScope.PROFILE_DETAILS_SCOPE)) {
        scoped { ProfileDetailsPresenter(get()) }
    }

    scope(named(AuthScope.FRIEND_DETAILS_SCOPE)) {
        scoped { FriendsDetailsPresenter(get()) }
    }

    scope(named(AuthScope.BLOCK_USER_SCOPE)) {
        scoped { BlockUserPresenter(get()) }
    }

    scope(named(AuthScope.BLACKLIST_SCOPE)) {
        scoped { BlackListPresenter(get()) }
    }

    scope(named(AuthScope.PROFILE_EDIT_SCOPE)) {
        scoped { ProfileEditPresenter(get()) }
    }

    scope(named(AuthScope.NEW_GAME_SCOPE)) {
        scoped { NewGamePresenter(get()) }
    }

    scope(named(AuthScope.RESULTS_FRIEND)) {
        scoped {
            ResultsFriendPresenter(
                get()
            )
        }
    }

    scope(named(AuthScope.CATEGORIES_CONFIRM)) {
        scoped { CategoryConfirmPresenter(get()) }
    }

    scope(named(AuthScope.START_ROUND_SCOPE)) {
        scoped { StartRoundPresenter(get()) }
    }

    scope(named(AuthScope.CATEGORIES_SELECT)) {
        scoped {
            CategorySelectPresenter(
                get()
            )
        }
    }

    scope(named(AuthScope.COUNTRY_LIST_SCOPE)) {
        scoped { CountryListPresenter(get()) }
    }

    scope(named(AuthScope.FRIENDS_TOP_LIST_SCOPE)) {
        scoped { FriendsTopListPresenter(get()) }
    }

    scope(named(AuthScope.TOP_LIST_SCOPE)) {
        scoped { TopListPresenter(get()) }
    }

    scope(named(AuthScope.GAME_FRIEND_SCOPE)) {
        scoped { GameFriendPresenter(get()) }
    }

    scope(named(AuthScope.SUGGEST_QUESTION)) {
        scoped { SuggestQuestionPresenter(get()) }
    }

    scope(named(AuthScope.HELP_SCOPE)) {
        scoped { HelpPresenter(get()) }
    }

    scope(named(AuthScope.PRE_DEP_SCOPE)) {
        scoped { PreDepartmentPresenter(get()) }
    }

    scope(named(AuthScope.POST_DEP_SCOPE)) {
        scoped { PostDepartmentPresenter(get()) }
    }

    scope(named(AuthScope.PRE_STUDENT_SCOPE)) {
        scoped { PreStudentPresenter(get()) }
    }

}

object AuthScope {
    const val MAIN_ACTIVITY_SCOPE = "MainActivityScope"
    const val COUNTRY_LIST_SCOPE = "CountryListScope"
    const val FRIENDS_TOP_LIST_SCOPE = "FriendsTopListScope"
    const val TOP_LIST_SCOPE = "TopListScope"
    const val LOGIN_SCOPE = "LoginScope"
    const val REGISTER_SCOPE = "RegisterScope"
    const val PROFILE_SCOPE = "ProfileScope"
    const val PROFILE_DETAILS_SCOPE = "ProfileDetailsScope"
    const val FRIEND_DETAILS_SCOPE = "FriendDetailsScope"
    const val CONFIRM_REG_SCOPE = "ConfirmScope"
    const val CREATE_USER_SCOPE = "CreateUserScope"
    const val RESET_REQUEST_SCOPE = "ResetRequestScope"
    const val RESET_CONFIRM_SCOPE = "ResetConfirmScope"
    const val RESET_NEW_PASSWORD = "ResetNewPassword"
    const val SEARCH_USERS = "SearchUsersScope"
    const val SETTINGS_SCOPE = "SettingsScope"
    const val BLOCK_USER_SCOPE = "BlockUserScope"
    const val BLACKLIST_SCOPE = "BlackListScope"
    const val PROFILE_EDIT_SCOPE = "ProfileEditScope"
    const val NEW_GAME_SCOPE = "NewGameScope"
    const val RESULTS_FRIEND = "ResultsFriendsScope"
    const val CATEGORIES_SELECT = "CategoriesSelect"
    const val START_ROUND_SCOPE = "StartRoundScope"
    const val CATEGORIES_CONFIRM = "CategoriesConfirm"
    const val GAME_FRIEND_SCOPE = "GameFriendScope"
    const val SUGGEST_QUESTION = "SuggestQuestion"
    const val HELP_SCOPE = "HelpScope"
    const val PRE_DEP_SCOPE = "PreDepScope"
    const val POST_DEP_SCOPE = "PostDepScope"
    const val PRE_STUDENT_SCOPE = "PreStudentScope"
}