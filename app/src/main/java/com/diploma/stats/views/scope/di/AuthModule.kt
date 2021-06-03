package com.diploma.stats.views.scope.di

import com.diploma.stats.views.main.presentation.activity.MainActivityPresenter
import com.diploma.stats.views.main.presentation.stats.department.course.StatsByCoursePresenter
import com.diploma.stats.views.main.presentation.stats.department.post.PostDepartmentPresenter
import com.diploma.stats.views.main.presentation.stats.department.pre.PreDepartmentPresenter
import com.diploma.stats.views.main.presentation.stats.student.city.StatsByCityPresenter
import com.diploma.stats.views.main.presentation.stats.student.pre.PreStudentPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authModule = module {

    scope(named(AuthScope.MAIN_ACTIVITY_SCOPE)) {
        scoped { MainActivityPresenter() }
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

    scope(named(AuthScope.CITY_STATS_SCOPE)) {
        scoped { StatsByCityPresenter(get()) }
    }

    scope(named(AuthScope.COURSE_STATS_SCOPE)) {
        scoped { StatsByCoursePresenter(get()) }
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
    const val CITY_STATS_SCOPE = "CITY_STATS_SCOPE"
    const val COURSE_STATS_SCOPE = "COURSE_STATS_SCOPE"
}