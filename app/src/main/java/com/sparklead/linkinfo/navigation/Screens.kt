package com.sparklead.linkinfo.navigation

import com.sparklead.linkinfo.R

sealed class Screens(
    val route: String,
    val title: String = "",
    val icon: Int = 0
) {
    data object DashboardScreen :
        Screens(route = "dashboard_screen", title = "Links", icon = R.drawable.ic_link)

    data object CoursesScreen :
        Screens(route = "courses_screen", title = "Courses", icon = R.drawable.ic_courses)

    data object AddLinkScreen :
        Screens(route = "add_link_screen", title = "Add", R.drawable.ic_plus)

    data object CampaignsScreen :
        Screens(route = "campaigns_screen", title = "Campaigns", R.drawable.ic_campaigns)

    data object ProfileScreen :
        Screens(route = "profile_screen", title = "Profile", R.drawable.ic_profile)
}