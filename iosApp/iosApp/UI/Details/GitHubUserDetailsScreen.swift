//
//  GitHubUserDetailsScreen.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 10..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI
import shared

struct GitHubUserDetailsScreen: View {
    var item: GitHubUser

    var body: some View {
        ScrollView {
            // TODO
        }
        .navigationBarTitle(Text(item.login))
    }
}
