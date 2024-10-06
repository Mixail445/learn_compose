package com.example.learn_compose.ui.navigation

import com.example.learn_compose.R

/**
 * This class described screen navigation objects
 * @param screenName - deeplink screen representation
 * @param titleResourceId - resource to name tabbar or appbar navigation title
 */
sealed class Screen(
    val screenName: String,
    val titleResourceId: Int,
) {
    data object Bottom : Screen("bottom", -1)

    data object Server : Screen("server", -1)

    data object Number : Screen("number", -1)

    data object Code : Screen("code", -1)

    data object Profile : Screen("profile", -1)

    data object Meeting : Screen("meeting", R.string.meeting)

    data object MeetingAll : Screen("meeting_all", R.string.meeting_all)

    data object MeetingActive : Screen("meeting_active", R.string.meeting_active)

    data object DetailMeeting : Screen("detail_meeting", R.string.meeting_detail)

    data object Communities : Screen("communities", R.string.Communities)

    data object More : Screen("more", R.string.more)

    data object MoreProfile : Screen("more_profile", -1)

    data object MoreMeetings : Screen("more_meetings", -1)
}
