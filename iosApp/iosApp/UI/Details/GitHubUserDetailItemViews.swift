//
//  GitHubUserDetailItemViews.swift
//  iosApp
//
//  Created by Imre Kaszab on 2023. 06. 19..
//  Copyright © 2023. orgName. All rights reserved.
//

import shared
import SwiftUI

struct GitHubUserDetailItemViews: View {
    var userDetails: GitHubUser

    var body: some View {
        VStack {
            GitHubUserDetailItemView(label: MR.strings().details_view_followers.localize(),
                                     value: String(userDetails.followers))
            GitHubUserDetailItemView(
                label: MR.strings().details_view_following.localize(),
                value: String(userDetails.following)
            )
            GitHubUserDetailItemView(
                label: MR.strings().details_view_public_repos.localize(),
                value: String(userDetails.publicRepos)
            )
            GitHubUserDetailItemView(label: MR.strings().details_view_company.localize(),
                                     value: userDetails.company)
            GitHubUserDetailItemView(label: MR.strings().details_view_location.localize(),
                                     value: userDetails.location)
            GitHubUserDetailItemView(label: MR.strings().details_view_email.localize(),
                                     value: userDetails.email)
            GitHubUserDetailItemView(label: MR.strings().details_view_blog.localize(),
                                     value: userDetails.blog)
            GitHubUserDetailItemView(label: MR.strings().details_view_twitter.localize(),
                                     value: userDetails.twitterUsername)
        }
    }
}
