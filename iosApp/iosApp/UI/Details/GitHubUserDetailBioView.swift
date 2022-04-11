//
//  GitHubUserDetailBioView.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 11..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI

struct GitHubUserDetailBioView: View {
    var bio: String?

    var body: some View {
        if let bio = bio, !bio.isEmpty {
            Text(bio)
                .padding(8)
        }
    }
}
